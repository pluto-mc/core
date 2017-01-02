package com.plutomc.core.client.renderers;

import com.plutomc.core.Core;
import com.plutomc.core.client.renderers.models.ModelUnderworldGate;
import com.plutomc.core.common.blocks.BlockUnderworldGate;
import com.plutomc.core.common.tileentities.TileEntityUnderworldGate;
import com.plutomc.core.init.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
@SideOnly(Side.CLIENT)
public class RendererUnderworldGate extends TileEntitySpecialRenderer<TileEntityUnderworldGate>
{
	private static final ResourceLocation[] TEXTURES_GATE = {
			new ResourceLocation(Core.MOD_ID, "textures/entity/underworld_gate_0.png"),
			new ResourceLocation(Core.MOD_ID, "textures/entity/underworld_gate_1.png"),
			new ResourceLocation(Core.MOD_ID, "textures/entity/underworld_gate_2.png"),
			new ResourceLocation(Core.MOD_ID, "textures/entity/underworld_gate_3.png")
	};
	private static final long MAX_TIME = 23999;
	private ModelUnderworldGate model;
	private int textureIndex;
	private long textureTime;

	public RendererUnderworldGate()
	{
		this.model = new ModelUnderworldGate();
		this.textureIndex = 0;
		this.textureTime = -1;
	}

	@Override
	public void renderTileEntityAt(TileEntityUnderworldGate te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		IBlockState state = te.getWorld().getBlockState(te.getPos());

		if (state.getBlock() == BlockRegistry.UNDERWORLD_GATE && state.getValue(BlockUnderworldGate.SUBBLOCK).isRender())
		{
			super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

			if (textureTime == -1 || textureTime < getWorld().getTotalWorldTime())
			{
				textureTime = getNextTextureTime();
				if (++textureIndex >= TEXTURES_GATE.length)
				{
					textureIndex = 0;
				}
			}
			bindTexture(TEXTURES_GATE[textureIndex]);

			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, z);
			GlStateManager.disableLighting();

			if (state.getValue(BlockUnderworldGate.AXIS) == EnumFacing.Axis.X)
			{
				GlStateManager.translate(0f, 0f, 0.5f);
			} else
			{
				GlStateManager.translate(0.5f, 0f, 0f);
				GlStateManager.rotate(-90f, 0f, 1f, 0f);
			}
			GlStateManager.scale(1f, 1f, 1f);
			model.renderGate();

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

	private long getNextTextureTime()
	{
		long nextTime = getWorld().getTotalWorldTime() + 2;
		return nextTime > MAX_TIME ? 0 : nextTime;
	}
}
