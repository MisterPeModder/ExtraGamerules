package com.misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(HungerManager.class)
public class HungerManagerMixin {
  @Shadow
  private int foodLevel;

  @Redirect(at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"),
      method = "update(Lnet/minecraft/entity/player/PlayerEntity;)V")
  private int changeFoodValue(int min, int max, PlayerEntity player) {
    if (((WorldHook) player.world).getEGValues().doHunger)
      return Math.max(min, max);
    return this.foodLevel;
  }
}
