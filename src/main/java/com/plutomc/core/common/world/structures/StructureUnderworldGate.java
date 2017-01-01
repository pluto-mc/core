package com.plutomc.core.common.world.structures;

import com.plutomc.core.common.blocks.BlockStairs;
import com.plutomc.core.init.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

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
	private static final IBlockState CROCOITE_STATE = BlockRegistry.CROCOITE.getBlock().getDefaultState();
	private static final IBlockState[] MAP_STATES = {
			BlockRegistry.BASALT_STAIRS.getBlock().getDefaultState().withProperty(BlockStairs.HALF, net.minecraft.block.BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.EAST),
			BlockRegistry.BASALT_STAIRS.getBlock().getDefaultState().withProperty(BlockStairs.HALF, net.minecraft.block.BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.EAST),
			BlockRegistry.BASALT_STAIRS.getBlock().getDefaultState().withProperty(BlockStairs.HALF, net.minecraft.block.BlockStairs.EnumHalf.BOTTOM).withProperty(BlockStairs.FACING, EnumFacing.WEST),
			BlockRegistry.BASALT_STAIRS.getBlock().getDefaultState().withProperty(BlockStairs.HALF, net.minecraft.block.BlockStairs.EnumHalf.TOP).withProperty(BlockStairs.FACING, EnumFacing.WEST),
			BlockRegistry.BASALT.getBlock().getDefaultState(),
			Blocks.MAGMA.getDefaultState()
	};
	private static final int[][] MAP = {
			{ 1, 3, 0, 0, 1, 3 },
			{ 5, 0, 0, 0, 0, 5 },
			{ 2, 3, 0, 0, 1, 4 },
			{ 9, 5, 0, 0, 5, 9 },
			{ 9, 5, 6, 6, 5, 9 }
	};

	@Override
	public boolean canGenerate(World world, BlockPos startPos, EnumFacing direction)
	{
		return false;
	}

	@Override
	public boolean containsState(IBlockState state)
	{
		return Arrays.asList(MAP_STATES).contains(state);
	}

	public static boolean isGateComplete(World world, BlockPos startPos, EnumFacing.Axis axis)
	{
		if (MAP.length > 0 && MAP[0].length > 0 && axis == EnumFacing.Axis.Z || axis == EnumFacing.Axis.X)
		{
			boolean isAxisZ = axis == EnumFacing.Axis.Z;

			for (int y = 0; y < MAP.length; y++)
			{
				for (int x = 0; x < MAP[0].length; x++)
				{
					int val = MAP[y][x];
					if (val >= MAP_STATES.length)
					{
						continue;
					}

					boolean isCrocoiteBlock = y == 3 && (x == 2 || x == 3);
					IBlockState state = !isCrocoiteBlock ? val == 0 ? AIR_STATE : MAP_STATES[val - 1] : CROCOITE_STATE;
					if (isAxisZ && val >= 1 && val <= 4 )
					{
						state = state.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING).rotateY().getOpposite());
					}

					BlockPos pos = startPos.east(isAxisZ ? 0 : x).down(y).north(isAxisZ ? x : 0);
					if (world.getBlockState(pos) != state)
					{
						if (isCrocoiteBlock && world.isAirBlock(pos)) {}
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
}
