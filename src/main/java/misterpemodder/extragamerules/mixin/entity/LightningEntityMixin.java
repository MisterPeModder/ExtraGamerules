package misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.ServerWorldHook;
import misterpemodder.extragamerules.util.DefaultValues;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.world.World;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {
  @ModifyArg(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/world/World;getVisibleEntities"
          + "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/BoundingBox;)Ljava/util/List;",
      ordinal = 0), method = "update()V", index = 1)
  BoundingBox AdjustLightningRange(BoundingBox original) {
    World world = ((Entity) (Object) this).world;
    if (world instanceof ServerWorldHook) {
      double range = ((ServerWorldHook) world).getLightningRange() - DefaultValues.LIGHTNING_RANGE;
      return new BoundingBox(original.minX + range, original.minY + range, original.minZ + range,
          original.maxX + range, original.maxY + range, original.maxZ + range);
    }
    return original;
  }

  @Inject(at = @At("HEAD"), method = "method_6960(I)V", cancellable = true)
  private void onSpawnFire(CallbackInfo ci) {
    World world = ((Entity) (Object) this).world;
    if (world instanceof ServerWorldHook && !((ServerWorldHook) world).getLightningSpawningFire())
      ci.cancel();
  }
}
