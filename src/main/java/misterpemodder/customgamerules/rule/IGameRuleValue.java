package misterpemodder.customgamerules.rule;

import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

/**
 * Holds a GameRule's value.
 * 
 * @param <V> the value type.
 */
public interface IGameRuleValue<V> {
  /**
   * @return This GameRule's current value.
   */
  V getValue();

  /**
   * @param value  the new value.
   * @param server the MinecraftServer instance, may be null.
   * @return The value that the GameRule will have.
   */
  <T extends V> void setValue(T value, @Nullable MinecraftServer server);

  /**
   * Called when this GameRule is set for the first time in this world.
   * 
   * @param world
   */
  default void onCreated(ServerWorld world) {
  }

  /**
   * @return The GameRuleType.
   */
  IGameRuleType<V> getGameRuleType();

  public static class BasicGameRuleValue<V> implements IGameRuleValue<V> {
    protected final IGameRuleType<V> type;
    protected V value;

    public BasicGameRuleValue(IGameRuleType<V> type, V value) {
      this.type = type;
      this.value = value;
    }

    @Override
    public V getValue() {
      return this.value;
    }

    @Override
    public <T extends V> void setValue(T value, MinecraftServer server) {
      this.value = value;
    }

    @Override
    public IGameRuleType<V> getGameRuleType() {
      return this.type;
    }
  }
}
