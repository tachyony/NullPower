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

import java.util.logging.Level;
import java.util.logging.Logger;

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
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
@Mod(name = "NullPower", modid = "nullpower", version = "1.0.4", acceptedMinecraftVersions="[1.10.2]")
/*, dependencies = "required-after:IC2;required-after:CoFHLib"*/
public class NullPower {
    /**
     * Common proxy
     */
    @SidedProxy(clientSide = "tachyony.nullPower.client.ClientProxy", serverSide = "tachyony.nullPower.CommonProxy", modId="nullpower")
    public static CommonProxy proxy;

    /**
     * Mod instance
     */
    @Mod.Instance("nullpower")
    public static NullPower instance;

    /**
     * Configuration
     */
    public static Configuration configuration;

    /**
     * Logger
     */
    public static Logger logger = Logger.getLogger("NullPower");

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
     * Ender generator
     */
    public static Block enderGenerator;
    
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
    public static ArmorMaterial nullArmour = EnumHelper.addArmorMaterial("nullArmour", "nullpower:nullArmour", 25, new int[]{3, 9, 7, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    
    public static Integer nullArmourRenderer = 5;
    
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
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();
        // Config here
        configuration.save();

        // Ranged weapons
        rifleAmmo = new Item().setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("rifleAmmo");   
        huntingRifle = new ItemHuntingRifle(ToolMaterial.IRON).setRegistryName("huntingRifle");
        huntingRifleB = new ItemHuntingRifle(ToolMaterial.DIAMOND).setRegistryName("huntingRifleB");
        huntingRifleC = new ItemHuntingRifle(enderIronMaterial).setRegistryName("huntingRifleC");
        huntingRifleD = new ItemHuntingRifle(cheatMaterial).setRegistryName("huntingRifleD");
        itemDynamitePickaxe = new ItemDynamitePickaxe().setCreativeTab(CreativeTabs.TOOLS).setRegistryName("dynamitePickaxe");
        
        // Mod items for crafting
        enderIron = new Item().setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("enderIron");
        enderIronDust = new Item().setCreativeTab(CreativeTabs.MATERIALS).setRegistryName("enderIronDust");
        enderGenerator = new BlockEnderGenerator(Material.IRON).setHardness(1F)//.setStepSound(Block.soundTypeMetal)
                .setRegistryName("enderGenerator").setCreativeTab(CreativeTabs.MATERIALS);
        blockEnderReed = new BlockEnderReed(Material.PLANTS).setHardness(1F)//.setStepSound(Block.soundTypeGrass)
                .setRegistryName("enderReed").setCreativeTab(CreativeTabs.MATERIALS);
        ////blockEnderReed.setBlockBounds(0.5F - 0.375F, 0.0F, 0.5F - 0.375F, 0.5F + 0.375F, 1.0F, 0.5F + 0.375F);
        blockEnderReed.setTickRandomly(true);
        itemEnderReed = new ItemEnderReed(blockEnderReed).setRegistryName("enderReed").setCreativeTab(CreativeTabs.MATERIALS);
        enderGeneratorCore = new Item().setRegistryName("enderGeneratorCore").setCreativeTab(CreativeTabs.MATERIALS);
        enderGeneratorCoreAdvanced = new Item().setRegistryName("enderGeneratorCoreAdvanced").setCreativeTab(CreativeTabs.MATERIALS);
        
        // Null armour
        nullHelmet = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.HEAD).setRegistryName("nullHelmet").setCreativeTab(CreativeTabs.COMBAT);
        nullChestplate = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.CHEST).setRegistryName("nullChestplate").setCreativeTab(CreativeTabs.COMBAT);
        nullLeggings = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.LEGS).setRegistryName("nullLeggings").setCreativeTab(CreativeTabs.COMBAT);
        nullBoots = new NullArmor(nullArmour, nullArmourRenderer, EntityEquipmentSlot.FEET).setRegistryName("nullBoots").setCreativeTab(CreativeTabs.COMBAT);
        
        rifleAmmo.setUnlocalizedName(rifleAmmo.getRegistryName().toString());
        huntingRifle.setUnlocalizedName(huntingRifle.getRegistryName().toString());
        huntingRifleB.setUnlocalizedName(huntingRifleB.getRegistryName().toString());
        huntingRifleC.setUnlocalizedName(huntingRifleC.getRegistryName().toString());
        huntingRifleD.setUnlocalizedName(huntingRifleD.getRegistryName().toString());
        itemDynamitePickaxe.setUnlocalizedName(itemDynamitePickaxe.getRegistryName().toString());
        enderIron.setUnlocalizedName(enderIron.getRegistryName().toString());
        enderIronDust.setUnlocalizedName(enderIronDust.getRegistryName().toString());
        enderGenerator.setUnlocalizedName(enderGenerator.getRegistryName().toString());
        blockEnderReed.setUnlocalizedName(blockEnderReed.getRegistryName().toString());
        itemEnderReed.setUnlocalizedName(itemEnderReed.getRegistryName().toString());
        enderGeneratorCore.setUnlocalizedName(enderGeneratorCore.getRegistryName().toString());
        enderGeneratorCoreAdvanced.setUnlocalizedName(enderGeneratorCoreAdvanced.getRegistryName().toString());
        nullHelmet.setUnlocalizedName(nullHelmet.getRegistryName().toString());
        nullChestplate.setUnlocalizedName(nullChestplate.getRegistryName().toString());
        nullLeggings.setUnlocalizedName(nullLeggings.getRegistryName().toString());
        nullBoots.setUnlocalizedName(nullBoots.getRegistryName().toString());
        
        // Register items
        GameRegistry.register(rifleAmmo);
        GameRegistry.register(huntingRifle);
        GameRegistry.register(huntingRifleB);
        GameRegistry.register(huntingRifleC);
        GameRegistry.register(huntingRifleD);
        GameRegistry.register(enderIron);
        GameRegistry.register(enderIronDust);
        GameRegistry.register(enderGenerator);
        GameRegistry.register(itemEnderReed);
        GameRegistry.register(blockEnderReed);
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
        ////EntityRegistry.registerGlobalEntityID(EntityRifleBolt.class, "RifleBoltD", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityRifleBolt.class, "RifleBoltD", 3, instance, 160, 1, true);
        
        // Add armour renderer
        ////RenderingRegistry.addNewArmourRendererPrefix(nullArmourRenderer.toString());
        
        proxy.registerTextures();
    }

    /**
     * @param logLevel Log level
     * @param message
     */
    public static void log(Level logLevel, String message) {
        logger.log(logLevel, message);
    }
}
