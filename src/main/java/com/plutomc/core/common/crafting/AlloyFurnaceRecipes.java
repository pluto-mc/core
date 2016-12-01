package com.plutomc.core.common.crafting;

import com.plutomc.core.init.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * plutomc_core
 * Copyright (C) 2016  Kevin Boxhoorn
 *
 * plutomc_core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * plutomc_core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with plutomc_core.  If not, see <http://www.gnu.org/licenses/>.
 */
public class AlloyFurnaceRecipes
{
	private static final AlloyFurnaceRecipes INSTANCE = new AlloyFurnaceRecipes();
	private final List<AlloySmeltingRecipe> smeltingList = new ArrayList<AlloySmeltingRecipe>();

	public static AlloyFurnaceRecipes instance()
	{
		return INSTANCE;
	}

	private AlloyFurnaceRecipes()
	{
		addSmeltingRecipe(new ArrayList<ItemStack>() {{
			add(new ItemStack(ItemRegistry.COPPER_INGOT, 8));
			add(new ItemStack(ItemRegistry.TIN_INGOT));
		}}, new ItemStack(ItemRegistry.BRONZE_INGOT, 9), 0.8f);
	}

	public void addSmeltingRecipeForBlock(final List<Block> inputs, ItemStack stack, float experience)
	{
		addSmelting(new ArrayList<Item>() {{
			add(Item.getItemFromBlock(inputs.get(0)));
			add(Item.getItemFromBlock(inputs.get(1)));
		}}, stack, experience);
	}

	public void addSmelting(final List<Item> inputs, final ItemStack stack, final float experience)
	{
		addSmeltingRecipe(new ArrayList<ItemStack>() {{
			add(new ItemStack(inputs.get(0), 1, 32767));
			add(new ItemStack(inputs.get(1), 1, 32767));
		}}, stack, experience);
	}

	public void addSmeltingRecipe(List<ItemStack> inputs, ItemStack output, float experience)
	{
		if (!getSmeltingRecipe(inputs).getOutput().isEmpty())
		{
			return;
		}
		smeltingList.add(new AlloySmeltingRecipe(inputs, output, experience));
	}

	public AlloySmeltingRecipe getSmeltingRecipe(List<ItemStack> inputs)
	{
		for (AlloySmeltingRecipe entry : smeltingList)
		{
			if (compareItemInputs(inputs, entry.getInputs()))
			{
				return entry;
			}
		}

		return AlloySmeltingRecipe.EMPTY;
	}

	public float getSmeltingExperience(List<ItemStack> inputs)
	{
		for (AlloySmeltingRecipe entry : smeltingList)
		{
			if (compareItemInputs(inputs, entry.getInputs()))
			{
				return entry.getExperience();
			}
		}

		return 0;
	}

	private boolean compareItemInputs(List<ItemStack> input1, List<ItemStack> input2)
	{
		return input1.size() == 2 && input2.size() == 2
				&& compareItemStacks(input1.get(0), input2.get(0))
				&& compareItemStacks(input1.get(1), input2.get(1));
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() == stack2.getItem()
				&& (stack1.getMetadata() == 32767 && stack2.getMetadata() == 32767
				|| stack1.getMetadata() == stack2.getMetadata());
	}

	public List<AlloySmeltingRecipe> getSmeltingList()
	{
		return smeltingList;
	}
}
