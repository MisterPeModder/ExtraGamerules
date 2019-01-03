package misterpemodder.extragamerules.util;

import java.util.Random;

public class BoundsControllingRandom extends Random {
  private static final long serialVersionUID = -7716638030559253662L;
  private int bound;
  private final Random source;

  public BoundsControllingRandom(Random source) {
    this.source = source;
    this.bound = -1;
  }

  @Override
  public int nextInt(int bound) {
    if (this.bound == 0)
      return Integer.MAX_VALUE;
    else if (this.bound < 0)
      return source.nextInt(bound);
    return source.nextInt(this.bound);
  }

  public void setBound(int bound) {
    this.bound = bound < 0 ? -1 : bound;
  }

  public int getBound() {
    return this.bound;
  }
}
