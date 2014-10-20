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

import ic2.api.energy.prefab.BasicSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tachyony.nullPower.powerNetwork.PowerNetwork;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

/**
 * Ender generator
 */
public class TileEntityEnderGenerator extends TileEntity implements
        IEnergyHandler {
    private BasicSource ic2EnergySource = new BasicSource(this, 2048, 1);

    private EnergyStorage storage = new EnergyStorage(1024);

    private int powerDrain = 10;
    
    private String owner;
    
    /**
     * 
     */
    public TileEntityEnderGenerator()
    {
        owner = "";
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1) {
        super.writeToNBT(par1);
        par1.setString("owner", owner);
        this.ic2EnergySource.writeToNBT(par1);
        this.storage.writeToNBT(par1);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
        owner = par1.getString("owner");
        this.ic2EnergySource.readFromNBT(par1);
        this.storage.readFromNBT(par1);
    }

    @Override
    public void onChunkUnload() {
        this.ic2EnergySource.onChunkUnload();
    }

    @Override
    public void invalidate() {
        this.ic2EnergySource.invalidate();
        super.invalidate();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote)
        {
            return;
        }
        
        this.ic2EnergySource.updateEntity();
        if (this.storage.getEnergyStored() >= this.storage.getMaxEnergyStored()) {
            this.storage.receiveEnergy(20, false);
        } else if (this.ic2EnergySource.getFreeCapacity() > 0) {
            this.ic2EnergySource.addEnergy(5);
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
            
            int powerDrained = Math.min(powerDrain, this.storage.getEnergyStored());
            data.currentPower = powerDrained + data.currentPower;
            data.markDirty();
            
            if (worldObj != null)
            {
                worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            }
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive,
            boolean simulate) {
        return this.storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract,
            boolean simulate) {
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return this.storage.getMaxEnergyStored();
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
    
    /**
     * @param owner
     * @return Owner
     */
    public String getPower()
    {
        return "" + this.storage.getEnergyStored() + this.ic2EnergySource.getEnergyStored();
    }
}
