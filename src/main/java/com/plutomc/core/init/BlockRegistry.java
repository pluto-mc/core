package com.plutomc.core.init;

import com.plutomc.core.common.blocks.*;
import com.plutomc.core.common.tileentities.TileEntityAlloyFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
public class BlockRegistry
{
	public enum Data
	{
		ALLOY_FURNACE("alloy_furnace", null, CreativeTabs.DECORATIONS, Material.ROCK),

		COPPER_BLOCK("copper_block", "blockCopper", CreativeTabs.DECORATIONS, Material.ROCK),
		COPPER_ORE("copper_ore", "oreCopper", CreativeTabs.BUILDING_BLOCKS, Material.ROCK),

		TIN_BLOCK("tin_block", "blockTin", CreativeTabs.DECORATIONS, Material.ROCK),
		TIN_ORE("tin_ore", "oreTin", CreativeTabs.BUILDING_BLOCKS, Material.ROCK);

		private final String name;
		private final String oreDictName;
		private final CreativeTabs tab;
		private final Material material;

		Data(String name, String oreDictName, CreativeTabs tab, Material material)
		{
			this.name = name;
			this.oreDictName = oreDictName;
			this.tab = tab;
			this.material = material;
		}

		public String getUnlocalizedName()
		{
			return name;
		}

		public String getRegistryName()
		{
			return name;
		}

		public String getOreDictName()
		{
			return oreDictName;
		}

		public CreativeTabs getCreativeTab()
		{
			return tab;
		}

		public Material getMaterial()
		{
			return material;
		}
	}

	public static final BaseItemBlock ALLOY_FURNACE = new BaseItemBlock(new BlockAlloyFurnace());
	public static final BaseItemBlock COPPER_BLOCK = new BaseItemBlock(new BlockCopperBlock());
	public static final BaseItemBlock COPPER_ORE = new BaseItemBlock(new BlockCopperOre());
	public static final BaseItemBlock TIN_BLOCK = new BaseItemBlock(new BlockTinBlock());
	public static final BaseItemBlock TIN_ORE = new BaseItemBlock(new BlockTinOre());

	public static void preInit()
	{
		register(ALLOY_FURNACE);
		register(COPPER_BLOCK);
		register(COPPER_ORE);
		register(TIN_BLOCK);
		register(TIN_ORE);

		GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, Data.ALLOY_FURNACE.getRegistryName() + "_tileentity");
	}

	private static void register(BaseItemBlock block)
	{
		GameRegistry.register(block);
		GameRegistry.register(block.getBlock());

		String oreDictName = block.getBlock().getData().getOreDictName();
		if (oreDictName != null && oreDictName.length() > 0)
		{
			OreDictionary.registerOre(oreDictName, block.getBlock());
		}
	}

	public static void registerRenders()
	{
		registerRender(ALLOY_FURNACE);
		registerRender(COPPER_BLOCK);
		registerRender(COPPER_ORE);
		registerRender(TIN_BLOCK);
		registerRender(TIN_ORE);
	}

	private static void registerRender(BaseItemBlock block)
	{
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
