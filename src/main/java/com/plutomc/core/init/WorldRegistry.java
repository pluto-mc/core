package com.plutomc.core.init;

import com.plutomc.core.common.blocks.BaseBlock;
import com.plutomc.core.common.world.GeneratorOre;
import com.plutomc.core.common.world.structures.IWorldStructure;
import com.plutomc.core.common.world.structures.StructureUnderworldGate;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
public class WorldRegistry
{
	public enum OreData {
		COPPER_ORE(BlockRegistry.COPPER_ORE, 1, 63, 8, 10),
		TIN_ORE(BlockRegistry.TIN_ORE, 1, 63, 8, 6),
		LEAD_ORE(BlockRegistry.LEAD_ORE, 1, 15, 4, 2);

		private final BaseBlock block;
		private final int minY;
		private final int maxY;
		private final int diffY;
		private final int blockCount;
		private final int spawnChance;

		OreData(BaseBlock block, int minY, int maxY, int blockCount, int spawnChance)
		{
			this.block = block;
			this.minY = minY;
			this.maxY = maxY;
			this.diffY = maxY - minY + 1;
			this.blockCount = blockCount;
			this.spawnChance = spawnChance;
		}

		public BaseBlock getBlock()
		{
			return block;
		}

		public int getMinY()
		{
			return minY;
		}

		public int getMaxY()
		{
			return maxY;
		}

		public int getDiffY()
		{
			return diffY;
		}

		public int getBlockCount()
		{
			return blockCount;
		}

		public int getSpawnChance()
		{
			return spawnChance;
		}
	}

	public static final IWorldStructure UNDERWORLD_GATE = new StructureUnderworldGate();

	public static void init()
	{
		GameRegistry.registerWorldGenerator(new GeneratorOre(), 0);
	}
}
