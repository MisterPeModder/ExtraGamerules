package misterpemodder.extragamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import misterpemodder.extragamerules.hook.EntityHook;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public final class EntityMixin {
  @ModifyArg(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
      ordinal = 0), method = "onStruckByLightning(Lnet/minecraft/entity/LightningEntity;)V",
      index = 1)
  float AdjustLightningDamage(float original) {
    return EntityHook.getLightningDamage();
  }
}
