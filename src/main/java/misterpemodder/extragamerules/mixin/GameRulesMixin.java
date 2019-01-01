package misterpemodder.extragamerules.mixin;

import java.util.TreeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.WorldHook;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.Key;
import net.minecraft.world.GameRules.Type;
import net.minecraft.world.dimension.DimensionType;

@Mixin(GameRules.class)
public final class GameRulesMixin {
  @Shadow
  private static TreeMap<String, Key> KEYS;

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    KEYS.put("lightningProbability", new Key("100000", Type.INTEGER, (server, rule) -> {
      for (ServerWorld world : server.getWorlds()) {
        if (world instanceof WorldHook) {
          ((WorldHook) world).getCustomRandom().setBound(rule.getInteger());
        } else {
          server.logError("Failed to set lightningProbability for dimension "
              + DimensionType.getId(world.getDimension().getType()));
        }
      }
    }));
  }
}
