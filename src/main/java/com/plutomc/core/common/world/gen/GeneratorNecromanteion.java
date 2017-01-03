package com.plutomc.core.common.world.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

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
public class GeneratorNecromanteion extends BaseGenerator
{
	private static final WorldGenNecromanteion WORLD_GEN = new WorldGenNecromanteion();
	private static final int MIN_Y = 4;
	private static final int MAX_Y = 8;
	private static final int CHUNK_COUNT = 64;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, int dimension)
	{
		if (dimension == 0)
		{
			generateNecromanteion(random, chunkX, chunkZ, world);
		}
	}

	private void generateNecromanteion(Random random, int chunkX, int chunkZ, World world)
	{
		if (random.nextInt(CHUNK_COUNT) == 0)
		{
			int y = MIN_Y + random.nextInt(MAX_Y - MIN_Y);
			WORLD_GEN.generate(world, random, new BlockPos(chunkX * 16, y, chunkZ * 16));
		}
	}
}
