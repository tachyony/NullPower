package tachyony.nullPower;

import net.minecraft.init.Items;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictCompat {
	public static void registerOres()
	{
		OreDictionary.registerOre("ingotBronze", ObjectRegistrar.ingotBronze);
		OreDictionary.registerOre("nullenderpearl", ObjectRegistrar.psuedoEnderPearl);
		OreDictionary.registerOre("nullenderpearl", Items.ENDER_PEARL);
	}
}
