package com.plutomc.core.common.tileentities;

import com.plutomc.core.common.blocks.BlockAlloyFurnace;
import com.plutomc.core.common.crafting.AlloyFurnaceRecipes;
import com.plutomc.core.common.crafting.AlloyRecipe;
import com.plutomc.core.init.BlockRegistry;
import net.minecraft.init.Items;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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
public class TileEntityAlloyFurnace extends BaseTileEntityInventory
{
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_SIDES = new int[] { 1, 2 };
	private static final int[] SLOTS_BOTTOM = new int[] { 3 };

	private int burnTime;
	private int currentBurnTime;
	private int cookTime;
	private int totalCookTime;

	public TileEntityAlloyFurnace()
	{
		super(BlockRegistry.Data.ALLOY_FURNACE);
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
			List<ItemStack> inputs = getInputItemStacks();
			ItemStack fuel = getFuelItemStack();

			if (isBurning() || !fuel.isEmpty() && !inputs.get(0).isEmpty() && !inputs.get(1).isEmpty())
			{
				if (!isBurning() && canSmelt())
				{
					burnTime = TileEntityFurnace.getItemBurnTime(fuel);
					currentBurnTime = burnTime;

					if (isBurning())
					{
						flagDirty = true;

						if (!fuel.isEmpty())
						{
							fuel.shrink(1);
							if (fuel.isEmpty())
							{
								ItemStack fuelStack = fuel.getItem().getContainerItem(fuel);
								setStackInSlot(0, fuelStack);
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
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemStack = getStackInSlot(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		setStackInSlot(index, stack);

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
				return currentBurnTime;
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
				currentBurnTime = value;
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
		burnTime = compound.getInteger("BurnTime");
		currentBurnTime = TileEntityFurnace.getItemBurnTime(getFuelItemStack());
		cookTime = compound.getInteger("CookTime");
		totalCookTime = compound.getInteger("CookTimeTotal");
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) burnTime);
		compound.setInteger("CookTime", (short) cookTime);
		compound.setInteger("CookTimeTotal", (short) totalCookTime);
		return compound;
	}

	@Override
	public String getDefaultName()
	{
		return "plutomc_core:alloy_furnace";
	}

	private boolean canSmelt()
	{
		List<ItemStack> inputs = getInputItemStacks();
		if (inputs.get(0).isEmpty() || inputs.get(1).isEmpty())
		{
			return false;
		}

		AlloyRecipe recipe = AlloyFurnaceRecipes.instance().getSmeltingRecipe(inputs);
		if (!recipe.canSmelt(inputs))
		{
			return false;
		}

		ItemStack output = getOutputItemStack();
		ItemStack recipeOutput = recipe.getOutput();
		boolean outputReady = output.isEmpty() || output.isItemEqual(recipeOutput);
		int resultSize = output.getCount() + recipeOutput.getCount();
		return outputReady && resultSize <= getInventoryStackLimit() && resultSize <= output.getMaxStackSize();
	}

	public int getCookTime(ItemStack stack)
	{
		return 200;
	}

	public ItemStack getFuelItemStack()
	{
		return getStackInSlot(0);
	}

	public List<ItemStack> getInputItemStacks()
	{
		return new ArrayList<ItemStack>() {{
			add(getStackInSlot(1));
			add(getStackInSlot(2));
		}};
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
			List<ItemStack> inputs = getInputItemStacks();
			AlloyRecipe result = AlloyFurnaceRecipes.instance().getSmeltingRecipe(inputs);
			ItemStack resultOutput = result.getOutput();
			ItemStack output = getOutputItemStack();

			if (output.isEmpty())
			{
				setStackInSlot(3, resultOutput.copy());
			}
			else if (output.getItem() == resultOutput.getItem())
			{
				output.grow(resultOutput.getCount());
			}

			inputs.get(0).shrink(result.getIngredient(inputs.get(0)).getCount());
			inputs.get(1).shrink(result.getIngredient(inputs.get(1)).getCount());
		}
	}
}
