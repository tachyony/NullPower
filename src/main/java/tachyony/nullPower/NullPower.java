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
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import tachyony.nullPower.block.BlockEnderGenerator;
import tachyony.nullPower.block.BlockEnderReed;
import tachyony.nullPower.entity.EntityRifleBolt;
import tachyony.nullPower.item.ItemDynamitePickaxe;
import tachyony.nullPower.item.ItemEnderReed;
import tachyony.nullPower.item.ItemHuntingRifle;
import tachyony.nullPower.item.NullArmor;
import tachyony.nullPower.tile.TileEntityEnderGenerator;

/**
 * Mod class
 */
@Mod(name = Reference.MODNAME, modid = Reference.MODID, version = Reference.VERSION, acceptedMinecraftVersions="[1.10.2]")
/*, dependencies = "required-after:IC2;required-after:CoFHLib"*/
public class NullPower {
    /**
     * Common proxy
     */
    @SidedProxy(clientSide = "tachyony.nullPower.client.ClientProxy", serverSide = "tachyony.nullPower.CommonProxy")
    public static CommonProxy proxy;

    /**
     * Mod instance
     */
    @Instance(Reference.MODID)
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
     * Rifle ammo
     */
    public static Item rifleAmmo;

    /**
     * Hunting rifle
     */
    public static Item huntingRifle;

    /**
     * Hunting rifle
     */
    public static Item huntingRifleB;

    /**
     * Hunting rifle
     */
    public static Item huntingRifleC;

    /**
     * Hunting rifle
     */
    public static Item huntingRifleD;
    
    /**
     * Ender iron
     */
    public static Item enderIron;

    /**
     * Ender iron dust
     */
    public static Item enderIronDust;

    /**
     * Ender reed
     */
    public static Item itemEnderReed;

    /**
     * Useless tool
     */
    public static Item itemDynamitePickaxe;

    /**
     * Ender reed
     */
    public static Block blockEnderReed;

    /**
     * Ender reed
     */
    public static ItemBlock itemBlockEnderReed;
    
    /**
     * Ender generator
     */
    public static Block blockEnderGenerator;
    
    /**
     * Ender generator
     */
    public static ItemBlock itemBlockEnderGenerator;
    
    /**
     * Ender core
     */
    public static Item enderGeneratorCore;

    /**
     * Ender core
     */
    public static Item enderGeneratorCoreAdvanced;
    
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
    public static ArmorMaterial nullArmour = EnumHelper.addArmorMaterial("nullArmour", Reference.MODID + ":nullArmour", 25, new int[]{3, 9, 7, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    
    public static final Integer nullArmourRenderer = 5;
    
    /**
     * Null armour
     */
    public static Item nullHelmet;
    
    /**
     * Null armour
     */
    public static Item nullChestplate;
    
    /**
     * Null armour
     */
    public static Item nullLeggings;
    
    /**
     * Null armour
     */
    public static Item nullBoots;
    
    /**
     * @param event Forge event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        
        proxy.preInit(event);
        
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();
        // Config here
        configuration.save();

        // Ranged weapons
        rifleAmmo = new Item().setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("rifleAmmo");   
        EntityRegistry.registerModEntity(EntityRifleBolt.class, "RifleBoltD", 3, instance, 160, 1, true);
        
        huntingRifle = new ItemHuntingRifle(ToolMaterial.IRON).setRegistryName("huntingRifle");
        huntingRifleB = new ItemHuntingRifle(ToolMaterial.DIAMOND).setRegistryName("huntingRifleB");
        huntingRifleC = new ItemHuntingRifle(enderIronMaterial).setRegistryName("huntingRifleC");
        huntingRifleD = new ItemHuntingRifle(cheatMaterial).setRegistryName("huntingRifleD");
        itemDynamitePickaxe = new ItemDynamitePickaxe().setCreativeTab(CreativeTabs.TOOLS).setRegistryName("dynamitePickaxe");
        
        // Mod items for crafting
        enderIron = new Item().setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("enderIron");
        enderIronDust = new Item().setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("enderIronDust");
        
        blockEnderGenerator = new BlockEnderGenerator(Material.IRON).setHardness(1F).setCreativeTab(CreativeTabs.MATERIALS);
        blockEnderGenerator.setRegistryName("blockEnderGenerator");
        itemBlockEnderGenerator = new ItemBlock(blockEnderGenerator);
        itemBlockEnderGenerator.setRegistryName("itemBlockEnderGenerator");
        
        blockEnderReed = new BlockEnderReed(Material.PLANTS).setHardness(1F).setCreativeTab(CreativeTabs.MATERIALS);
        blockEnderReed.setRegistryName("blockEnderReed");
        blockEnderReed.setTickRandomly(true);
        itemBlockEnderReed = new ItemBlock(blockEnderReed);
        itemBlockEnderReed.setRegistryName("itemBlockEnderReed");
        
        itemEnderReed = new ItemEnderReed(blockEnderReed).setRegistryName("enderReed").setCreativeTab(CreativeTabs.MATERIALS);
        enderGeneratorCore = new Item().setRegistryName("enderGeneratorCore").setCreativeTab(CreativeTabs.MATERIALS);
        enderGeneratorCoreAdvanced = new Item().setRegistryName("enderGeneratorCoreAdvanced").setCreativeTab(CreativeTabs.MATERIALS);
        
        // Null armour
        nullHelmet = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.HEAD).setRegistryName("nullHelmet").setCreativeTab(CreativeTabs.COMBAT);
        nullChestplate = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.CHEST).setRegistryName("nullChestplate").setCreativeTab(CreativeTabs.COMBAT);
        nullLeggings = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.LEGS).setRegistryName("nullLeggings").setCreativeTab(CreativeTabs.COMBAT);
        nullBoots = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.FEET).setRegistryName("nullBoots").setCreativeTab(CreativeTabs.COMBAT);
        
        rifleAmmo.setUnlocalizedName(Reference.MODID + "." + "rifleAmmo");
        huntingRifle.setUnlocalizedName(Reference.MODID + "." + "huntingRifle");
        huntingRifleB.setUnlocalizedName(Reference.MODID + "." + "huntingRifleB");
        huntingRifleC.setUnlocalizedName(Reference.MODID + "." + "huntingRifleC");
        huntingRifleD.setUnlocalizedName(Reference.MODID + "." + "huntingRifleD");
        itemDynamitePickaxe.setUnlocalizedName(Reference.MODID + "." + "itemDynamitePickaxe");
        enderIron.setUnlocalizedName(Reference.MODID + "." + "enderIron");
        enderIronDust.setUnlocalizedName(Reference.MODID + "." + "enderIronDust");
        
        blockEnderGenerator.setUnlocalizedName(Reference.MODID + "." + "blockEnderGenerator");
        blockEnderReed.setUnlocalizedName(Reference.MODID + "." + "blockEnderReed");
        itemBlockEnderGenerator.setUnlocalizedName(Reference.MODID + "." + "itemBlockEnderGenerator");
        itemBlockEnderReed.setUnlocalizedName(Reference.MODID + "." + "itemBlockEnderReed");
        
        itemEnderReed.setUnlocalizedName(Reference.MODID + "." + "itemEnderReed");
        enderGeneratorCore.setUnlocalizedName(Reference.MODID + "." + "enderGeneratorCore");
        enderGeneratorCoreAdvanced.setUnlocalizedName(Reference.MODID + "." + "enderGeneratorCoreAdvanced");
        nullHelmet.setUnlocalizedName(Reference.MODID + "." + "nullHelmet");
        nullChestplate.setUnlocalizedName(Reference.MODID + "." + "nullChestplate");
        nullLeggings.setUnlocalizedName(Reference.MODID + "." + "nullLeggings");
        nullBoots.setUnlocalizedName(Reference.MODID + "." + "nullBoots");
        
        // Register items
        GameRegistry.register(rifleAmmo);
        GameRegistry.register(huntingRifle);
        GameRegistry.register(huntingRifleB);
        GameRegistry.register(huntingRifleC);
        GameRegistry.register(huntingRifleD);
        GameRegistry.register(enderIron);
        GameRegistry.register(enderIronDust);
        
        GameRegistry.register(blockEnderGenerator);
        GameRegistry.register(itemBlockEnderGenerator);
        GameRegistry.register(blockEnderReed);
        GameRegistry.register(itemBlockEnderReed);
        
        GameRegistry.register(itemEnderReed);
        GameRegistry.register(enderGeneratorCore);
        GameRegistry.register(enderGeneratorCoreAdvanced);
        GameRegistry.register(itemDynamitePickaxe);
        GameRegistry.register(nullHelmet);
        GameRegistry.register(nullChestplate);
        GameRegistry.register(nullLeggings);
        GameRegistry.register(nullBoots);
    }

    /**
     * @param event
     *            Forge event
     */
    @SuppressWarnings("boxing")
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        
        // Register block, item, and entity renderers after they have been initialized and
        // registered in pre-init; however, Minecraft's RenderItem and ModelMesher instances
        // must also be ready, so we have to register renderers during init, not earlier
        proxy.registerRenderers();
        
        GameRegistry.addRecipe(new ItemStack(rifleAmmo, 16, 0), "ci ", "   ",
                "   ", 'c', Items.CLAY_BALL, 'i', enderIron);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(huntingRifle,
                1, 0), "iii", "css", "w  ", 'i', enderIron, 'c',
                enderGeneratorCore, 's', "stickWood", 'w', "plankWood"));
        /*GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(huntingRifleB,
                1, 0), "iii", "css", "w  ", 'i', enderIron, 'c',
                enderGeneratorCore, 's', Item.ingotGold, 'w', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(huntingRifleC,
                1, 0), "iii", "css", "w  ", 'i', enderIron, 'c',
                enderGeneratorCore, 's', Item.diamond, 'w', "plankWood"));*/
        GameRegistry.addShapelessRecipe(new ItemStack(enderIronDust, 1, 0), new Object[]{new ItemStack(itemEnderReed)});
        GameRegistry.addRecipe(new ItemStack(Items.SPAWN_EGG, 1, 58), "wbw",
                "brb", "wbw", 'w', Items.WATER_BUCKET, 'r', Items.REEDS, 'b', Blocks.IRON_BARS);
        GameRegistry.addRecipe(new ItemStack(itemEnderReed, 1, 0), " e ",
                "wrw", " i ", 'w', Items.WATER_BUCKET, 'r', Items.REEDS, 'i', Items.IRON_INGOT, 'e', Items.ENDER_PEARL);
        GameRegistry.addSmelting(enderIronDust, new ItemStack(enderIron, 1, 0), 0.1f);
        GameRegistry.addRecipe(new ItemStack(enderGeneratorCore, 1, 0), "eee",
                "ewe", "eee", 'e', enderIron, 'w', Items.WATER_BUCKET);
        GameRegistry.addRecipe(new ItemStack(enderGeneratorCoreAdvanced, 1, 0), "ewe",
                "wew", "ewe", 'e', enderGeneratorCore, 'w', Items.WATER_BUCKET);
        GameRegistry.registerTileEntity(TileEntityEnderGenerator.class, "EnderGenerator");
    }

    /**
     * @param event Forge event
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
    
    /**
     * @param logLevel Log level
     * @param message
     */
    public static void log(Level logLevel, String message) {
        logger.log(logLevel, message);
    }
}
