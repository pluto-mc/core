package com.plutomc.core.common.items;

import com.plutomc.core.init.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

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
public abstract class BaseItemDurability extends BaseItem
{
	private final String usesKey = "Uses";
	protected int uses;
	protected int durability;

	public BaseItemDurability(ItemRegistry.Data data)
	{
		super(data);
		setHasSubtypes(true);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		ensureTagCompound(stack);
		NBTTagCompound compound = stack.getTagCompound();
		if (!compound.hasKey(usesKey))
		{
			compound.setInteger(usesKey, 0);
		}

		return compound.getInteger(usesKey) / durability;
	}

	@Override
	public int getItemStackLimit(ItemStack stack)
	{
		return 1;
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		subItems.add(new ItemStack(itemIn));
		if (itemIn instanceof BaseItemDurability)
		{
			BaseItemDurability itemDurability = (BaseItemDurability) itemIn;
			ItemStack stackDegraded = new ItemStack(itemDurability);
			itemDurability.setUses(stackDegraded, itemDurability.getDurability());
			subItems.add(stackDegraded);
		}
	}

	protected void ensureTagCompound(ItemStack stack)
	{
		if (stack.getTagCompound() == null)
		{
			stack.setTagCompound(new NBTTagCompound());
		}
	}

	protected int getUses(ItemStack stack)
	{
		ensureTagCompound(stack);
		return stack.getTagCompound().getInteger(usesKey);
	}

	protected int getDurability()
	{
		return durability;
	}

	protected void setUses(ItemStack stack, int uses)
	{
		ensureTagCompound(stack);
		stack.getTagCompound().setInteger(usesKey, uses);
		this.uses = uses;
	}

	protected void setDurability(int durability)
	{
		this.durability = durability;
	}

	public void use(ItemStack stack)
	{
		setUses(stack, getUses(stack) + 1);
	}
}
