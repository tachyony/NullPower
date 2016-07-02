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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import tachyony.nullPower.NullPower;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Ender reed.
 */
public class BlockEnderReed extends Block implements IPlantable {
    /**
     * @param material Material
     */
    public BlockEnderReed(Material material) {
        super(material);
    }

    /**
     * Ticks the block if it's been scheduled.
     */
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (par1World.getBlock(par2, par3 - 1, par4) == Blocks.reeds || this.checkBlockCoordValid(par1World, par2, par3, par4))
        {
            if (par1World.isAirBlock(par2, par3 + 1, par4)) {
                int l;
                for (l = 1; par1World.getBlock(par2, par3 - l, par4) == this; ++l) {
                    ;
                }

                if (l < 11) {
                    int i1 = par1World.getBlockMetadata(par2, par3, par4);
                    if (i1 >= 7) {
                        par1World.setBlock(par2, par3 + 1, par4, this);
                        par1World.setBlockMetadataWithNotify(par2, par3, par4,
                                0, 4);
                    } else {
                        par1World.setBlockMetadataWithNotify(par2, par3, par4,
                                i1 + 1, 4);
                    }
                }
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified
     * coordinates. Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        Block block = par1World.getBlock(par2, par3 - 1, par4);
        if ((block == this) || (block == Blocks.obsidian)) {
            return true;
        }

        return false;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which
     * neighbor changed (coordinates passed are their own) Args: x, y, z,
     * neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3,
            int par4, Block par5) {
        this.checkBlockCoordValid(par1World, par2, par3, par4);
    }

    /**
     * Checks if current block pos is valid, if not, breaks the block as
     * dropable item. Used for reed and cactus. (func_150170_e)
     * @param par1World World
     * @param par2 X
     * @param par3 Y
     * @param par4 Z
     */
    protected final boolean checkBlockCoordValid(World par1World, int par2, int par3, int par4) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Can this block stay at this position. Similar to canPlaceBlockAt except
     * gets checked often with plants.
     */
    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this
     * box can change after the pool has been cleared to be reused)
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return NullPower.itemEnderReed;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether
     * or not to render the shared face of two adjacent blocks and also whether
     * the player can attach torches, redstone wire, etc to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False
     * (examples: signs, buttons, stairs, etc)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    @Override
    public Item getItem(World par1World, int par2, int par3, int par4) {
        return NullPower.itemEnderReed;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Beach;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied
     * against the blocks color. Note only called when first determining what to
     * render.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_,
            int p_149720_3_, int p_149720_4_) {
        return p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_)
                .getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
    }
}
