package misterpemodder.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import misterpemodder.customgamerules.hook.GameRulesKeyHook;
import net.minecraft.world.GameRules;

@Mixin(GameRules.Key.class)
public final class GameRulesKeyMixin implements GameRulesKeyHook {
  @Shadow
  private String defaultValue;

  @Override
  public String getDefaultValue() {
    return this.getDefaultValue();
  }
}
