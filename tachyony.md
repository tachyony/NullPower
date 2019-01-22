NullPower Reference Doc

//RenderingRegistry.registerEntityRenderingHandler(EntityRifleBolt.class, new RenderSnowball(mc.getRenderManager(), NullPower.rifleAmmo, mc.getRenderItem()));

is ender reed automatable?
is ender reed watering can compatible?
actual use for the null wrench
texture for nullWrench
texture for derpyfurnace
??EntityRifleBolt
??ItemHuntingRifle
??ItemRifleAmmo

no ItemStack parameter
ItemStack itemStack = player.getHeldItem(hand);

set stack size
stack.setCount() / stack.getCount()
also:
itemstack.shrink(1);

getSound > getSoundType(iblockstate1, worldIn, pos, playerIn);

public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
World > IBlockAccess

public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
new fromPos parameter

GameRegistry > 
new registry system in 1.12, new recipe system, stuff now done in CommonProxy

getRepairItem() > direct reference to item

register tile entity needs ResourceLocation > GameRegistry.registerTileEntity(TileEntityEnderGenerator.class, new ResourceLocation(Reference.MODID, "EnderGenerator"));

EnergyItems/ PowerNetwork gone, obsolete and not needed now

net item/ block rendering events
ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(name, "inventory"));
block version:
Item item = Item.getItemFromBlock(block);
ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
+ change event
@SubscribeEvent
public static void registerModels(ModelRegistryEvent event) { ...
+ resource names need to be lower case

Registers all Minecraft stuff
Item.class: public static void registerItems()

reeds =
registerItem(338, "reeds", (new ItemBlockSpecial(Blocks.REEDS)).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.MATERIALS));
reeds class = ItemBlockSpecial

update forge commands:
gradlew.bat cleanCache
gradlew setupDecompWorkspace --refresh-dependencies
gradlew eclipse

* https://wiki.mcjty.eu/modding/index.php?title=Main_Page
* https://suppergerrie2.com/category/forge-tutorial/
* https://github.com/HarryTechRevs/Minecraft-Modding-1.12/
* https://www.youtube.com/watch?v=rARVLYm9TBs&list=PLiDUvCGH5WEUEV9nc0Ll2pzUFmSFc21uR&index=21
* https://www.youtube.com/watch?v=iL1Ix56zQmI&index=27&list=PLiDUvCGH5WEUEV9nc0Ll2pzUFmSFc21uR
