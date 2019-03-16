package com.misterpemodder.extragamerules.mixin.entity;

import com.misterpemodder.extragamerules.ExtraGameRuleValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.entity.player.HungerManager;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {
  @ModifyArg(
      at = @At(value = "INVOKE",
          target = "Lnet/minecraft/entity/player/PlayerEntity;"
              + "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"),
      method = "Lnet/minecraft/entity/player/HungerManager;"
          + "update(Lnet/minecraft/entity/player/PlayerEntity;)V",
      index = 1)
  private float adjustHungerDamage(float original) {
    return ExtraGameRuleValues.get().hungerDamage;
  }

  @Inject(at = @At("HEAD"), method = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V",
      cancellable = true)
  private void blockExhaustion(CallbackInfo ci) {
    if (!ExtraGameRuleValues.get().doHunger)
      ci.cancel();
  }
}
