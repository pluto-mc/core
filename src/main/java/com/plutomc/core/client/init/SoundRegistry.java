package com.plutomc.core.client.init;

import com.plutomc.core.client.sounds.BaseSoundEvent;
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
public class SoundRegistry
{
	public static final BaseSoundEvent BLOCK_QUERN_STONE_GRIND = new BaseSoundEvent("quern_stone_grind");

	public static void init()
	{
		registerSoundEvent(BLOCK_QUERN_STONE_GRIND);
	}

	public static void registerSoundEvent(BaseSoundEvent soundEvent)
	{
		GameRegistry.register(soundEvent, soundEvent.getSoundName());
	}
}
