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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tachyony.nullPower.CommonProxy;
import tachyony.nullPower.NullPower;
import tachyony.nullPower.Reference;
import tachyony.nullPower.entity.EntityRifleBolt;

/**
 * Client proxy
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    private final Minecraft mc = Minecraft.getMinecraft();
    
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
	
	@Override
    public void registerRenderers() {
        ItemModelMesher mesher = mc.getRenderItem().getItemModelMesher();
        
        registerItemRenderer(mesher, NullPower.rifleAmmo);
        registerItemRenderer(mesher, NullPower.huntingRifle);
        registerItemRenderer(mesher, NullPower.huntingRifleB);
        registerItemRenderer(mesher, NullPower.huntingRifleC);
        registerItemRenderer(mesher, NullPower.huntingRifleD);
        registerItemRenderer(mesher, NullPower.itemDynamitePickaxe);
        registerItemRenderer(mesher, NullPower.itemNullWrench);
        registerItemRenderer(mesher, NullPower.enderIron);
        registerItemRenderer(mesher, NullPower.enderIronDust);
        
        registerBlock(NullPower.blockEnderGenerator);
        registerItemBlock(mesher, NullPower.itemBlockEnderGenerator);
        registerBlock(NullPower.blockEnderReed);
        registerItemBlock(mesher, NullPower.itemBlockEnderReed);
        
        registerItemRenderer(mesher, NullPower.itemEnderReed);
        registerItemRenderer(mesher, NullPower.enderGeneratorCore);
        registerItemRenderer(mesher, NullPower.enderGeneratorCoreAdvanced);
        registerItemRenderer(mesher, NullPower.nullHelmet);
        registerItemRenderer(mesher, NullPower.nullChestplate);
        registerItemRenderer(mesher, NullPower.nullLeggings);
        registerItemRenderer(mesher, NullPower.nullBoots);
        
        RenderingRegistry.registerEntityRenderingHandler(EntityRifleBolt.class, new RenderSnowball(mc.getRenderManager(), NullPower.rifleAmmo, mc.getRenderItem()));
    }

    /**
     * Registers an item with no subtypes using the unlocalized name as the texture name
     */
    private void registerItemRenderer(ItemModelMesher mesher, Item item) {
        registerItemRenderer(mesher, item, 0);
    }
    
    /**
     * Registers a specific item subtype using the unlocalized name as the texture name
     * @param meta  Always 0 if only one type, otherwise the subtype's metadata value
     */
    private void registerItemRenderer(ItemModelMesher mesher, Item item, int meta) {
        String name = item.getRegistryName().toString();
        registerItemRenderer(mesher, item, name, meta);
    }
    
    /**
     * Registers a specific item subtype using the specified texture name
     * @param name  Exact name of the texture file to be used, including the "modid:" prefix
     * @param meta  Always 0 if only one type, otherwise the subtype's metadata value
     */
    private void registerItemRenderer(ItemModelMesher mesher, Item item, String name, int meta) {
        NullPower.logger.info("Registering renderer for " + name);
        mesher.register(item, meta, new ModelResourceLocation(name, "inventory"));
    }
    
    public static void registerItemBlock(ItemModelMesher mesher, ItemBlock block) {
        
    }
    
    public static void registerBlock(Block block) {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
        .register(item, 0, new ModelResourceLocation(
            item.getRegistryName(), "inventory"));  
        
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
            .register(item, 0, new ModelResourceLocation(
                Reference.MODID + ":" + item.getRegistryName().toString().substring(5), 
                "inventory"));      
        
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(block.getRegistryName(), "inventory"));
        
        String r = item.getRegistryName().toString();
        ModelResourceLocation loc = new ModelResourceLocation(
                r, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(
            item, 0, loc);
    }
}