package com.misterpemodder.extragamerules.hook;

import net.minecraft.entity.Entity;

/**
 * Implemented by {@link net.minecraft.entity.damage.DamageSource}
 */
public interface FilterableDamageSource {
  /**
   * Called by {@link Entity#isInvulnerableTo} to determine whether this DamageSource should damage
   * the passed entity.
   * 
   * @param entity The target entity.
   * @return true if the entity is invulnerable to this damage source, false otherwise.
   */
  public boolean extragamerulesIsInvulnerable(Entity entity);
}
