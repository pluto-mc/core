package com.plutomc.core.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * plutomc_core
 * Copyright (C) 2016  Kevin Boxhoorn
 * <p>
 * plutomc_core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * plutomc_core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with plutomc_core.  If not, see <http://www.gnu.org/licenses/>.
 */
public class RecipeRegistry
{
	public static void init()
	{
		// Miscellaneous
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.GRASS), Blocks.DIRT, Items.WHEAT_SEEDS);

		// Metals
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.COPPER_INGOT), "NNN", "NNN", "NNN", 'N', ItemRegistry.COPPER_NUGGET);
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.TIN_INGOT), "NNN", "NNN", "NNN", 'N', ItemRegistry.TIN_NUGGET);
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.COPPER_NUGGET, 9), ItemRegistry.COPPER_INGOT);
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.TIN_NUGGET, 9), ItemRegistry.TIN_INGOT);
	}
}
