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
package tachyony.nullPower.powerNetwork;

import tachyony.nullPower.Reference;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

/**
 * 
 */
public class PowerNetwork extends WorldSavedData {
    private static final String DATA_NAME = Reference.MODID + "_CurrentPower";
    
    /**
     * 
     */
    public int currentPower;

    public PowerNetwork() {
        super(DATA_NAME);
    }
    
    /**
     * @param par1Str
     */
    public PowerNetwork(String par1Str) {
        super(par1Str);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        currentPower = nbttagcompound.getInteger("currentPower");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("currentPower", currentPower);
        return nbttagcompound;
    }
}
