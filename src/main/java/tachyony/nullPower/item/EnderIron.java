package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tachyony.nullPower.Reference;

public class EnderIron extends Item {
	public EnderIron() {
	 	setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("enderIron");
        setUnlocalizedName(Reference.MODID + ".enderIron");
	}
}
