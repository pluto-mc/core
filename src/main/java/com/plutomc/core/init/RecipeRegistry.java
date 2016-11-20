package com.plutomc.core.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

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
public class RecipeRegistry
{
	public static void init()
	{
		registerRecipes();
		registerSmelting();
	}

	private static void registerRecipes()
	{
		// Miscellaneous
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.GRASS), Blocks.DIRT, Items.WHEAT_SEEDS);

		// Metals
		GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.COPPER_BLOCK,
				"III", "III", "III", 'I', ItemRegistry.COPPER_INGOT.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.TIN_BLOCK,
				"III", "III", "III", 'I', ItemRegistry.TIN_INGOT.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.COPPER_INGOT,
				"NNN", "NNN", "NNN", 'N', ItemRegistry.COPPER_NUGGET.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.TIN_INGOT,
				"NNN", "NNN", "NNN", 'N', ItemRegistry.TIN_NUGGET.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.COPPER_INGOT, 9),
				BlockRegistry.COPPER_BLOCK.block.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.TIN_INGOT, 9),
				BlockRegistry.TIN_BLOCK.block.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.COPPER_NUGGET, 9),
				ItemRegistry.COPPER_INGOT.data.getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.TIN_NUGGET, 9),
				ItemRegistry.TIN_INGOT.data.getOreDictName()));

		// Tools
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.COPPER_SWORD,
				" I ", " I ", " S ", 'I', ItemRegistry.COPPER_INGOT.data.getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.TIN_SWORD,
				" I ", " I ", " S ", 'I', ItemRegistry.TIN_INGOT.data.getOreDictName(), 'S', Items.STICK));
	}

	private static void registerSmelting()
	{
		GameRegistry.addSmelting(BlockRegistry.COPPER_ORE.block, new ItemStack(ItemRegistry.COPPER_INGOT), 0.7F);
		GameRegistry.addSmelting(BlockRegistry.TIN_ORE.block, new ItemStack(ItemRegistry.TIN_INGOT), 0.7F);
	}
}
