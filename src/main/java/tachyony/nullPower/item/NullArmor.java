package tachyony.nullPower.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NullArmor extends ItemArmor {
    public NullArmor(ArmorMaterial material, int renderId, EntityEquipmentSlot partId) {
        super(material, renderId, partId);
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        /*if ((player.getCurrentArmor(3) != null) &&
            (player.getCurrentArmor(2) != null) &&
            (player.getCurrentArmor(1) != null) &&
            (player.getCurrentArmor(0) != null) &&
            player.getCurrentArmor(3).getItem().equals(NullPower.nullHelmet) &&
            player.getCurrentArmor(2).getItem().equals(NullPower.nullChestplate) &&
            player.getCurrentArmor(1).getItem().equals(NullPower.nullLeggings) &&
            player.getCurrentArmor(0).getItem().equals(NullPower.nullBoots))
        {
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 20, 1));
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 1));
            
            // Absorption
            player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 20, 1));
            player.addPotionEffect(new PotionEffect(Potion.jump.id, 20, 1));
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1));
        }*/
    }
}