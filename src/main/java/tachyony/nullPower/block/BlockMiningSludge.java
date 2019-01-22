package tachyony.nullPower.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.IFluidBlock;
import tachyony.nullPower.Reference;

public class BlockMiningSludge extends Block implements IPlantable {
	public BlockMiningSludge()
	{
		super(Material.GRASS); // Not plants
        setHardness(1F);
        setCreativeTab(CreativeTabs.MATERIALS);
        setRegistryName("blockMiningSludge");
        setUnlocalizedName(Reference.MODID + "." + "blockMiningSludge");
        setTickRandomly(true);
	}
	
    /**
     * Ticks the block if it's been scheduled.
     */
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
    	ForgeHooks.onCropsGrowPre(world, pos, state, true);
    	
    	// Spread to other blocks
    	MiningSludgeize(world, pos, pos.north());
    	MiningSludgeize(world, pos, pos.east());
    	MiningSludgeize(world, pos, pos.south());
    	MiningSludgeize(world, pos, pos.west());
    	MiningSludgeize(world, pos, pos.up());
    	MiningSludgeize(world, pos, pos.down());
    	
    	// Set source to air
    	world.setBlockToAir(pos); 
    	ForgeHooks.onCropsGrowPost(world, pos, state, world.getBlockState(pos));
    }

	private void MiningSludgeize(World world, BlockPos origPos, BlockPos pos) {
		// If block is not air
		if(!world.isAirBlock(pos))
		{
			Block block = world.getBlockState(pos).getBlock();
			
			//Same chunk only
			int chunkX = origPos.getX() / 16;
			int chunkZ = origPos.getZ() / 16;
			
			int newChunkX = pos.getX() / 16;
			int newChunkZ = pos.getZ() / 16;
			
			boolean inChunk = (chunkX == newChunkX) && (chunkZ == newChunkZ);
			if (inChunk)
			{
				// Strip away noise blocks
				if (block == Blocks.STONE || block == Blocks.DIRT || block == Blocks.SAND || block == Blocks.GRAVEL || block == Blocks.GRASS || block == Blocks.BEDROCK)
				{
					if (pos.getY() == 0)
					{
						Block bedrock = Blocks.BEDROCK;
						world.setBlockState(pos, bedrock.getDefaultState());
					}
					else
					{
						world.setBlockState(pos, this.getDefaultState());
					}
				}
				else if (block instanceof IFluidBlock || block instanceof BlockLiquid)
				{
					world.setBlockState(pos, this.getDefaultState());
				}
			}
			else
			{
				if (block instanceof IFluidBlock || block instanceof BlockLiquid)
				{
					Block stone = Blocks.COBBLESTONE;
					world.setBlockState(pos, stone.getDefaultState());
				}
			}
		}
	}
	
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Beach;
    }
    
    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.getDefaultState();
    }
}
