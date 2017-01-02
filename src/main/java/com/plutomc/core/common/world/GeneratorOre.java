package com.plutomc.core.common.world;

import com.plutomc.core.common.data.IDataOre;
import com.plutomc.core.init.WorldRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class GeneratorOre extends BaseGenerator
{
	private List<WorldGenOre> worldGens;

	public GeneratorOre()
	{
		super();

		worldGens = new ArrayList<WorldGenOre>() {{
			add(new WorldGenOre(WorldRegistry.OreData.COPPER_ORE));
			add(new WorldGenOre(WorldRegistry.OreData.TIN_ORE));
			add(new WorldGenLeadOre());
		}};
	}

	@Override
	public void run(Random random, int chunkX, int chunkZ, World world, int dimension)
	{
		switch (dimension)
		{
			case -1:
				// Nether
				break;

			case 0:
				// Surface
				for (WorldGenOre worldGen : worldGens)
				{
					generateOre(worldGen, random, chunkX, chunkZ, world);
				}
				break;

			case 1:
				// End
				break;
		}
	}

	private void generateOre(WorldGenOre worldGen, Random random, int chunkX, int chunkZ, World world)
	{
		IDataOre oreData = worldGen.getOreData();
		for (int i = 0; i < oreData.getSpawnChance(); i ++) {
			double x = chunkX * 16 + random.nextInt(16);
			double y = oreData.getMinY() + random.nextInt(oreData.getDiffY());
			double z = chunkZ * 16 + random.nextInt(16);
			worldGen.generate(world, random, new BlockPos(x, y, z));
		}
	}
}
