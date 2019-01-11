package misterpemodder.extragamerules.mixin.world;

import java.util.TreeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.util.GameRulesUtil;
import net.minecraft.world.GameRules;

@Mixin(GameRules.class)
public final class GameRulesMixin {
  @Shadow
  private static TreeMap<String, GameRules.Key> KEYS;

  @Inject(at = @At("TAIL"), method = "<clinit>")
  private static void onClinit(CallbackInfo ci) {
    GameRulesUtil.registerEGGameRules(KEYS);
  }
}
