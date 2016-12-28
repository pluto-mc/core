package com.plutomc.core.common.blocks;

import com.plutomc.core.common.data.IDataBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;

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
public class BaseBlock extends Block
{
	public static final double PIXEL_SIZE = 0.0625d;

	private final IDataBlock data;

	public BaseBlock(IDataBlock data)
	{
		super(data.getMaterial());
		setUnlocalizedName(data.getUnlocalizedName());
		setRegistryName(data.getRegistryName());
		setCreativeTab(data.getCreativeTab());
		this.data = data;
	}

	public IDataBlock getData()
	{
		return data;
	}

	public float getBlockHardness()
	{
		return blockHardness;
	}

	public float getBlockResistance()
	{
		return blockResistance;
	}

	public SoundType getBlockSoundType()
	{
		return blockSoundType;
	}
}
