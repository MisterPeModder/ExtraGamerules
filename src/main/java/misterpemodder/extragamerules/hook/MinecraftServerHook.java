package misterpemodder.extragamerules.hook;

import net.minecraft.server.MinecraftServer;

/**
 * Extended by {@link MinecraftServer}
 */
public interface MinecraftServerHook {
  int getLightningProbability();

  void setLightningProbability(int value);

  boolean getLightningSpawningFire();

  void setLightningSpawningFire(boolean value);

  float getLightningDamage();

  void setLightningDamage(float value);

  double getLightningRange();

  void setLightningRange(double value);

  double getHorseTrapSpawningChance();

  void setHorseTrapSpawingChance(double value);

  boolean isInsomniaEnabled();

  void setInsomniaEnabled(boolean value);

  boolean getTntExplodes();

  void setTntExplodes(boolean value);

  float getExplosionPowerModifier();

  void setExplosionPowerModifier(float value);

  boolean isPvpEnabled();

  void setPvpEnabled(boolean value);

  boolean isDrowningDamageEnabled();

  void setDrowningDamageEnabled(boolean value);

  boolean isFallDamageEnabled();

  void setFallDamageEnabled(boolean value);

  boolean isFireDamageEnabled();

  void setFireDamageEnabled(boolean value);
}
