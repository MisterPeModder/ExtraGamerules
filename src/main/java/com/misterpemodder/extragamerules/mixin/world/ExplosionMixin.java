package com.misterpemodder.extragamerules.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

@Mixin(Explosion.class)
public final class ExplosionMixin {
  @Shadow
  private World world;
  @Shadow
  private float power;

  @Inject(at = @At("RETURN"),
      method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;DDDFZZ)V")
  public void onConstruct(CallbackInfo ci) {
    this.power *= ((ServerWorldHook) world).getEGValues().explosionPowerModifer;
  }
}
