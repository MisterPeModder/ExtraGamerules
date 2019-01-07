package misterpemodder.extragamerules.mixin;

import java.util.TreeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.util.DefaultValues;
import misterpemodder.extragamerules.util.GameRulesUtil;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Key;
import net.minecraft.world.GameRules.Type;

@Mixin(GameRules.class)
public final class GameRulesMixin {
  @Shadow
  private static TreeMap<String, Key> KEYS;

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningProbability",
        DefaultValues.LIGHTNING_PROBABILITY, Type.INTEGER, (world, value) -> {
          world.setLightningProbability(value.getInteger());
        }, (value) -> {
          return value.getInteger() >= 0;
        });
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningFire", DefaultValues.LIGHTNING_FIRE,
        Type.BOOLEAN, (world, value) -> {
          world.setLightningSpawningFire(value.getBoolean());
        });
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningDamage", DefaultValues.LIGHTNING_DAMAGE,
        Type.STRING, (world, value) -> {
          world.setLightningDamage(Float.parseFloat(value.getString()));
        }, (value) -> {
          return Float.parseFloat(value.getString()) >= 0f;
        });
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningRange", DefaultValues.LIGHTNING_RANGE,
        Type.STRING, (world, value) -> {
          double v = Double.parseDouble(value.getString());
          // Due to how weird entity checking code, range does not work above ~6.57...
          if (v > 6.57) {
            v = 6.57;
            value.set("6.57", world.getServer());
          }
          world.setLightningRange(v);
        }, (value) -> {
          return Double.parseDouble(value.getString()) >= 0.0;
        });
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningHorseSpawningModifier",
        DefaultValues.LIGHTNING_HORSE_SPAWNING_CHANCE, Type.STRING, (world, value) -> {
          world.setHorseTrapSpawingChance(Double.parseDouble(value.getString()));
        }, (value) -> {
          return Double.parseDouble(value.getString()) >= 0.0;
        });
    GameRulesUtil.registerWorldHookGamerule(KEYS, "doInsomnia", DefaultValues.DO_INSOMNIA,
        Type.BOOLEAN, (world, value) -> {
          world.setInsomniaEnabled(value.getBoolean());
        });
  }
}
