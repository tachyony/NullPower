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
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import tachyony.nullPower.entity.EntityRifleBolt;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Hunting rifle
 */
public class ItemHuntingRifle extends Item {
    private float attackDamage;
    
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
		this.bFull3D = true;
		this.setMaxDamage(this.toolMaterial.getMaxUses());
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.attackDamage = 4.0F + this.toolMaterial.getDamageVsEntity();
	}
    
	@SuppressWarnings("unused")
    @Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
    	if (playerIn.capabilities.isCreativeMode || true/*((playerIn.inventory.hasItemStack(NullPower.rifleAmmo) && playerIn.inventory.consumeInventoryItem(NullPower.rifleAmmo)))*/)
        {
    	    EntityRifleBolt entityBolt = new EntityRifleBolt(worldIn, playerIn, 4.0F);
    	    entityBolt.setIsCritical(true);
    	    int k = 0;//EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStackIn);
            entityBolt.setDamage(attackDamage + (double)k);
    	    int l = 1;//EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStackIn);
    	    if (l > 0)
            {
    	        entityBolt.setKnockbackStrength(l);
            }
    	    
    	    //if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStackIn) > 0)
            {
    	        entityBolt.setFire(100);
            }
    	    
    	    itemStackIn.damageItem(1, playerIn);
    	    //worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
    	    if (!worldIn.isRemote)
            {
    	        worldIn.spawnEntityInWorld(entityBolt);
            }
    	    
    	    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
        }
    	else
    	{
    	    return new ActionResult(EnumActionResult.FAIL, itemStackIn);
    	}
	}
	
    /*obsolete @Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        return p_77654_1_;
    }*/
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    	stack.damageItem(1, attacker);
    	return true;
	}
	
	/**
	* Override to add custom weapon damage field rather than vanilla ItemSword's field
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
	
    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
	@Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }

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
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = this.toolMaterial.getRepairItemStack();
        if (mat != null && OreDictionary.itemMatches(mat, repair, false))
        {
            return true;
        }
        
        return super.getIsRepairable(toRepair, repair);
    }
    
    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 72000;
    }
    
    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();
        if (block == Blocks.WEB)
        {
            return 15.0F;
        }
        else
        {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }
    
    /**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
    public float getDamageVsEntity()
    {
        return this.toolMaterial.getDamageVsEntity();
    }
    
    /**
     * Check whether this Item can harvest the given Block
     */
    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return blockIn.getBlock() == Blocks.WEB;
    }
}
