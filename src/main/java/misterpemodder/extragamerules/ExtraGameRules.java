package misterpemodder.extragamerules;

import java.util.function.BiConsumer;
import java.util.function.Function;
import misterpemodder.customgamerules.CustomGameRulesApi;
import misterpemodder.customgamerules.rule.IGameRuleType;
import misterpemodder.extragamerules.hook.MinecraftServerHook;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

public class ExtraGameRules implements ModInitializer {
  @Override
  public void onInitialize() {
    registerIntRule("lightningProbability", DefaultValues.LIGHTNING_PROBABILITY,
        MinecraftServerHook::setLightningProbability, MinecraftServerHook::getLightningProbability);
    registerBooleanRule("lightningFire", DefaultValues.LIGHTNING_FIRE,
        MinecraftServerHook::setLightningSpawningFire,
        MinecraftServerHook::getLightningSpawningFire);
    registerFloatRule("lightningDamage", DefaultValues.LIGHTNING_DAMAGE,
        MinecraftServerHook::setLightningDamage, MinecraftServerHook::getLightningDamage);
    registerDoubleRule("lightningRange", DefaultValues.LIGHTNING_RANGE,
        MinecraftServerHook::setLightningRange, MinecraftServerHook::getLightningRange);
    registerDoubleRule("lightningHorseSpawningModifier",
        DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE,
        MinecraftServerHook::setHorseTrapSpawingChance,
        MinecraftServerHook::getHorseTrapSpawningChance);
    registerBooleanRule("doInsomnia", DefaultValues.DO_INSOMNIA,
        MinecraftServerHook::setInsomniaEnabled, MinecraftServerHook::isInsomniaEnabled);
    registerBooleanRule("tntExplodes", DefaultValues.TNT_EXPLODES,
        MinecraftServerHook::setTntExplodes, MinecraftServerHook::getTntExplodes);
    registerFloatRule("explosionPowerModifier", DefaultValues.EXPLOSION_POWER_MODIFER,
        MinecraftServerHook::setExplosionPowerModifier,
        MinecraftServerHook::getExplosionPowerModifier);
    registerBooleanRule("pvp", DefaultValues.PVP_ENABLED, MinecraftServerHook::setPvpEnabled,
        MinecraftServerHook::isPvpEnabled).onCreated((value, world) -> {
          MinecraftServer server = world.getServer();
          value.setValue(server.isPvpEnabled(), server);
        });
    registerBooleanRule("drowningDamage", DefaultValues.DROWNING_DAMAGE,
        MinecraftServerHook::setDrowningDamageEnabled,
        MinecraftServerHook::isDrowningDamageEnabled);
    registerBooleanRule("fallDamage", DefaultValues.FALL_DAMAGE,
        MinecraftServerHook::setFallDamageEnabled, MinecraftServerHook::isFallDamageEnabled);
    registerBooleanRule("fireDamage", DefaultValues.FIRE_DAMAGE,
        MinecraftServerHook::setFireDamageEnabled, MinecraftServerHook::isFireDamageEnabled);
  }

  private static ExtendedGameRule<Integer> registerIntRule(String name, Integer defaultValue,
      BiConsumer<MinecraftServerHook, Integer> updateValueFunction,
      Function<MinecraftServerHook, Integer> getValueFunction) {
    return register(name, GameRules.Type.INTEGER, defaultValue, Integer::valueOf,
        updateValueFunction, getValueFunction).validator(ExtendedGameRule::validatePositive);
  }

  private static ExtendedGameRule<Boolean> registerBooleanRule(String name, Boolean defaultValue,
      BiConsumer<MinecraftServerHook, Boolean> updateValueFunction,
      Function<MinecraftServerHook, Boolean> getValueFunction) {
    return register(name, GameRules.Type.BOOLEAN, defaultValue, Boolean::valueOf,
        updateValueFunction, getValueFunction);
  }

  private static ExtendedGameRule<Float> registerFloatRule(String name, Float defaultValue,
      BiConsumer<MinecraftServerHook, Float> updateValueFunction,
      Function<MinecraftServerHook, Float> getValueFunction) {
    return register(name, GameRules.Type.STRING, defaultValue, Float::valueOf, updateValueFunction,
        getValueFunction).validator(ExtendedGameRule::validatePositive);
  }

  private static ExtendedGameRule<Double> registerDoubleRule(String name, Double defaultValue,
      BiConsumer<MinecraftServerHook, Double> updateValueFunction,
      Function<MinecraftServerHook, Double> getValueFunction) {
    return register(name, GameRules.Type.STRING, defaultValue, Double::valueOf, updateValueFunction,
        getValueFunction).validator(ExtendedGameRule::validatePositive);
  }

  private static <V> ExtendedGameRule<V> register(String name, GameRules.Type mcType,
      V defaultValue, Function<String, V> parseFunction,
      BiConsumer<MinecraftServerHook, V> updateValueFunction,
      Function<MinecraftServerHook, V> getValueFunction) {
    ExtendedGameRule<V> rule = new ExtendedGameRule<V>(mcType, defaultValue, parseFunction,
        updateValueFunction, getValueFunction);
    register(name, rule);
    return rule;
  }

  private static void register(String name, IGameRuleType<?> type) {
    CustomGameRulesApi.INSTANCE.registerGameRule(name, type);
  }
}
