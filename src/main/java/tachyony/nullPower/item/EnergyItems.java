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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 */
public class EnergyItems extends Item {
    /**
     * @param ownerName
     * @return Essence
     */
    public static int getCurrentPower(String ownerName) {
        /*if (MinecraftServer.getServer() == null) {
            return 0;
        }*/
        
        ////World world = MinecraftServer.getServer().worldServers[0];
        ////PowerNetwork data = (PowerNetwork) world.loadItemData(PowerNetwork.class, ownerName);
        ////if (data == null) {
            ////data = new PowerNetwork(ownerName);
            ////world.setItemData(ownerName, data);
        ////}
        
        return 0;////data.currentPower;
    }

    /**
     * @param item 
     * @param player
     */
    public static void checkAndSetItemOwner(ItemStack item, EntityPlayer player) {
        /*if (item.stackTagCompound == null) {
            item.setTagCompound(new NBTTagCompound());
        }
        
        if (item.stackTagCompound.getString("ownerName").equals("")) {
            item.stackTagCompound.setString("ownerName", PowerNetworkHandler.getUsername(player));
        }*/
        
        initializePlayer(player);
    }
    
    /**
     * @param player
     */
    public static void initializePlayer(EntityPlayer player) {
        NBTTagCompound tag = player.getEntityData();
        if (tag.getInteger("currentPower") == 0) {
            tag.setInteger("currentPower", 0);
        }
    }
    
    /**
     * @param item
     * @return Owner
     */
    public static String getOwnerName(ItemStack item) {
        /*if (item.stackTagCompound == null) {
            item.setTagCompound(new NBTTagCompound());
        }*/
        
        return "";////item.stackTagCompound.getString("ownerName");
    }
}
