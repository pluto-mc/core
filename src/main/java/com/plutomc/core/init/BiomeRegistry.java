package com.plutomc.core.init;

import com.plutomc.core.common.world.biomes.BaseBiome;
import com.plutomc.core.common.world.biomes.BiomeRottingFields;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

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
public class BiomeRegistry
{
	public static final BaseBiome ROTTING_FIELDS = new BiomeRottingFields(new Biome.BiomeProperties("Rotting Fields").setRainDisabled());

	public static void init()
	{
		register(ROTTING_FIELDS);
	}

	public static void register(BaseBiome biome)
	{
		// TODO: Create biome IDs dynamically.
		Biome.registerBiome(99, biome.getBiomeName(), biome);
		if (biome.isSpawnBiome())
		{
			BiomeManager.addSpawnBiome(biome);
		}
		BiomeDictionary.addTypes(biome, biome.getTypes());
	}
}
