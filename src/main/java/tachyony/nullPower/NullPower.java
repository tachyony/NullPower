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

import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tachyony.nullPower.block.BlockEnderGenerator;
import tachyony.nullPower.block.BlockEnderGenerator2;
import tachyony.nullPower.block.BlockEnderGenerator3;
import tachyony.nullPower.block.BlockEnderReed;
import tachyony.nullPower.entity.EntityRifleBolt;
import tachyony.nullPower.item.ItemDynamitePickaxe;
import tachyony.nullPower.item.ItemEnderReed;
import tachyony.nullPower.item.ItemHuntingRifle;
import tachyony.nullPower.tile.TileEntityEnderGenerator;
import tachyony.nullPower.tile.TileEntityEnderGenerator2;
import tachyony.nullPower.tile.TileEntityEnderGenerator3;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Mod class
 */
@Mod(name = "NullPower", modid = "NullPower", version = "1.0.4", dependencies = "required-after:IC2;required-after:CoFHLib")
public class NullPower {
    /**
     * Common proxy
     */
    @SidedProxy(clientSide = "tachyony.nullPower.client.ClientProxy", serverSide = "tachyony.nullPower.CommonProxy")
    public static CommonProxy proxy;

    /**
     * Mod instance
     */
    @Mod.Instance("NullPower")
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
     * Ender generator
     */
    public static Block enderGenerator2;

    /**
     * Ender generator
     */
    public static Block enderGenerator3;
    
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
     * @param event Forge event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();
        // Config here
        configuration.save();

        // Ranged weapons
        rifleAmmo = new Item().setCreativeTab(CreativeTabs.tabMaterials)
                .setUnlocalizedName("rifleAmmo").setTextureName("nullpower:rifleAmmo");
        huntingRifle = new ItemHuntingRifle(ToolMaterial.IRON)
                .setUnlocalizedName("huntingRifle").setTextureName("nullpower:huntingRifle");
        huntingRifleB = new ItemHuntingRifle(ToolMaterial.EMERALD)
                .setUnlocalizedName("huntingRifleB").setTextureName("nullpower:huntingRifleB");
        huntingRifleC = new ItemHuntingRifle(enderIronMaterial)
                .setUnlocalizedName("huntingRifleC").setTextureName("nullpower:huntingRifleC");
        huntingRifleD = new ItemHuntingRifle(cheatMaterial)
                .setUnlocalizedName("huntingRifleD").setTextureName("nullpower:huntingRifleD");
        itemDynamitePickaxe = new ItemDynamitePickaxe().setCreativeTab(CreativeTabs.tabTools)
                .setUnlocalizedName("dynamitePickaxe").setTextureName("nullpower:dynamitePickaxe");

        // Mod items for crafting
        enderIron = new Item()
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setUnlocalizedName("enderIron")
                .setTextureName("nullpower:enderIron");
        enderIronDust = new Item()
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setUnlocalizedName("enderIronDust")
                .setTextureName("nullpower:enderIronDust");
        enderGenerator = new BlockEnderGenerator(
                Material.iron).setHardness(1F)
                .setStepSound(Block.soundTypeMetal)
                .setBlockName("enderGenerator")
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setBlockTextureName("nullpower:enderGenerator");
        enderGenerator2 = new BlockEnderGenerator2(
                Material.iron).setHardness(1F)
                .setStepSound(Block.soundTypeMetal)
                .setBlockName("enderGenerator2")
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setBlockTextureName("nullpower:enderGenerator2");
        enderGenerator3 = new BlockEnderGenerator3(
                Material.iron).setHardness(1F)
                .setStepSound(Block.soundTypeMetal)
                .setBlockName("enderGenerator3")
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setBlockTextureName("nullpower:enderGenerator3");
        blockEnderReed = new BlockEnderReed(Material.plants)
                .setHardness(1F).setStepSound(Block.soundTypeGrass)
                .setBlockName("enderReed")
                .setBlockTextureName("nullpower:blockEnderReed");
        blockEnderReed.setBlockBounds(0.5F - 0.375F, 0.0F, 0.5F - 0.375F,
                0.5F + 0.375F, 1.0F, 0.5F + 0.375F);
        blockEnderReed.setTickRandomly(true);
        itemEnderReed = new ItemEnderReed(
                blockEnderReed).setUnlocalizedName("enderReed")
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setTextureName("nullpower:itemEnderReed");
        enderGeneratorCore = new Item()
                .setUnlocalizedName("enderGeneratorCore")
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setTextureName("nullpower:enderGeneratorCore");
        enderGeneratorCoreAdvanced = new Item()
        .setUnlocalizedName("enderGeneratorCoreAdvanced")
        .setCreativeTab(CreativeTabs.tabMaterials)
        .setTextureName("nullpower:enderGeneratorCoreAdvanced");
        GameRegistry.registerItem(rifleAmmo, "item.rifleAmmo");
        GameRegistry.registerItem(huntingRifle, "item.huntingRifle");
        GameRegistry.registerItem(huntingRifleB, "item.huntingRifleB");
        GameRegistry.registerItem(huntingRifleC, "item.huntingRifleC");
        GameRegistry.registerItem(huntingRifleD, "item.huntingRifleD");
        GameRegistry.registerItem(enderIron, "item.enderIron");
        GameRegistry.registerItem(enderIronDust, "item.enderIronDust");
        GameRegistry.registerBlock(enderGenerator, "block.enderGenerator");
        GameRegistry.registerBlock(enderGenerator2, "block.enderGenerator2");
        GameRegistry.registerBlock(enderGenerator3, "block.enderGenerator3");
        GameRegistry.registerItem(itemEnderReed, "item.enderReed");
        GameRegistry.registerBlock(blockEnderReed, "block.enderReed");
        GameRegistry.registerItem(enderGeneratorCore, "item.enderGeneratorCore");
        GameRegistry.registerItem(enderGeneratorCoreAdvanced, "item.enderGeneratorCoreAdvanced");
        GameRegistry.registerItem(itemDynamitePickaxe, "item.dynamitePickaxe");
    }

    /**
     * @param event
     *            Forge event
     */
    @SuppressWarnings("boxing")
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.addRecipe(new ItemStack(rifleAmmo, 16, 0), "ci ", "   ",
                "   ", 'c', Items.clay_ball, 'i', enderIron);
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
        Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(itemEnderReed, 1, 0)), null, new ItemStack(enderIronDust, 2, 0));
        GameRegistry.addRecipe(new ItemStack(Items.spawn_egg, 1, 58), "wbw",
                "brb", "wbw", 'w', Items.water_bucket, 'r', Items.reeds, 'b', Blocks.iron_bars);
        GameRegistry.addRecipe(new ItemStack(itemEnderReed, 1, 0), " e ",
                "wrw", " i ", 'w', Items.water_bucket, 'r', Items.reeds, 'i', Items.iron_ingot, 'e', Items.ender_pearl);
        GameRegistry.addSmelting(enderIronDust, new ItemStack(enderIron, 1, 0), 0.1f);
        GameRegistry.addRecipe(new ItemStack(enderGeneratorCore, 1, 0), "eee",
                "ewe", "eee", 'e', enderIron, 'w', Items.water_bucket);
        GameRegistry.addRecipe(new ItemStack(enderGeneratorCoreAdvanced, 1, 0), "ewe",
                "wew", "ewe", 'e', enderGeneratorCore, 'w', Items.water_bucket);
        GameRegistry.addRecipe(new ItemStack(enderGenerator, 1, 0), " w ",
                "wew", " c ", 'w', Items.water_bucket, 'e', enderGeneratorCore,
                'c', IC2Items.getItem("generator"));
        GameRegistry.addRecipe(new ItemStack(enderGenerator2, 1, 0), "iii",
                "i i", "aga", 'i', enderIron, 'a', enderGeneratorCoreAdvanced,
                'g', IC2Items.getItem("generator"));
        GameRegistry.registerTileEntity(TileEntityEnderGenerator.class, "EnderGenerator");
        GameRegistry.registerTileEntity(TileEntityEnderGenerator2.class, "EnderGenerator2");
        GameRegistry.registerTileEntity(TileEntityEnderGenerator3.class, "EnderGenerator3");
        EntityRegistry.registerGlobalEntityID(EntityRifleBolt.class, "RifleBoltD", EntityRegistry.findGlobalUniqueEntityId());
        EntityRegistry.registerModEntity(EntityRifleBolt.class, "RifleBoltD", 3, instance, 160, 1, true);
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
