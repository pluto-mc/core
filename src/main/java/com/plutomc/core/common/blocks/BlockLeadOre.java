package com.plutomc.core.common.blocks;

import com.plutomc.core.init.BlockRegistry;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
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
public class BlockLeadOre extends BlockOre
{
	public static final PropertyBool HAS_CROCOITE = PropertyBool.create("has_crocoite");

	public BlockLeadOre()
	{
		super(BlockRegistry.Data.LEAD_ORE);
		setDefaultState(getBlockState().getBaseState().withProperty(HAS_CROCOITE, false));
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, HAS_CROCOITE);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(HAS_CROCOITE, meta > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(HAS_CROCOITE) ? 1 : 0;
	}

	@Nonnull
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = new ArrayList<ItemStack>();
		Random rand = world instanceof World ? ((World) world).rand : RANDOM;

		ret.add(new ItemStack(this, 1, damageDropped(state)));
		if (state.getValue(HAS_CROCOITE))
		{
			ret.add(new ItemStack(BlockRegistry.CROCOITE, rand.nextInt(1) + 1, 0));
		}

		return ret;
	}
}
