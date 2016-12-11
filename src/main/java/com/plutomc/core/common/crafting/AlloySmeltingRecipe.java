package com.plutomc.core.common.crafting;

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
public class AlloySmeltingRecipe
{
	private static class ListSizeException extends IllegalStateException
	{
		private static final int targetSize = 2;

		public ListSizeException(int size)
		{
			super(String.format("Size of list must be 2, got list with size %d.", size));
		}

		private static void checkList(List<ItemStack> list)
		{
			if (list.size() != targetSize) throw new ListSizeException(list.size());
		}
	}

	public static final AlloySmeltingRecipe EMPTY = new AlloySmeltingRecipe(new ArrayList<ItemStack>() {{
		add(ItemStack.EMPTY);
		add(ItemStack.EMPTY);
	}}, ItemStack.EMPTY, 0);

	private final List<ItemStack> inputs;
	private final ItemStack output;
	private final float experience;
	private final boolean isEmpty;

	public AlloySmeltingRecipe(List<ItemStack> inputs, ItemStack output, float experience)
	{
		ListSizeException.checkList(inputs);
		this.inputs = inputs;
		this.output = output;
		this.experience = experience;
		isEmpty = inputs.get(0).isEmpty() || inputs.get(1).isEmpty() || output.isEmpty();
	}

	public List<ItemStack> getInputs()
	{
		return inputs;
	}

	public ItemStack getOutput()
	{
		return output;
	}

	public float getExperience()
	{
		return experience;
	}

	public boolean isEmpty()
	{
		return isEmpty;
	}

	public boolean canSmelt(List<ItemStack> inputs)
	{
		if (isEmpty()) return false;
		ListSizeException.checkList(inputs);
		return getInputs().get(0).getCount() <= inputs.get(0).getCount()
				&& getInputs().get(1).getCount() <= inputs.get(1).getCount();
	}
}
