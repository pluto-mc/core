package com.plutomc.core.common.items.armor;

import com.plutomc.core.init.ItemRegistry;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

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
public class BaseItemHelmet extends ItemArmor
{
	private final ItemRegistry.Data data;

	public BaseItemHelmet(ItemRegistry.Data data, ArmorMaterial materialIn)
	{
		super(materialIn, 1, EntityEquipmentSlot.HEAD);
		setUnlocalizedName(data.getUnlocalizedName());
		setRegistryName(data.getRegistryName());
		setCreativeTab(data.getCreativeTab());
		this.data = data;
	}

	public ItemRegistry.Data getData()
	{
		return data;
	}
}
