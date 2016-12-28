package com.plutomc.core.common.items;

import com.plutomc.core.common.blocks.BaseBlock;
import net.minecraft.item.ItemBlock;

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
public class BaseItemBlock extends ItemBlock
{
	private final BaseBlock block;

	public BaseItemBlock(BaseBlock block)
	{
		super(block);
		setUnlocalizedName(block.getUnlocalizedName());
		setRegistryName(block.getRegistryName());
		setCreativeTab(block.getCreativeTabToDisplayOn());
		this.block = block;
	}

	@Nonnull
	@Override
	public BaseBlock getBlock()
	{
		return block;
	}
}
