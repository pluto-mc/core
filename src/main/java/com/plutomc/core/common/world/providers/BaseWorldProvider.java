package com.plutomc.core.common.world.providers;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;

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
public abstract class BaseWorldProvider extends WorldProvider
{
	@Override
	protected void generateLightBrightnessTable()
	{
		float mod = getLightBrightnessTableMod();
		for (int i = 0; i < 16; i++)
		{
			float f = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f) / (f * 3.0F + 1.0F) * (1.0F - mod);
		}
	}

	@Nonnull
	@Override
	public abstract IChunkGenerator createChunkGenerator();

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	public abstract float getLightBrightnessTableMod();
}
