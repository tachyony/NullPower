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

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import tachyony.nullPower.tile.TileEntityEnderGenerator3;

/**
 * Ender generator
 */
public class BlockEnderGenerator3 extends BlockContainer {
  /**
   * @param itemId Item id
   * @param material Material
   */
  public BlockEnderGenerator3(Material material) {
	super(material);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int metadata)
  {
    return new TileEntityEnderGenerator3();
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float px, float py, float pz)
  {
    if (world.isRemote) {
      return true;
    }
    
    return false;
  }
}
