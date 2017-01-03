package com.plutomc.core.common.world.biomes;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeHellDecorator;
import net.minecraftforge.common.BiomeDictionary;

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
public class BiomeRottingFields extends BaseBiome
{
	public BiomeRottingFields(BiomeProperties properties)
	{
		super(properties);
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 25, 4, 6));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieVillager.class, 50, 4, 6));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieHorse.class, 25, 2, 4));
		this.theBiomeDecorator = new BiomeHellDecorator();
		this.topBlock = Blocks.SOUL_SAND.getDefaultState();
		this.fillerBlock = Blocks.SOUL_SAND.getDefaultState();
	}

	@Override
	public BiomeDictionary.Type[] getTypes()
	{
		return new BiomeDictionary.Type[] { BiomeDictionary.Type.WET, BiomeDictionary.Type.SPOOKY, BiomeDictionary.Type.DEAD };
	}

	@Override
	public boolean isSpawnBiome()
	{
		return true;
	}
}
