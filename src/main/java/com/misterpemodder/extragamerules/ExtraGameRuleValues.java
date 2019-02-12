package com.misterpemodder.extragamerules;

import javax.annotation.Nullable;
import com.misterpemodder.extragamerules.hook.MinecraftServerHook;
import net.minecraft.server.MinecraftServer;

/**
 * Holds all values used by ExtraGameRules. Each world contains a reference the
 * {@link ExtraGameRuleValues} instance which is set to {@link ExtraGameRulesValues#DUMMY} when the
 * server is not present.
 */
public class ExtraGameRuleValues {
  public int lightningProbability;
  public boolean lightningSpawnsFire;
  public float lightningDamage;
  public double lightningRange;
  public double horseTrapSpawningChance;
  public boolean doInsomnia;
  public boolean tntExplodes;
  public float explosionPowerModifer;
  public boolean drowningDamage;
  public boolean fallDamage;
  public boolean fireDamage;
  public boolean snowMelts;
  public int pistonPushLimit;
  public boolean doHunger;
  public boolean instantRespawn;

  public static final ExtraGameRuleValues DUMMY = new ExtraGameRuleValues(null);

  private MinecraftServer server;

  public ExtraGameRuleValues(@Nullable MinecraftServer server) {
    this.server = server;
    resetToDefault();
  }

  public void resetToDefault() {
    this.lightningProbability = DefaultValues.LIGHTNING_PROBABILITY;
    this.lightningSpawnsFire = DefaultValues.LIGHTNING_FIRE;
    this.lightningDamage = DefaultValues.LIGHTNING_DAMAGE;
    this.lightningRange = DefaultValues.LIGHTNING_RANGE;
    this.horseTrapSpawningChance = DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE;
    this.doInsomnia = DefaultValues.DO_INSOMNIA;
    this.tntExplodes = DefaultValues.TNT_EXPLODES;
    this.explosionPowerModifer = DefaultValues.EXPLOSION_POWER_MODIFER;
    this.drowningDamage = DefaultValues.DROWNING_DAMAGE;
    this.fallDamage = DefaultValues.FALL_DAMAGE;
    this.fireDamage = DefaultValues.FIRE_DAMAGE;
    this.snowMelts = DefaultValues.SNOW_MELTS;
    this.pistonPushLimit = DefaultValues.PISTON_PUSH_LIMIT;
    this.doHunger = DefaultValues.DO_HUNGER;
    this.instantRespawn = DefaultValues.INSTANT_RESPAWN;
  }

  public static ExtraGameRuleValues get() {
    MinecraftServer server = ExtraGameRules.getProxy().getServerInstance();
    return server != null ? ((MinecraftServerHook) server).getEGValues() : DUMMY;
  }

  public static ExtraGameRuleValues get(@Nullable MinecraftServer server) {
    return server != null ? ((MinecraftServerHook) server).getEGValues() : DUMMY;
  }

  public int getLightningProbability() {
    return this.lightningProbability;
  }

  public void setLightningProbability(int value) {
    this.lightningProbability = (value == 0 ? Integer.MAX_VALUE : value);
  }

  public boolean getLightningSpawningFire() {
    return this.lightningSpawnsFire;
  }

  public void setLightningSpawningFire(boolean value) {
    this.lightningSpawnsFire = value;
  }

  public float getLightningDamage() {
    return this.lightningDamage;
  }

  public void setLightningDamage(float value) {
    this.lightningDamage = value < 0f ? DefaultValues.LIGHTNING_DAMAGE : value;
  }

  public double getLightningRange() {
    return this.lightningRange;
  }

  public void setLightningRange(double value) {
    this.lightningRange = value < 0.0 ? DefaultValues.LIGHTNING_RANGE : value;
  }

  public double getHorseTrapSpawningChance() {
    return this.horseTrapSpawningChance;
  }

  public void setHorseTrapSpawingChance(double value) {
    this.horseTrapSpawningChance =
        value < 0.0 ? DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE : value;
  }

  public boolean isInsomniaEnabled() {
    return this.doInsomnia;
  }

  public void setInsomniaEnabled(boolean value) {
    this.doInsomnia = value;
  }

  public boolean doesTntExplodes() {
    return this.tntExplodes;
  }

  public void setTntExplodes(boolean value) {
    this.tntExplodes = value;
  }

  public float getExplosionPowerModifier() {
    return this.explosionPowerModifer;
  }

  public void setExplosionPowerModifier(float value) {
    this.explosionPowerModifer = value;
  }

  public boolean isPvpEnabled() {
    if (this.server == null)
      return DefaultValues.PVP_ENABLED;
    return this.server.isPvpEnabled();
  }

  public void setPvpEnabled(boolean value) {
    if (this.server != null)
      this.server.setPvpEnabled(value);
  }

  public boolean isDrowningDamageEnabled() {
    return this.drowningDamage;
  }

  public void setDrowningDamageEnabled(boolean value) {
    this.drowningDamage = value;
  }

  public boolean isFallDamageEnabled() {
    return this.fallDamage;
  }

  public void setFallDamageEnabled(boolean value) {
    this.fallDamage = value;
  }

  public boolean isFireDamageEnabled() {
    return this.fireDamage;
  }

  public void setFireDamageEnabled(boolean value) {
    this.fireDamage = value;
  }

  public boolean doSnowMelt() {
    return this.snowMelts;
  }

  public void setSnowMelt(boolean value) {
    this.snowMelts = value;
  }

  public int getPistonPushLimit() {
    return this.pistonPushLimit;
  }

  public void setPistonPushLimit(int value) {
    this.pistonPushLimit = value;
  }

  public boolean isHungerEnabled() {
    return this.doHunger;
  }

  public void setHungerEnabled(boolean value) {
    this.doHunger = value;
  }

  public boolean doInstantRespawn() {
    return this.instantRespawn;
  }

  public void setInstantRespawn(boolean value) {
    this.instantRespawn = value;
  }
}
