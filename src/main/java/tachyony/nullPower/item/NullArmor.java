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

public class NullArmor extends ItemArmor {
    private final ArmorMaterial material;

    public NullArmor(ArmorMaterial materialIn, int renderId, EntityEquipmentSlot partId) {
        super(materialIn, renderId, partId);
        this.material = materialIn;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.COMBAT);
    }
    
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }
    
    /**
     * Return the armour material for this armour item.
     */
    @Override
    public ItemArmor.ArmorMaterial getArmorMaterial()
    {
        return this.material;
    }
    
    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return this.material.getRepairItem() == repair.getItem() ? true : super.getIsRepairable(toRepair, repair);
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
        if ((player.getItemStackFromSlot(EntityEquipmentSlot.HEAD) != null) &&
            (player.getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null) &&
            (player.getItemStackFromSlot(EntityEquipmentSlot.LEGS) != null) &&
            (player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(NullPower.nullHelmet) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem().equals(NullPower.nullChestplate) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem().equals(NullPower.nullLeggings) &&
            player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem().equals(NullPower.nullBoots))
        {
            //int resistance = 11;
            //player.addPotionEffect(new PotionEffect(Potion.getPotionById(resistance), 20, 0, false, false));
            //int regeneration = 10;
            //player.addPotionEffect(new PotionEffect(Potion.getPotionById(regeneration), 20, 0, false, false));
            //int absorbtion = 22;
            //player.addPotionEffect(new PotionEffect(Potion.getPotionById(absorbtion), 20, 0, false, false));
            int jump = 8;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(jump), 20, 1, false, false));
            int saturation = 23;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(saturation), 20, 0, false, false));
            int speed = 1;
            player.addPotionEffect(new PotionEffect(Potion.getPotionById(speed), 20, 1, false, false));
        }
    }
}