package com.misterpemodder.extragamerules.mixin.world;

import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.World;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public final class PhantomSpawnerMixin {
  @Inject(at = @At("HEAD"), method = "spawn(Lnet/minecraft/world/World;ZZ)I", cancellable = true)
  private void preventPhantomsSpawning(World world, boolean spawnMonsters, boolean spawnAnimals,
      CallbackInfoReturnable<Integer> ci) {
    if (!world.isClient && !((ServerWorldHook) world).getEGValues().doInsomnia)
      ci.setReturnValue(0);
  }
}
