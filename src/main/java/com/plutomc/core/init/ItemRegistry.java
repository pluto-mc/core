package com.plutomc.core.init;

import com.plutomc.core.common.items.*;
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
public class ItemRegistry
{
	public enum Data
	{
		CRUCIBLE("crucible", null, CreativeTabs.MATERIALS),
		CRUCIBLE_UNFIRED("crucible_unfired", null, CreativeTabs.MATERIALS),

		COPPER_INGOT("copper_ingot", "ingotCopper", CreativeTabs.MATERIALS),
		COPPER_NUGGET("copper_nugget", "nuggetCopper", CreativeTabs.MATERIALS),
		COPPER_AXE("copper_axe", "axeCopper", CreativeTabs.TOOLS),
		COPPER_HOE("copper_hoe", "hoeCopper", CreativeTabs.TOOLS),
		COPPER_PICKAXE("copper_pickaxe", "pickaxeCopper", CreativeTabs.TOOLS),
		COPPER_SHOVEL("copper_shovel", "shovelCopper", CreativeTabs.TOOLS),
		COPPER_SWORD("copper_sword", "swordCopper", CreativeTabs.COMBAT),
		COPPER_BOOTS("copper_boots", "bootsCopper", CreativeTabs.COMBAT),
		COPPER_CHESTPLATE("copper_chestplate", "chestplateCopper", CreativeTabs.COMBAT),
		COPPER_HELMET("copper_helmet", "helmetCopper", CreativeTabs.COMBAT),
		COPPER_LEGGINGS("copper_leggings", "leggingsCopper", CreativeTabs.COMBAT),

		TIN_INGOT("tin_ingot", "ingotTin", CreativeTabs.MATERIALS),
		TIN_NUGGET("tin_nugget", "nuggetTin", CreativeTabs.MATERIALS),
		TIN_AXE("tin_axe", "axeTin", CreativeTabs.TOOLS),
		TIN_HOE("tin_hoe", "hoeTin", CreativeTabs.TOOLS),
		TIN_PICKAXE("tin_pickaxe", "pickaxeTin", CreativeTabs.TOOLS),
		TIN_SHOVEL("tin_shovel", "shovelTin", CreativeTabs.TOOLS),
		TIN_SWORD("tin_sword", "swordTin", CreativeTabs.COMBAT),
		TIN_BOOTS("tin_boots", "bootsTin", CreativeTabs.COMBAT),
		TIN_CHESTPLATE("tin_chestplate", "chestplateTin", CreativeTabs.COMBAT),
		TIN_HELMET("tin_helmet", "helmetTin", CreativeTabs.COMBAT),
		TIN_LEGGINGS("tin_leggings", "leggingsTin", CreativeTabs.COMBAT),

		BRONZE_INGOT("bronze_ingot", "ingotBronze", CreativeTabs.MATERIALS),
		BRONZE_NUGGET("bronze_nugget", "nuggetBronze", CreativeTabs.MATERIALS);

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

	public static final BaseItem CRUCIBLE = new BaseItem(Data.CRUCIBLE);
	public static final BaseItem CRUCIBLE_UNFIRED = new BaseItem(Data.CRUCIBLE_UNFIRED);
	public static final BaseItem COPPER_INGOT = new BaseItem(Data.COPPER_INGOT);
	public static final BaseItem COPPER_NUGGET = new BaseItem(Data.COPPER_NUGGET);
	public static final BaseItem TIN_INGOT = new BaseItem(Data.TIN_INGOT);
	public static final BaseItem TIN_NUGGET = new BaseItem(Data.TIN_NUGGET);
	public static final BaseItem BRONZE_INGOT = new BaseItem(Data.BRONZE_INGOT);
	public static final BaseItem BRONZE_NUGGET = new BaseItem(Data.BRONZE_NUGGET);

	public static void preInit()
	{
		register(CRUCIBLE);
		register(CRUCIBLE_UNFIRED);
		register(COPPER_INGOT);
		register(COPPER_NUGGET);
		register(TIN_INGOT);
		register(TIN_NUGGET);
		register(BRONZE_INGOT);
		register(BRONZE_NUGGET);
	}

	private static void register(BaseItem item)
	{
		GameRegistry.register(item);

		String oreDictName = item.getData().getOreDictName();
		if (oreDictName != null && oreDictName.length() > 0)
		{
			OreDictionary.registerOre(oreDictName, item);
		}
	}

	public static void registerRenders()
	{
		registerRender(CRUCIBLE);
		registerRender(CRUCIBLE_UNFIRED);
		registerRender(COPPER_INGOT);
		registerRender(COPPER_NUGGET);
		registerRender(TIN_INGOT);
		registerRender(TIN_NUGGET);
		registerRender(BRONZE_INGOT);
		registerRender(BRONZE_NUGGET);
	}

	private static void registerRender(BaseItem item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
