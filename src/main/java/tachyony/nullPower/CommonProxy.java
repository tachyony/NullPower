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
package tachyony.nullPower;

import net.minecraft.block.Block;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tachyony.nullPower.block.BlockDerpyFurnace;
import tachyony.nullPower.block.BlockEnderGenerator;
import tachyony.nullPower.block.BlockEnderReed;
import tachyony.nullPower.block.BlockMiningSludge;
import tachyony.nullPower.item.EnderGeneratorCore;
import tachyony.nullPower.item.EnderGeneratorCoreAdvanced;
import tachyony.nullPower.item.EnderIron;
import tachyony.nullPower.item.EnderIronDust;
import tachyony.nullPower.item.IngotBronze;
import tachyony.nullPower.item.ItemDynamitePickaxe;
import tachyony.nullPower.item.ItemEnderReed;
import tachyony.nullPower.item.ItemNullWrench;
import tachyony.nullPower.item.NullArmor;
import tachyony.nullPower.item.PsuedoEnderPearl;
import tachyony.nullPower.tile.TileEntityDerpyFurnace;
import tachyony.nullPower.tile.TileEntityEnderGenerator;

/**
 * Common proxy
 */
@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    /**
     * Does nothing.
     * @param event 
     */
    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    	event.getRegistry().register(new BlockEnderGenerator());
    	event.getRegistry().register(new BlockEnderReed());
    	event.getRegistry().register(new BlockMiningSludge());
    	event.getRegistry().register(new BlockDerpyFurnace(false));
    	//?event.getRegistry().register(new BlockDerpyFurnace(true));
    	
        // Tile entity
        GameRegistry.registerTileEntity(TileEntityEnderGenerator.class, new ResourceLocation(Reference.MODID, "EnderGenerator"));
        GameRegistry.registerTileEntity(TileEntityDerpyFurnace.class, new ResourceLocation(Reference.MODID, "derpyfurnace"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    	 event.getRegistry().register(new PsuedoEnderPearl());
    	 event.getRegistry().register(new IngotBronze());
         event.getRegistry().register(new EnderIron());
    	 event.getRegistry().register(new EnderIronDust());
    	 event.getRegistry().register(new ItemDynamitePickaxe());
    	 event.getRegistry().register(new ItemNullWrench());
    	 event.getRegistry().register(new ItemEnderReed(ObjectRegistrar.blockEnderReed));
    	 event.getRegistry().register(new EnderGeneratorCore());
    	 event.getRegistry().register(new EnderGeneratorCoreAdvanced());
    	 
    	 event.getRegistry().register(new NullArmor(NullPower.nullArmourRenderer, EntityEquipmentSlot.HEAD, "nullHelmet"));
    	 event.getRegistry().register(new NullArmor(NullPower.nullArmourRenderer, EntityEquipmentSlot.CHEST, "nullChestplate"));
         event.getRegistry().register(new NullArmor(NullPower.nullArmourRenderer, EntityEquipmentSlot.LEGS, "nullLeggings"));
         event.getRegistry().register(new NullArmor(NullPower.nullArmourRenderer, EntityEquipmentSlot.FEET, "nullBoots"));
    	 
         ItemBlock blockEnderGenerator = new ItemBlock(ObjectRegistrar.blockEnderGenerator);
         blockEnderGenerator.setRegistryName(ObjectRegistrar.blockEnderGenerator.getRegistryName());
    	 event.getRegistry().register(blockEnderGenerator);
         ItemBlock blockEnderReed = new ItemBlock(ObjectRegistrar.blockEnderReed);
         blockEnderReed.setRegistryName(ObjectRegistrar.blockEnderReed.getRegistryName());
         event.getRegistry().register(blockEnderReed);
         ItemBlock blockMiningSludge = new ItemBlock(ObjectRegistrar.blockMiningSludge);
         blockMiningSludge.setRegistryName(ObjectRegistrar.blockMiningSludge.getRegistryName());
         event.getRegistry().register(blockMiningSludge);
         ItemBlock blockDerpyFurnace = new ItemBlock(ObjectRegistrar.blockDerpyFurnace);
         blockDerpyFurnace.setRegistryName(ObjectRegistrar.blockDerpyFurnace.getRegistryName());
         event.getRegistry().register(blockDerpyFurnace);
    }
}
