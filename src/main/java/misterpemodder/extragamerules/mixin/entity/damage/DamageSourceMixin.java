package misterpemodder.extragamerules.mixin.entity.damage;

import java.util.function.Predicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.FilterableDamageSource;
import misterpemodder.extragamerules.hook.MinecraftServerHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;

@Mixin(DamageSource.class)
public final class DamageSourceMixin implements FilterableDamageSource {
  private Predicate<MinecraftServerHook> isVulnerablePredicate = null;

  @Override
  public boolean isInvulnerable(Entity entity) {
    MinecraftServerHook server = ((MinecraftServerHook) entity.world.getServer());
    if (isVulnerablePredicate == null || server == null)
      return false;
    return !isVulnerablePredicate.test(server);
  }

  private static void setVulnerablePredicate(DamageSource source,
      Predicate<MinecraftServerHook> predicate) {
    ((DamageSourceMixin) (Object) source).isVulnerablePredicate = predicate;
  }

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    setVulnerablePredicate(DamageSource.IN_FIRE, MinecraftServerHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.ON_FIRE, MinecraftServerHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.LAVA, MinecraftServerHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.HOT_FLOOR, MinecraftServerHook::isFireDamageEnabled);
    setVulnerablePredicate(DamageSource.DROWN, MinecraftServerHook::isDrowningDamageEnabled);
    setVulnerablePredicate(DamageSource.FALL, MinecraftServerHook::isFallDamageEnabled);
  }
}
