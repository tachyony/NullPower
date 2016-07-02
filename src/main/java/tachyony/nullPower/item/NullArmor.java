package tachyony.nullPower.item;

import tachyony.nullPower.NullPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class NullArmor extends ItemArmor {
    public NullArmor(ArmorMaterial material, int renderId, int partId) {
        super(material, renderId, partId);
    }
    
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return "nullpower" + ":textures/armour/" + "nullArmour" + "_" +
            (this.armorType == 2 ? "2" : "1") + ".png";
    }
    
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if ((player.getCurrentArmor(3) != null) &&
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
        }
    }
}