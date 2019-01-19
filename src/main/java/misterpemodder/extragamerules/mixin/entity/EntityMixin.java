package misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import misterpemodder.extragamerules.DefaultValues;
import misterpemodder.extragamerules.hook.FilterableDamageSource;
import misterpemodder.extragamerules.hook.MinecraftServerHook;
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
    MinecraftServerHook server = ((MinecraftServerHook) this.world.getServer());
    if (server != null && server.getLightningDamage() == 0f)
      ci.cancel();
  }

  @ModifyArg(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
      ordinal = 0), method = "onStruckByLightning(Lnet/minecraft/entity/LightningEntity;)V",
      index = 1)
  private float AdjustLightningDamage(float original) {
    MinecraftServerHook server = ((MinecraftServerHook) this.world.getServer());
    float damage = server != null ? server.getLightningDamage() : DefaultValues.LIGHTNING_DAMAGE;
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
