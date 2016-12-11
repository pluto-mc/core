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
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.CRUCIBLE_UNFIRED),
				"   ", "C C", "CCC", 'C', Items.CLAY_BALL);
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.ALLOY_FURNACE),
				"SSS", "FCF", "SSS", 'S', Blocks.COBBLESTONE, 'F', Blocks.FURNACE, 'C', ItemRegistry.CRUCIBLE);

		// Metals
		GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.COPPER_BLOCK,
				"III", "III", "III", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.TIN_BLOCK,
				"III", "III", "III", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(BlockRegistry.BRONZE_BLOCK,
				"III", "III", "III", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.COPPER_INGOT,
				"NNN", "NNN", "NNN", 'N', ItemRegistry.COPPER_NUGGET.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.TIN_INGOT,
				"NNN", "NNN", "NNN", 'N', ItemRegistry.TIN_NUGGET.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ItemRegistry.BRONZE_INGOT,
				"NNN", "NNN", "NNN", 'N', ItemRegistry.BRONZE_NUGGET.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.COPPER_INGOT, 9),
				BlockRegistry.COPPER_BLOCK.getBlock().getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.TIN_INGOT, 9),
				BlockRegistry.TIN_BLOCK.getBlock().getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.BRONZE_INGOT, 9),
				BlockRegistry.BRONZE_BLOCK.getBlock().getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.COPPER_NUGGET, 9),
				ItemRegistry.COPPER_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.TIN_NUGGET, 9),
				ItemRegistry.TIN_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemRegistry.BRONZE_NUGGET, 9),
				ItemRegistry.BRONZE_INGOT.getData().getOreDictName()));

		// Tools
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.COPPER_AXE,
				" II", " SI", " S ", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.TIN_AXE,
				" II", " SI", " S ", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.BRONZE_AXE,
				" II", " SI", " S ", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.COPPER_HOE,
				" II", " S ", " S ", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.TIN_HOE,
				" II", " S ", " S ", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.BRONZE_HOE,
				" II", " S ", " S ", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.COPPER_PICKAXE,
				"III", " S ", " S ", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.TIN_PICKAXE,
				"III", " S ", " S ", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.BRONZE_PICKAXE,
				"III", " S ", " S ", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.COPPER_SHOVEL,
				" I ", " S ", " S ", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.TIN_SHOVEL,
				" I ", " S ", " S ", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.BRONZE_SHOVEL,
				" I ", " S ", " S ", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.COPPER_SWORD,
				" I ", " I ", " S ", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.TIN_SWORD,
				" I ", " I ", " S ", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName(), 'S', Items.STICK));
		GameRegistry.addRecipe(new ShapedOreRecipe(ToolRegistry.BRONZE_SWORD,
				" I ", " I ", " S ", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName(), 'S', Items.STICK));

		// Armor
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.COPPER_BOOTS,
				"   ", "I I", "I I", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.TIN_BOOTS,
				"   ", "I I", "I I", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.BRONZE_BOOTS,
				"   ", "I I", "I I", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.COPPER_CHESTPLATE,
				"I I", "III", "III", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.TIN_CHESTPLATE,
				"I I", "III", "III", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.BRONZE_CHESTPLATE,
				"I I", "III", "III", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.COPPER_HELMET,
				"III", "I I", "   ", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.TIN_HELMET,
				"III", "I I", "   ", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.BRONZE_HELMET,
				"III", "I I", "   ", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.COPPER_LEGGINGS,
				"III", "I I", "I I", 'I', ItemRegistry.COPPER_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.TIN_LEGGINGS,
				"III", "I I", "I I", 'I', ItemRegistry.TIN_INGOT.getData().getOreDictName()));
		GameRegistry.addRecipe(new ShapedOreRecipe(ArmorRegistry.BRONZE_LEGGINGS,
				"III", "I I", "I I", 'I', ItemRegistry.BRONZE_INGOT.getData().getOreDictName()));
	}

	private static void registerSmelting()
	{
		GameRegistry.addSmelting(ItemRegistry.CRUCIBLE_UNFIRED, new ItemStack(ItemRegistry.CRUCIBLE), 0.4f);
		GameRegistry.addSmelting(BlockRegistry.COPPER_ORE.getBlock(), new ItemStack(ItemRegistry.COPPER_INGOT), 0.7f);
		GameRegistry.addSmelting(BlockRegistry.TIN_ORE.getBlock(), new ItemStack(ItemRegistry.TIN_INGOT), 0.7f);
	}
}
