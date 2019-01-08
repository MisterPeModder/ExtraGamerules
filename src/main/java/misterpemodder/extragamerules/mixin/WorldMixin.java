package misterpemodder.extragamerules.mixin;

import java.util.Random;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
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
  private boolean lightningSpawnsFire = DefaultValues.LIGHTNING_FIRE;
  private float lightningDamage = DefaultValues.LIGHTNING_DAMAGE;
  private double lightningRange = DefaultValues.LIGHTNING_RANGE;
  private double horseTrapSpawningChance = DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE;
  private boolean doInsomnia = DefaultValues.DO_INSOMNIA;
  private boolean tntExplodes = DefaultValues.TNT_EXPLODES;
  private float explosionPowerModifer = DefaultValues.EXPLOSION_POWER_MODIFER;

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

  @Override
  public double getLightningRange() {
    return this.lightningRange;
  }

  @Override
  public void setLightningRange(double value) {
    this.lightningRange = value < 0.0 ? DefaultValues.LIGHTNING_RANGE : value;
  }

  @Override
  public double getHorseTrapSpawningChance() {
    return this.horseTrapSpawningChance;
  }

  @Override
  public void setHorseTrapSpawingChance(double value) {
    this.horseTrapSpawningChance =
        value < 0.0 ? DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE : value;
  }

  @Override
  public boolean isInsomniaEnabled() {
    return this.doInsomnia;
  }

  @Override
  public void setInsomniaEnabled(boolean value) {
    this.doInsomnia = value;
  }

  @Override
  public boolean getTntExplodes() {
    return this.tntExplodes;
  }

  @Override
  public void setTntExplodes(boolean value) {
    this.tntExplodes = value;
  }

  @Override
  public float getExplosionPowerModifier() {
    return this.explosionPowerModifer;
  }

  @Override
  public void setExplosionPowerModifier(float value) {
    this.explosionPowerModifer = value;
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
      this.lightningRange =
          GameRulesUtil.getDouble(rules.get("lightningRange"), DefaultValues.LIGHTNING_RANGE);
      this.horseTrapSpawningChance =
          GameRulesUtil.getDouble(rules.get("lightningHorseSpawningModifier"),
              DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE);
      this.doInsomnia =
          GameRulesUtil.getBoolean(rules.get("doInsomnia"), DefaultValues.DO_INSOMNIA);
      this.tntExplodes =
          GameRulesUtil.getBoolean(rules.get("tntExplodes"), DefaultValues.TNT_EXPLODES);
      this.explosionPowerModifer = GameRulesUtil.getFloat(rules.get("explosionPowerModifier"),
          DefaultValues.EXPLOSION_POWER_MODIFER);
    }
  }

  @Redirect(
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;random:Ljava/util/Random;",
          opcode = Opcodes.GETFIELD, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V")
  private Random getFieldValue(World owner) {
    return this.customRandom;
  }

  @ModifyConstant(constant = @Constant(doubleValue = 0.01, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V")
  private double modifyHorseSpawningChance(double original) {
    return this.horseTrapSpawningChance;
  }
}
