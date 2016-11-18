package com.plutomc.core.init;

import com.plutomc.core.common.items.ItemCopperIngot;
import com.plutomc.core.common.items.ItemCopperNugget;
import com.plutomc.core.common.items.ItemTinIngot;
import com.plutomc.core.common.items.ItemTinNugget;
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
public class ItemRegistry
{
	public static enum Data
	{
		COPPER_INGOT("copper_ingot", CreativeTabs.MATERIALS),
		COPPER_NUGGET("copper_nugget", CreativeTabs.MATERIALS),
		TIN_INGOT("tin_ingot", CreativeTabs.MATERIALS),
		TIN_NUGGET("tin_nugget", CreativeTabs.MATERIALS);

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

	public static final Item COPPER_INGOT = new ItemCopperIngot();
	public static final Item COPPER_NUGGET = new ItemCopperNugget();
	public static final Item TIN_INGOT = new ItemTinIngot();
	public static final Item TIN_NUGGET = new ItemTinNugget();

	public static void preInit()
	{
		GameRegistry.register(COPPER_INGOT);
		GameRegistry.register(COPPER_NUGGET);
		GameRegistry.register(TIN_INGOT);
		GameRegistry.register(TIN_NUGGET);
	}

	public static void registerRenders()
	{
		registerRender(COPPER_INGOT);
		registerRender(COPPER_NUGGET);
		registerRender(TIN_INGOT);
		registerRender(TIN_NUGGET);
	}

	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
