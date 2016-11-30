package com.plutomc.core.client.gui;

import com.plutomc.core.Core;
import com.plutomc.core.common.gui.ContainerAlloyFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
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
public class GuiAlloyFurnace extends GuiContainer
{
	private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(Core.MOD_ID + ":textures/gui/container/alloy_furnace.png");
	private final InventoryPlayer playerInventory;
	private final IInventory tileFurnace;

	public GuiAlloyFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory)
	{
		super(new ContainerAlloyFurnace(playerInventory, furnaceInventory));
		this.playerInventory = playerInventory;
		this.tileFurnace = furnaceInventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tileFurnace.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		drawTexturedModalRect(i, j, 0, 0, xSize, ySize);

		if (TileEntityFurnace.isBurning(tileFurnace))
		{
			int k = getBurnLeftScaled(13);
			drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
		}

		int l = getCookProgressScaled(24);
		drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
	}

	private int getCookProgressScaled(int pixels)
	{
		int i = tileFurnace.getField(2);
		int j = tileFurnace.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}

	private int getBurnLeftScaled(int pixels)
	{
		int i = tileFurnace.getField(1);

		if (i == 0)
		{
			i = 200;
		}

		return tileFurnace.getField(0) * pixels / i;
	}
}
