package com.plutomc.core.common.tileentities;

import com.plutomc.core.common.blocks.BlockAlloyFurnace;
import com.plutomc.core.common.crafting.AlloyFurnaceRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
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
public class TileEntityAlloyFurnace extends TileEntity implements ITickable, ISidedInventory
{
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_SIDES = new int[] { 1, 2 };
	private static final int[] SLOTS_BOTTOM = new int[] { 3 };

	private NonNullList<ItemStack> furnaceItemStacks;
	private int burnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;
	private String customName;

	public TileEntityAlloyFurnace()
	{
		furnaceItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
	}

	@Override
	public void update()
	{
		boolean flagBurning = isBurning();
		boolean flagDirty = false;

		if (isBurning())
		{
			--burnTime;
		}

		if (!world.isRemote)
		{
			ItemStack[] inputs = getInputItemStacks();
			ItemStack fuel = getFuelItemStack();

			if (isBurning() || !fuel.isEmpty() && !(inputs[0].isEmpty() && inputs[1].isEmpty()))
			{
				if (!isBurning() && canSmelt())
				{
					burnTime = TileEntityFurnace.getItemBurnTime(fuel);
					currentItemBurnTime = burnTime;

					if (isBurning())
					{
						flagDirty = true;
						if (!fuel.isEmpty())
						{
							Item fuelItem = fuel.getItem();
							fuel.shrink(1);

							if (fuel.isEmpty())
							{
								ItemStack fuelStack = fuelItem.getContainerItem(fuel);
								furnaceItemStacks.set(0, fuelStack);
							}
						}
					}
				}

				if (isBurning() && canSmelt())
				{
					++cookTime;

					if (cookTime == totalCookTime)
					{
						cookTime = 0;
						totalCookTime = getCookTime(getFuelItemStack());
						smeltItem();
						flagDirty = true;
					}
				}
				else
				{
					cookTime = 0;
				}
			}
			else if (!isBurning() && cookTime > 0)
			{
				cookTime = MathHelper.clamp(cookTime - 2, 0, totalCookTime);
			}

			if (flagBurning != isBurning())
			{
				flagDirty = true;
				BlockAlloyFurnace.setBurningAtPos(world, pos, isBurning());
			}
		}

		if (flagDirty)
		{
			markDirty();
		}
	}

	@Nonnull
	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (direction == EnumFacing.DOWN && index == 0)
		{
			Item item = stack.getItem();
			if (item != Items.WATER_BUCKET && item != Items.BUCKET)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public int getSizeInventory()
	{
		return 4;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemStack : furnaceItemStacks)
		{
			if (!itemStack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return furnaceItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(furnaceItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemStack = getStackInSlot(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		furnaceItemStacks.set(index, stack);

		if (stack.getCount() > getInventoryStackLimit())
		{
			stack.setCount(getInventoryStackLimit());
		}

		if ((index == 1 || index == 2) && !flag)
		{
			totalCookTime = getCookTime(stack);
			cookTime = 0;
			markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer)
	{
		return world.getTileEntity(pos) == this && entityPlayer.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) <= 64d;
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
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 3)
		{
			return false;
		}
		else if (index != 0)
		{
			return true;
		}
		else
		{
			ItemStack itemStack = getFuelItemStack();
			return TileEntityFurnace.isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && itemStack.getItem() != Items.BUCKET;
		}
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return burnTime;
			case 1:
				return currentItemBurnTime;
			case 2:
				return cookTime;
			case 3:
				return totalCookTime;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				burnTime = value;
				break;
			case 1:
				currentItemBurnTime = value;
				break;
			case 2:
				cookTime = value;
				break;
			case 3:
				totalCookTime = value;
				break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 4;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		furnaceItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, furnaceItemStacks);
		burnTime = compound.getInteger("BurnTime");
		cookTime = compound.getInteger("CookTime");
		totalCookTime = compound.getInteger("CookTimeTotal");
		currentItemBurnTime = TileEntityFurnace.getItemBurnTime(getFuelItemStack());

		if (compound.hasKey("CustomName", 8))
		{
			setCustomInventoryName(compound.getString("CustomName"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) burnTime);
		compound.setInteger("CookTime", (short) cookTime);
		compound.setInteger("CookTimeTotal", (short) totalCookTime);
		ItemStackHelper.saveAllItems(compound, furnaceItemStacks);

		if (hasCustomName())
		{
			compound.setString("CustomName", customName);
		}

		return compound;
	}

	@Override
	public void clear()
	{
		furnaceItemStacks.clear();
	}

	@Nonnull
	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.alloy_furnace";
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null && !customName.isEmpty();
	}

	public void setCustomInventoryName(String customName)
	{
		this.customName = customName;
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName());
	}

	private boolean canSmelt()
	{
		ItemStack[] inputs = getInputItemStacks();

		if (inputs[0].isEmpty() || inputs[1].isEmpty())
		{
			return false;
		}
		else
		{
			AlloyFurnaceRecipes.AlloySmeltingResult result = AlloyFurnaceRecipes.instance().getSmeltingResult(inputs);
			ItemStack resultOutput = result.getOutput();

			if (resultOutput.isEmpty())
			{
				return false;
			}
			else
			{
				ItemStack output = getOutputItemStack();
				if (output.isEmpty()) return true;
				if (!output.isItemEqual(resultOutput)) return false;
				int resultSize = output.getCount() + resultOutput.getCount();
				return resultSize <= getInventoryStackLimit() && resultSize <= output.getMaxStackSize()
						&& result.getInputs()[0].getCount() <= inputs[0].getCount()
						&& result.getInputs()[1].getCount() <= inputs[1].getCount();
			}
		}
	}

	public int getCookTime(ItemStack stack)
	{
		return 200;
	}

	public ItemStack getFuelItemStack()
	{
		return getStackInSlot(0);
	}

	public ItemStack[] getInputItemStacks()
	{
		return new ItemStack[] { getStackInSlot(1), getStackInSlot(2) };
	}

	public ItemStack getOutputItemStack()
	{
		return getStackInSlot(3);
	}

	public boolean isBurning() {
		return burnTime > 0;
	}

	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack fuel = getFuelItemStack();
			ItemStack[] inputs = getInputItemStacks();
			AlloyFurnaceRecipes.AlloySmeltingResult result = AlloyFurnaceRecipes.instance().getSmeltingResult(inputs);
			ItemStack resultOutput = result.getOutput();
			ItemStack output = getOutputItemStack();

			if (output.isEmpty())
			{
				furnaceItemStacks.set(3, resultOutput.copy());
			}
			else if (output.getItem() == resultOutput.getItem())
			{
				output.grow(resultOutput.getCount());
			}

			if (((inputs[0].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && inputs[0].getMetadata() == 1)
					|| (inputs[1].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && inputs[1].getMetadata() == 1))
					&& !fuel.isEmpty() && fuel.getItem() == Items.BUCKET)
			{
				furnaceItemStacks.set(0, new ItemStack(Items.WATER_BUCKET));
			}

			inputs[0].shrink(result.getInputs()[0].getCount());
			inputs[1].shrink(result.getInputs()[1].getCount());
		}
	}
}
