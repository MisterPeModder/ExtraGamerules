package com.misterpemodder.extragamerules.mixin.entity;

import java.util.function.Predicate;
import com.misterpemodder.extragamerules.ExtraGameRuleValues;
import com.misterpemodder.extragamerules.hook.FilterableDamageSource;
import com.misterpemodder.extragamerules.hook.WorldHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

@Mixin(DamageSource.class)
public final class DamageSourceMixin implements FilterableDamageSource {
  private Predicate<ExtraGameRuleValues> isVulnerablePredicate = null;

  @Shadow
  public boolean isFire() {
    return false;
  }

  @Override
  public boolean extragamerulesIsInvulnerable(Entity entity) {
    if (entity.world.isClient)
      return false;
    if (isVulnerablePredicate == null)
      return isFire() && !((WorldHook) entity.world).getEGValues().isFireDamageEnabled();
    return !isVulnerablePredicate.test(((WorldHook) entity.world).getEGValues());
  }

  private static void setVulnerablePredicate(DamageSource source,
      Predicate<ExtraGameRuleValues> predicate) {
    ((DamageSourceMixin) (Object) source).isVulnerablePredicate = predicate;
  }

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    setVulnerablePredicate(DamageSource.DROWN, ExtraGameRuleValues::isDrowningDamageEnabled);
    setVulnerablePredicate(DamageSource.FALL, ExtraGameRuleValues::isFallDamageEnabled);
  }
}
