package tachyony.nullPower.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tachyony.nullPower.Reference;
import tachyony.nullPower.container.ContainerDerpyFurnace;
import tachyony.nullPower.gui.GuiDerpyFurnace;
import tachyony.nullPower.tile.TileEntityDerpyFurnace;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.DERPYFRUNACE_GUIID)
			return new ContainerDerpyFurnace(player.inventory, (TileEntityDerpyFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Reference.DERPYFRUNACE_GUIID)
			return new GuiDerpyFurnace(player.inventory, (TileEntityDerpyFurnace)world.getTileEntity(new BlockPos(x,y,z)));
		
		return null;
	}
}
