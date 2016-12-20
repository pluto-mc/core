package com.plutomc.core.common.items;

import com.plutomc.core.init.ItemRegistry;
import net.minecraft.item.ItemStack;

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
public class ItemHandStone extends BaseItem
{
	private static final float maxEfficiency = 5f;

	public ItemHandStone(ItemRegistry.Data data)
	{
		super(data);
		setMaxDamage(getHandStoneDurability(data));
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}

	public float getEfficiencyMultiplier()
	{
		return (maxEfficiency - getHandStoneEfficiency((ItemRegistry.Data) getData())) / maxEfficiency;
	}

	public static int getHandStoneDurability(ItemRegistry.Data data)
	{
		switch (data)
		{
			case STONE_HANDSTONE:
				return 32;
			case GRANITE_HANDSTONE:
				return 64;
			case QUARTZ_HANDSTONE:
				return 128;
			case DIAMOND_HANDSTONE:
				return 256;
			default:
				return 0;
		}
	}

	public static float getHandStoneEfficiency(ItemRegistry.Data data)
	{
		switch (data)
		{
			case STONE_HANDSTONE:
				return 1f;
			case GRANITE_HANDSTONE:
				return 1f;
			case QUARTZ_HANDSTONE:
				return 1.75f;
			case DIAMOND_HANDSTONE:
				return 2.5f;
			default:
				return 1f;
		}
	}
}
