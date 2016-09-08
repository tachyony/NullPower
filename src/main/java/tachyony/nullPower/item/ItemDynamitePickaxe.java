/*
 * Copyright (C) 2014 Tachyony
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tachyony.nullPower.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * LOL
 */
public class ItemDynamitePickaxe extends EnergyItems {
	/**
	 * @param itemId Item id
	 */
	public ItemDynamitePickaxe() {
		super();
		this.maxStackSize = 1;
	}
	
	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	////@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		int p4 = par4;
		int p5 = par5;
		int p6 = par6;
		int p7 = par7;
		if (p7 == 0)
        {
            --p5;
        }

        if (p7 == 1)
        {
            ++p5;
        }

        if (p7 == 2)
        {
            --p6;
        }

        if (p7 == 3)
        {
            ++p6;
        }

        if (p7 == 4)
        {
            --p4;
        }

        if (p7 == 5)
        {
            ++p4;
        }
		
		if(!par3World.isRemote) {
			////if (!par2EntityPlayer.canPlayerEdit(p4, p5, p6, p7, par1ItemStack)) {
		        ////return false;
		    /*} else*/ if (par1ItemStack.stackSize == 0) {
		        return false;
		    }
			
		    //par3World.createExplosion(par2EntityPlayer, p4, p5, p6, 3.0F, true);
		    //return true;
		}
		
		return false;
    }
	
	/**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return this.bFull3D;
    }
    
    ////@Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
        EnergyItems.checkAndSetItemOwner(itemstack, player);
        if (player.worldObj.isRemote)
        {
            return itemstack;
        }
        
        /*NBTTagCompound itemTag = itemstack.stackTagCompound;
        if (itemTag == null || itemTag.getString("ownerName").equals(""))
        {
            return itemstack;
        }
        
        String ownerName = itemTag.getString("ownerName");
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, false);
        if (movingobjectposition == null) {
            ////player.addChatMessage(new ChatComponentText("Current Power: " + EnergyItems.getCurrentPower(ownerName)));
            return itemstack;
        } else {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                ////player.addChatMessage(new ChatComponentText("Current Power: " + EnergyItems.getCurrentPower(ownerName)));
                return itemstack;
            }
        }*/
        
        return itemstack;
    }
}
