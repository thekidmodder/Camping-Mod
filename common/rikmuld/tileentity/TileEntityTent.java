package rikmuld.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;

public class TileEntityTent extends TileEntity implements IInventory, ISidedInventory {
	
	private ItemStack[] TentStacks = new ItemStack[55];

	public int getSizeInventory() 
	{
		return this.TentStacks.length;
	}

	public ItemStack getStackInSlot(int par1) 
	{
		return this.TentStacks[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) 
	{
		if (this.TentStacks[par1] != null) 
		{
			ItemStack var3;

			if (this.TentStacks[par1].stackSize <= par2) 
			{
				var3 = this.TentStacks[par1];
				this.TentStacks[par1] = null;
				return var3;
			} 
			
			else 
			{
				var3 = this.TentStacks[par1].splitStack(par2);

				if (this.TentStacks[par1].stackSize == 0) 
				{
					this.TentStacks[par1] = null;
				}
				return var3;
			}
		} 
		
		else
		{
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) 
	{
		if (this.TentStacks[par1] != null) 
		{
			ItemStack var2 = this.TentStacks[par1];
			this.TentStacks[par1] = null;
			return var2;
		} 
		
		else 
		{
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) 
	{
		this.TentStacks[par1] = par2ItemStack;

		if (par2ItemStack != null&& par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	public String getInvName() 
	{
		return "container.furnace";
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) 
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.TentStacks = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) 
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");

			if (var5 >= 0 && var5 < this.TentStacks.length) 
			{
				this.TentStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) 
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();
		
		for (int var3 = 0; var3 < this.TentStacks.length; ++var3) 
		{
			if (this.TentStacks[var3] != null) 
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.TentStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	public int getInventoryStackLimit() 
	{
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) 
	{
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
	}

	public void openChest() {}
	public void closeChest() {}

	@Override
	public int getStartInventorySide(ForgeDirection side) 
	{
		if (side == ForgeDirection.DOWN) return 1;
		if (side == ForgeDirection.UP) return 0;
		return 2;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) 
	{
		return 1;
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return true;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) 
	{
		return true;
	}
}