package tachyony.nullPower

import net.minecraft.client.Minecraft
import net.minecraft.server.MinecraftServer
import net.minecraft.logging.ILogAgent

class CommonProxy {
  def registerRenderers() {
  }

  def getLogger: ILogAgent = MinecraftServer.getServer().getLogAgent()
}
