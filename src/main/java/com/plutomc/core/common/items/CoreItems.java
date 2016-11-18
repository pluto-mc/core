package com.plutomc.core.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
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
public class CoreItems
{
	public static enum Items
	{
		COPPER_INGOT("copper_ingot", CreativeTabs.MATERIALS),
		TIN_INGOT("tin_ingot", CreativeTabs.MATERIALS);

		private final String name;
		private final CreativeTabs tab;

		Items(String name, CreativeTabs tab)
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

	public static Item copperIngot;
	public static Item tinIngot;

	public static void init()
	{
		GameRegistry.register(copperIngot = new ItemCopperIngot());
		GameRegistry.register(tinIngot = new ItemTinIngot());
	}

	public static void registerRenders()
	{
		registerRender(copperIngot);
		registerRender(tinIngot);
	}

	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
