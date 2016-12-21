package com.plutomc.core.common.items;

import com.plutomc.core.init.ItemRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
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
	public int getItemEnchantability()
	{
		switch ((ItemRegistry.Data) getData())
		{
			case STONE_HANDSTONE:
				return 5;
			case GRANITE_HANDSTONE:
				return 5;
			case QUARTZ_HANDSTONE:
				return 8;
			case DIAMOND_HANDSTONE:
				return 10;
			default:
				return 0;
		}
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
	{
		return enchantment.getRegistryName().getResourcePath().equals("efficiency");
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
//		if (book.getItem() instanceof ItemEnchantedBook)
//		{
//			ItemEnchantedBook bookItem = (ItemEnchantedBook) book.getItem();
//			NBTTagList nbtTagList = bookItem.getEnchantments(book);
//
//			for (int i = 0; i < nbtTagList.tagCount(); ++i)
//			{
//				int id = nbtTagList.getCompoundTagAt(i).getShort("id");
//				Enchantment enchantment = Enchantment.getEnchantmentByID(id);
//
//				if (enchantment != null && canApplyAtEnchantingTable(stack, enchantment))
//				{
//					return true;
//				}
//			}
//		}
//
//		return false;
		return super.isBookEnchantable(stack, book);
	}

	public float getEfficiencyMultiplier(ItemStack stack)
	{
		int enchantmentModifier = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack) + 1;
		return (maxEfficiency - getHandStoneEfficiency((ItemRegistry.Data) getData())) / (maxEfficiency * enchantmentModifier);
	}

	public int getHandStoneDurability(ItemRegistry.Data data)
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

	public float getHandStoneEfficiency(ItemRegistry.Data data)
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
