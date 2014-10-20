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

/**
 * Ender generator
 */
public class TileEntityEnderGenerator3 extends TileEntityEnderGenerator {
    /**
     * 
     */
    public TileEntityEnderGenerator3()
    {
        super();
    }

    @Override
    protected int getIc2Tier() {
        return 3;
    }

    @Override
    protected int addIc2Energy() {
        return 500;
    }

    @Override
    protected int addRfEnergy() {
        return 2000;
    }
}
