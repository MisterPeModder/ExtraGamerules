package com.misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.misterpemodder.extragamerules.hook.FilterableDamageSource;
import com.misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;

@Mixin(Entity.class)
public final class EntityMixin {
  @Shadow
  private int fireTimer;
  @Shadow
  public World world;

  @Inject(at = @At("HEAD"), method = "onStruckByLightning(Lnet/minecraft/entity/LightningEntity;)V",
      cancellable = true)
  private void AdjustFireTimer(CallbackInfo ci) {
    if (((WorldHook) world).getEGValues().lightningDamage == 0f)
      ci.cancel();
  }

  @ModifyArg(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
      ordinal = 0), method = "onStruckByLightning(Lnet/minecraft/entity/LightningEntity;)V",
      index = 1)
  private float AdjustLightningDamage(float original) {
    float damage = ((WorldHook) world).getEGValues().lightningDamage;
    if (damage == 0f)
      this.fireTimer = 0;
    return damage;
  }

  @Inject(at = @At("HEAD"),
      method = "isInvulnerableTo(Lnet/minecraft/entity/damage/DamageSource;)Z", cancellable = true)
  private void isInvulnerableTo(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
    if (((FilterableDamageSource) source).isInvulnerable((Entity) (Object) this))
      ci.setReturnValue(true);
  }
}
