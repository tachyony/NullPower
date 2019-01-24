package tachyony.nullPower.tile;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import tachyony.nullPower.block.BlockDerpyFurnace;

public class TileEntityDerpyFurnace extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final int[] SLOTS_TOP = new int[] {0};
    
    private static final int[] SLOTS_BOTTOM = new int[] {2, 1};
    
    private static final int[] SLOTS_SIDES = new int[] {1};
    
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStackHandler itemStackHandler = new ItemStackHandler(3);
	
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    private int currentItemBurnTime;
    
    private int cookTime;
    
    private int totalCookTime = 200;
    
    private String furnaceCustomName;

    /**
     * See TileEntityFurnace
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return 3;
    }

    @Override
    public boolean isEmpty()
    {
    	if (this.itemStackHandler.getStackInSlot(0).isEmpty() || this.itemStackHandler.getStackInSlot(1).isEmpty() || this.itemStackHandler.getStackInSlot(2).isEmpty())
    	{
        	return false;
        }
    	else
    	{
    		return true;
    	}
    }

    /**
     * Returns the stack in the given slot.
     */
    @Override
    public ItemStack getStackInSlot(int index)
    {
        if (index == 0)
        {
        	return this.itemStackHandler.getStackInSlot(0);
        }
        else if (index == 1)
        {
        	return this.itemStackHandler.getStackInSlot(1);
        }
        else if (index == 2)
        {
        	return this.itemStackHandler.getStackInSlot(2);
        }
        else
        {
        	return ItemStack.EMPTY;
        }
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if (index == 0)
        {
        	return this.itemStackHandler.extractItem(0, count, false);
        }
        else if (index == 1)
        {
        	return this.itemStackHandler.extractItem(1, count, false);
        }
        else if (index == 2)
        {
        	return this.itemStackHandler.extractItem(2, count, false);
        }
        else
        {
        	return ItemStack.EMPTY;
        }
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
    	ItemStack itemStack = this.getStackInSlot(index);
    	this.itemStackHandler.setStackInSlot(index, ItemStack.EMPTY);
        return itemStack;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        ItemStack itemStack = getStackInSlot(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
        this.itemStackHandler.setStackInSlot(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    /**
     * Get the name of this object. For players this returns their username
     */
    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    }

    @Override
	public ITextComponent getDisplayName() 
	{
		return this.hasCustomName() ? new TextComponentString(this.furnaceCustomName) : new TextComponentTranslation("container.derpyfurnace");
	}
    
    /**
     * Returns true if this thing is named
     */
    @Override
    public boolean hasCustomName()
    {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomName(String customName) 
	{
		this.furnaceCustomName = customName;
	}

    public void setCustomInventoryName(String customName)
    {
        this.furnaceCustomName = customName;
    }

    public static void registerFixesFurnace(DataFixer fixer)
    {
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TileEntityDerpyFurnace.class, new String[] {"Items"}));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.itemStackHandler = new ItemStackHandler(3);
        this.itemStackHandler.deserializeNBT(compound.getCompoundTag("inventory"));
        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.itemStackHandler.getStackInSlot(1));

        if (compound.hasKey("CustomName", 8))
        {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", (short)this.furnaceBurnTime);
        compound.setInteger("CookTime", (short)this.cookTime);
        compound.setInteger("CookTimeTotal", (short)this.totalCookTime);
        compound.setTag("inventory", this.itemStackHandler.serializeNBT());

        if (this.hasCustomName())
        {
            compound.setString("CustomName", this.furnaceCustomName);
        }

        return compound;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
	public static boolean isBurning(TileEntityDerpyFurnace te) 
	{
		return te.getField(0) > 0;
	}
    
    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    /**
     * Update method, does things
     */
    @Override
    public void update()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.itemStackHandler.getStackInSlot(1);

            if (this.isBurning() || !itemstack.isEmpty() && !(this.itemStackHandler.getStackInSlot(0).isEmpty()))
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    this.furnaceBurnTime = getItemBurnTime(itemstack);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isBurning())
                    {
                        flag1 = true;

                        if (!itemstack.isEmpty())
                        {
                        	Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty())
                            {
                                ItemStack item1 = item.getContainerItem(itemstack);
                                this.itemStackHandler.setStackInSlot(1, item1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt())
                {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime)
                    {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.itemStackHandler.getStackInSlot(0));
                        this.smeltItem();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }
            else if (!this.isBurning() && this.cookTime > 0)
            {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning())
            {
                flag1 = true;
                BlockDerpyFurnace.setState(this.isBurning(), this.world, this.pos);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    public int getCookTime(ItemStack stack)
    {
        return 200;
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if (this.itemStackHandler.getStackInSlot(0).isEmpty())
        {
            return false;
        }
        else
        {
        	// Not smelting it, compare source item instead
            ItemStack itemStack = this.itemStackHandler.getStackInSlot(0);
        	
            if (itemStack.isEmpty())
            {
                return false;
            }
            else
            {
                ItemStack itemstack1 = this.itemStackHandler.getStackInSlot(2);

                if (itemstack1.isEmpty())
                {
                    return true;
                }
                else if (!itemstack1.isItemEqual(itemStack))
                {
                    return false;
                }
                else if (itemstack1.getCount() + itemStack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemStack.getCount() <= itemstack1.getMaxStackSize())  // Forge fix: make furnace respect stack sizes in furnace recipes
                {
                    return true;
                }
                else
                {
                    return itemstack1.getCount() + itemStack.getCount() <= itemStack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
                }
            }
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = this.itemStackHandler.getStackInSlot(0);
            
            // Smelting result, or source item in our case
            //ItemStack itemStack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
            ItemStack itemStack1 = itemstack;
        	///Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:stone"));
        	///ItemStack itemStack1 = new ItemStack(item, 1, 1); // item, amount(1), metadata
            ///Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("nullpower:enderreed"));
            ///Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("nullpower:blockendergenerator"));
            ///ItemStack itemStack1 = new ItemStack(item, 1, 0); // item, amount(1), metadata
            ///itemStack1.setTagCompound(nbt);
            
            ItemStack itemstack2 = this.itemStackHandler.getStackInSlot(2);

            if (itemstack2.isEmpty())
            {
            	ItemStack copied = itemStack1.copy();
            	copied.setCount(1);
                this.itemStackHandler.setStackInSlot(2, copied);
            }
            else if (itemstack2.getItem() == itemStack1.getItem())
            {
                itemstack2.grow(1);
            }

            // Disable functionality
            //if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !this.itemStackHandler.getStackInSlot(1).isEmpty() && this.itemStackHandler.getStackInSlot(1).getItem() == Items.BUCKET)
            //{
            //    this.itemStackHandler.setStackInSlot(1, new ItemStack(Items.WATER_BUCKET));
            //}

            // Remove one from source item, not wanted in this case
            //itemstack.shrink(1);
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     */
    public static int getItemBurnTime(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return 0;
        }
        else
        {
            int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
            if (burnTime >= 0)
            	return burnTime;
            
            Item item = stack.getItem();
            if ((item == Item.getItemFromBlock(Blocks.WOODEN_SLAB)) ||
        		(item == Item.getItemFromBlock(Blocks.WOOL)) ||
        		(item == Item.getItemFromBlock(Blocks.CARPET)) ||
        		(item == Item.getItemFromBlock(Blocks.LADDER)) ||
        		(item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON)) ||
        		(item == Items.STICK) ||
        		(item == Items.SIGN) ||
        		(item == Items.BOW) ||
        		(item == Items.FISHING_ROD) ||
        		(item == Item.getItemFromBlock(Blocks.SAPLING)) ||
        		(item == Items.BOWL) ||
        		(item instanceof ItemDoor) ||
        		(item == Items.IRON_DOOR) ||
        		(item instanceof ItemBoat))
            {
                return 0;
            }
            else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
            {
                return 0;
            }
            else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
            {
                return 0;
            }
            else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
            {
                return 0;
            }
            else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
            {
                return 0;
            }
            // Actually valid stuff
            else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
            {
                return 16000;
            }
            else if (item == Items.COAL)
            {
                return 1600;
            }
            else if (item == Items.LAVA_BUCKET)
            {
                return 20000;
            }
            else if (item == Items.BLAZE_ROD)
            {
                return 2400;
            }
            else
            {
                return 0;
            }
        }
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        if (this.world.getTileEntity(this.pos) != this)
        {
            return false;
        }
        else
        {
            return player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
     * guis use Slot.isItemValid
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 2)
        {
            return false;
        }
        else if (index != 1)
        {
            return true;
        }
        else
        {
            ItemStack itemstack = this.itemStackHandler.getStackInSlot(1);
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        if (side == EnumFacing.DOWN)
        {
            return SLOTS_BOTTOM;
        }
        else
        {
            return side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES;
        }
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side.
     */
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side.
     */
    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1)
        {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET)
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getGuiID()
    {
        return "nullpower:derpyfurnace";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFurnace(playerInventory, this);
    }

    @Override
    public int getField(int id)
    {
        switch (id)
        {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id)
        {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
                break;
            default:
            	break;
        }
    }

    @Override
    public int getFieldCount()
    {
        return 4;
    }

    @Override
    public void clear()
    {
    	for(int i = 0; i < this.getSizeInventory(); i++) {
    		this.itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
    	}
    }

    IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
    
    IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
    
    IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

    @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		else
			return false;
	}
    
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
}