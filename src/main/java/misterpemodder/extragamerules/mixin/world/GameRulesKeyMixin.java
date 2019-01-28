package misterpemodder.extragamerules.mixin.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import misterpemodder.customgamerules.hook.GameRulesKeyHook;
import net.minecraft.world.GameRules;

@Mixin(GameRules.Key.class)
public final class GameRulesKeyMixin implements GameRulesKeyHook {
  @Shadow
  private String defaultValue;
  @Shadow
  private GameRules.Type type;
  private String typeName;

  @Override
  public String getDefaultValue() {
    return this.getDefaultValue();
  }

  public String getModId() {
    return "minecraft";
  }

  private static String getTypeName(GameRules.Type type) {
    switch (type) {
      case STRING:
        return "string";
      case INTEGER:
        return "integer";
      case BOOLEAN:
        return "boolean";
      default:
        return type.name().toLowerCase();
    }
  }

  @Override
  public String getTypeName() {
    return this.typeName != null ? this.typeName : (this.typeName = getTypeName(this.type));
  }
}
