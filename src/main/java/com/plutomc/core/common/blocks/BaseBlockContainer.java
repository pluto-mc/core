package com.plutomc.core.common.blocks;

import com.plutomc.core.init.BlockRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

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
public abstract class BaseBlockContainer extends BlockContainer
{
	public final BlockRegistry.Data data;

	public BaseBlockContainer(BlockRegistry.Data data)
	{
		super(data.getMaterial());
		setUnlocalizedName(data.getUnlocalizedName());
		setRegistryName(data.getRegistryName());
		setCreativeTab(data.getCreativeTab());
		this.data = data;
	}

	@Override
	public abstract Item getItemDropped(IBlockState state, Random rand, int fortune);

	@Override
	public abstract TileEntity createNewTileEntity(World worldIn, int meta);
}
