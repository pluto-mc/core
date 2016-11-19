package com.plutomc.core.init;

import com.plutomc.core.common.blocks.BaseItemBlock;
import com.plutomc.core.common.blocks.BlockCopperOre;
import com.plutomc.core.common.blocks.BlockTinOre;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		COPPER_ORE("copper_ore", CreativeTabs.BUILDING_BLOCKS),
		TIN_ORE("tin_ore", CreativeTabs.BUILDING_BLOCKS);

		private final String name;
		private final CreativeTabs tab;

		Data(String name, CreativeTabs tab)
		{
			this.name = name;
			this.tab = tab;
		}

		public String getUnlocalizedName()
		{
			return name;
		}

		public String getRegistryName()
		{
			return name;
		}

		public CreativeTabs getCreativeTab()
		{
			return tab;
		}
	}

	public static final ItemBlock COPPER_ORE = new BaseItemBlock(new BlockCopperOre());
	public static final ItemBlock TIN_ORE = new BaseItemBlock(new BlockTinOre());

	public static void preInit()
	{
		register(COPPER_ORE);
		register(TIN_ORE);
	}

	private static void register(ItemBlock block)
	{
		GameRegistry.register(block);
		GameRegistry.register(block.block);
	}

	public static void registerRenders()
	{
		registerRender(COPPER_ORE);
		registerRender(TIN_ORE);
	}

	private static void registerRender(ItemBlock block)
	{
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
