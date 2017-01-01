package com.plutomc.core.common.blocks.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

/**
 * plutomc_core
 * Copyright (C) 2017  Kevin Boxhoorn
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
public class BlockPosDirection extends BlockPos
{
	private EnumFacing direction;

	public BlockPosDirection(int x, int y, int z, EnumFacing direction)
	{
		super(x, y, z);
		this.direction = direction;
	}

	public BlockPosDirection(double x, double y, double z, EnumFacing direction)
	{
		super(x, y, z);
		this.direction = direction;
	}

	public BlockPosDirection(Vec3d vec, EnumFacing direction)
	{
		super(vec);
		this.direction = direction;
	}

	public BlockPosDirection(Vec3i source, EnumFacing direction)
	{
		super(source);
		this.direction = direction;
	}

	public EnumFacing getDirection()
	{
		return direction;
	}

	public void setDirection(EnumFacing direction)
	{
		this.direction = direction;
	}
}
