package tachyony.nullPower.items

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import tachyony.nullPower.NullPower
import tachyony.nullPower.entity.EntityRifleBolt

class ItemHuntingRifle(par1: Int) extends Item(par1) {
    override def onItemRightClick(par1ItemStack: ItemStack, par2World: World, par3EntityPlayer: EntityPlayer): ItemStack =
    {
        if (par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.consumeInventoryItem(NullPower.rifleAmmo.itemID))
        {
            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(new EntityRifleBolt(par2World, par3EntityPlayer.asInstanceOf[EntityLivingBase]));
            }
        }

        par1ItemStack;
    }
}
