package com.plutomc.core.init;

import com.plutomc.core.Core;
import com.plutomc.core.client.renderers.RendererCrestedCrow;
import com.plutomc.core.client.renderers.factories.FactoryCrestedCrow;
import com.plutomc.core.common.data.IDataEntity;
import com.plutomc.core.common.entities.mobs.AnimalCrestedCrow;
import com.plutomc.core.common.entities.mobs.BaseAnimal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
public class MobRegistry
{
	public enum Data implements IDataEntity
	{
		CRESTED_CROW(0, "crested_crow", AnimalCrestedCrow.class, 0xFCDC00, 0xA40000);

		private final int id;
		private final String name;
		private final Class<? extends BaseAnimal> entityClass;
		private final int eggPrimary;
		private final int eggSecondary;

		Data(int id, String name, Class<? extends BaseAnimal> entityClass, int eggPrimary, int eggSecondary)
		{
			this.id = id;
			this.name = name;
			this.entityClass = entityClass;
			this.eggPrimary = eggPrimary;
			this.eggSecondary = eggSecondary;
		}

		@Override
		public int getID()
		{
			return id;
		}

		@Override
		public ResourceLocation getRegistryLocation()
		{
			return new ResourceLocation(Core.MOD_ID, name);
		}

		@Override
		public Class<? extends BaseAnimal> getEntityClass()
		{
			return entityClass;
		}

		@Override
		public String getEntityName()
		{
			return name;
		}

		@Override
		public int getEggPrimary()
		{
			return eggPrimary;
		}

		@Override
		public int getEggSecondary()
		{
			return eggSecondary;
		}
	}

	public static void preInit()
	{
		for (Data data : Data.values())
		{
			registerMob(data, Core.instance());
		}
	}

	public static void registerMob(Data data, Object mod)
	{
		EntityRegistry.registerModEntity(data.getRegistryLocation(), data.getEntityClass(), data.getEntityName(), data.getID(), mod, 64, 1, true, data.getEggPrimary(), data.getEggSecondary());
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		for (Data data : Data.values())
		{
			registerRender(data);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerRender(Data data)
	{
		RenderingRegistry.registerEntityRenderingHandler(data.getEntityClass(), new FactoryCrestedCrow());
	}
}
