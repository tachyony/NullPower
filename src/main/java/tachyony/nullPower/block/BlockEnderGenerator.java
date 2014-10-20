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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import tachyony.nullPower.item.EnergyItems;
import tachyony.nullPower.item.ItemDynamitePickaxe;
import tachyony.nullPower.tile.TileEntityEnderGenerator;

/**
 * Ender generator
 */
public class BlockEnderGenerator extends BlockContainer {
  /**
   * @param itemId Item id
   * @param material Material
   */
  public BlockEnderGenerator(Material material) {
	super(material);
  }

  @Override
  public TileEntity createNewTileEntity(World world, int metadata)
  {
      return new TileEntityEnderGenerator();
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float px, float py, float pz)
  {
      TileEntityEnderGenerator tileEntity = (TileEntityEnderGenerator)world.getTileEntity(x, y, z);
      if  ((tileEntity == null) || player.isSneaking()) {
          return false;
      }
      
      ItemStack playerItem = player.getCurrentEquippedItem();
      if (playerItem == null)
      {
          return false;
      }
      
      Item item = playerItem.getItem();
      if (!(item instanceof ItemDynamitePickaxe))
      {
          return false;
      }
      
      if (world.isRemote)
      {
          return true;
      }
      
      tileEntity.setOwner(EnergyItems.getOwnerName(playerItem));
      player.addChatMessage(new ChatComponentText("Current Power: " + tileEntity.getPower() + "/ " + tileEntity.getOwner()));
      world.markBlockForUpdate(x, y, z);
      return true;
  }
}
