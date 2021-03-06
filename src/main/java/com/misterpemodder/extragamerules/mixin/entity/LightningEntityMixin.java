package com.misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.misterpemodder.extragamerules.DefaultValues;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BoundingBox;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {
  @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntities"
      + "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BoundingBox;Ljava/util/function/Predicate;)Ljava/util/List;",
      ordinal = 0), method = "update()V", index = 1)
  BoundingBox adjustLightningRange(BoundingBox original) {
    double range = ((ServerWorldHook) ((Entity) (Object) this).world).getEGValues().lightningRange
        - DefaultValues.LIGHTNING_RANGE;
    return new BoundingBox(original.minX + range, original.minY + range, original.minZ + range,
        original.maxX + range, original.maxY + range, original.maxZ + range);
  }

  @Inject(
      at = @At(value = "INVOKE_ASSIGN",
          target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"),
      method = "spawnFire(I)V", cancellable = true)
  private void onSpawnFire(CallbackInfo ci) {
    if (!((ServerWorldHook) ((Entity) (Object) this).world).getEGValues().lightningSpawnsFire)
      ci.cancel();
  }
}
