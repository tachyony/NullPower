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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;

/**
 * 
 */
public class PowerNetworkHandler {
    /**
     * @param player
     * @return Name
     */
    public static ITextComponent getUsername(EntityPlayer player)
    {
        return player.getDisplayName();
    }
}
