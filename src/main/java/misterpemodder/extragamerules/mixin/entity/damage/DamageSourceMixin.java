package misterpemodder.extragamerules.mixin.entity.damage;

import java.util.function.Predicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.FilterableDamageSource;
import misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

@Mixin(DamageSource.class)
public final class DamageSourceMixin implements FilterableDamageSource {
  private Predicate<ServerWorldHook> isVulnerablePredicate = null;

  @Override
  public boolean isInvulnerable(Entity entity) {
    if (isVulnerablePredicate == null || !(entity.world instanceof ServerWorldHook))
      return false;
    return !isVulnerablePredicate.test((ServerWorldHook) entity.world);
  }

  private static void setVulnerablePredicate(DamageSource source,
      Predicate<ServerWorldHook> predicate) {
    ((DamageSourceMixin) (Object) source).isVulnerablePredicate = predicate;
  }

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    setVulnerablePredicate(DamageSource.IN_FIRE, ServerWorldHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.ON_FIRE, ServerWorldHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.LAVA, ServerWorldHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.HOT_FLOOR, ServerWorldHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.DROWN, ServerWorldHook::isDrowningDamageEnabled);
    setVulnerablePredicate(DamageSource.FALL, ServerWorldHook::isFallDamageEnabled);
  }
}
