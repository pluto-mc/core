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
		furnaceItemStacks = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
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

		if (!worldObj.isRemote)
		{
			ItemStack[] inputs = getInputItemStacks();
			ItemStack fuel = getFuelItemStack();

			// if burning or the fuel slot is not empty and neither of the input slots are empty
			if (isBurning() || !fuel.func_190926_b() && !(inputs[0].func_190926_b() && inputs[1].func_190926_b()))
			{
				if (!isBurning() && canSmelt())
				{
					burnTime = TileEntityFurnace.getItemBurnTime(fuel);
					currentItemBurnTime = burnTime;

					if (isBurning())
					{
						flagDirty = true;

						// if the fuel slot is not empty
						if (!fuel.func_190926_b())
						{
							Item fuelItem = fuel.getItem();
							// decrease the fuel slot size by 1
							fuel.func_190918_g(1);

							// if the fuel slot is empty
							if (fuel.func_190926_b())
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
				cookTime = MathHelper.clamp_int(cookTime - 2, 0, totalCookTime);
			}

			if (flagBurning != isBurning())
			{
				flagDirty = true;
				BlockAlloyFurnace.setBurningAtPos(worldObj, pos, isBurning());
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
	public boolean func_191420_l()
	{
		for (ItemStack itemStack : furnaceItemStacks)
		{
			if (!itemStack.func_190926_b())
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
		boolean flag = !stack.func_190926_b() && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
		furnaceItemStacks.set(index, stack);

		if (stack.func_190916_E() > getInventoryStackLimit())
		{
			stack.func_190920_e(getInventoryStackLimit());
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
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) <= 64d;
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
		furnaceItemStacks = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
		ItemStackHelper.func_191283_b(compound, furnaceItemStacks);
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
		ItemStackHelper.func_191282_a(compound, furnaceItemStacks);

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
		// if either inputs stacks are empty
		if (inputs[0].func_190926_b() || inputs[1].func_190926_b())
		{
			return false;
		}
		else
		{
			AlloyFurnaceRecipes.AlloySmeltingResult result = AlloyFurnaceRecipes.instance().getSmeltingResult(inputs);
			ItemStack resultOutput = result.getOutput();

			// if there would be no resulting output stack
			if (resultOutput.func_190926_b())
			{
				return false;
			}
			else
			{
				ItemStack output = getOutputItemStack();
				// if the output slot is empty
				if (output.func_190926_b()) return true;
				if (!output.isItemEqual(resultOutput)) return false;
				// get the size of the resulting output stack
				int resultSize = output.func_190916_E() + resultOutput.func_190916_E();
				return resultSize <= getInventoryStackLimit() && resultSize <= output.getMaxStackSize()
						// check that the size of the input slots are large enough to smelt the output
						&& result.getInputs()[0].func_190916_E() <= inputs[0].func_190916_E()
						&& result.getInputs()[1].func_190916_E() <= inputs[1].func_190916_E();
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

			// if the output slot is empty
			if (output.func_190926_b())
			{
				furnaceItemStacks.set(3, resultOutput.copy());
			}
			else if (output.getItem() == resultOutput.getItem())
			{
				// increase the size of the output slot by the size of the output stack
				output.func_190917_f(resultOutput.func_190916_E());
			}

			if (((inputs[0].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && inputs[0].getMetadata() == 1)
					|| (inputs[1].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && inputs[1].getMetadata() == 1))
					&& !fuel.func_190926_b() && fuel.getItem() == Items.BUCKET)
			{
				furnaceItemStacks.set(0, new ItemStack(Items.WATER_BUCKET));
			}

			// decrease the input slot sizes by the size of the input stacks
			inputs[0].func_190918_g(result.getInputs()[0].func_190916_E());
			inputs[1].func_190918_g(result.getInputs()[1].func_190916_E());
		}
	}
}
