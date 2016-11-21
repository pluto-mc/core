package com.plutomc.core.init;

import com.plutomc.core.Core;
import com.plutomc.core.common.items.armor.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
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
public class ArmorRegistry
{
	public static final ItemArmor.ArmorMaterial COPPER = EnumHelper.addArmorMaterial("COPPER", Core.MOD_ID + ":copper", 10, new int[] { 2, 3, 4, 2 }, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
	public static final ItemArmor.ArmorMaterial TIN = EnumHelper.addArmorMaterial("TIN", Core.MOD_ID + ":tin", 13, new int[] { 2, 4, 5, 2 }, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);

	public static final BaseItemBoots COPPER_BOOTS = new ItemCopperBoots();
	public static final BaseItemChestplate COPPER_CHESTPLATE = new ItemCopperChestplate();
	public static final BaseItemHelmet COPPER_HELMET = new ItemCopperHelmet();
	public static final BaseItemLeggings COPPER_LEGGINGS = new ItemCopperLeggings();
	public static final BaseItemBoots TIN_BOOTS = new ItemTinBoots();
	public static final BaseItemChestplate TIN_CHESTPLATE = new ItemTinChestplate();
	public static final BaseItemHelmet TIN_HELMET = new ItemTinHelmet();
	public static final BaseItemLeggings TIN_LEGGINGS = new ItemTinLeggings();

	public static void preInit()
	{
		register(COPPER_BOOTS.data, COPPER_BOOTS);
		register(COPPER_CHESTPLATE.data, COPPER_CHESTPLATE);
		register(COPPER_HELMET.data, COPPER_HELMET);
		register(COPPER_LEGGINGS.data, COPPER_LEGGINGS);
		register(TIN_BOOTS.data, TIN_BOOTS);
		register(TIN_CHESTPLATE.data, TIN_CHESTPLATE);
		register(TIN_HELMET.data, TIN_HELMET);
		register(TIN_LEGGINGS.data, TIN_LEGGINGS);
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
		registerRender(COPPER_BOOTS);
		registerRender(COPPER_CHESTPLATE);
		registerRender(COPPER_HELMET);
		registerRender(COPPER_LEGGINGS);
		registerRender(TIN_BOOTS);
		registerRender(TIN_CHESTPLATE);
		registerRender(TIN_HELMET);
		registerRender(TIN_LEGGINGS);
	}

	private static void registerRender(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
