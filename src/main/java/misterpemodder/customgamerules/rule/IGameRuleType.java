package misterpemodder.customgamerules.rule;

import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import misterpemodder.customgamerules.hook.GameRulesKeyHook;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Type;

public interface IGameRuleType<V> {
  /**
   * @return This GameRule's default value. This value may mary.
   */
  @Nonnull
  V getDefaultValue();

  IGameRuleValue<V> createValue();

  /**
   * Returns the vanilla GameRule type. If you use a custom type, return {@link Type#STRING}.
   * 
   * @return The vanilla gameruleType.
   */
  Type getMcType();

  /**
   * Attemps to parse a value of type V
   * 
   * @param source
   * @return
   * @throws NumberFormatException
   */
  @Nullable
  V parse(String source) throws NumberFormatException;

  <T extends V> String stringify(T value);

  /**
   * @return the modId used by the mod that registered this rule.
   */
  String getModId();

  /**
   * Creates an @{IGameRuleType} from a vanilla {@GameRules.Key} instance.
   * 
   * @param key The vanilla gamerule key.
   * @return
   */
  static IGameRuleType<?> fromVanillaKey(GameRules.Key key) {
    String defaultValue = ((GameRulesKeyHook) (Object) key).getDefaultValue();
    switch (key.getType()) {
      case BOOLEAN:
        return new BasicGameRuleType<Boolean>("minecraft", Type.BOOLEAN, Boolean::valueOf,
            defaultValue);
      case INTEGER:
        return new BasicGameRuleType<Integer>("minecraft", Type.INTEGER, Integer::valueOf,
            defaultValue);
      default:
        return new BasicGameRuleType<String>("minecraft", Type.STRING, defaultValue,
            String::toString);
    }
  }

  static class BasicGameRuleType<V> implements IGameRuleType<V> {
    protected final String modId;
    protected final Type mcType;
    protected final V defaultValue;
    protected final Function<String, V> parseFunction;

    public BasicGameRuleType(String modId, Type mcType, Function<String, V> parseFunction,
        String defaultValue) {
      this(modId, mcType, parseFunction.apply(defaultValue), parseFunction);
    }

    public BasicGameRuleType(String modId, Type mcType, V defaultValue,
        Function<String, V> parseFunction) {
      this.modId = modId;
      this.mcType = mcType;
      this.defaultValue = defaultValue;
      this.parseFunction = parseFunction;
    }

    @Override
    public V getDefaultValue() {
      return this.defaultValue;
    }

    @Override
    public IGameRuleValue<V> createValue() {
      return new IGameRuleValue.BasicGameRuleValue<>(this, this.defaultValue);
    }

    @Override
    public V parse(String source) throws NumberFormatException {
      return this.parseFunction.apply(source);
    }

    @Override
    public Type getMcType() {
      return this.mcType;
    }

    @Override
    public <T extends V> String stringify(T value) {
      return value.toString();
    }

    @Override
    public String getModId() {
      return this.modId;
    }
  }
}
