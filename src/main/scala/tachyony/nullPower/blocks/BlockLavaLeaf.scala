package tachyony.nullPower.blocks;

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.minecraftforge.common.IShearable
import net.minecraft.world.World
import java.util.ArrayList
import net.minecraft.entity.player.EntityPlayer
import java.util.Random
import cpw.mods.fml.relauncher.SideOnly
import cpw.mods.fml.relauncher.Side
import net.minecraft.block.BlockBreakable
import net.minecraft.client.renderer.texture.IconRegister

class BlockLavaLeaf(blockId: Int, material: Material) extends BlockBreakable(blockId, "ice", material, false) with IShearable {
    var adjacentTreeBlocks: Array[Int] = null;

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int, par6: Int)
    {
        var b0: Byte = 1;
        var j1: Int = b0 + 1;
        if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1))
        {
            for (k1 <- -b0 until b0)
            {
                for (l1 <- -b0 until b0)
                {
                    for (i2 <- -b0 until b0)
                    {
                        var j2: Int = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);
                        if (Block.blocksList(j2) != null)
                        {
                            Block.blocksList(j2).beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Ticks the block if it's been scheduled
     */
    override def updateTick(par1World: World, par2: Int, par3: Int, par4: Int, par5Random: Random)
    {
        if (!par1World.isRemote)
        {
            var l = par1World.getBlockMetadata(par2, par3, par4);
            if ((l & 8) != 0 && (l & 4) == 0)
            {
                var b0: Byte = 4;
                var i1: Int = b0 + 1;
                var b1: Byte = 32;
                var j1: Int = b1 * b1;
                var k1: Int = b1 / 2;
                if (this.adjacentTreeBlocks == null)
                {
                    this.adjacentTreeBlocks = Array[Int](b1 * b1 * b1);
                }

                /*if (par1World.checkChunksExist(par2 - i1, par3 - i1, par4 - i1, par2 + i1, par3 + i1, par4 + i1))
                {
                    for (l1 <- -b0 until b0)
                    {
                        for (i2 <- -b0 until b0)
                        {
                            for (j2 <- -b0 until b0)
                            {
                                var k2 = par1World.getBlockId(par2 + l1, par3 + i2, par4 + j2);
                                var block = Block.blocksList(k2);
                                if (block != null && block.canSustainLeaves(par1World, par2 + l1, par3 + i2, par4 + j2))
                                {
                                    adjacentTreeBlocks((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1) = 0;
                                }
                                else if (block != null && block.isLeaves(par1World, par2 + l1, par3 + i2, par4 + j2))
                                {
                                    adjacentTreeBlocks((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1) = -2;
                                }
                                else
                                {
                                    adjacentTreeBlocks((l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1) = -1;
                                }
                            }
                        }
                    }

                    for (l1 <- 1 to 4)
                    {
                        for (i2 <- -b0 until b0)
                        {
                            for (j2 <- -b0 until b0)
                            {
                                for (k2 <- -b0 until b0)
                                {
                                    if (this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1) == l1 - 1)
                                    {
                                        if (this.adjacentTreeBlocks((i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1) == -2)
                                        {
                                            this.adjacentTreeBlocks((i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1) = l1;
                                        }

                                        if (this.adjacentTreeBlocks((i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1) == -2)
                                        {
                                            this.adjacentTreeBlocks((i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1) = l1;
                                        }

                                        if (this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1) == -2)
                                        {
                                            this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1) = l1;
                                        }

                                        if (this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1) == -2)
                                        {
                                            this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1) = l1;
                                        }

                                        if (this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)) == -2)
                                        {
                                            this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)) = l1;
                                        }

                                        if (this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1) == -2)
                                        {
                                            this.adjacentTreeBlocks((i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1) = l1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var l1 = this.adjacentTreeBlocks(k1 * j1 + k1 * b1 + k1);
                if (l1 >= 0)
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, l & -9, 4);
                }
                else
                {
                    this.removeLeaves(par1World, par2, par3, par4);
                }*/
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    override def randomDisplayTick(par1World: World, par2: Int, par3: Int, par4: Int, par5Random: Random)
    {
        if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1)
        {
            var d0: Double = (par2.asInstanceOf[Float] + par5Random.nextFloat()).asInstanceOf[Double];
            var d1: Double = par3.asInstanceOf[Double] - 0.05D;
            var d2: Double = (par4.asInstanceOf[Float] + par5Random.nextFloat()).asInstanceOf[Double];
            par1World.spawnParticle("dripLava", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
  
    private def removeLeaves(par1World: World, par2: Int, par3: Int, par4: Int)
    {
        this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
        par1World.setBlockToAir(par2, par3, par4);
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    override def quantityDropped(par1Random: Random): Int =
    {
        0;
    }
    
    override def getMobilityFlag(): Int =
    {
        0;
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    override def idDropped(par1: Int, par2Random: Random, par3: Int): Int =
    {
        blockID;
    }
  
   /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    override def harvestBlock(par1World: World, par2EntityPlayer: EntityPlayer, par3: Int, par4: Int, par5: Int, par6: Int)
    {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }
    
    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    override protected def createStackedBlock(par1: Int): ItemStack =
    {
        new ItemStack(this.blockID, 1, par1 & 3);
    }
  
    override def isShearable(item: ItemStack, world: World, x: Int, y: Int, z: Int): Boolean =
    {
        true;
    }
    
    override def onSheared(item: ItemStack, world: World, x: Int, y: Int, z: Int, fortune: Int): ArrayList[ItemStack] =
    {
        var ret: ArrayList[ItemStack] = new ArrayList[ItemStack];
        ret.add(new ItemStack(this, 1, 0));
        ret;
    }
  
  override def beginLeavesDecay(world: World, x: Int, y: Int, z: Int)
    {
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
    }
  
    override def isLeaves(world: World, x: Int, y: Int, z: Int): Boolean =
    {
        true;
    }
}
