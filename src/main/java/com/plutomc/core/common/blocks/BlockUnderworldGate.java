package com.plutomc.core.common.blocks;

import com.plutomc.core.common.blocks.properties.PredicateAxisOrientation;
import com.plutomc.core.common.tileentities.TileEntityUnderworldGate;
import com.plutomc.core.init.BlockRegistry;
import com.plutomc.core.init.DimensionRegistry;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
public class BlockUnderworldGate extends BaseBlock implements ITileEntityProvider
{
	public enum EnumSubBlock implements IStringSerializable
	{
		BOTTOM_LEFT(0, "bottom_left", 0, 0),
		BOTTOM_RIGHT(1, "bottom_right", 1, 0),
		CENTER_LEFT(2, "center_left", 0, 1),
		CENTER_RIGHT(3, "center_right", 1, 1),
		TOP_LEFT(4, "top_left", 0, 2),
		TOP_RIGHT(5, "top_right", 1, 2);

		private final int index;
		private final String name;
		private final BlockPos pos;

		EnumSubBlock(int index, String name, int x, int y)
		{
			this.index = index;
			this.name = name;
			this.pos = new BlockPos(x, y, 0);
		}

		public int getIndex()
		{
			return index;
		}

		@Nonnull
		@Override
		public String getName()
		{
			return name;
		}

		public BlockPos getPos()
		{
			return pos;
		}

		public BlockPos getRotatedPos(EnumFacing.Axis axis)
		{
			return getRotatedPos(axis, true);
		}

		public BlockPos getRotatedPos(EnumFacing.Axis axis, boolean translate)
		{
			return axis == EnumFacing.Axis.X ? getPos() : getPos().rotate(Rotation.CLOCKWISE_90).north(translate ? 1 : 0);
		}

		public static EnumSubBlock fromIndex(int index)
		{
			return values()[MathHelper.abs(index % values().length)];
		}

		public boolean isRender()
		{
			return this == BOTTOM_LEFT;
		}
	}

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, PredicateAxisOrientation.HORIZONTAL);
	public static final PropertyEnum<EnumSubBlock> SUBBLOCK = PropertyEnum.create("subblock", EnumSubBlock.class);

	private static final AxisAlignedBB X_AABB = new AxisAlignedBB(0d, 0d, 9d * PIXEL_SIZE, 1d, 1d, 7d * PIXEL_SIZE);
	private static final AxisAlignedBB Z_AABB = new AxisAlignedBB(7d * PIXEL_SIZE, 0d, 1d, 9d * PIXEL_SIZE, 1d, 0d);
	private static final AxisAlignedBB X_RENDER_AABB = new AxisAlignedBB(0d, 0d, 9d * PIXEL_SIZE, 2d, 3d, 7d * PIXEL_SIZE);
	private static final AxisAlignedBB Z_RENDER_AABB = new AxisAlignedBB(7d * PIXEL_SIZE, 0d, 2d, 9d * PIXEL_SIZE, 3d, 0d);

	private static final int DIMENSION = DimensionRegistry.UNDERWORLD.getId();

	public BlockUnderworldGate()
	{
		super(BlockRegistry.Data.UNDERWORLD_GATE);
		setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X).withProperty(SUBBLOCK, EnumSubBlock.BOTTOM_LEFT));
		setBlockUnbreakable();
		setLightLevel(1f);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AXIS, SUBBLOCK);
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(AXIS, (meta & 1) == 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z).withProperty(SUBBLOCK, EnumSubBlock.fromIndex((meta & 14) >> 1));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int result = 0;
		result |= (state.getValue(AXIS) == EnumFacing.Axis.X ? 0 : 1);
		result |= (state.getValue(SUBBLOCK).getIndex() << 1);
		return result;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(SUBBLOCK).isRender())
		{
			worldIn.removeTileEntity(pos);
		}

		BlockUnderworldGate.destroy(worldIn, pos, state);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityUnderworldGate();
	}

	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return state.getValue(AXIS) == EnumFacing.Axis.X ? X_AABB : Z_AABB;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return (state.getValue(AXIS) == EnumFacing.Axis.X ? X_RENDER_AABB : Z_RENDER_AABB).offset(pos);
	}

	@Nonnull
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		BlockPos subBlockPos = state.getValue(SUBBLOCK).getRotatedPos(state.getValue(AXIS), false);
		return getRenderBoundingBox(state, worldIn, pos).offset(-subBlockPos.getX(), -subBlockPos.getY(), -subBlockPos.getZ());
	}

	@Nonnull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(Blocks.AIR);
	}

	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.INVISIBLE;
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
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
		{
			int toDimension;
			if (entityIn.dimension == 0)
			{
				toDimension = DIMENSION;
			}
			else if (entityIn.dimension == DIMENSION)
			{
				toDimension = 0;
			}
			else
			{
				return;
			}

			if (entityIn instanceof EntityPlayerMP)
			{
				// TODO: Create custom Teleporter.
				entityIn.changeDimension(toDimension);
			}
			else
			{
				// TODO: Teleport other entities to Underworld.
			}
		}
	}

	public static void create(World world, BlockPos pos, EnumFacing.Axis axis)
	{
		IBlockState state = BlockRegistry.UNDERWORLD_GATE.getDefaultState().withProperty(AXIS, axis);
		for (EnumSubBlock subBlock : EnumSubBlock.values())
		{
			world.setBlockState(pos.add(subBlock.getRotatedPos(axis)), state.withProperty(SUBBLOCK, subBlock), 3);
		}
	}

	public static void destroy(World world, BlockPos pos, IBlockState state)
	{
		BlockPos startPos = pos.subtract(state.getValue(SUBBLOCK).getRotatedPos(state.getValue(AXIS)));
		for (EnumSubBlock subBlock : EnumSubBlock.values())
		{
			BlockPos subBlockPos = startPos.add(subBlock.getRotatedPos(state.getValue(AXIS)));
			if (world.getBlockState(subBlockPos) == state.withProperty(SUBBLOCK, subBlock))
			{
				world.destroyBlock(subBlockPos, false);
			}
		}
	}
}
