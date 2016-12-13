package com.plutomc.core.common.gui;

import com.plutomc.core.common.gui.slots.SlotQuernStoneHand;
import com.plutomc.core.common.gui.slots.SlotQuernStoneOutput;
import com.plutomc.core.common.tileentities.TileEntityQuernStone;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

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
public class ContainerQuernStone extends Container
{
	private TileEntityQuernStone tileEntity;
	private int grindTime;
	private int totalGrindTime;

	public ContainerQuernStone(InventoryPlayer playerInventory, TileEntityQuernStone tileEntity)
	{
		this.tileEntity = tileEntity;

		addSlotToContainer(new SlotQuernStoneHand(tileEntity, 0, 47, 17));
		addSlotToContainer(new Slot(tileEntity, 1, 47, 52));
		addSlotToContainer(new SlotQuernStoneOutput(tileEntity, 2, 109, 35));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tileEntity.isUsableByPlayer(playerIn);
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileEntity);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (IContainerListener listener : listeners)
		{
			if (grindTime != tileEntity.getField(0))
			{
				listener.sendProgressBarUpdate(this, 0, tileEntity.getField(0));
			}
			if (totalGrindTime != tileEntity.getField(1))
			{
				listener.sendProgressBarUpdate(this, 1, tileEntity.getField(1));
			}
		}

		grindTime = tileEntity.getField(0);
		totalGrindTime = tileEntity.getField(1);
	}

	@Override
	public void updateProgressBar(int id, int data)
	{
		tileEntity.setField(id, data);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack defaultStack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();
			defaultStack = stack.copy();

			if (index == 2)
			{
				if (!mergeItemStack(stack, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(stack, defaultStack);
			}
			else if (index > 2)
			{
				if (TileEntityQuernStone.isItemHandStone(stack))
				{
					if (!mergeItemStack(stack, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 3 && index < 30)
				{
					if (!mergeItemStack(stack, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 30 && index < 39 && !mergeItemStack(stack, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!mergeItemStack(stack, 3, 39, false))
			{
				return ItemStack.EMPTY;
			}

			if (stack.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (stack.getCount() == defaultStack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack);
		}

		return defaultStack;
	}
}
