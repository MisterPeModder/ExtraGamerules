package com.misterpemodder.extragamerules.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;
import com.misterpemodder.extragamerules.ExtraGameRuleValues;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.server.world.ServerWorld;

@Mixin(ServerWorld.class)
public class ServerWorldMixin implements ServerWorldHook {
  private ExtraGameRuleValues egValues = ExtraGameRuleValues.DUMMY;

  @ModifyConstant(constant = @Constant(intValue = 100000, ordinal = 0),
      method = "tickChunk(Lnet/minecraft/world/chunk/WorldChunk;I)V",
      slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=thunder"}, ordinal = 0),
          to = @At(value = "NEW", target = "Lnet/minecraft/entity/LightningEntity;", ordinal = 0)))
  private int modifyLightningSpawningChance(int original) {
    return egValues.lightningProbability <= 0 ? Integer.MAX_VALUE : egValues.lightningProbability;
  }

  @ModifyConstant(constant = @Constant(doubleValue = 0.01, ordinal = 0),
      method = "tickChunk(Lnet/minecraft/world/chunk/WorldChunk;I)V",
      slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=thunder"}, ordinal = 0),
          to = @At(value = "NEW", target = "Lnet/minecraft/entity/LightningEntity;", ordinal = 0)))
  private double modifyHorseSpawningChance(double original) {
    return egValues.horseTrapSpawningChance;
  }

  @Override
  public ExtraGameRuleValues getEGValues() {
    return this.egValues;
  }

  @Override
  public void setEGValues(ExtraGameRuleValues egValues) {
    this.egValues = egValues;
  }
}
