package com.plutomc.core.common.world.structures;

import com.plutomc.core.common.blocks.util.BlockPosDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
public interface IWorldStructure
{
	boolean canGenerate(World world, BlockPos originPos, EnumFacing direction);

	boolean containsState(IBlockState state);

	boolean generate(World world, BlockPos originPos, EnumFacing direction, int xMod, int zMod);

	@Nullable
	BlockPosDirection getOriginPos(World world, BlockPos pos);

	int getWidth();

	int getHeight();

	boolean isComplete(World world, BlockPos originPos, EnumFacing direction);
}
