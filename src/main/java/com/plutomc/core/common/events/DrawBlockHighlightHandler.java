package com.plutomc.core.common.events;

import com.plutomc.core.common.tileentities.TileEntityUnderworldGate;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
@Mod.EventBusSubscriber
public class DrawBlockHighlightHandler
{
	@SubscribeEvent
	public static void drawBlockHighlight(DrawBlockHighlightEvent event)
	{
		World world = event.getPlayer().getEntityWorld();

		if (event.getTarget() != null)
		{
			BlockPos blockPos = event.getTarget().getBlockPos();
			TileEntity tileEntity = world.getTileEntity(blockPos);

			if (tileEntity instanceof TileEntityUnderworldGate)
			{
				event.setCanceled(true);
			}
		}
	}
}
