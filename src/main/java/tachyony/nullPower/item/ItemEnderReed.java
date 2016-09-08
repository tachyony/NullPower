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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Ender reed
 */
public class ItemEnderReed extends Item {
    private Block spawn;

    /**
     * @param spawn What the block is
     */
    public ItemEnderReed(Block spawn) {
        super();
        this.spawn = spawn;
    }

    /**
     * Callback for item usage. If the item does something special on right
     * clicking, he will have one of those. Return True if something happen and
     * false if it don't. This is for ITEMS, not BLOCKS
     */
    ////@Override
    public boolean onItemUse(ItemStack par1ItemStack,
            EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
            int par6, int par7, float par8, float par9, float par10) {
        /*Block block = par3World.getBlock(par4, par5, par6);
        if (block == Blocks.snow_layer && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
            par7 = 1;
        } else if (block != Blocks.vine && block != Blocks.tallgrass && block != Blocks.deadbush) {
            if (par7 == 0) {
                --par5;
            }

            if (par7 == 1) {
                ++par5;
            }

            if (par7 == 2) {
                --par6;
            }

            if (par7 == 3) {
                ++par6;
            }

            if (par7 == 4) {
                --par4;
            }

            if (par7 == 5) {
                ++par4;
            }
        }*/

        /*if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7,
                par1ItemStack)) {
            return false;
        } else*/ if (par1ItemStack.stackSize == 0) {
            return false;
        } else {
            /*if (par3World.canPlaceEntityOnSide(this.spawn, par4, par5, par6, false, par7, (Entity)null, par1ItemStack)) {
                int j1 = block.onBlockPlaced(par3World, par4, par5, par6, par7,
                        par8, par9, par10, 0);
                if (par3World.setBlock(par4, par5, par6, this.spawn, j1, 3)) {
                    if (par3World.getBlock(par4, par5, par6) == this.spawn) {
                        this.spawn.onBlockPlacedBy(par3World, par4, par5, par6,
                                par2EntityPlayer, par1ItemStack);
                        this.spawn.onPostBlockPlaced(par3World, par4, par5,
                                par6, j1);
                    }

                    par3World.playSoundEffect((par4 + 0.5F), (par5 + 0.5F),
                            (par6 + 0.5F), block.stepSound.func_150496_b(),
                            (block.stepSound.getVolume() + 1.0F) / 2.0F,
                            block.stepSound.getPitch() * 0.8F);
                    --par1ItemStack.stackSize;
                }
            }*/

            return true;
        }
    }
}
