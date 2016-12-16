package com.plutomc.core.init;

import com.plutomc.core.common.items.*;
import com.plutomc.core.common.items.ItemHandStone;
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
		ASH("ash", null, CreativeTabs.MATERIALS),
		CROCOITE_DUST("crocoite_dust", null, CreativeTabs.MATERIALS),
		CRUCIBLE("crucible", null, CreativeTabs.MATERIALS),
		CRUCIBLE_UNFIRED("crucible_unfired", null, CreativeTabs.MATERIALS),
		STEEL_COMPOUND("steel_compound", null, CreativeTabs.MATERIALS),

		STONE_HANDSTONE("hand_stone", null, CreativeTabs.TOOLS),
		GRANITE_HANDSTONE("hand_stone_granite", null, CreativeTabs.TOOLS),
		QUARTZ_HANDSTONE("hand_stone_quartz", null, CreativeTabs.TOOLS),
		DIAMOND_HANDSTONE("hand_stone_diamond", null, CreativeTabs.TOOLS),

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
		BRONZE_NUGGET("bronze_nugget", "nuggetBronze", CreativeTabs.MATERIALS),
		BRONZE_AXE("bronze_axe", "axeBronze", CreativeTabs.TOOLS),
		BRONZE_HOE("bronze_hoe", "hoeBronze", CreativeTabs.TOOLS),
		BRONZE_PICKAXE("bronze_pickaxe", "pickaxeBronze", CreativeTabs.TOOLS),
		BRONZE_SHOVEL("bronze_shovel", "shovelBronze", CreativeTabs.TOOLS),
		BRONZE_SWORD("bronze_sword", "swordBronze", CreativeTabs.COMBAT),
		BRONZE_BOOTS("bronze_boots", "bootsBronze", CreativeTabs.COMBAT),
		BRONZE_CHESTPLATE("bronze_chestplate", "chestplateBronze", CreativeTabs.COMBAT),
		BRONZE_HELMET("bronze_helmet", "helmetBronze", CreativeTabs.COMBAT),
		BRONZE_LEGGINGS("bronze_leggings", "leggingsBronze", CreativeTabs.COMBAT),

		STEEL_INGOT("steel_ingot", "ingotSteel", CreativeTabs.MATERIALS),
		STEEL_NUGGET("steel_nugget", "nuggetSteel", CreativeTabs.MATERIALS),
		STEEL_AXE("steel_axe", "axeSteel", CreativeTabs.TOOLS),
		STEEL_HOE("steel_hoe", "hoeSteel", CreativeTabs.TOOLS),
		STEEL_PICKAXE("steel_pickaxe", "pickaxeSteel", CreativeTabs.TOOLS),
		STEEL_SHOVEL("steel_shovel", "shovelSteel", CreativeTabs.TOOLS),
		STEEL_SWORD("steel_sword", "swordSteel", CreativeTabs.COMBAT),
		STEEL_BOOTS("steel_boots", "bootsSteel", CreativeTabs.COMBAT),
		STEEL_CHESTPLATE("steel_chestplate", "chestplateSteel", CreativeTabs.COMBAT),
		STEEL_HELMET("steel_helmet", "helmetSteel", CreativeTabs.COMBAT),
		STEEL_LEGGINGS("steel_leggings", "leggingsSteel", CreativeTabs.COMBAT);

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

	public static final BaseItem ASH = new BaseItem(Data.ASH);
	public static final BaseItem CROCOITE_DUST = new BaseItem(Data.CROCOITE_DUST);
	public static final BaseItem CRUCIBLE = new BaseItem(Data.CRUCIBLE);
	public static final BaseItem CRUCIBLE_UNFIRED = new BaseItem(Data.CRUCIBLE_UNFIRED);
	public static final BaseItem STEEL_COMPOUND = new BaseItem(Data.STEEL_COMPOUND);
	public static final BaseItem STONE_HANDSTONE = new ItemHandStone(Data.STONE_HANDSTONE);
	public static final BaseItem GRANITE_HANDSTONE = new ItemHandStone(Data.GRANITE_HANDSTONE);
	public static final BaseItem QUARTZ_HANDSTONE = new ItemHandStone(Data.QUARTZ_HANDSTONE);
	public static final BaseItem DIAMOND_HANDSTONE = new ItemHandStone(Data.DIAMOND_HANDSTONE);
	public static final BaseItem COPPER_INGOT = new BaseItem(Data.COPPER_INGOT);
	public static final BaseItem COPPER_NUGGET = new BaseItem(Data.COPPER_NUGGET);
	public static final BaseItem TIN_INGOT = new BaseItem(Data.TIN_INGOT);
	public static final BaseItem TIN_NUGGET = new BaseItem(Data.TIN_NUGGET);
	public static final BaseItem BRONZE_INGOT = new BaseItem(Data.BRONZE_INGOT);
	public static final BaseItem BRONZE_NUGGET = new BaseItem(Data.BRONZE_NUGGET);
	public static final BaseItem STEEL_INGOT = new BaseItem(Data.STEEL_INGOT);
	public static final BaseItem STEEL_NUGGET = new BaseItem(Data.STEEL_NUGGET);

	public static void preInit()
	{
		register(ASH);
		register(CROCOITE_DUST);
		register(CRUCIBLE);
		register(CRUCIBLE_UNFIRED);
		register(STEEL_COMPOUND);
		register(STONE_HANDSTONE);
		register(GRANITE_HANDSTONE);
		register(QUARTZ_HANDSTONE);
		register(DIAMOND_HANDSTONE);
		register(COPPER_INGOT);
		register(COPPER_NUGGET);
		register(TIN_INGOT);
		register(TIN_NUGGET);
		register(BRONZE_INGOT);
		register(BRONZE_NUGGET);
		register(STEEL_INGOT);
		register(STEEL_NUGGET);
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
		registerRender(ASH);
		registerRender(CROCOITE_DUST);
		registerRender(CRUCIBLE);
		registerRender(CRUCIBLE_UNFIRED);
		registerRender(STEEL_COMPOUND);
		registerRender(STONE_HANDSTONE);
		registerRender(GRANITE_HANDSTONE);
		registerRender(QUARTZ_HANDSTONE);
		registerRender(DIAMOND_HANDSTONE);
		registerRender(COPPER_INGOT);
		registerRender(COPPER_NUGGET);
		registerRender(TIN_INGOT);
		registerRender(TIN_NUGGET);
		registerRender(BRONZE_INGOT);
		registerRender(BRONZE_NUGGET);
		registerRender(STEEL_INGOT);
		registerRender(STEEL_NUGGET);
	}

	private static void registerRender(BaseItem item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
