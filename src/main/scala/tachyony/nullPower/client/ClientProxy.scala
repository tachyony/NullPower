package tachyony.nullPower.client

import net.minecraftforge.client.MinecraftForgeClient
import net.minecraft.client.Minecraft
import tachyony.nullPower.CommonProxy
import net.minecraft.logging.ILogAgent

class ClientProxy extends CommonProxy {
        override def getLogger: ILogAgent = Minecraft.getMinecraft().getLogAgent()

        override def registerRenderers() {
        }
}
