package com.misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.misterpemodder.extragamerules.DefaultValues;
import com.misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BoundingBox;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {
  @ModifyArg(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/world/World;getVisibleEntities"
          + "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BoundingBox;)Ljava/util/List;",
      ordinal = 0), method = "update()V", index = 1)
  BoundingBox AdjustLightningRange(BoundingBox original) {
    double range = ((WorldHook) ((Entity) (Object) this).world).getEGValues().lightningRange
        - DefaultValues.LIGHTNING_RANGE;
    return new BoundingBox(original.minX + range, original.minY + range, original.minZ + range,
        original.maxX + range, original.maxY + range, original.maxZ + range);
  }

  @Inject(
      at = @At(value = "INVOKE_ASSIGN",
          target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"),
      method = "method_6960(I)V", cancellable = true)
  private void onSpawnFire(CallbackInfo ci) {
    if (!((WorldHook) ((Entity) (Object) this).world).getEGValues().lightningSpawnsFire)
      ci.cancel();
  }
}
