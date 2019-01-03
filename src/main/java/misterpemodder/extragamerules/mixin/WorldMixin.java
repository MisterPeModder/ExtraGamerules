package misterpemodder.extragamerules.mixin;

import java.util.Random;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.WorldHook;
import misterpemodder.extragamerules.util.BoundsControllingRandom;
import misterpemodder.extragamerules.util.DefaultValues;
import misterpemodder.extragamerules.util.GameRulesUtil;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelProperties;

@Mixin(World.class)
public final class WorldMixin implements WorldHook {
  @Shadow
  public Random random;
  @Shadow
  protected LevelProperties properties;
  @Shadow
  public boolean isClient;

  private BoundsControllingRandom customRandom = null;
  private boolean lightningSpawnsFire = true;
  private float lightningDamage = 5.0f;

  @Override
  public int getLightningProbability() {
    return this.customRandom.getBound();
  }

  @Override
  public void setLightningProbability(int value) {
    this.customRandom.setBound(value);
  }

  @Override
  public boolean getLightningSpawningFire() {
    return this.lightningSpawnsFire;
  }

  @Override
  public void setLightningSpawningFire(boolean value) {
    this.lightningSpawnsFire = value;
  }

  @Override
  public float getLightningDamage() {
    return this.lightningDamage;
  }

  @Override
  public void setLightningDamage(float value) {
    this.lightningDamage = value < 0f ? DefaultValues.LIGHTNING_DAMAGE : value;
  }

  @Inject(at = @At("RETURN"), method = "<init>")
  public void onConstruct(CallbackInfo ci) {
    this.customRandom = new BoundsControllingRandom(this.random);
    GameRules rules = this.properties.getGameRules();
    if (!this.isClient) {
      this.customRandom.setBound(GameRulesUtil.getInt(rules.get("lightningProbability"),
          DefaultValues.LIGHTNING_PROBABILITY));
      this.lightningSpawnsFire =
          GameRulesUtil.getBoolean(rules.get("lightningFire"), DefaultValues.LIGHTNING_FIRE);
      this.lightningDamage =
          GameRulesUtil.getFloat(rules.get("lightningDamage"), DefaultValues.LIGHTNING_DAMAGE);
    }
  }

  @Redirect(
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;random:Ljava/util/Random;",
          opcode = Opcodes.GETFIELD, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V")
  private Random getFieldValue(World owner) {
    return this.customRandom;
  }
}
