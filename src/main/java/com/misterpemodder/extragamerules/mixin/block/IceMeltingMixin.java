package com.misterpemodder.extragamerules.mixin.block;

import java.util.Random;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.minecraft.block.BlockState;
import net.minecraft.block.IceBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(IceBlock.class)
public class IceMeltingMixin {
  @ModifyConstant(constant = @Constant(intValue = 11, ordinal = 0),
      method = "onScheduledTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V")
  private int modifyMeltingLightLevel(int level, BlockState state, World world, BlockPos pos,
      Random random) {
    return ((ServerWorldHook) world).getEGValues().snowMelts ? level : Integer.MAX_VALUE;
  }
}
