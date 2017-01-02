package com.plutomc.core.common.blocks;

import com.plutomc.core.common.data.IDataBlock;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
public class BlockPillar extends BaseBlock
{
	public static final PropertyBool BASE_BOTTOM = PropertyBool.create("base_bottom");
	public static final PropertyBool BASE_TOP = PropertyBool.create("base_top");

	private final BaseBlock modelBlock;
	private final IBlockState modelState;

	public BlockPillar(IDataBlock data, BaseBlock modelBlock)
	{
		super(data);
		setDefaultState(this.blockState.getBaseState().withProperty(BASE_BOTTOM, true).withProperty(BASE_TOP, true));
		this.modelBlock = modelBlock;
		this.modelState = modelBlock.getDefaultState();
		setHardness(this.modelBlock.getBlockHardness());
		String harvestTool;
		if ((harvestTool = this.modelBlock.getHarvestTool(this.modelState)) != null)
		{
			setHarvestLevel(harvestTool, this.modelBlock.getHarvestLevel(this.modelState));
		}
		setResistance(this.modelBlock.getBlockResistance() / 2f);
		setSoundType(this.modelBlock.getBlockSoundType());
		setLightOpacity(127);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BASE_BOTTOM, BASE_TOP);
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		IBlockState downState = world.getBlockState(pos.down());
		IBlockState upState = world.getBlockState(pos.up());
		return getDefaultState().withProperty(BASE_BOTTOM, stateHasBase(downState)).withProperty(BASE_TOP, stateHasBase(upState));
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(BASE_BOTTOM, (meta & 1) == 1).withProperty(BASE_TOP, (meta >> 1 & 1) == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int result = 0;
		result |= state.getValue(BASE_BOTTOM) ? 1 : 0;
		result |= (state.getValue(BASE_TOP) ? 1 : 0) << 1;
		return result;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		IBlockState neighborState = worldIn.getBlockState(fromPos);
		if (pos.down().equals(fromPos))
		{
			worldIn.setBlockState(pos, state.withProperty(BASE_BOTTOM, stateHasBase(neighborState)));
		}
		else if (pos.up().equals(fromPos))
		{
			worldIn.setBlockState(pos, state.withProperty(BASE_TOP, stateHasBase(neighborState)));
		}
	}

	public boolean stateHasBase(IBlockState state)
	{
		return state.getBlock() != this;
	}
}
