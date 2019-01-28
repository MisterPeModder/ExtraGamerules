package misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.PrimedTNTEntity;
import net.minecraft.world.World;

@Mixin(PrimedTNTEntity.class)
public abstract class PrimedTNTEntityMixin extends Entity {
  public PrimedTNTEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Inject(at = @At("HEAD"), method = "explode()V", cancellable = true)
  private void onExplode(CallbackInfo ci) {
    if (!((WorldHook) ((Entity) (Object) this).world).getEGValues().tntExplodes)
      ci.cancel();
  }
}
