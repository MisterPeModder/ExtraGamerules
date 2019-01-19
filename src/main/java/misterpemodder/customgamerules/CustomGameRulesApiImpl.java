package misterpemodder.customgamerules;

import misterpemodder.customgamerules.rule.ExtendedGameRuleKey;
import misterpemodder.customgamerules.rule.IGameRuleType;
import net.minecraft.world.GameRules;

public class CustomGameRulesApiImpl implements CustomGameRulesApi {
  public static final int API_VERSION = 1;

  /**
   * Use {@link CustomGameRulesApi#INSTANCE} instead.
   */
  CustomGameRulesApiImpl() {

  }

  @Override
  public <T> IGameRuleType<T> registerGameRule(String name, IGameRuleType<T> type) {
    GameRules.getKeys().put(name, new ExtendedGameRuleKey<>(type));
    return type;
  }
}
