package com.misterpemodder.extragamerules.mixin.entity;

import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.util.concurrent.GenericFutureListener;
import com.misterpemodder.extragamerules.hook.ServerWorldHook;
import net.minecraft.client.network.packet.CombatEventClientPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.packet.ClientStatusServerPacket;
import net.minecraft.server.world.ServerWorld;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
  @Shadow
  public ServerPlayNetworkHandler networkHandler;

  private ServerPlayerEntityMixin(ServerWorld world, GameProfile profile) {
    super(world, profile);
  }

  @Redirect(
      at = @At(value = "INVOKE",
          target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket"
              + "(Lnet/minecraft/network/Packet;)V"),
      method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
  private void removeDeathPackets(ServerPlayNetworkHandler handler, Packet<?> packet) {
    // Just to make sure these are death packets...
    // Don't send packet if matches and instantRespawn is on.
    if (packet instanceof CombatEventClientPacket
        && ((CombatEventClientPacket) packet).type == CombatEventClientPacket.Type.DEATH
        && ((ServerWorldHook) this.world).getEGValues().instantRespawn)
      return;
    this.networkHandler.sendPacket(packet);
  }

  @Redirect(at = @At(value = "INVOKE",
      target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket"
          + "(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V"),
      method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
  private void removeDeathPackets(ServerPlayNetworkHandler handler, Packet<?> packet,
      GenericFutureListener<?> futureListener) {
    // Just to make sure these are death packets...
    // Don't send packet if matches and instantRespawn is on.
    if (packet instanceof CombatEventClientPacket
        && ((CombatEventClientPacket) packet).type == CombatEventClientPacket.Type.DEATH
        && ((ServerWorldHook) this.world).getEGValues().instantRespawn)
      return;
    this.networkHandler.sendPacket(packet);
  }

  @Inject(at = @At("RETURN"), method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V")
  private void onDeath(CallbackInfo ci) {
    // Call ServerPlayNetworkHandler#onClientStatus with a fake packet to force respawn.
    if (((ServerWorldHook) this.world).getEGValues().instantRespawn) {
      this.networkHandler.onClientStatus(
          new ClientStatusServerPacket(ClientStatusServerPacket.Mode.PERFORM_RESPAWN));
      this.extinguish();
    }
  }
}
