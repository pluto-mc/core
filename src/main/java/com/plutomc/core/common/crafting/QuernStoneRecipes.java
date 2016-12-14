package com.plutomc.core.common.crafting;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
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
public class QuernStoneRecipes
{
	private static final QuernStoneRecipes INSTANCE = new QuernStoneRecipes();
	private final Map<ItemStack, ItemStack> grindingList = new HashMap<ItemStack, ItemStack>();

	public static QuernStoneRecipes instance()
	{
		return INSTANCE;
	}

	public void addGrinding(ItemStack input, ItemStack stack)
	{
		grindingList.put(input, stack);
	}

	public ItemStack getResult(ItemStack input)
	{
		for (ItemStack entry : grindingList.keySet())
		{
			if (entry.isItemEqual(input))
			{
				return grindingList.get(entry);
			}
		}

		return ItemStack.EMPTY;
	}

	public Map<ItemStack, ItemStack> getGrindingList()
	{
		return grindingList;
	}
}
