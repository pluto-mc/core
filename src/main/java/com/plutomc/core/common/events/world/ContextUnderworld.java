package com.plutomc.core.common.events.world;

import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;

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
public class ContextUnderworld extends InitNoiseGensEvent.Context
{
	private NoiseGeneratorOctaves boneBlocks;

	public ContextUnderworld(NoiseGeneratorOctaves lperlin1, NoiseGeneratorOctaves lperlin2, NoiseGeneratorOctaves perlin, NoiseGeneratorOctaves scale, NoiseGeneratorOctaves depth, NoiseGeneratorOctaves boneBlocks)
	{
		super(lperlin1, lperlin2, perlin, scale, depth);
		this.boneBlocks = boneBlocks;
	}

	public NoiseGeneratorOctaves getBoneBlocks()
	{
		return boneBlocks;
	}

	public void setBoneBlocks(NoiseGeneratorOctaves boneBlocks)
	{
		this.boneBlocks = boneBlocks;
	}
}
