package com.misterpemodder.extragamerules.mixin.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.PrimedTntEntity;
import net.minecraft.world.World;

@Mixin(PrimedTntEntity.class)
public abstract class PrimedTntEntityMixin extends Entity {
  public PrimedTntEntityMixin(EntityType<?> type, World world) {
    super(type, world);
  }

  @Inject(at = @At("HEAD"), method = "explode()V", cancellable = true)
  private void onExplode(CallbackInfo ci) {
    if (!((ServerWorldHook) ((Entity) (Object) this).world).getEGValues().tntExplodes)
      ci.cancel();
  }
}
