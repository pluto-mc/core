package com.plutomc.core.init;

import com.plutomc.core.common.blocks.*;
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
		COPPER_BLOCK("copper_block", "blockCopper", CreativeTabs.DECORATIONS),
		COPPER_ORE("copper_ore", "oreCopper", CreativeTabs.BUILDING_BLOCKS),

		TIN_BLOCK("tin_block", "blockTin", CreativeTabs.DECORATIONS),
		TIN_ORE("tin_ore", "oreTin", CreativeTabs.BUILDING_BLOCKS);

		private final String name;
		private final String oreDictName;
		private final CreativeTabs tab;

		Data(String name, String oreDictName, CreativeTabs tab)
		{
			this.name = name;
			this.oreDictName = oreDictName;
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

		public String getOreDictName()
		{
			return oreDictName;
		}

		public CreativeTabs getCreativeTab()
		{
			return tab;
		}
	}

	public static final BaseItemBlock COPPER_BLOCK = new BaseItemBlock(new BlockCopperBlock());
	public static final BaseItemBlock COPPER_ORE = new BaseItemBlock(new BlockCopperOre());
	public static final BaseItemBlock TIN_BLOCK = new BaseItemBlock(new BlockTinBlock());
	public static final BaseItemBlock TIN_ORE = new BaseItemBlock(new BlockTinOre());

	public static void preInit()
	{
		register(COPPER_BLOCK);
		register(COPPER_ORE);
		register(TIN_BLOCK);
		register(TIN_ORE);
	}

	private static void register(BaseItemBlock block)
	{
		GameRegistry.register(block);
		GameRegistry.register(block.block);

		String oreDictName = block.block.data.getOreDictName();
		if (oreDictName != null && oreDictName.length() > 0)
		{
			OreDictionary.registerOre(oreDictName, block.block);
		}
	}

	public static void registerRenders()
	{
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
