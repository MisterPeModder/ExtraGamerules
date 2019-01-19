package misterpemodder.customgamerules.rule;

import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Value;

public class ExtendedGameRuleKey<V> extends GameRules.Key {
  public IGameRuleType<V> type;

  public ExtendedGameRuleKey(IGameRuleType<V> type) {
    super(type.stringify(type.getDefaultValue()), type.getMcType());
    this.type = type;
  }

  @Override
  public Value createValue() {
    return new ExtendedGameRuleValue<>(this.type.createValue());
  }
}
