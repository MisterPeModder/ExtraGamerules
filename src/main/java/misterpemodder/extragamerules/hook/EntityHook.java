package misterpemodder.extragamerules.hook;

public final class EntityHook {
  private static float lightningDamage = 5.0f;

  public static float getLightningDamage() {
    return lightningDamage;
  }

  public static void setLightningDamage(float damage) {
    lightningDamage = damage < 0f ? 5.0f : damage;
  }
}
