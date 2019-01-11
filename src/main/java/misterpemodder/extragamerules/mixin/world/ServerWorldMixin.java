package misterpemodder.extragamerules.mixin.world;

import java.util.function.BiFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.ServerWorldHook;
import misterpemodder.extragamerules.util.DefaultValues;
import misterpemodder.extragamerules.util.IExtendedGameRule;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.GameRules;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements ServerWorldHook {
  protected ServerWorldMixin(WorldSaveHandler worldSaveHandler_1,
      PersistentStateManager persistentStateManager_1, LevelProperties levelProperties_1,
      DimensionType dimensionType_1, BiFunction<World, Dimension, ChunkManager> biFunction_1,
      Profiler profiler_1, boolean boolean_1) {
    super(worldSaveHandler_1, persistentStateManager_1, levelProperties_1, dimensionType_1,
        biFunction_1, profiler_1, boolean_1);
  }

  @Shadow
  private MinecraftServer server;

  private int lightningProbabilityBound = DefaultValues.LIGHTNING_PROBABILITY;
  private boolean lightningSpawnsFire = DefaultValues.LIGHTNING_FIRE;
  private float lightningDamage = DefaultValues.LIGHTNING_DAMAGE;
  private double lightningRange = DefaultValues.LIGHTNING_RANGE;
  private double horseTrapSpawningChance = DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE;
  private boolean doInsomnia = DefaultValues.DO_INSOMNIA;
  private boolean tntExplodes = DefaultValues.TNT_EXPLODES;
  private float explosionPowerModifer = DefaultValues.EXPLOSION_POWER_MODIFER;
  private boolean drowningDamage = DefaultValues.DROWNING_DAMAGE;
  private boolean fallDamage = DefaultValues.FALL_DAMAGE;
  private boolean fireDamage = DefaultValues.FIRE_DAMAGE;

  @Override
  public int getLightningProbability() {
    return this.lightningProbabilityBound;
  }

  @Override
  public void setLightningProbability(int value) {
    this.lightningProbabilityBound = value == 0 ? Integer.MAX_VALUE : value;
  }

  @Override
  public boolean getLightningSpawningFire() {
    return this.lightningSpawnsFire;
  }

  @Override
  public void setLightningSpawningFire(boolean value) {
    this.lightningSpawnsFire = value;
  }

  @Override
  public float getLightningDamage() {
    return this.lightningDamage;
  }

  @Override
  public void setLightningDamage(float value) {
    this.lightningDamage = value < 0f ? DefaultValues.LIGHTNING_DAMAGE : value;
  }

  @Override
  public double getLightningRange() {
    return this.lightningRange;
  }

  @Override
  public void setLightningRange(double value) {
    this.lightningRange = value < 0.0 ? DefaultValues.LIGHTNING_RANGE : value;
  }

  @Override
  public double getHorseTrapSpawningChance() {
    return this.horseTrapSpawningChance;
  }

  @Override
  public void setHorseTrapSpawingChance(double value) {
    this.horseTrapSpawningChance =
        value < 0.0 ? DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE : value;
  }

  @Override
  public boolean isInsomniaEnabled() {
    return this.doInsomnia;
  }

  @Override
  public void setInsomniaEnabled(boolean value) {
    this.doInsomnia = value;
  }

  @Override
  public boolean getTntExplodes() {
    return this.tntExplodes;
  }

  @Override
  public void setTntExplodes(boolean value) {
    this.tntExplodes = value;
  }

  @Override
  public float getExplosionPowerModifier() {
    return this.explosionPowerModifer;
  }

  @Override
  public void setExplosionPowerModifier(float value) {
    this.explosionPowerModifer = value;
  }

  @Override
  public boolean isPvpEnabled() {
    return this.server.isPvpEnabled();
  }

  @Override
  public void setPvpEnabled(boolean value) {
    this.server.setPvpEnabled(value);
  }

  @Override
  public boolean isDrowningDamageEnabled() {
    return this.drowningDamage;
  }

  @Override
  public void setDrowningDamageEnabled(boolean value) {
    this.drowningDamage = value;
  }

  @Override
  public boolean isFallDamageEnabled() {
    return this.fallDamage;
  }

  @Override
  public void setFallDamageEnabled(boolean value) {
    this.fallDamage = value;
  }

  @Override
  public boolean isFireDamageEnabled() {
    return this.fireDamage;
  }

  @Override
  public void setFireDamageEnabled(boolean value) {
    this.fireDamage = value;
  }

  @SuppressWarnings("unchecked")
  @Inject(at = @At("RETURN"), method = "<init>")
  public <W extends ServerWorld & ServerWorldHook> void onConstruct(CallbackInfo ci) {
    GameRules rules = this.properties.getGameRules();

    for (String name : IExtendedGameRule.RULES.keySet()) {
      IExtendedGameRule<Object> rule =
          (IExtendedGameRule<Object>) IExtendedGameRule.RULES.get(name);
      GameRules.Value value = rules.get(name);
      rule.setValue(this.server,
          value == null ? rule.getDefaultValue() : rule.parseValue(value.getString()));
      rule.initialize((W) (Object) this);
    }
  }
}
