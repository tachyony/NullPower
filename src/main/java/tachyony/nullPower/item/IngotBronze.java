package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tachyony.nullPower.Reference;

public class IngotBronze extends Item {
	public IngotBronze() {
	 	setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("ingotBronze");
        setUnlocalizedName(Reference.MODID + ".ingotBronze");
	}
}
