package com.misterpemodder.extragamerules.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public final class PhantomSpawnerMixin {
  @Redirect(at = @At(value = "INVOKE", target = "net/minecraft/util/math/MathHelper.clamp(III)I",
      ordinal = 0), method = "spawn(Lnet/minecraft/world/World;ZZ)I")
  private int changeInsomniaTime(int value, int min, int max, World world, boolean boolean_1,
      boolean boolean_2) {
    if (!world.isClient && ((ServerWorldHook) world).getEGValues().doInsomnia)
      return 1;
    return MathHelper.clamp(value, min, max);
  }
}
