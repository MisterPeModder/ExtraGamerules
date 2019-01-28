package misterpemodder.extragamerules.mixin.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.world.World;

@Mixin(PistonHandler.class)
public class PistonHandlerMixin {
  @Shadow
  private World world;

  @ModifyConstant(constant = @Constant(intValue = 12),
      method = "tryMove(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z")
  private int modifyPistonPushLimit(int original) {
    return ((WorldHook) this.world).getEGValues().pistonPushLimit;
  }
}
