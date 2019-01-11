package misterpemodder.extragamerules.util;

import java.util.HashMap;
import java.util.Map;
import misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public interface IExtendedGameRule<T> {
  public static final Map<String, ExtendedGameRule<?>> RULES = new HashMap<>();

  T getValue(MinecraftServer server);

  T getDefaultValue();

  void setValue(MinecraftServer server, T value);

  T parseValue(String source);

  GameRules.Type getGameRuleType();

  <W extends ServerWorld & ServerWorldHook> void initialize(W world);

  /**
   * Puts all of the IExtendedGameRules in the passed map as 'regular' gamerules
   *
   * @param mcRules A map of GameRules Keys.
   */
  @SuppressWarnings("unchecked")
  public static void registerAll(Map<String, GameRules.Key> mcRules) {
    for (String name : RULES.keySet()) {
      final ExtendedGameRule<Object> rule = (ExtendedGameRule<Object>) RULES.get(name);
      mcRules.put(name, new GameRules.Key(rule.getDefaultValue().toString(), rule.getGameRuleType(),
          (server, value) -> rule.setValue(server, rule.parseValue(value.getString()))));
    }
  }
}
