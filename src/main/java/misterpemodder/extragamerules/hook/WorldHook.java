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
}
