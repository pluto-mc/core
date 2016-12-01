package com.plutomc.core.common.crafting;

import com.google.common.collect.Maps;
import com.plutomc.core.init.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

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
	public class AlloySmeltingResult
	{
		private final ItemStack[] inputs;
		private final ItemStack output;

		public AlloySmeltingResult(ItemStack[] inputs, ItemStack output)
		{
			this.inputs = inputs;
			this.output = output;
		}

		public ItemStack[] getInputs()
		{
			return inputs;
		}

		public ItemStack getOutput()
		{
			return output;
		}
	}

	private static final AlloyFurnaceRecipes SMELTING_BASE = new AlloyFurnaceRecipes();
	private final Map<ItemStack[], ItemStack> smeltingList = Maps.newHashMap();
	private final Map<ItemStack[], Float> experienceList = Maps.newHashMap();

	public static AlloyFurnaceRecipes instance()
	{
		return SMELTING_BASE;
	}

	private AlloyFurnaceRecipes()
	{
		addSmeltingRecipe(new ItemStack[] { new ItemStack(ItemRegistry.COPPER_INGOT, 8), new ItemStack(ItemRegistry.TIN_INGOT) }, new ItemStack(ItemRegistry.BRONZE_INGOT, 9), 0.8f);
	}

	public void addSmeltingRecipeForBlock(Block[] input, ItemStack stack, float experience)
	{
		addSmelting(new Item[] { Item.getItemFromBlock(input[0]), Item.getItemFromBlock(input[1]) }, stack, experience);
	}

	public void addSmelting(Item[] input, ItemStack stack, float experience)
	{
		addSmeltingRecipe(new ItemStack[] { new ItemStack(input[0], 1, 32767), new ItemStack(input[1], 1, 32767) }, stack, experience);
	}

	public void addSmeltingRecipe(ItemStack[] input, ItemStack stack, float experience)
	{
		if (!getSmeltingResult(input).getOutput().isEmpty())
		{
			return;
		}
		smeltingList.put(input, stack);
		experienceList.put(input, experience);
	}

	public AlloySmeltingResult getSmeltingResult(ItemStack[] inputs)
	{
		ItemStack output = ItemStack.EMPTY;
		for (Map.Entry<ItemStack[], ItemStack> entry : smeltingList.entrySet())
		{
			if (compareItemInputs(inputs, entry.getKey()))
			{
				output = entry.getValue();
				break;
			}
		}

		return new AlloySmeltingResult(inputs, output);
	}

	private boolean compareItemInputs(ItemStack[] input1, ItemStack[] input2)
	{
		return input1.length == 2 && input2.length == 2 && compareItemStacks(input1[0], input2[0]) && compareItemStacks(input1[1], input2[1]);
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack1.getItem() == stack2.getItem() && (stack1.getMetadata() == 32767 || stack1.getMetadata() == stack2.getMetadata());
	}

	public Map<ItemStack[], ItemStack> getSmeltingList()
	{
		return smeltingList;
	}

	public float getSmeltingExperience(ItemStack[] input)
	{
		for (Map.Entry<ItemStack[], Float> entry : experienceList.entrySet())
		{
			if (compareItemInputs(input, entry.getKey()))
			{
				return entry.getValue();
			}
		}

		return 0;
	}
}
