package rikmuld.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import rikmuld.inventory.inventory.InventoryCamping;
import rikmuld.inventory.slot.BackpackNoSlot;
import rikmuld.inventory.slot.BackpackOnlySlot;
import rikmuld.inventory.slot.CamperToolOnlySlot;
import rikmuld.inventory.slot.CampingSlot;

public class ContainerCamping extends Container {
	
	public ItemStack backpack;
	public World worldObj;
	public EntityPlayer player;
	public InventoryCamping campingInv;
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public InventoryCrafting craftMatrix2 = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    public IInventory craftResult2 = new InventoryCraftResult();
    
	public ContainerCamping(InventoryPlayer playerInventory,  InventoryCamping campInv) {
		
		this.worldObj = playerInventory.player.worldObj;
		this.player = playerInventory.player;
		
		campingInv = campInv;
		
		this.addSlotToContainer(new BackpackOnlySlot(campingInv, 0, 142, 1));
		this.addSlotToContainer(new CamperToolOnlySlot(campingInv, 1, 17, 1));
		
		for (int row = 0; row < 3; ++row) for (int col = 0; col < 9; ++col) 
		{
			BackpackNoSlot slot = new BackpackNoSlot(campingInv, col + row * 9+2, 8 + col * 18, 24 + row * 18);
			slot.noItemsValid = true;
			campingInv.backpackSlots.add(slot);
			this.addSlotToContainer(slot);
		}
		
		this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, -45, 97));
		this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix2, this.craftResult2, 0, 208, 97));	
		
		for (int l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 3; ++i1)
            {
            	CampingSlot slot = new CampingSlot(this.craftMatrix, i1 + l * 3, -64 + i1 * 18, 3 + l * 18);
            	slot.noItemsValid = true;
            	campingInv.craftingSlots.add(slot);
                this.addSlotToContainer(slot);
            }
        }
		
		for (int l = 0; l < 3; ++l)
        {
            for (int i1 = 0; i1 < 3; ++i1)
            {
            	CampingSlot slot = new CampingSlot(this.craftMatrix2, i1 + l * 3, 189 + i1 * 18, 3 + l * 18);
            	slot.noItemsValid = true;
            	campingInv.craftingSlots.add(slot);
                this.addSlotToContainer(slot);
            }
        }

		campingInv.containerExsists = true;
		
        this.onCraftMatrixChanged(this.craftMatrix);
        this.onCraftMatrixChanged(this.craftMatrix2);
		
		
		for (int var3 = 0; var3 < 3; ++var3) 
		{
			for (int var4 = 0; var4 < 9; ++var4) 
			{
				this.addSlotToContainer(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 90 + var3 * 18));
			}
		}

		for (int var3 = 0; var3 < 9; ++var3) 
		{
			this.addSlotToContainer(new Slot(playerInventory, var3, 8 + var3 * 18, 148));
		}
		
		campingInv.openChest();
	}
	
	public void onCraftMatrixChanged(IInventory par1IInventory)
    {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
        this.craftResult2.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix2, this.worldObj));
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return true;
	}

	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);

        if (!this.worldObj.isRemote)
        {
            for (int i = 0; i < 9; ++i)
            {
                ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    par1EntityPlayer.dropPlayerItem(itemstack);
                }
                
                ItemStack itemstack2 = this.craftMatrix2.getStackInSlotOnClosing(i);

                if (itemstack != null)
                {
                    par1EntityPlayer.dropPlayerItem(itemstack2);
                }
            }
        }
    }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p, int i) 
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if(campingInv.getStackInSlot(0)!=null)
		{
			if (slot != null && slot.getHasStack()) 
			{
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				
				if (i < campingInv.getInventorySize()) 
				{
					if (!mergeItemStack(itemstack1, campingInv.getInventorySize(), inventorySlots.size(), true)) 
					{
						return null;
					}
				} 
				
				else if (!mergeItemStack(itemstack1, 0, campingInv.getInventorySize(), false)) 
				{
					return null;
				}
				
				if (itemstack1.stackSize == 0) 
				{
					slot.putStack(null);
				} 
				
				else 
				{
					slot.onSlotChanged();
				}
			}
		}
		return itemstack;
	}
	
	@Override
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
    {
        return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
    }
}
