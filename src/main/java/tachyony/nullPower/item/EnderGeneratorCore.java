package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tachyony.nullPower.Reference;

public class EnderGeneratorCore extends Item {
	public EnderGeneratorCore() {
		setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("enderGeneratorCore");
		setUnlocalizedName(Reference.MODID + "." + "enderGeneratorCore");
	}
}
