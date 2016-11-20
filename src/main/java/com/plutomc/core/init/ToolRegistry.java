package com.plutomc.core.init;

import com.plutomc.core.common.items.tools.BaseItemSword;
import com.plutomc.core.common.items.tools.ItemCopperSword;
import com.plutomc.core.common.items.tools.ItemTinSword;
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

	public static final BaseItemSword COPPER_SWORD = new ItemCopperSword();
	public static final BaseItemSword TIN_SWORD = new ItemTinSword();

	public static void preInit()
	{
		registerSword(COPPER_SWORD);
		registerSword(TIN_SWORD);
	}

	private static void registerSword(BaseItemSword item)
	{
		GameRegistry.register(item);

		String oreDictName = item.data.getOreDictName();
		if (oreDictName != null && oreDictName.length() > 0)
		{
			OreDictionary.registerOre(oreDictName, item);
		}
	}

	public static void registerRenders()
	{
		registerSwordRender(COPPER_SWORD);
		registerSwordRender(TIN_SWORD);
	}

	private static void registerSwordRender(BaseItemSword item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
