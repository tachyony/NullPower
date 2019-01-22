package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tachyony.nullPower.Reference;

public class EnderIronDust extends Item {
	public EnderIronDust() {
	 	setCreativeTab(CreativeTabs.MATERIALS);
		setRegistryName("enderIronDust");
        setUnlocalizedName(Reference.MODID + ".enderIronDust");
	}
}
