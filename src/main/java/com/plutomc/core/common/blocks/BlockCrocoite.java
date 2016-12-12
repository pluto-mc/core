package com.plutomc.core.common.blocks;

import com.plutomc.core.init.BlockRegistry;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

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
public class BlockCrocoite extends BaseBlock implements IGrowable
{
	public static final PropertyInteger GROWTH = PropertyInteger.create("growth", 0, getMaxGrowth());

	private final List<AxisAlignedBB> boundingBoxes = new ArrayList<AxisAlignedBB>() {{
		add(new AxisAlignedBB(5d * BaseBlock.PIXEL_SIZE, 0d, 5d * BaseBlock.PIXEL_SIZE,
				11d * BaseBlock.PIXEL_SIZE, 7d * BaseBlock.PIXEL_SIZE, 11d * BaseBlock.PIXEL_SIZE));
		add(new AxisAlignedBB(5d * BaseBlock.PIXEL_SIZE, 0d, 5d * BaseBlock.PIXEL_SIZE,
				13d * BaseBlock.PIXEL_SIZE, 9d * BaseBlock.PIXEL_SIZE, 13d * BaseBlock.PIXEL_SIZE));
		add(new AxisAlignedBB(3d * BaseBlock.PIXEL_SIZE, 0d, 3d * BaseBlock.PIXEL_SIZE,
				13d * BaseBlock.PIXEL_SIZE, 11d * BaseBlock.PIXEL_SIZE, 13d * BaseBlock.PIXEL_SIZE));
		add(new AxisAlignedBB(3d * BaseBlock.PIXEL_SIZE, 0d, 3d * BaseBlock.PIXEL_SIZE,
				13d * BaseBlock.PIXEL_SIZE, 13d * BaseBlock.PIXEL_SIZE, 13d * BaseBlock.PIXEL_SIZE));
	}};

	public BlockCrocoite()
	{
		super(BlockRegistry.Data.CROCOITE);
		setDefaultState(getBlockState().getBaseState().withProperty(GROWTH, 0));
		setHardness(1);
		setSoundType(SoundType.GLASS);
		setTickRandomly(true);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, GROWTH);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(GROWTH, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(GROWTH);
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}

	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return boundingBoxes.get(state.getValue(GROWTH));
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return state.getValue(GROWTH) + 1;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (canGrow(worldIn, pos, state, !worldIn.isRemote)
				&& ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(31) == 0))
		{
			grow(worldIn, rand, pos, state);
			ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return worldIn.getBlockState(pos.down()).getBlock().getUnlocalizedName().equals(BlockRegistry.LEAD_ORE.getUnlocalizedName())
				&& state.getValue(GROWTH) < getMaxGrowth();
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, state.withProperty(GROWTH, state.getValue(GROWTH) + 1));
	}

	public static int getMaxGrowth()
	{
		return 3;
	}
}
