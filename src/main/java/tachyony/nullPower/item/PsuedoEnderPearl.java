package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tachyony.nullPower.Reference;

public class PsuedoEnderPearl extends Item {
	public PsuedoEnderPearl() {
		setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("psuedoEnderPearl");
		setUnlocalizedName(Reference.MODID + "." + "psuedoEnderPearl");
	}
}
