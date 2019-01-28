package misterpemodder.extragamerules.mixin.entity;

import java.util.function.Predicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.ExtraGameRuleValues;
import misterpemodder.extragamerules.hook.FilterableDamageSource;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

@Mixin(DamageSource.class)
public final class DamageSourceMixin implements FilterableDamageSource {
  private Predicate<ExtraGameRuleValues> isVulnerablePredicate = null;

  @Override
  public boolean isInvulnerable(Entity entity) {
    if (isVulnerablePredicate == null)
      return false;
    return !isVulnerablePredicate.test(((WorldHook) entity.world).getEGValues());
  }

  private static void setVulnerablePredicate(DamageSource source,
      Predicate<ExtraGameRuleValues> predicate) {
    ((DamageSourceMixin) (Object) source).isVulnerablePredicate = predicate;
  }

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    setVulnerablePredicate(DamageSource.IN_FIRE, ExtraGameRuleValues::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.ON_FIRE, ExtraGameRuleValues::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.LAVA, ExtraGameRuleValues::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.HOT_FLOOR, ExtraGameRuleValues::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.DROWN, ExtraGameRuleValues::isDrowningDamageEnabled);
    setVulnerablePredicate(DamageSource.FALL, ExtraGameRuleValues::isFallDamageEnabled);
  }
}
