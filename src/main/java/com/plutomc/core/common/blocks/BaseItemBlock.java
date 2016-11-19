package com.plutomc.core.common.blocks;

import net.minecraft.item.ItemBlock;

/**
 * plutomc_core
 * Copyright (C) 2016  Kevin Boxhoorn
 * <p>
 * plutomc_core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * plutomc_core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with plutomc_core.  If not, see <http://www.gnu.org/licenses/>.
 */
public class BaseItemBlock extends ItemBlock
{
	public BaseItemBlock(BaseBlock block)
	{
		super(block);
		setUnlocalizedName(block.getUnlocalizedName());
		setRegistryName(block.getRegistryName());
		setCreativeTab(block.getCreativeTabToDisplayOn());
	}
}
