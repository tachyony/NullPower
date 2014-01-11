package tachyony.nullPower.blocks;

import java.util.Random

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly
import net.minecraft.block.Block
import net.minecraft.block.BlockRotatedPillar
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IconRegister
import net.minecraft.util.Icon
import net.minecraft.world.World

class BlockLavaLog(blockId: Int, material: Material) extends BlockRotatedPillar(blockId, material) {
    var woodType: Array[String] = Array("lava");
    
    @SideOnly(Side.CLIENT)
    private var field_111052_c: Array[Icon] = new Array[Icon](1);
  
    @SideOnly(Side.CLIENT)
    private var tree_top: Array[Icon] = new Array[Icon](1);

    override def idDropped(par1: Int, par2Random: Random, par3: Int): Int = {
        blockId;
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int, par6: Int)
    {
        var b0: Byte = 4;
        var j1: Int = b0 + 1;
        if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1))
        {
            for (k1 <- -b0 until b0)
            {
                for (l1 <- -b0 until b0)
                {
                    for (i2 <- -b0 until b0)
                    {
                        var j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);
                        if (Block.blocksList(j2) != null)
                        {
                            Block.blocksList(j2).beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                        }
                    }
                }
            }
        }
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * The icon for the side of the block.
     */
    override def getSideIcon(par1: Int): Icon =
    {
        this.field_111052_c(par1);
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * The icon for the tops and bottoms of the block.
     */
    override protected def getEndIcon(par1: Int): Icon = {
        this.tree_top(par1);
    }
    
    @SideOnly(Side.CLIENT)
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    override def registerIcons(par1IconRegister: IconRegister)
    {
        this.field_111052_c = new Array[Icon](woodType.length);
        this.tree_top = new Array[Icon](woodType.length);
        for (i <- 0 until this.field_111052_c.length)
        {
            this.field_111052_c(i) = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType(i));
            this.tree_top(i) = par1IconRegister.registerIcon(this.getTextureName() + "_" + woodType(i) + "_top");
        }
    }
  
    override def canSustainLeaves(world: World, x: Int, y: Int, z: Int): Boolean =
    {
        true;
    }

    override def isWood(world: World, x: Int, y: Int, z: Int): Boolean =
    {
        true;
    }
}
