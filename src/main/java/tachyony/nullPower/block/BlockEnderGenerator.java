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
package tachyony.nullPower.block;

import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tachyony.nullPower.item.EnergyItems;
import tachyony.nullPower.tile.TileEntityEnderGenerator;

/**
 * Ender generator
 */
public class BlockEnderGenerator extends BlockContainer implements ITileEntityProvider {
    /**
     * @param material Material
     */
    public BlockEnderGenerator(Material material) {
      super(material);
    }
    
    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    /**
     * Determines if the player can harvest this block, obtaining it's drops when the block is destroyed.
     *
     * @param player The player damaging the block, may be null
     * @param meta The block's current metadata
     * @return True to spawn the drops
     */
    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
        return true;
    }

    /**
     * Create new tile entity
     */
    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileEntityEnderGenerator();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntityEnderGenerator tileEntity = (TileEntityEnderGenerator)world.getTileEntity(pos);
        if  ((tileEntity == null) || player.isSneaking()) {
            return false;
        }
        
        ItemStack playerItem = player.getHeldItem(hand);
        if (playerItem == null)
        {
            return false;
        }
        
        Item item = playerItem.getItem();
        if (!(item instanceof EnergyItems))
        {
            return false;
        }
        
        if (world.isRemote)
        {
            return true;
        }
        
        // Set to different user if needed to align network correctly
        ((EnergyItems)item).checkAndSetItemOwner(playerItem, player);
        
        // Set owner to current energy item
        tileEntity.setOwner(EnergyItems.getOwnerName(playerItem));
        player.addChatMessage(new TextComponentString("Current owner: " + tileEntity.getOwner()));
        world.notifyBlockUpdate(pos, state, state, 3);
        return true;
    }
}
