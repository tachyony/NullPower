package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import tachyony.nullPower.Reference;

public class ItemRifleAmmo extends Item {
    public ItemRifleAmmo()
    {
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName("rifleAmmo");
        setUnlocalizedName(Reference.MODID + ".rifleAmmo");
    }
    
    public boolean isInfinite(ItemStack stack, ItemStack bow, EntityPlayer player)
    {
        int enchant = net.minecraft.enchantment.EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.INFINITY, bow);
        return enchant <= 0 ? false : this.getClass() == ItemRifleAmmo.class;
    }
}
