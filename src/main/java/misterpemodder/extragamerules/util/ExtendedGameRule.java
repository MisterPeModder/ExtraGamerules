package misterpemodder.extragamerules.util;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public class ExtendedGameRule<T> implements IExtendedGameRule<T> {
  public final Type<T> type;
  protected T defaultValue;
  protected final BiConsumer<ServerWorldHook, T> updateValueConsumer;
  protected final Function<ServerWorldHook, T> getValueFunction;
  protected final Predicate<T> validator;

  public ExtendedGameRule(Type<T> type, T defaultValue, BiConsumer<ServerWorldHook, T> updateValue,
      Function<ServerWorldHook, T> getValue, Predicate<T> validator) {
    this.type = type;
    this.defaultValue = defaultValue;
    this.updateValueConsumer = updateValue;
    this.getValueFunction = getValue;
    this.validator = validator;
  }

  public static <T> void create(String name, Type<T> type, T defaultValue,
      BiConsumer<ServerWorldHook, T> updateValue, Function<ServerWorldHook, T> getValue) {
    new ExtendedGameRule<>(type, defaultValue, updateValue, getValue,
        ExtendedGameRule::validateAlways).register(name);
  }

  public static <T> void create(String name, Type<T> type, T defaultValue,
      BiConsumer<ServerWorldHook, T> updateValue, Function<ServerWorldHook, T> getValue,
      Predicate<T> validator) {
    new ExtendedGameRule<>(type, defaultValue, updateValue, getValue, validator).register(name);
  }

  public void register(String name) {
    RULES.put(name, this);
  }

  @Override
  public T getValue(MinecraftServer server) {
    for (ServerWorld world : server.getWorlds())
      if (world instanceof ServerWorldHook)
        return this.getValueFunction.apply((ServerWorldHook) world);
    return getDefaultValue();
  }

  @Override
  public T getDefaultValue() {
    return this.defaultValue;
  }

  @Override
  public void setValue(MinecraftServer server, T value) {
    for (ServerWorld world : server.getWorlds())
      if (world instanceof ServerWorldHook) {
        if (!this.validator.test(value))
          value = getDefaultValue();
        this.updateValueConsumer.accept((ServerWorldHook) world, value);
      }
  }

  @Override
  public GameRules.Type getGameRuleType() {
    return this.type.mcType;
  }

  @Override
  public T parseValue(String source) {
    try {
      return this.type.parseValue.apply(source);
    } catch (NumberFormatException e) {
      return getDefaultValue();
    }
  }

  @Override
  public <W extends ServerWorld & ServerWorldHook> void initialize(W world) {
  }

  public static <T extends Number> boolean validatePositive(T value) {
    return value.doubleValue() >= 0;
  }

  public static <T> boolean validateAlways(T value) {
    return true;
  }

  public static class Type<V> {
    public static final Type<String> STRING = new Type<>(GameRules.Type.STRING, String::toString);
    public static final Type<Boolean> BOOLEAN =
        new Type<>(GameRules.Type.BOOLEAN, Boolean::valueOf);
    public static final Type<Integer> INT = new Type<>(GameRules.Type.INTEGER, Integer::valueOf);
    public static final Type<Float> FLOAT = new Type<>(GameRules.Type.STRING, Float::valueOf);
    public static final Type<Double> DOUBLE = new Type<>(GameRules.Type.STRING, Double::valueOf);

    public final GameRules.Type mcType;
    public final Function<String, V> parseValue;

    public Type(GameRules.Type mcType, Function<String, V> parseValue) {
      this.mcType = mcType;
      this.parseValue = parseValue;
    }
  }
}
