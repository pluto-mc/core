package com.plutomc.core.common.gui.slots;

import com.plutomc.core.common.crafting.AlloyFurnaceRecipes;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

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
public class SlotAlloyFurnaceOutput extends Slot
{
	private final EntityPlayer player;
	private int removeCount;

	public SlotAlloyFurnaceOutput(EntityPlayer player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition)
	{
		super(inventoryIn, slotIndex, xPosition, yPosition);
		this.player = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	protected void onCrafting(ItemStack stack, int amount)
	{
		this.removeCount += amount;
		this.onCrafting(stack);
	}

	@Override
	protected void onCrafting(ItemStack stack)
	{

		stack.onCrafting(this.player.world, this.player, this.removeCount);

		if (!this.player.world.isRemote)
		{
			int i = this.removeCount;
			float f = AlloyFurnaceRecipes.instance().getSmeltingExperience(stack);

			if (f == 0.0F)
			{
				i = 0;
			}
			else if (f < 1.0F)
			{
				int j = MathHelper.floor((float)i * f);

				if (j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j))
				{
					++j;
				}

				i = j;
			}

			while (i > 0)
			{
				int k = EntityXPOrb.getXPSplit(i);
				i -= k;
				this.player.world.spawnEntity(new EntityXPOrb(this.player.world, this.player.posX, this.player.posY + 0.5d, this.player.posZ + 0.5d, k));
			}
		}

		this.removeCount = 0;
	}
}
