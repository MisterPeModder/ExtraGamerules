package misterpemodder.extragamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.world.World;

@Mixin(LightningEntity.class)
public abstract class LightningEntityMixin {
  @Inject(at = @At("HEAD"), method = "method_6960(I)V", cancellable = true)
  private void onSpawnFire(CallbackInfo ci) {
    World world = ((Entity) (Object) this).world;
    if (world instanceof WorldHook && !((WorldHook) world).getLightningSpawningFire())
      ci.cancel();
  }
}
