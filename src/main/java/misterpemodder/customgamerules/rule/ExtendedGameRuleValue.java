package misterpemodder.customgamerules.rule;

import java.util.function.BiConsumer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

public class ExtendedGameRuleValue<V> extends GameRules.Value {
  public IGameRuleValue<V> value;
  public IGameRuleType<V> type;

  private static final BiConsumer<MinecraftServer, GameRules.Value> EMPTY = (s, v) -> {
  };

  public ExtendedGameRuleValue(IGameRuleValue<V> value) {
    super(value.getGameRuleType().stringify(value.getValue()), value.getGameRuleType().getMcType(),
        EMPTY);
    this.value = value;
    this.type = value.getGameRuleType();
  }

  @Override
  public void set(String source, MinecraftServer server) {
    super.set(source, server);
    if (this.value == null)
      return;
    try {
      this.value.setValue(this.type.parse(source), server);
    } catch (NumberFormatException e) {
      this.value.setValue(this.type.getDefaultValue(), server);
    }
  }
}
