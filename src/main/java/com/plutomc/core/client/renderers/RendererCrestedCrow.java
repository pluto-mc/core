package com.plutomc.core.client.renderers;

import com.plutomc.core.Core;
import com.plutomc.core.client.renderers.models.ModelCrestedCrow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

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
@SideOnly(Side.CLIENT)
public class RendererCrestedCrow extends RenderLiving
{
	private static final ResourceLocation TEXTURE_CROW = new ResourceLocation(Core.MOD_ID, "textures/entity/crested_crow.png");

	public RendererCrestedCrow(RenderManager manager)
	{
		super(manager, new ModelCrestedCrow(), 0.8F);
	}

	@Nullable
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return TEXTURE_CROW;
	}
}
