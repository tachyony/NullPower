package tachyony.nullPower.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tachyony.nullPower.NullPower;
import tachyony.nullPower.ObjectRegistrar;
import tachyony.nullPower.Reference;

public class NullArmor extends ItemArmor {
    private final ArmorMaterial material;

    public NullArmor(int renderId, EntityEquipmentSlot partId, String name) {
        super(NullPower.nullArmour, renderId, partId);
        material = NullPower.nullArmour;
        maxStackSize = 1;
        setCreativeTab(CreativeTabs.COMBAT);
        setRegistryName(name);
        setUnlocalizedName(Reference.MODID + "." + name);
    }
    
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return material.getEnchantability();
    }
    
    /**
     * Return the armour material for this armour item.
     */
    @Override
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        return material;
    }
    
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
    	return ObjectRegistrar.enderIron == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if ((player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null) &&
            (player.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) &&
            (player.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) &&
            (player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(ObjectRegistrar.nullHelmet) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem().equals(ObjectRegistrar.nullChestplate) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem().equals(ObjectRegistrar.nullLeggings) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem().equals(ObjectRegistrar.nullBoots))
        {
            int speed = 1;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(speed), 20, 1, false, false));
            int jump = 8;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(jump), 20, 1, false, false));
            int resistance = 11;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(resistance), 20, 0, false, false));
            int absorbtion = 22;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(absorbtion), 20, 0, false, false));
            int saturation = 23;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(saturation), 20, 0, false, false));
        }
    }
}