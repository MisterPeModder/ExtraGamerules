package com.misterpemodder.extragamerules.proxy;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

class ServerProxy implements SidedProxy {
  private final MinecraftServer server;

  ServerProxy() {
    this.server = (MinecraftServer) FabricLoader.getInstance().getGameInstance();
  }

  @Override
  public MinecraftServer getServerInstance() {
    return this.server;
  }
}
