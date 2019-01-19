package misterpemodder.extragamerules.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;
import misterpemodder.extragamerules.hook.MinecraftServerHook;
import net.minecraft.world.World;

@Mixin(World.class)
public class WorldMixin {
  @ModifyConstant(constant = @Constant(intValue = 100000, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V",
      slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=thunder"}, ordinal = 0),
          to = @At(value = "NEW", target = "Lnet/minecraft/entity/LightningEntity;", ordinal = 0)))
  private int modifyLightningSpawningChance(int original) {
    MinecraftServerHook server = (MinecraftServerHook) ((World) (Object) this).getServer();
    if (server != null) {
      int chance = server.getLightningProbability();
      return chance <= 0 ? Integer.MAX_VALUE : chance;
    }
    return original;
  }

  @ModifyConstant(constant = @Constant(doubleValue = 0.01, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V",
      slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=thunder"}, ordinal = 0),
          to = @At(value = "NEW", target = "Lnet/minecraft/entity/LightningEntity;", ordinal = 0)))
  private double modifyHorseSpawningChance(double original) {
    MinecraftServerHook server = (MinecraftServerHook) ((World) (Object) this).getServer();
    return server != null ? server.getHorseTrapSpawningChance() : original;
  }
}
