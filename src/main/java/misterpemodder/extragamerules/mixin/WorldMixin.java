package misterpemodder.extragamerules.mixin;

import java.util.Random;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import misterpemodder.extragamerules.BoundsControllingRandom;
import misterpemodder.extragamerules.WorldHook;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelProperties;

@Mixin(World.class)
public final class WorldMixin implements WorldHook {
  @Shadow
  public Random random;
  @Shadow
  protected LevelProperties properties;
  protected BoundsControllingRandom customRandom = null;

  public BoundsControllingRandom getCustomRandom() {
    return this.customRandom;
  }

  @Inject(at = @At("RETURN"), method = "<init>")
  public void onConstruct(CallbackInfo ci) {
    this.customRandom = new BoundsControllingRandom(this.random);
    GameRules.Value value = this.properties.getGameRules().get("lightningProbability");
    if (value != null && value.getType() == GameRules.Type.INTEGER)
      this.customRandom.setBound(value.getInteger());
  }

  @Redirect(
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;random:Ljava/util/Random;",
          opcode = Opcodes.GETFIELD, ordinal = 0),
      method = "method_8462(Lnet/minecraft/world/chunk/WorldChunk;I)V")
  private Random getFieldValue(World owner) {
    return this.customRandom;
  }
}
