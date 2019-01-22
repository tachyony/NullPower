package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tachyony.nullPower.Reference;

public class EnderGeneratorCoreAdvanced extends Item {
	public EnderGeneratorCoreAdvanced() {
		setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("enderGeneratorCoreAdvanced");
		setUnlocalizedName(Reference.MODID + "." + "enderGeneratorCoreAdvanced");
	}
}
