package com.plutomc.core.common.tileentities;

import com.plutomc.core.common.data.IDataBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

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
public abstract class BaseTileEntityInventory extends BaseTileEntity implements ISidedInventory
{
	private NonNullList<ItemStack> itemStacks;
	private String customName;

	public BaseTileEntityInventory(IDataBlock blockData)
	{
		super(blockData);
		this.itemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemStack : itemStacks)
		{
			if (!itemStack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return itemStacks.get(index);
	}

	public void setStackInSlot(int index, ItemStack stack)
	{
		itemStacks.set(index, stack);
	}

	@Nonnull
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(itemStacks, index, count);
	}

	@Nonnull
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(itemStacks, index);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return world.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) <= 64d;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{

	}

	@Override
	public void closeInventory(EntityPlayer player)
	{

	}

	@Override
	public void clear()
	{
		itemStacks.clear();
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, itemStacks);

		if (hasCustomName())
		{
			compound.setString("CustomName", customName);
		}

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, itemStacks);

		if (compound.hasKey("CustomName", 8))
		{
			setCustomName(compound.getString("CustomName"));
		}
	}

	@Nonnull
	@Override
	public String getName()
	{
		return hasCustomName() ? customName : getDefaultName();
	}

	public abstract String getDefaultName();

	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null && !customName.isEmpty();
	}

	public void setCustomName(String customName)
	{
		this.customName = customName;
	}
}
