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
	public static final Item.ToolMaterial BRONZE = EnumHelper.addToolMaterial("BRONZE", 2, 364, 6.5f, 2.5f, 14);

	public static final BaseItemAxe COPPER_AXE = new BaseItemAxe(ItemRegistry.Data.COPPER_AXE, COPPER, -3.1f);
	public static final BaseItemHoe COPPER_HOE = new BaseItemHoe(ItemRegistry.Data.COPPER_HOE, COPPER);
	public static final BaseItemPickaxe COPPER_PICKAXE = new BaseItemPickaxe(ItemRegistry.Data.COPPER_PICKAXE, COPPER);
	public static final BaseItemShovel COPPER_SHOVEL = new BaseItemShovel(ItemRegistry.Data.COPPER_SHOVEL, COPPER);
	public static final BaseItemSword COPPER_SWORD = new BaseItemSword(ItemRegistry.Data.COPPER_SWORD, COPPER);
	public static final BaseItemAxe TIN_AXE = new BaseItemAxe(ItemRegistry.Data.TIN_AXE, TIN, -3.1f);
	public static final BaseItemHoe TIN_HOE = new BaseItemHoe(ItemRegistry.Data.TIN_HOE, TIN);
	public static final BaseItemPickaxe TIN_PICKAXE = new BaseItemPickaxe(ItemRegistry.Data.TIN_PICKAXE, TIN);
	public static final BaseItemShovel TIN_SHOVEL = new BaseItemShovel(ItemRegistry.Data.TIN_SHOVEL, TIN);
	public static final BaseItemSword TIN_SWORD = new BaseItemSword(ItemRegistry.Data.TIN_SWORD, TIN);
	public static final BaseItemAxe BRONZE_AXE = new BaseItemAxe(ItemRegistry.Data.BRONZE_AXE, BRONZE, -2.9f);
	public static final BaseItemHoe BRONZE_HOE = new BaseItemHoe(ItemRegistry.Data.BRONZE_HOE, BRONZE);
	public static final BaseItemPickaxe BRONZE_PICKAXE = new BaseItemPickaxe(ItemRegistry.Data.BRONZE_PICKAXE, BRONZE);
	public static final BaseItemShovel BRONZE_SHOVEL = new BaseItemShovel(ItemRegistry.Data.BRONZE_SHOVEL, BRONZE);
	public static final BaseItemSword BRONZE_SWORD = new BaseItemSword(ItemRegistry.Data.BRONZE_SWORD, BRONZE);

	public static void preInit()
	{
		register(COPPER_AXE.getData(), COPPER_AXE);
		register(COPPER_HOE.getData(), COPPER_HOE);
		register(COPPER_PICKAXE.getData(), COPPER_PICKAXE);
		register(COPPER_SHOVEL.getData(), COPPER_SHOVEL);
		register(COPPER_SWORD.getData(), COPPER_SWORD);
		register(TIN_AXE.getData(), TIN_AXE);
		register(TIN_HOE.getData(), TIN_HOE);
		register(TIN_PICKAXE.getData(), TIN_PICKAXE);
		register(TIN_SHOVEL.getData(), TIN_SHOVEL);
		register(TIN_SWORD.getData(), TIN_SWORD);
		register(BRONZE_AXE.getData(), BRONZE_AXE);
		register(BRONZE_HOE.getData(), BRONZE_HOE);
		register(BRONZE_PICKAXE.getData(), BRONZE_PICKAXE);
		register(BRONZE_SHOVEL.getData(), BRONZE_SHOVEL);
		register(BRONZE_SWORD.getData(), BRONZE_SWORD);
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
		registerRender(COPPER_HOE);
		registerRender(COPPER_PICKAXE);
		registerRender(COPPER_SHOVEL);
		registerRender(COPPER_SWORD);
		registerRender(TIN_AXE);
		registerRender(TIN_HOE);
		registerRender(TIN_PICKAXE);
		registerRender(TIN_SHOVEL);
		registerRender(TIN_SWORD);
		registerRender(BRONZE_AXE);
		registerRender(BRONZE_HOE);
		registerRender(BRONZE_PICKAXE);
		registerRender(BRONZE_SHOVEL);
		registerRender(BRONZE_SWORD);
	}

	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
