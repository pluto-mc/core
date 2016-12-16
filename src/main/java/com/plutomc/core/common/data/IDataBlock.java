package com.plutomc.core.common.data;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;

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
public interface IDataBlock
{
	public String getUnlocalizedName();

	public String getRegistryName();

	public String getOreDictName();

	public CreativeTabs getCreativeTab();

	public Material getMaterial();

	public Class<? extends TileEntity> getTileEntityClass();

	public String getTileEntityName();

	public void setTileEntityID(int tileEntityID);

	public int getTileEntityID();
}
