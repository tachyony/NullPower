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

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tachyony.nullPower.entity.EntityRifleBolt;
import tachyony.nullPower.handler.GuiHandler;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Mod class
 */
@Mod(name = Reference.MODNAME, modid = Reference.MODID, version = Reference.VERSION, acceptedMinecraftVersions="[1.12.2]", useMetadata = true,
	dependencies = "required-after:forge@[14.23.5.2806,)")
public class NullPower {
    /**
     * Common proxy
     */
    @SidedProxy(clientSide = "tachyony.nullPower.client.ClientProxy", serverSide = "tachyony.nullPower.ServerProxy")
    public static CommonProxy proxy;

    /**
     * Mod instance
     */
    @Mod.Instance(Reference.MODID)
    public static NullPower instance;

    /**
     * Configuration
     */
    public static Configuration configuration;

    /**
     * Logger
     */
    public static Logger logger;
    
    /**
     * Tool material
     */
    public static ToolMaterial enderIronMaterial = EnumHelper.addToolMaterial("EnderIron", 4, 1561, 12.0F, 6.0F, 22);
    
    /**
     * Tool material
     */
    public static ToolMaterial cheatMaterial = EnumHelper.addToolMaterial("CheatIron", 4, 8000, 40.0F, 100.0F, 30);
    
    /**
     * Null armour material
     */
    public static ArmorMaterial nullArmour = EnumHelper.addArmorMaterial("nullArmour", Reference.MODID + ":nullArmour", 25, new int[] { 3, 6, 8, 3 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);
    
    public static final Integer nullArmourRenderer = 5;
    
    /**
     * @param event Forge event
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        
        proxy.preInit(event);
        
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        Config.readConfig();

        // Ranged weapons
        EntityRegistry.registerModEntity(new ResourceLocation("EntityRifleBolt"), EntityRifleBolt.class, "RifleBoltD", 3, instance, 160, 1, true);
    }

    /**
     * @param event
     *            Forge event
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        
        /*GameRegistry.addRecipe(new ItemStack(rifleAmmo, 16, 0), "ci ", "   ",
                "   ", 'c', Items.CLAY_BALL, 'i', enderIron);*/
        /*GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(huntingRifle,
                1, 0), "iii", "css", "w  ", 'i', enderIron, 'c',
                enderGeneratorCore, 's', "stickWood", 'w', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(huntingRifleB,
                1, 0), "cii", "css", "w  ", 'i', enderIron, 'c',
                enderGeneratorCore, 's', Items.GOLD_INGOT, 'w', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(huntingRifleC,
                1, 0), "cii", "css", "i  ", 'i', enderIron, 'c',
                enderGeneratorCore, 's', Items.DIAMOND));*/
        GameRegistry.addSmelting(ObjectRegistrar.enderIronDust, new ItemStack(ObjectRegistrar.enderIron, 1, 0), 0.1f);
        
        OreDictCompat.registerOres();
        
    	// Register GuiHandler, makes GUIs work
    	NetworkRegistry.INSTANCE.registerGuiHandler(NullPower.instance, new GuiHandler());
    }

    /**
     * @param event Forge event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (configuration.hasChanged()) {
            configuration.save();
        }
    	
        proxy.postInit(event);
    }
    
    /**
     * @param logLevel Log level
     * @param message
     */
    public static void error(String message) {
        log(Level.ERROR, message);
    }
    
    /**
     * @param logLevel Log level
     * @param message
     */
    private static void log(Level logLevel, String message) {
        logger.log(logLevel, message);
    }
}
