package com.plutomc.core.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

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
public class ContainerAlloyFurnace extends Container
{
	private IInventory tileFurnace;
	private int burnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;

	public ContainerAlloyFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory)
	{
		this.tileFurnace = furnaceInventory;
		addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 0, 51, 53));
		addSlotToContainer(new Slot(furnaceInventory, 1, 41, 17));
		addSlotToContainer(new Slot(furnaceInventory, 2, 59, 17));
		addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 3, 114, 35));

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
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileFurnace);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tileFurnace.isUseableByPlayer(playerIn);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (IContainerListener listener : listeners)
		{
			if (burnTime != tileFurnace.getField(0))
			{
				listener.sendProgressBarUpdate(this, 0, tileFurnace.getField(0));
			}

			if (currentItemBurnTime != tileFurnace.getField(1))
			{
				listener.sendProgressBarUpdate(this, 1, tileFurnace.getField(1));
			}

			if (cookTime != tileFurnace.getField(2))
			{
				listener.sendProgressBarUpdate(this, 2, tileFurnace.getField(2));
			}

			if (totalCookTime != tileFurnace.getField(3))
			{
				listener.sendProgressBarUpdate(this, 3, tileFurnace.getField(3));
			}
		}

		burnTime = tileFurnace.getField(0);
		currentItemBurnTime = tileFurnace.getField(1);
		cookTime = tileFurnace.getField(2);
		totalCookTime = tileFurnace.getField(3);
	}

	@Override
	public void updateProgressBar(int id, int data)
	{
		tileFurnace.setField(id, data);
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack defaultStack = ItemStack.field_190927_a;
		Slot slot = inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();
			defaultStack = stack.copy();

			if (index == 3)
			{
				if (!this.mergeItemStack(stack, 3, 39, true))
				{
					return ItemStack.field_190927_a;
				}

				slot.onSlotChange(stack, defaultStack);
			}
			else if (index != 0 && index != 1 && index != 2)
			{
				if (TileEntityFurnace.isItemFuel(stack))
				{
					if (!this.mergeItemStack(stack, 1, 2, false))
					{
						return ItemStack.field_190927_a;
					}
				}
				else if (index >= 3 && index < 30)
				{
					if (!this.mergeItemStack(stack, 30, 39, false))
					{
						return ItemStack.field_190927_a;
					}
				}
				else if (index >= 30 && index < 39 && !this.mergeItemStack(stack, 3, 30, false))
				{
					return ItemStack.field_190927_a;
				}
			}
			else if (!this.mergeItemStack(stack, 3, 39, false))
			{
				return ItemStack.field_190927_a;
			}

			if (stack.func_190926_b())
			{
				slot.putStack(ItemStack.field_190927_a);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (stack.func_190916_E() == defaultStack.func_190916_E())
			{
				return ItemStack.field_190927_a;
			}

			slot.func_190901_a(playerIn, stack);
		}

		return defaultStack;
	}
}
