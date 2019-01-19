package misterpemodder.extragamerules;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import misterpemodder.customgamerules.rule.IGameRuleType;
import misterpemodder.customgamerules.rule.IGameRuleValue;
import misterpemodder.extragamerules.hook.MinecraftServerHook;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Type;

public class ExtendedGameRule<V> implements IGameRuleType<V> {
  protected final GameRules.Type mcType;
  protected final V defaultValue;
  protected final Function<String, V> parseFunction;
  protected final BiConsumer<MinecraftServerHook, V> updateValueFunction;
  protected final Function<MinecraftServerHook, V> getValueFunction;
  protected BiConsumer<ExtendedGameRuleValue<V>, ServerWorld> onCreatedFuntion;
  protected Predicate<V> validator;

  public ExtendedGameRule(GameRules.Type mcType, V defaultValue, Function<String, V> parseFunction,
      BiConsumer<MinecraftServerHook, V> updateValueFunction,
      Function<MinecraftServerHook, V> getValueFunction) {
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

  public ExtendedGameRule<V> onCreated(
      BiConsumer<ExtendedGameRuleValue<V>, ServerWorld> onCreatedFuntion) {
    this.onCreatedFuntion = onCreatedFuntion;
    return this;
  }

  public static <T extends Number> boolean validatePositive(T value) {
    return value.doubleValue() >= 0;
  }

  @Override
  public String getModId() {
    return "extra-gamerules";
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
      MinecraftServerHook server = (MinecraftServerHook) (Object) FabricLoader.INSTANCE
          .getEnvironmentHandler().getServerInstance();
      return server == null ? this.type.getDefaultValue()
          : this.type.getValueFunction.apply(server);
    }

    @Override
    public <T extends V> void setValue(T value, MinecraftServer server) {
      if (server == null
          && (server = FabricLoader.INSTANCE.getEnvironmentHandler().getServerInstance()) == null)
        return;
      if (this.type.validator != null && !this.type.validator.test(value))
        this.type.updateValueFunction.accept((MinecraftServerHook) (Object) server,
            this.type.defaultValue);
      else
        this.type.updateValueFunction.accept((MinecraftServerHook) (Object) server, value);
    }

    @Override
    public void onCreated(ServerWorld world) {
      if (this.type.onCreatedFuntion != null)
        this.type.onCreatedFuntion.accept(this, world);
    }
  }
}
