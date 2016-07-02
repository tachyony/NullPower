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
package tachyony.nullPower.tile;

import tachyony.nullPower.powerNetwork.PowerNetwork;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Ender generator
 */
public class TileEntityEnderGenerator extends TileEntity {    
    private String owner;
    
    //IC2 protected BasicSource ic2EnergySource;

    //RF protected EnergyStorage storage;
    
    /**
     * 
     */
    public int MAXNETWORKPOWER = 10000000;
    
    /**
     * 
     */
    public TileEntityEnderGenerator()
    {
        super();
        owner = "";
    }

    /**
     * @param owner
     */
    public void setOwner(String owner)
    {
        this.owner = owner;
    }
    
    /**
     * @param owner
     * @return Owner
     */
    public String getOwner()
    {
        return owner;
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1) {
        super.writeToNBT(par1);
        par1.setString("owner", owner);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
        owner = par1.getString("owner");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote)
        {
            return;
        }
        
        int worldTime = (int) (worldObj.getWorldTime() % 24000);
        if (worldTime % 1 == 0)
        {
            String ownerName = owner;
            if (ownerName.equals("")) {
                return;
            }
            
            World world = MinecraftServer.getServer().worldServers[0];
            PowerNetwork data = (PowerNetwork) world.loadItemData(PowerNetwork.class, ownerName);
            if (data == null) {
                data = new PowerNetwork(ownerName);
                world.setItemData(ownerName, data);
            }
            
            int powerAdd = Math.min(addRfEnergy(), MAXNETWORKPOWER - data.currentPower);
            data.currentPower = powerAdd + data.currentPower;
            data.markDirty();
            
            if (worldObj != null)
            {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    /**
     * Power to add
     * @return Added power
     */
    private int addRfEnergy() {
        return 100;
    }
}
