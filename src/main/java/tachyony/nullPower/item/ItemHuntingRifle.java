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

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Hunting rifle
 */
public class ItemHuntingRifle extends Item {
    private float weaponDamage;
    
    private ToolMaterial toolMaterial;
    
    /**
     * Hunting rifle
     * @param toolMaterial Material
     * @param otherDamage Damage
     */
	public ItemHuntingRifle(ToolMaterial toolMaterial) {
		super();
		this.toolMaterial = toolMaterial;
		this.maxStackSize = 1;
		this.setMaxDamage(this.toolMaterial.getMaxUses());
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.weaponDamage = 4.0F + this.toolMaterial.getDamageVsEntity();
	}
    
	////@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
    	/*if (player.capabilities.isCreativeMode || (player.inventory.hasItem(NullPower.rifleAmmo) && player.inventory.consumeInventoryItem(NullPower.rifleAmmo)))
        {
    	    EntityRifleBolt entityBolt = new EntityRifleBolt(par2World, player, 4.0F);
    	    entityBolt.setIsCritical(true);
    	    ////int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);
            ////entityBolt.setDamage(weaponDamage + (double)k);
    	    ////int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);
    	    ////if (l > 0)
            ////{
    	    ////    entityBolt.setKnockbackStrength(l);
            ////}
    	    
    	    /*if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
    	        entityBolt.setFire(100);
            }*
    	    
    	    par1ItemStack.damageItem(1, player);
    	    ////par2World.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    	    if (!par2World.isRemote)
            {
        	    par2World.spawnEntityInWorld(entityBolt);
            }
        }*/
    	
    	return par1ItemStack;
	}
	
    ////@Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        return p_77654_1_;
    }
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    	stack.damageItem(1, attacker);
    	return true;
	}
	
	/**
	* Override to add custom weapon damage field rather than vanilla ItemSword's field
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
    ////@Override
	public Multimap getItemAttributeModifiers() {
    	Multimap multimap = HashMultimap.create();
    	////multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", weaponDamage, 0));
    	return multimap;
	}
	
	////@Override
	public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        /*if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
        {
            p_150894_1_.damageItem(1, p_150894_7_);
        }*/

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
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.BOW;
    }
    
    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }

    /**
     * Return the name for this tool's material.
     * @return Material name
     */
    public String getToolMaterialName()
    {
        return this.toolMaterial.toString();
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_)
    {
        return false;//this.toolMaterial.func_150995_f() == p_82789_2_.getItem() ? true : super.getIsRepairable(p_82789_1_, p_82789_2_);
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 72000;
    }
}
