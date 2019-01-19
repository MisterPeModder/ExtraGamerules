package misterpemodder.extragamerules.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import misterpemodder.extragamerules.hook.MinecraftServerHook;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.PhantomSpawner;

@Mixin(PhantomSpawner.class)
public final class PhantomSpawnerMixin {
  @Redirect(at = @At(value = "INVOKE", target = "net/minecraft/util/math/MathHelper.clamp(III)I",
      ordinal = 0), method = "spawn(Lnet/minecraft/world/World;ZZ)I")
  private int changeInsomniaTime(int value, int min, int max, World world, boolean boolean_1,
      boolean boolean_2) {
    MinecraftServerHook server = ((MinecraftServerHook) world.getServer());
    if (server != null && !server.isInsomniaEnabled())
      return 1;
    return MathHelper.clamp(value, min, max);
  }
}
