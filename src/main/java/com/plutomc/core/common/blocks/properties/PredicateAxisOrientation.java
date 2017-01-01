package com.plutomc.core.common.blocks.properties;

import com.google.common.base.Predicate;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nullable;

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
public enum PredicateAxisOrientation implements Predicate<EnumFacing.Axis>
{
	HORIZONTAL(EnumFacing.Plane.HORIZONTAL),
	VERTICAL(EnumFacing.Plane.VERTICAL);

	private final EnumFacing.Plane plane;

	PredicateAxisOrientation(EnumFacing.Plane plane)
	{
		this.plane = plane;
	}

	@Override
	public boolean apply(@Nullable EnumFacing.Axis input)
	{
		return input != null && input.getPlane() == this.plane;
	}
}
