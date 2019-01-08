package misterpemodder.extragamerules.hook;

public interface WorldHook {
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
}
