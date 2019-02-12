package com.misterpemodder.extragamerules;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import com.misterpemodder.customgamerules.rule.IGameRuleType;
import com.misterpemodder.customgamerules.rule.IGameRuleValue;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Type;

public class ExtendedGameRule<V> implements IGameRuleType<V> {
  protected final String typeName;
  protected final GameRules.Type mcType;
  protected final V defaultValue;
  protected final Function<String, V> parseFunction;
  protected final BiConsumer<ExtraGameRuleValues, V> updateValueFunction;
  protected final Function<ExtraGameRuleValues, V> getValueFunction;
  protected Predicate<V> validator;

  public ExtendedGameRule(String typeName, GameRules.Type mcType, V defaultValue,
      Function<String, V> parseFunction, BiConsumer<ExtraGameRuleValues, V> updateValueFunction,
      Function<ExtraGameRuleValues, V> getValueFunction) {
    this.typeName = typeName;
    this.mcType = mcType;
    this.defaultValue = defaultValue;
    this.parseFunction = parseFunction;
    this.updateValueFunction = updateValueFunction;
    this.getValueFunction = getValueFunction;
  }

  @Override
  public V getDefaultValue() {
    return this.defaultValue;
  }

  @Override
  public IGameRuleValue<V> createValue() {
    return new ExtendedGameRuleValue<>(this);
  }

  @Override
  public Type getMcType() {
    return this.mcType;
  }

  @Override
  public V parse(String source) throws NumberFormatException {
    return this.parseFunction.apply(source);
  }

  @Override
  public <T extends V> String stringify(T value) {
    return value.toString();
  }

  public ExtendedGameRule<V> validator(Predicate<V> validator) {
    this.validator = validator;
    return this;
  }

  @Override
  public <T extends V> boolean isValidValue(T value) {
    return this.validator == null ? true : this.validator.test(value);
  }

  public static <T extends Number> boolean validatePositive(T value) {
    return value.doubleValue() >= 0;
  }

  @Override
  public String getTypeName() {
    return this.typeName;
  }

  public static class ExtendedGameRuleValue<V> implements IGameRuleValue<V> {
    protected final ExtendedGameRule<V> type;

    public ExtendedGameRuleValue(ExtendedGameRule<V> type) {
      this.type = type;
      setValue(type.defaultValue, null);
    }

    @Override
    public IGameRuleType<V> getGameRuleType() {
      return this.type;
    }

    @Override
    public V getValue() {
      return this.type.getValueFunction.apply(ExtraGameRuleValues.get());
    }

    @Override
    public <T extends V> void setValue(T value, MinecraftServer server) {
      this.type.updateValueFunction.accept(ExtraGameRuleValues.get(server), value);
    }
  }
}
