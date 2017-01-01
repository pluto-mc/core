package com.plutomc.core.common.world.util;

import com.google.common.base.Objects;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

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
public class WorldStructurePoint
{
	private int x, y, z;
	private EnumFacing direction;
	private boolean is2D;

	public WorldStructurePoint(int x, int y, EnumFacing direction)
	{
		this(x, y, 0, direction);
		this.is2D = true;
	}

	public WorldStructurePoint(int x, int y, int z, EnumFacing direction)
	{
		setX(x);
		setY(y);
		setZ(z);
		setDirection(direction);
		this.is2D = false;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this).add("x", getX()).add("y", getY()).add("z", getZ()).add("direction", getDirection()).toString();
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getZ()
	{
		return z;
	}

	public EnumFacing getDirection()
	{
		return direction;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setZ(int z)
	{
		if (is2D) return;
		this.z = z;
	}

	public void setDirection(EnumFacing direction)
	{
		this.direction = direction;
	}

	public boolean isAxisX()
	{
		return getDirection().getAxis() == EnumFacing.Axis.X;
	}

	public boolean isAxisY()
	{
		return getDirection().getAxis() == EnumFacing.Axis.Y;
	}

	public boolean isAxisZ()
	{
		return getDirection().getAxis() == EnumFacing.Axis.Z;
	}

	public BlockPos addToPos(BlockPos pos)
	{
		return pos.east(isAxisZ() ? 0 : getX()).down(getY()).north(isAxisZ() ? getX() : 0);
	}

	public BlockPos subtractFromPos(BlockPos pos)
	{
		return pos.west(isAxisZ() ? 0 : getX()).up(getY()).south(isAxisZ() ? getX() : 0);
	}
}
