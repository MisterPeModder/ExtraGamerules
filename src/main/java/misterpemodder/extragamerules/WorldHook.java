package misterpemodder.extragamerules;

public interface WorldHook {
  BoundsControllingRandom getCustomRandom();

  void setLightningSpawningFire(boolean value);
}
