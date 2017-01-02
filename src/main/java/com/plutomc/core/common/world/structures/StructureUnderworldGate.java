package com.plutomc.core.common.world.structures;

import com.plutomc.core.common.blocks.BlockStairs;
import com.plutomc.core.common.blocks.BlockUnderworldGate;
import com.plutomc.core.common.blocks.util.BlockPosDirection;
import com.plutomc.core.common.world.util.WorldStructurePoint;
import com.plutomc.core.init.BlockRegistry;
import com.plutomc.core.init.WorldRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
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
public class StructureUnderworldGate implements IWorldStructure
{
	private static final IBlockState AIR_STATE = Blocks.AIR.getDefaultState();
	private static final IBlockState CROCOITE_STATE = BlockRegistry.CROCOITE.getDefaultState();
	private static final IBlockState GATE_STATE = BlockRegistry.UNDERWORLD_GATE.getDefaultState();
	private static final IBlockState[] MAP_STATES = {
			BlockRegistry.BASALT_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.EAST),
			BlockRegistry.BASALT_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.EAST),
			BlockRegistry.BASALT_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST),
			BlockRegistry.BASALT_STAIRS.getDefaultState().withProperty(BlockStairs.HALF, BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.WEST),
			BlockRegistry.BASALT.getDefaultState(),
			Blocks.MAGMA.getDefaultState()
	};
	private static final int[][] MAP = {
			{ 1, 3, -5, -6, 1, 3 },
			{ 5, 0, -3, -4, 0, 5 },
			{ 2, 3, -1, -2, 1, 4 },
			{ 9, 5, 0,  0,  5, 9 },
			{ 9, 5, 6,  6,  5, 9 }
	};

	private static final EnumFacing[] AXIS_HORIZONTALS = { EnumFacing.NORTH, EnumFacing.EAST };

	@Override
	public boolean canGenerate(World world, BlockPos originPos, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean containsState(IBlockState state)
	{
		if (state.getBlock() == BlockRegistry.BASALT_STAIRS && state.getValue(BlockStairs.FACING).getAxis() == EnumFacing.Axis.Z)
		{
			state = state.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING).rotateY().getOpposite());
		}
		return Arrays.asList(MAP_STATES).contains(state);
	}

	@Override
	public void generate(World world, BlockPos originPos, EnumFacing direction)
	{
		// TODO: Implement method.
	}

	@Nullable
	@Override
	public BlockPosDirection getOriginPos(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (containsState(state))
		{
			for (EnumFacing direction : AXIS_HORIZONTALS)
			{
				for (WorldStructurePoint point : getPointListForState(state, direction))
				{
					BlockPos originPos = point.subtractFromPos(pos);
					if (isComplete(world, originPos, direction))
					{
						return new BlockPosDirection(originPos, direction);
					}
				}
			}
		}

		return null;
	}

	@Override
	public boolean isComplete(World world, BlockPos originPos, EnumFacing direction)
	{
		if (MAP.length > 0 && MAP[0].length > 0 && direction.getAxis().isHorizontal())
		{
			for (int y = 0; y < MAP.length; y++)
			{
				for (int x = 0; x < MAP[0].length; x++)
				{
					int val = MAP[y][x];
					if (val > MAP_STATES.length)
					{
						continue;
					}

					WorldStructurePoint point = new WorldStructurePoint(x, y, direction);
					BlockPos pos = point.addToPos(originPos);
					if (world.getBlockState(pos) != getStateFromPoint(point))
					{
						if ((isCrocoiteBlock(x, y) || val < 0) && world.isAirBlock(pos)) {}
						else
						{
							return false;
						}
					}
				}
			}

			return true;
		}

		return false;
	}

	private List<WorldStructurePoint> getPointListForState(IBlockState state, EnumFacing direction)
	{
		List<WorldStructurePoint> points = new ArrayList<WorldStructurePoint>();
		if (!containsState(state)) return points;

		if (MAP.length > 0 && MAP[0].length > 0)
		{
			for (int y = 0; y < MAP.length; y++)
			{
				for (int x = 0; x < MAP[0].length; x++)
				{
					WorldStructurePoint point = new WorldStructurePoint(x, y, direction);
					IBlockState pointState = getStateFromPoint(point);
					if (pointState == state)
					{
						points.add(point);
					}
				}
			}
		}

		return points;
	}

	private IBlockState getStateFromPoint(WorldStructurePoint point)
	{
		int val = MAP[point.getY()][point.getX()];
		IBlockState state;
		if (isCrocoiteBlock(point.getX(), point.getY()))
		{
			state = CROCOITE_STATE;
		}
		else if (val < 0)
		{
			int index = Math.abs(val) - 1;
			if (point.isAxisZ())
			{
				// Swap indexes on the z-axis.
				index = index % 2 == 0 ? index + 1 : index - 1;
			}
			state = GATE_STATE.withProperty(BlockUnderworldGate.AXIS, point.getDirection().getAxis())
					.withProperty(BlockUnderworldGate.SUBBLOCK, BlockUnderworldGate.EnumSubBlock.fromIndex(index));
		}
		else if (val == 0 || val > MAP_STATES.length)
		{
			state = AIR_STATE;
		}
		else
		{
			state = MAP_STATES[val - 1];
			if (state.getBlock() == BlockRegistry.BASALT_STAIRS && point.isAxisZ())
			{
				state = state.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING).rotateY().getOpposite());
			}
		}
		return state;
	}

	private boolean isCrocoiteBlock(int x, int y)
	{
		return (x == 2 || x == 3) && y == 3;
	}

	public static void blockBreak(World world, BlockPos pos)
	{
		BlockPosDirection originPos = WorldRegistry.UNDERWORLD_GATE.getOriginPos(world, pos);
		if (originPos != null)
		{
			world.destroyBlock(originPos.getDirection().getAxis() == EnumFacing.Axis.Z ? originPos.north(2) : originPos.east(2), false);
		}
	}
}
