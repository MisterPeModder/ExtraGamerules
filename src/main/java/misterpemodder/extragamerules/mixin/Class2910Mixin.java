package misterpemodder.extragamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.class_2910;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

// Phantom spawner class mixin.
@Mixin(class_2910.class)
public final class Class2910Mixin {
  @Redirect(at = @At(value = "INVOKE", target = "net/minecraft/util/math/MathHelper.clamp(III)I",
      ordinal = 0), method = "spawn(Lnet/minecraft/world/World;ZZ)I")
  private int changeInsomniaTime(int value, int min, int max, World world, boolean boolean_1,
      boolean boolean_2) {
    if (world instanceof WorldHook && !((WorldHook) world).isInsomniaEnabled())
      return 1;
    return MathHelper.clamp(value, min, max);
  }
}
