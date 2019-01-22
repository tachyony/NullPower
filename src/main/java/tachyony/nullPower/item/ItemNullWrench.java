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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tachyony.nullPower.Reference;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * LOL
 */
public class ItemNullWrench extends Item/*EnergyItems*/ {
    protected float damageVsEntity;
    
    protected float attackSpeed;
    
	/**
	 * @param itemId Item id
	 */
	public ItemNullWrench() {
		super();
		this.bFull3D = true;
		this.maxStackSize = 1;
        this.damageVsEntity = 0f;
        this.attackSpeed = -3f;
		this.setCreativeTab(CreativeTabs.TOOLS);
		setRegistryName("nullWrench");
        setUnlocalizedName(Reference.MODID + "." + "itemNullWrench");
	}
	
    /**
     * ItemStack sensitive version of getItemAttributeModifiers
     */
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.damageVsEntity, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }
    
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(0, attacker);
        return true;
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

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, playerIn.getHeldItem(hand)))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            return EnumActionResult.SUCCESS;
        }
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
    	ItemStack itemStack = player.getHeldItem(hand);
    	/*checkAndSetItemOwner(itemStack, player);*/
        if (world.isRemote)
        {
            return new ActionResult(EnumActionResult.PASS, itemStack);
        }
        
        /*String ownerName = this.getOwnerName(itemStack);
        player.addChatMessage(new TextComponentString("Current Power: " + ownerName + ": " + EnergyItems.getCurrentPower(ownerName)));*/
        return new ActionResult(EnumActionResult.SUCCESS, itemStack);
    }
}
