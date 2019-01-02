package misterpemodder.extragamerules.mixin;

import java.util.Random;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.BoundsControllingRandom;
import misterpemodder.extragamerules.GameRulesUtil;
import misterpemodder.extragamerules.WorldHook;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelProperties;

@Mixin(World.class)
public final class WorldMixin implements WorldHook {
  @Shadow
  public Random random;
  @Shadow
  protected LevelProperties properties;

  protected BoundsControllingRandom customRandom = null;
  protected boolean lightningSpawnsFire = true;

  public BoundsControllingRandom getCustomRandom() {
    return this.customRandom;
  }

  @Override
  public void setLightningSpawningFire(boolean value) {
    this.lightningSpawnsFire = value;
  }

  @Inject(at = @At("RETURN"), method = "<init>")
  public void onConstruct(CallbackInfo ci) {
    this.customRandom = new BoundsControllingRandom(this.random);
    GameRules rules = this.properties.getGameRules();
    this.customRandom.setBound(GameRulesUtil.getInt(rules.get("lightningProbability"), 100000));
    this.lightningSpawnsFire = GameRulesUtil.getBoolean(rules.get("lightningFire"), true);
  }

  @Redirect(
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;random:Ljava/util/Random;",
          opcode = Opcodes.GETFIELD, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V")
  private Random getFieldValue(World owner) {
    return this.customRandom;
  }

  @ModifyArg(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/entity/LightningEntity;<init>(Lnet/minecraft/world/World;DDDZ)V",
      ordinal = 0), method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V")
  private boolean adjustLightningFireParam(boolean nofire) {
    return !this.lightningSpawnsFire;
  }
}
