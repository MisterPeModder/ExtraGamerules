package misterpemodder.extragamerules.hook;

import misterpemodder.extragamerules.util.BoundsControllingRandom;

public interface WorldHook {
  BoundsControllingRandom getCustomRandom();

  void setLightningSpawningFire(boolean value);
}
