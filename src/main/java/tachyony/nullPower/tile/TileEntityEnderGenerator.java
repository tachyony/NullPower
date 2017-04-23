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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import tachyony.nullPower.powerNetwork.PowerNetwork;

/**
 * Ender generator
 */
public class TileEntityEnderGenerator extends TileEntity implements ITickable, IEnergyStorage {    
    private String owner;
    
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
    public NBTTagCompound writeToNBT(NBTTagCompound par1) {
        super.writeToNBT(par1);
        par1.setString("owner", owner);
        return par1;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1) {
        super.readFromNBT(par1);
        owner = par1.getString("owner");
    }

    @Override
    public void update() {
        if (worldObj.isRemote)
        {
            return;
        }
        
        int worldTime = (int)(worldObj.getWorldTime() % 24000);
        if (worldTime % 1 == 0)
        {
            String ownerName = owner;
            if (ownerName.equals("")) {
                return;
            }
            
            World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers[0];
            PowerNetwork data = (PowerNetwork)world.loadItemData(PowerNetwork.class, ownerName);
            if (data == null) {
                data = new PowerNetwork(ownerName);
                world.setItemData(ownerName, data);
            }
            
            int powerAdd = Math.min(addEnergy(), MAXNETWORKPOWER - data.currentPower);
            data.currentPower = powerAdd + data.currentPower;
            data.markDirty();
        }
    }

    /**
     * Power to add
     * @return Added power
     */
    private int addEnergy() {
        return 100;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (owner == "")
        {
            return 0;
        }
        
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (owner == "")
        {
            return 0;
        }
        
        return 0;
    }

    @Override
    public int getEnergyStored() {
        if (owner == "")
        {
            return 0;
        }
        
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return MAXNETWORKPOWER;
    }

    @Override
    public boolean canExtract() {
        if (owner == "")
        {
            return false;
        }
        
        return false;
    }

    @Override
    public boolean canReceive() {
        if (owner == "")
        {
            return false;
        }
        
        return false;
    }
}
