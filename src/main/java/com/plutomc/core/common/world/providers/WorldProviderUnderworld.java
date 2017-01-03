package com.plutomc.core.common.world.providers;

import com.plutomc.core.init.BiomeRegistry;
import com.plutomc.core.init.DimensionRegistry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

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
public class WorldProviderUnderworld extends BaseWorldProvider
{
	@Override
	public float getLightBrightnessTableMod()
	{
		return 0.2f;
	}

	@Override
	protected void init()
	{
		biomeProvider = new BiomeProviderSingle(BiomeRegistry.ROTTING_FIELDS);
		hasNoSky = true;
	}

	@Nonnull
	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderUnderworld(world, true);
	}

	@Nonnull
	@SideOnly(Side.CLIENT)
	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_)
	{
		return new Vec3d(0.1875, 0.125, 0.125);
	}

	@Override
	public boolean canRespawnHere()
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return true;
	}

	@Nonnull
	@Override
	public DimensionType getDimensionType()
	{
		return DimensionRegistry.UNDERWORLD;
	}
}
