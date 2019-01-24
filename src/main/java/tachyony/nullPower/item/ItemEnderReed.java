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

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tachyony.nullPower.Reference;

/**
 * Ender reed
 */
public class ItemEnderReed extends Item {
	private final Block block;
	
    /**
     * @param spawn What the block is
     */
    public ItemEnderReed(Block block) {
        super();
        setCreativeTab(CreativeTabs.MATERIALS);
        setRegistryName("enderReed");
        setUnlocalizedName(Reference.MODID + "." + "itemEnderReed");
        this.block = block;
    }

    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. This is for ITEMS, not BLOCKS
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing,
            float hitX, float hitY, float hitZ) {
    	IBlockState iblockstate = world.getBlockState(pos);
        Block block = iblockstate.getBlock();
        
        if (block == Blocks.SNOW_LAYER && (iblockstate.getValue(BlockSnow.LAYERS)).intValue() < 1)
        {
            facing = EnumFacing.UP;
        }
        else if (!block.isReplaceable(world, pos))
        {
            pos = pos.offset(facing);
        }
        
        ItemStack itemstack = player.getHeldItem(hand);
        
        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && world.mayPlace(this.block, pos, false, facing, (Entity)null))
        {
            IBlockState iblockstate1 = this.block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, 0, player, hand);
            
            if (!world.setBlockState(pos, iblockstate1, 11))
            {
                return EnumActionResult.FAIL;
            }
            else
            {
                iblockstate1 = world.getBlockState(pos);
                if (iblockstate1.getBlock() == this.block)
                {
                    ItemBlock.setTileEntityNBT(world, player, pos, itemstack);
                    iblockstate1.getBlock().onBlockPlacedBy(world, pos, iblockstate1, player, itemstack);
                    
                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }
                }

                SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, world, pos, player);
                world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F,
                        soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}
