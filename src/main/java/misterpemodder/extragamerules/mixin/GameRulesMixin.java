package misterpemodder.extragamerules.mixin;

import java.util.TreeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.hook.EntityHook;
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
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningProbability", "100000", Type.INTEGER,
        (world, value) -> {
          world.getCustomRandom().setBound(value.getInteger());
        });
    GameRulesUtil.registerWorldHookGamerule(KEYS, "lightningFire", "true", Type.BOOLEAN,
        (world, value) -> {
          world.setLightningSpawningFire(value.getBoolean());
        });
    KEYS.put("lightningDamage", new Key("5.0", Type.STRING, (server, value) -> {
      try {
        float damage = Float.parseFloat(value.getString());
        EntityHook.setLightningDamage(damage);
      } catch (NumberFormatException e) {
        EntityHook.setLightningDamage(0f);
      }
    }));
  }
}
