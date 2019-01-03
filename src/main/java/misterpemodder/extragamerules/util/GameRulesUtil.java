package misterpemodder.extragamerules.util;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Key;
import net.minecraft.world.GameRules.Type;
import net.minecraft.world.GameRules.Value;

public final class GameRulesUtil {
  public static int getInt(Value value, int defaultValue) {
    if (value == null || value.getType() != GameRules.Type.INTEGER)
      return defaultValue;
    return value.getInteger();
  }

  public static boolean getBoolean(Value value, boolean defaultValue) {
    if (value == null || value.getType() != GameRules.Type.BOOLEAN)
      return defaultValue;
    return value.getBoolean();
  }

  public static String getString(Value value, String defaultValue) {
    if (value == null || value.getType() != GameRules.Type.STRING)
      return defaultValue;
    return value.getString();
  }

  public static float getFloat(Value value, float defaultValue) {
    if (value == null || value.getType() != GameRules.Type.STRING)
      return defaultValue;
    try {
      return Float.parseFloat(value.getString());
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static double getDouble(Value value, double defaultValue) {
    if (value == null || value.getType() != GameRules.Type.STRING)
      return defaultValue;
    try {
      return Double.parseDouble(value.getString());
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public static <T extends ServerWorld & WorldHook> void registerWorldHookGamerule(
      Map<String, Key> map, String name, Object defaultValue, Type type,
      BiConsumer<T, Value> handler) {
    registerWorldHookGamerule(map, name, defaultValue, type, handler, null);
  };

  /**
   * Registers a gamerule in the given map.
   * 
   * @param map          The registry
   * @param name         Name of the gamerule
   * @param defaultValue Its default value
   * @param type         Its type.
   * @param handler      Executed for each world when the gamerule is changed.
   * @param validator    returns false if the value is invalid and should be set to default.
   */
  @SuppressWarnings("unchecked")
  public static <T extends ServerWorld & WorldHook> void registerWorldHookGamerule(
      Map<String, Key> map, String name, Object defaultValue, Type type,
      BiConsumer<T, Value> handler, Predicate<Value> validator) {
    map.put(name, new Key(defaultValue.toString(), type, (server, value) -> {
      try {
        if (validator != null && !validator.test(value))
          value.set(defaultValue.toString(), server);
      } catch (NumberFormatException e) {
        value.set(defaultValue.toString(), server);
      }
      for (ServerWorld world : server.getWorlds()) {
        if (world instanceof WorldHook) {
          try {
            handler.accept((T) world, value);
          } catch (NumberFormatException e) {
          }
        }
      }
    }));
  }
}
