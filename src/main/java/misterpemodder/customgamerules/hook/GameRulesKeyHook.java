package misterpemodder.customgamerules.hook;

import net.minecraft.world.GameRules;

/**
 * Implemented by {@link GameRules.Key}
 */
public interface GameRulesKeyHook {
  /**
   * Retrieve the default value associated with this Key.
   * 
   * @return the (stringified) default value.
   */
  public String getDefaultValue();
}
