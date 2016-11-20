package com.plutomc.core.init;

import com.plutomc.core.common.items.tools.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
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
public class ToolRegistry
{
	public static final Item.ToolMaterial COPPER = EnumHelper.addToolMaterial("COPPER", 2, 132, 4, 1.5f, 14);
	public static final Item.ToolMaterial TIN = EnumHelper.addToolMaterial("TIN", 2, 196, 5, 1.5f, 14);

	public static final BaseItemAxe COPPER_AXE = new ItemCopperAxe();
	public static final BaseItemPickaxe COPPER_PICKAXE = new ItemCopperPickaxe();
	public static final BaseItemSword COPPER_SWORD = new ItemCopperSword();
	public static final BaseItemAxe TIN_AXE = new ItemTinAxe();
	public static final BaseItemPickaxe TIN_PICKAXE = new ItemTinPickaxe();
	public static final BaseItemSword TIN_SWORD = new ItemTinSword();

	public static void preInit()
	{
		register(COPPER_AXE.data, COPPER_AXE);
		register(COPPER_PICKAXE.data, COPPER_PICKAXE);
		register(COPPER_SWORD.data, COPPER_SWORD);
		register(TIN_AXE.data, TIN_AXE);
		register(TIN_PICKAXE.data, TIN_PICKAXE);
		register(TIN_SWORD.data, TIN_SWORD);
	}

	private static void register(ItemRegistry.Data data, Item item)
	{
		GameRegistry.register(item);

		String oreDictName = data.getOreDictName();
		if (oreDictName != null && oreDictName.length() > 0)
		{
			OreDictionary.registerOre(oreDictName, item);
		}
	}

	public static void registerRenders()
	{
		registerRender(COPPER_AXE);
		registerRender(COPPER_PICKAXE);
		registerRender(COPPER_SWORD);
		registerRender(TIN_AXE);
		registerRender(TIN_PICKAXE);
		registerRender(TIN_SWORD);
	}

	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
