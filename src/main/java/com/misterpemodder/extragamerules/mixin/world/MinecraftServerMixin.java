package com.misterpemodder.extragamerules.mixin.world;

import java.util.Map;
import com.misterpemodder.extragamerules.ExtraGameRuleValues;
import com.misterpemodder.extragamerules.hook.MinecraftServerHook;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerHook {
  @Shadow
  private Map<DimensionType, ServerWorld> worlds;
  private ExtraGameRuleValues egValues;

  @Inject(at = @At("RETURN"), method = "<init>")
  private void onConstruct(CallbackInfo ci) {
    ExtraGameRuleValues.DUMMY.resetToDefault();
    this.egValues = new ExtraGameRuleValues((MinecraftServer) (Object) this);
  }

  @Inject(at = @At("RETURN"), method = "createWorlds")
  private void onCreateWorlds(WorldSaveHandler saveHandler, LevelProperties levelProperties,
      LevelInfo levelInfo, WorldGenerationProgressListener worldGenerationProgressListener,
      CallbackInfo ci) {
    for (ServerWorld world : this.worlds.values())
      ((ServerWorldHook) world).setEGValues(this.egValues);
  }

  @Override
  public ExtraGameRuleValues getEGValues() {
    return this.egValues;
  }
}
