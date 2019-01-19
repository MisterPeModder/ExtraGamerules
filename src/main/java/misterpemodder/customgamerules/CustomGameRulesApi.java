package misterpemodder.customgamerules;

import misterpemodder.customgamerules.rule.IGameRuleType;

public interface CustomGameRulesApi {
  /**
   * The CustomGameRules API instance.
   * 
   * Before retrieving the API, you should always check if the mod is loaded.
   * 
   * <p>
   * For example:
   * 
   * <pre>
   * <code>
   * if (FabricLoader.INSTANCE.isModLoaded("extra-gamerules")) {
   *   CustomGameRulesApi api = CustomGameRulesApi.INSTANCE;
   *   // use API here.
   * }
   * </code>
   * </pre>
   * </p>
   */
  static final CustomGameRulesApi INSTANCE = new CustomGameRulesApiImpl();

  /**
   * Register an {@link IGameRuleType}.
   * 
   * @param name The gamerule name. must be unique.
   * @param type Its type.
   * @return The passed {@link IGameRuleType}
   */
  <T> IGameRuleType<T> registerGameRule(String name, IGameRuleType<T> type);
}
