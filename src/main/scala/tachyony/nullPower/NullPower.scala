package tachyony.nullPower

import java.util.logging.Level
import java.util.logging.Logger
import cpw.mods.fml.common.FMLLog
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.SidedProxy
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent
import cpw.mods.fml.common.event.FMLPostInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.network.NetworkMod
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.registry.LanguageRegistry
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.common.Configuration
import tachyony.nullPower.blocks.BlockLavaLeaf
import tachyony.nullPower.blocks.BlockLavaLog
import tachyony.nullPower.items.ItemHuntingRifle

@Mod(name = "NullPower", modid = "nullpower", version = "1.0", modLanguage="scala")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
object NullPower {
	@SidedProxy(clientSide="tachyony.nullPower.client.ClientProxy", serverSide="tachyony.nullPower.CommonProxy")
	var proxy:CommonProxy = null;
 
	var configuration: Configuration = null;
	
	var logger: Logger = Logger.getLogger("NullPower");
	
	var lavaLog: Block = null;
	
	var lavaLeaf: Block = null;
	
	var rifleAmmo: Item = null;
	
	var huntingRifle: Item = null;
	
    @EventHandler
    def preInit(event: FMLPreInitializationEvent) {
		logger.setParent(FMLLog.getLogger());
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		configuration.load();
		var lavaLeafId = configuration.getBlock("LavaLeaf", 410).getInt();
		var lavaLogId = configuration.getBlock("LavaLog", 411).getInt();
		var rifleAmmoId = configuration.getItem("rifleAmmo", 21200).getInt();
		var huntingRifleId = configuration.getItem("huntingRifle", 21201).getInt();
		configuration.save();
		lavaLog = new BlockLavaLog(lavaLogId, Material.wood).setHardness(4F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("block.lavaLog").setCreativeTab(CreativeTabs.tabBlock).setTextureName("nullpower:lavaLog");
		lavaLeaf = new BlockLavaLeaf(lavaLeafId, Material.leaves).setHardness(1F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("block.lavaLeaf").setCreativeTab(CreativeTabs.tabBlock).setTextureName("nullpower:lavaLeaf").setLightValue(1.0F).setTickRandomly(true);
		rifleAmmo = new Item(rifleAmmoId).setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("item.rifleAmmo").setTextureName("nullpower:rifleAmmo");
		huntingRifle = new ItemHuntingRifle(huntingRifleId).setCreativeTab(CreativeTabs.tabCombat).setUnlocalizedName("item.huntingRifle").setTextureName("nullpower:huntingRifle");
		GameRegistry.registerBlock(lavaLog, "block.lavaLog");
		GameRegistry.registerBlock(lavaLeaf, "block.lavaLeaf");
		GameRegistry.registerItem(rifleAmmo, "item.rifleAmmo");
		GameRegistry.registerItem(huntingRifle, "item.huntingRifle");
		LanguageRegistry.addName(lavaLog, "Lava log");
		LanguageRegistry.addName(lavaLeaf, "Lava leaf");
		LanguageRegistry.addName(rifleAmmo, "Hunting Rifle ammunition");
		LanguageRegistry.addName(huntingRifle, "Hunting Rifle");
   }
        
   @EventHandler
   def init(event: FMLInitializationEvent) {
       GameRegistry.addRecipe(new ItemStack(rifleAmmo, 16, 0), "cf ", "   ", "   ", 'c'.asInstanceOf[Object], Item.clay, 'f'.asInstanceOf[Object], Item.flint);
       GameRegistry.addRecipe(new ItemStack(huntingRifle, 1, 0), "iii", "fss", "w  ", 'i'.asInstanceOf[Object], Item.ingotIron, 'f'.asInstanceOf[Object], Item.flintAndSteel, 's'.asInstanceOf[Object], Item.stick, 'w'.asInstanceOf[Object], Block.planks);
   }
        
   @EventHandler
   def postInit(event:FMLPostInitializationEvent) {
   }
   
    @EventHandler
    def handleIMCMessages(event:IMCEvent) {
    }
    
    def log(logLevel:Level, message:String) {
        logger.log(logLevel, message);
    }
}
