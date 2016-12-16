package com.plutomc.core.common.crafting;

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
	private final List<AlloyRecipe> smeltingList = new ArrayList<AlloyRecipe>();

	public static AlloyFurnaceRecipes instance()
	{
		return INSTANCE;
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

		smeltingList.add(new AlloyRecipe(inputs, output, experience));
	}

	public AlloyRecipe getSmeltingRecipe(List<ItemStack> inputs)
	{
		for (AlloyRecipe entry : smeltingList)
		{
			if (entry.canSmelt(inputs))
			{
				return entry;
			}
		}

		return AlloyRecipe.EMPTY;
	}

	public float getSmeltingExperience(ItemStack stack)
	{
		for (AlloyRecipe entry : smeltingList)
		{
			if (entry.getOutput().isItemEqual(stack))
			{
				return entry.getExperience();
			}
		}

		return 0;
	}

	public List<AlloyRecipe> getSmeltingList()
	{
		return smeltingList;
	}
}
