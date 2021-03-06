package rikmuld.core.helper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemStackHelper {

	public static ItemStack[] getMetaCycle(Object input, int maxMetadata)
	{
		ItemStack[] stack = new ItemStack[maxMetadata];
		for(int i = 0; i<maxMetadata; i++)
		{
			if(input instanceof Block) stack[i] = new ItemStack((Block)input, 1, i);
			if(input instanceof Item) stack[i] = new ItemStack((Item)input, 1, i);
		}
		return stack;
	}
	
	public static ItemStack getWildValue(ItemStack input)
	{
		ItemStack stack = new ItemStack(input.itemID, 1, OreDictionary.WILDCARD_VALUE);
		return stack;
	}
}
