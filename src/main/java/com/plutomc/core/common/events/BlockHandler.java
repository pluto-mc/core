package com.plutomc.core.common.events;

import com.plutomc.core.common.blocks.BlockUnderworldGate;
import com.plutomc.core.common.world.structures.StructureUnderworldGate;
import com.plutomc.core.init.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
@Mod.EventBusSubscriber
public class BlockHandler
{
	@SubscribeEvent
	public static void handleBreakEvent(BlockEvent.BreakEvent event)
	{
		// TODO: Destroy Underworld Gate when any of the structure's blocks are destroyed.
	}

	@SubscribeEvent
	public static void handlePlaceEvent(BlockEvent.PlaceEvent event)
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();

		if (event.getPlacedBlock().getBlock() == BlockRegistry.CROCOITE.getBlock())
		{
			BlockPos downPos = pos.down();
			IBlockState downState = world.getBlockState(downPos);
			if (downState.getBlock() == Blocks.MAGMA)
			{
				EnumFacing.Axis structAxis;
				BlockPos structPos;
				if (world.getBlockState(downPos.north()).getBlock() == Blocks.MAGMA)
				{
					structAxis = EnumFacing.Axis.Z;
					structPos = downPos.south(2).up(4);
				}
				else if (world.getBlockState(downPos.south()).getBlock() == Blocks.MAGMA)
				{
					structAxis = EnumFacing.Axis.Z;
					structPos = downPos.south(3).up(4);
				}
				else if (world.getBlockState(downPos.east()).getBlock() == Blocks.MAGMA)
				{
					structAxis = EnumFacing.Axis.X;
					structPos = downPos.west(2).up(4);
				}
				else if (world.getBlockState(downPos.west()).getBlock() == Blocks.MAGMA)
				{
					structAxis = EnumFacing.Axis.X;
					structPos = downPos.west(3).up(4);
				}
				else
				{
					world.destroyBlock(pos, true);
					return;
				}
				BlockPos gatePos = (structAxis == EnumFacing.Axis.X ? structPos.east(2) : structPos.north(2)).down(2);

				if (StructureUnderworldGate.isGateComplete(world, structPos, structAxis))
				{
					event.setCanceled(true);
					event.getItemInHand().shrink(1);
					BlockUnderworldGate.create(world, gatePos, structAxis);
				}
				else
				{
					world.destroyBlock(pos, true);
				}
			}
		}
	}
}
