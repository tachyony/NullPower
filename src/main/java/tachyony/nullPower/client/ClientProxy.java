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
package tachyony.nullPower.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tachyony.nullPower.CommonProxy;
import tachyony.nullPower.ObjectRegistrar;

/**
 * Client proxy
 */
@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }
    
	@Override
	public void init(FMLInitializationEvent event) {
	    super.init(event);
	}
	
	@Override
    public void postInit(FMLPostInitializationEvent event) {
	    super.postInit(event);
    }
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
        registerItemRenderer(ObjectRegistrar.rifleAmmo);
        registerItemRenderer(ObjectRegistrar.huntingRifle);
        registerItemRenderer(ObjectRegistrar.huntingRifleB);
        registerItemRenderer(ObjectRegistrar.huntingRifleC);
        registerItemRenderer(ObjectRegistrar.huntingRifleD);
        registerItemRenderer(ObjectRegistrar.itemDynamitePickaxe);
        registerItemRenderer(ObjectRegistrar.itemNullWrench);
        registerItemRenderer(ObjectRegistrar.psuedoEnderPearl);
        registerItemRenderer(ObjectRegistrar.ingotBronze);
        registerItemRenderer(ObjectRegistrar.enderIron);
        registerItemRenderer(ObjectRegistrar.enderIronDust);
        
        registerBlock(ObjectRegistrar.blockEnderGenerator);
        registerBlock(ObjectRegistrar.blockEnderReed);
        registerBlock(ObjectRegistrar.blockMiningSludge);
        registerBlock(ObjectRegistrar.blockDerpyFurnace);
        
        registerItemRenderer(ObjectRegistrar.itemEnderReed);
        registerItemRenderer(ObjectRegistrar.enderGeneratorCore);
        registerItemRenderer(ObjectRegistrar.enderGeneratorCoreAdvanced);
        registerItemRenderer(ObjectRegistrar.nullHelmet);
        registerItemRenderer(ObjectRegistrar.nullChestplate);
        registerItemRenderer(ObjectRegistrar.nullLeggings);
        registerItemRenderer(ObjectRegistrar.nullBoots);
        
        //RenderingRegistry.registerEntityRenderingHandler(EntityRifleBolt.class, new RenderSnowball(mc.getRenderManager(), NullPower.rifleAmmo, mc.getRenderItem()));
    }
    
    /**
     * Registers a specific item subtype using the specified texture name
     * @param name  Exact name of the texture file to be used, including the "modid:" prefix
     * @param meta  Always 0 if only one type, otherwise the subtype's metadata value
     */
    private static void registerItemRenderer(Item item) {
    	ResourceLocation name = item.getRegistryName();
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
    }
    
    public static void registerBlock(Block block) {
        Item item = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}