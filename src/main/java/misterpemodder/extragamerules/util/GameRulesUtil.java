package misterpemodder.extragamerules.util;

import java.util.Map;
import java.util.function.BiConsumer;
import misterpemodder.extragamerules.hook.WorldHook;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Key;
import net.minecraft.world.GameRules.Type;
import net.minecraft.world.GameRules.Value;
import net.minecraft.world.dimension.DimensionType;

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

  public static Float getFloat(Value value, float defaultValue) {
    if (value == null || value.getType() != GameRules.Type.STRING)
      return defaultValue;
    try {
      return Float.parseFloat(value.getString());
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  /**
   * Registers a gamerule in the given map.
   * 
   * @param map          The registry
   * @param name         Name of the gamerule
   * @param defaultValue Its default value
   * @param type         Its type
   * @param handler      Executed for each world when the gamerule is changed.
   */
  @SuppressWarnings("unchecked")
  public static <T extends ServerWorld & WorldHook> void registerWorldHookGamerule(
      Map<String, Key> map, String name, String defaultValue, Type type,
      BiConsumer<T, Value> handler) {
    map.put(name, new Key(defaultValue, type, (server, value) -> {
      for (ServerWorld world : server.getWorlds()) {
        if (world instanceof WorldHook) {
          handler.accept((T) world, value);
        } else {
          server.logError("Failed to set gamerule '" + name + "' for dimension "
              + DimensionType.getId(world.getDimension().getType()));
        }
      }
    }));
  }
}
