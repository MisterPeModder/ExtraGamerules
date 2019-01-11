package misterpemodder.extragamerules.util;

import java.util.Map;
import misterpemodder.extragamerules.hook.ServerWorldHook;
import misterpemodder.extragamerules.util.ExtendedGameRule.Type;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;

public final class GameRulesUtil {
  public static void registerEGGameRules(Map<String, GameRules.Key> keys) {
    ExtendedGameRule.create("lightningProbability", Type.INT, DefaultValues.LIGHTNING_PROBABILITY,
        ServerWorldHook::setLightningProbability, ServerWorldHook::getLightningProbability,
        ExtendedGameRule::validatePositive);
    ExtendedGameRule.create("lightningFire", Type.BOOLEAN, DefaultValues.LIGHTNING_FIRE,
        ServerWorldHook::setLightningSpawningFire, ServerWorldHook::getLightningSpawningFire);
    ExtendedGameRule.create("lightningDamage", Type.FLOAT, DefaultValues.LIGHTNING_DAMAGE,
        ServerWorldHook::setLightningDamage, ServerWorldHook::getLightningDamage,
        ExtendedGameRule::validatePositive);
    ExtendedGameRule.create("lightningRange", Type.DOUBLE, DefaultValues.LIGHTNING_RANGE,
        ServerWorldHook::setLightningRange, ServerWorldHook::getLightningRange,
        ExtendedGameRule::validatePositive);
    ExtendedGameRule.create("lightningHorseSpawningModifier", Type.DOUBLE,
        DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE, ServerWorldHook::setHorseTrapSpawingChance,
        ServerWorldHook::getHorseTrapSpawningChance, ExtendedGameRule::validatePositive);
    ExtendedGameRule.create("doInsomnia", Type.BOOLEAN, DefaultValues.DO_INSOMNIA,
        ServerWorldHook::setInsomniaEnabled, ServerWorldHook::isInsomniaEnabled);
    ExtendedGameRule.create("tntExplodes", Type.BOOLEAN, DefaultValues.TNT_EXPLODES,
        ServerWorldHook::setTntExplodes, ServerWorldHook::getTntExplodes);
    ExtendedGameRule.create("explosionPowerModifier", Type.FLOAT,
        DefaultValues.EXPLOSION_POWER_MODIFER, ServerWorldHook::setExplosionPowerModifier,
        ServerWorldHook::getExplosionPowerModifier, ExtendedGameRule::validatePositive);
    new ExtendedGameRule<Boolean>(Type.BOOLEAN, DefaultValues.PVP_ENABLED,
        ServerWorldHook::setPvpEnabled, ServerWorldHook::isPvpEnabled,
        ExtendedGameRule::validateAlways) {
      @Override
      public <W extends ServerWorld & ServerWorldHook> void initialize(W world) {
        MinecraftServer server = world.getServer();
        setValue(server, server.isPvpEnabled());
      }
    }.register("pvp");
    ExtendedGameRule.create("drowningDamage", Type.BOOLEAN, DefaultValues.DROWNING_DAMAGE,
        ServerWorldHook::setDrowningDamageEnabled, ServerWorldHook::isDrowningDamageEnabled);
    ExtendedGameRule.create("fallDamage", Type.BOOLEAN, DefaultValues.FALL_DAMAGE,
        ServerWorldHook::setFallDamageEnabled, ServerWorldHook::isFallDamageEnabled);
    ExtendedGameRule.create("fireDamage", Type.BOOLEAN, DefaultValues.FIRE_DAMAGE,
        ServerWorldHook::setFireDamageEnabled, ServerWorldHook::isFireDamageEnabled);
    IExtendedGameRule.registerAll(keys);
  }
}
