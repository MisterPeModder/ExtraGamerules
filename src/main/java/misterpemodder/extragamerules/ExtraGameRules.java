package misterpemodder.extragamerules;

import java.util.function.BiConsumer;
import java.util.function.Function;
import misterpemodder.customgamerules.GameRuleRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

public class ExtraGameRules implements ModInitializer {
  @Override
  public void onInitialize() {
    registerIntRule("lightningProbability", DefaultValues.LIGHTNING_PROBABILITY,
        ExtraGameRuleValues::setLightningProbability, ExtraGameRuleValues::getLightningProbability);
    registerBooleanRule("lightningFire", DefaultValues.LIGHTNING_FIRE,
        ExtraGameRuleValues::setLightningSpawningFire,
        ExtraGameRuleValues::getLightningSpawningFire);
    registerFloatRule("lightningDamage", DefaultValues.LIGHTNING_DAMAGE,
        ExtraGameRuleValues::setLightningDamage, ExtraGameRuleValues::getLightningDamage);
    registerDoubleRule("lightningRange", DefaultValues.LIGHTNING_RANGE,
        ExtraGameRuleValues::setLightningRange, ExtraGameRuleValues::getLightningRange);
    registerDoubleRule("lightningHorseSpawningModifier",
        DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE,
        ExtraGameRuleValues::setHorseTrapSpawingChance,
        ExtraGameRuleValues::getHorseTrapSpawningChance);
    registerBooleanRule("doInsomnia", DefaultValues.DO_INSOMNIA,
        ExtraGameRuleValues::setInsomniaEnabled, ExtraGameRuleValues::isInsomniaEnabled);
    registerBooleanRule("tntExplodes", DefaultValues.TNT_EXPLODES,
        ExtraGameRuleValues::setTntExplodes, ExtraGameRuleValues::doesTntExplodes);
    registerFloatRule("explosionPowerModifier", DefaultValues.EXPLOSION_POWER_MODIFER,
        ExtraGameRuleValues::setExplosionPowerModifier,
        ExtraGameRuleValues::getExplosionPowerModifier);
    GameRuleRegistry.INSTANCE.registerGameRule("extra-gamerules", "pvp",
        new ExtendedGameRule<Boolean>("boolean", GameRules.Type.BOOLEAN, DefaultValues.PVP_ENABLED,
            Boolean::valueOf, ExtraGameRuleValues::setPvpEnabled,
            ExtraGameRuleValues::isPvpEnabled) {
          @Override
          public Boolean getDefaultValue() {
            MinecraftServer server = (MinecraftServer) FabricLoader.getInstance().getGameInstance();
            if (server == null)
              return this.defaultValue;
            return server.isPvpEnabled();
          }
        });
    registerBooleanRule("drowningDamage", DefaultValues.DROWNING_DAMAGE,
        ExtraGameRuleValues::setDrowningDamageEnabled,
        ExtraGameRuleValues::isDrowningDamageEnabled);
    registerBooleanRule("fallDamage", DefaultValues.FALL_DAMAGE,
        ExtraGameRuleValues::setFallDamageEnabled, ExtraGameRuleValues::isFallDamageEnabled);
    registerBooleanRule("fireDamage", DefaultValues.FIRE_DAMAGE,
        ExtraGameRuleValues::setFireDamageEnabled, ExtraGameRuleValues::isFireDamageEnabled);
    registerBooleanRule("doSnowMelt", DefaultValues.SNOW_MELTS, ExtraGameRuleValues::setSnowMelt,
        ExtraGameRuleValues::doSnowMelt);
    registerIntRule("pistonPushLimit", DefaultValues.PISTON_PUSH_LIMIT,
        ExtraGameRuleValues::setPistonPushLimit, ExtraGameRuleValues::getPistonPushLimit);
    registerBooleanRule("doHunger", DefaultValues.DO_HUNGER, ExtraGameRuleValues::setHungerEnabled,
        ExtraGameRuleValues::isHungerEnabled);
    registerBooleanRule("instantRespawn", DefaultValues.INSTANT_RESPAWN,
        ExtraGameRuleValues::setInstantRespawn, ExtraGameRuleValues::doInstantRespawn);
  }

  private static ExtendedGameRule<Integer> registerIntRule(String name, Integer defaultValue,
      BiConsumer<ExtraGameRuleValues, Integer> updateValueFunction,
      Function<ExtraGameRuleValues, Integer> getValueFunction) {
    return register("integer", name, GameRules.Type.INTEGER, defaultValue, Integer::valueOf,
        updateValueFunction, getValueFunction).validator(ExtendedGameRule::validatePositive);
  }

  private static ExtendedGameRule<Boolean> registerBooleanRule(String name, Boolean defaultValue,
      BiConsumer<ExtraGameRuleValues, Boolean> updateValueFunction,
      Function<ExtraGameRuleValues, Boolean> getValueFunction) {
    return register("boolean", name, GameRules.Type.BOOLEAN, defaultValue, Boolean::valueOf,
        updateValueFunction, getValueFunction);
  }

  private static ExtendedGameRule<Float> registerFloatRule(String name, Float defaultValue,
      BiConsumer<ExtraGameRuleValues, Float> updateValueFunction,
      Function<ExtraGameRuleValues, Float> getValueFunction) {
    return register("float", name, GameRules.Type.STRING, defaultValue, Float::valueOf,
        updateValueFunction, getValueFunction).validator(ExtendedGameRule::validatePositive);
  }

  private static ExtendedGameRule<Double> registerDoubleRule(String name, Double defaultValue,
      BiConsumer<ExtraGameRuleValues, Double> updateValueFunction,
      Function<ExtraGameRuleValues, Double> getValueFunction) {
    return register("double", name, GameRules.Type.STRING, defaultValue, Double::valueOf,
        updateValueFunction, getValueFunction).validator(ExtendedGameRule::validatePositive);
  }

  private static <V> ExtendedGameRule<V> register(String typeName, String name,
      GameRules.Type mcType, V defaultValue, Function<String, V> parseFunction,
      BiConsumer<ExtraGameRuleValues, V> updateValueFunction,
      Function<ExtraGameRuleValues, V> getValueFunction) {
    ExtendedGameRule<V> rule = new ExtendedGameRule<V>(typeName, mcType, defaultValue,
        parseFunction, updateValueFunction, getValueFunction);
    GameRuleRegistry.INSTANCE.registerGameRule("extra-gamerules", name, rule);
    return rule;
  }
}
