package com.plutomc.core.common.blocks;

import com.google.common.collect.Lists;
import com.plutomc.core.common.data.IDataBlock;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
// NOTE: Copied from net.minecraft.block.BlockStairs.
public class BlockStairs extends BaseBlock
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<net.minecraft.block.BlockStairs.EnumHalf> HALF = net.minecraft.block.BlockStairs.HALF;
	public static final PropertyEnum<net.minecraft.block.BlockStairs.EnumShape> SHAPE = net.minecraft.block.BlockStairs.SHAPE;
	/**
	 * B: .. T: xx
	 * B: .. T: xx
	 */
	protected static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	/**
	 * B: .. T: x.
	 * B: .. T: x.
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
	/**
	 * B: .. T: .x
	 * B: .. T: .x
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	/**
	 * B: .. T: xx
	 * B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
	/**
	 * B: .. T: ..
	 * B: .. T: xx
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
	/**
	 * B: .. T: x.
	 * B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 0.5D);
	/**
	 * B: .. T: .x
	 * B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
	/**
	 * B: .. T: ..
	 * B: .. T: x.
	 */
	protected static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 0.5D, 1.0D, 1.0D);
	/**
	 * B: .. T: ..
	 * B: .. T: .x
	 */
	protected static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
	/**
	 * B: xx T: ..
	 * B: xx T: ..
	 */
	protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	/**
	 * B: x. T: ..
	 * B: x. T: ..
	 */
	protected static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
	/**
	 * B: .x T: ..
	 * B: .x T: ..
	 */
	protected static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	/**
	 * B: xx T: ..
	 * B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
	/**
	 * B: .. T: ..
	 * B: xx T: ..
	 */
	protected static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
	/**
	 * B: x. T: ..
	 * B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);
	/**
	 * B: .x T: ..
	 * B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
	/**
	 * B: .. T: ..
	 * B: x. T: ..
	 */
	protected static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, 1.0D);
	/**
	 * B: .. T: ..
	 * B: .x T: ..
	 */
	protected static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
	private final BaseBlock modelBlock;
	private final IBlockState modelState;

	public BlockStairs(IDataBlock data, BaseBlock modelBlock)
	{
		super(data);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, net.minecraft.block.BlockStairs.EnumHalf.BOTTOM).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.STRAIGHT));
		this.modelBlock = modelBlock;
		this.modelState = modelBlock.getDefaultState();
		this.setHardness(this.modelBlock.getBlockHardness());
		String harvestTool;
		if ((harvestTool = modelBlock.getHarvestTool(modelState)) != null)
		{
			this.setHarvestLevel(harvestTool, modelBlock.getHarvestLevel(modelState));
		}
		this.setResistance(this.modelBlock.getBlockResistance() / 3.0F);
		this.setSoundType(this.modelBlock.getBlockSoundType());
		this.setLightOpacity(255);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
	{
		state = this.getActualState(state, worldIn, pos);

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state))
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
		}
	}

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
	{
		List<AxisAlignedBB> list = Lists.newArrayList();
		boolean flag = bstate.getValue(HALF) == net.minecraft.block.BlockStairs.EnumHalf.TOP;
		list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
		net.minecraft.block.BlockStairs.EnumShape blockstairs$enumshape = bstate.getValue(SHAPE);

		if (blockstairs$enumshape == net.minecraft.block.BlockStairs.EnumShape.STRAIGHT || blockstairs$enumshape == net.minecraft.block.BlockStairs.EnumShape.INNER_LEFT || blockstairs$enumshape == net.minecraft.block.BlockStairs.EnumShape.INNER_RIGHT)
		{
			list.add(getCollQuarterBlock(bstate));
		}

		if (blockstairs$enumshape != net.minecraft.block.BlockStairs.EnumShape.STRAIGHT)
		{
			list.add(getCollEighthBlock(bstate));
		}

		return list;
	}

	/**
	 * Returns a bounding box representing a quarter of a block (two eight-size cubes back to back).
	 * Used in all stair shapes except OUTER.
	 */
	private static AxisAlignedBB getCollQuarterBlock(IBlockState bstate)
	{
		boolean flag = bstate.getValue(HALF) == net.minecraft.block.BlockStairs.EnumHalf.TOP;

		switch (bstate.getValue(FACING))
		{
			case NORTH:
			default:
				return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
			case SOUTH:
				return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
			case WEST:
				return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
			case EAST:
				return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
		}
	}

	/**
	 * Returns a bounding box representing an eighth of a block (a block whose three dimensions are halved).
	 * Used in all stair shapes except STRAIGHT (gets added alone in the case of OUTER; alone with a quarter block in
	 * case of INSIDE).
	 */
	private static AxisAlignedBB getCollEighthBlock(IBlockState bstate)
	{
		EnumFacing enumfacing = bstate.getValue(FACING);
		EnumFacing enumfacing1;

		switch (bstate.getValue(SHAPE))
		{
			case OUTER_LEFT:
			default:
				enumfacing1 = enumfacing;
				break;
			case OUTER_RIGHT:
				enumfacing1 = enumfacing.rotateY();
				break;
			case INNER_RIGHT:
				enumfacing1 = enumfacing.getOpposite();
				break;
			case INNER_LEFT:
				enumfacing1 = enumfacing.rotateYCCW();
		}

		boolean flag = bstate.getValue(HALF) == net.minecraft.block.BlockStairs.EnumHalf.TOP;

		switch (enumfacing1)
		{
			case NORTH:
			default:
				return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
			case SOUTH:
				return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
			case WEST:
				return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
			case EAST:
				return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
		}
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		this.modelBlock.randomDisplayTick(stateIn, worldIn, pos, rand);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
	{
		this.modelBlock.onBlockClicked(worldIn, pos, playerIn);
	}

	/**
	 * Called when a player destroys this Block
	 */
	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state)
	{
		this.modelBlock.onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return this.modelState.getPackedLightmapCoords(source, pos);
	}

	/**
	 * Returns how much this block can resist explosions from the passed in entity.
	 */
	@Override
	public float getExplosionResistance(Entity exploder)
	{
		return this.modelBlock.getExplosionResistance(exploder);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn)
	{
		return this.modelBlock.tickRate(worldIn);
	}

	@Nonnull
	@Override
	public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion)
	{
		return this.modelBlock.modifyAcceleration(worldIn, pos, entityIn, motion);
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return this.modelBlock.getBlockLayer();
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		return this.modelState.getSelectedBoundingBox(worldIn, pos);
	}

	/**
	 * Returns if this block is collidable. Only used by fire, although stairs return that of the block that the stair
	 * is made of (though nobody's going to make fire stairs, right?)
	 */
	@Override
	public boolean isCollidable()
	{
		return this.modelBlock.isCollidable();
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
	{
		return this.modelBlock.canCollideCheck(state, hitIfLiquid);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return this.modelBlock.canPlaceBlockAt(worldIn, pos);
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile Entity is set
	 */
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		this.modelState.neighborChanged(worldIn, pos, Blocks.AIR, pos);
		this.modelBlock.onBlockAdded(worldIn, pos, this.modelState);
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
	 */
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		this.modelBlock.breakBlock(worldIn, pos, this.modelState);
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block)
	 */
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		this.modelBlock.onEntityWalk(worldIn, pos, entityIn);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		this.modelBlock.updateTick(worldIn, pos, state, rand);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return this.modelBlock.onBlockActivated(worldIn, pos, this.modelState, playerIn, hand, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
	}

	/**
	 * Called when this Block is destroyed by an Explosion
	 */
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn)
	{
		this.modelBlock.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	/**
	 * Checks if an IBlockState represents a block that is opaque and a full cube.
	 */
	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return state.getValue(HALF) == net.minecraft.block.BlockStairs.EnumHalf.TOP;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Nonnull
	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return this.modelBlock.getMapColor(this.modelState);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	@Nonnull
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
		iblockstate = iblockstate.withProperty(FACING, placer.getHorizontalFacing()).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.STRAIGHT);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate.withProperty(HALF, net.minecraft.block.BlockStairs.EnumHalf.BOTTOM) : iblockstate.withProperty(HALF, net.minecraft.block.BlockStairs.EnumHalf.TOP);
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit.
	 */
	@Nullable
	@Override
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
	{
		List<RayTraceResult> list = Lists.newArrayList();

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
		{
			list.add(this.rayTrace(pos, start, end, axisalignedbb));
		}

		RayTraceResult raytraceresult1 = null;
		double d1 = 0.0D;

		for (RayTraceResult raytraceresult : list)
		{
			if (raytraceresult != null)
			{
				double d0 = raytraceresult.hitVec.squareDistanceTo(end);

				if (d0 > d1)
				{
					raytraceresult1 = raytraceresult;
					d1 = d0;
				}
			}
		}

		return raytraceresult1;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(HALF, (meta & 4) > 0 ? net.minecraft.block.BlockStairs.EnumHalf.TOP : net.minecraft.block.BlockStairs.EnumHalf.BOTTOM);
		iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(5 - (meta & 3)));
		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		if (state.getValue(HALF) == net.minecraft.block.BlockStairs.EnumHalf.TOP)
		{
			i |= 4;
		}

		i = i | 5 - state.getValue(FACING).getIndex();
		return i;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Nonnull
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(SHAPE, getStairsShape(state, worldIn, pos));
	}

	private static net.minecraft.block.BlockStairs.EnumShape getStairsShape(IBlockState p_185706_0_, IBlockAccess p_185706_1_, BlockPos p_185706_2_)
	{
		EnumFacing enumfacing = p_185706_0_.getValue(FACING);
		IBlockState iblockstate = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing));

		if (isBlockStairs(iblockstate) && p_185706_0_.getValue(HALF) == iblockstate.getValue(HALF))
		{
			EnumFacing enumfacing1 = iblockstate.getValue(FACING);

			if (enumfacing1.getAxis() != p_185706_0_.getValue(FACING).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing1.getOpposite()))
			{
				if (enumfacing1 == enumfacing.rotateYCCW())
				{
					return net.minecraft.block.BlockStairs.EnumShape.OUTER_LEFT;
				}

				return net.minecraft.block.BlockStairs.EnumShape.OUTER_RIGHT;
			}
		}

		IBlockState iblockstate1 = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing.getOpposite()));

		if (isBlockStairs(iblockstate1) && p_185706_0_.getValue(HALF) == iblockstate1.getValue(HALF))
		{
			EnumFacing enumfacing2 = iblockstate1.getValue(FACING);

			if (enumfacing2.getAxis() != p_185706_0_.getValue(FACING).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing2))
			{
				if (enumfacing2 == enumfacing.rotateYCCW())
				{
					return net.minecraft.block.BlockStairs.EnumShape.INNER_LEFT;
				}

				return net.minecraft.block.BlockStairs.EnumShape.INNER_RIGHT;
			}
		}

		return net.minecraft.block.BlockStairs.EnumShape.STRAIGHT;
	}

	private static boolean isDifferentStairs(IBlockState p_185704_0_, IBlockAccess p_185704_1_, BlockPos p_185704_2_, EnumFacing p_185704_3_)
	{
		IBlockState iblockstate = p_185704_1_.getBlockState(p_185704_2_.offset(p_185704_3_));
		return !isBlockStairs(iblockstate) || iblockstate.getValue(FACING) != p_185704_0_.getValue(FACING) || iblockstate.getValue(HALF) != p_185704_0_.getValue(HALF);
	}

	public static boolean isBlockStairs(IBlockState state)
	{
		return state.getBlock() instanceof net.minecraft.block.BlockStairs;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Nonnull
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Nonnull
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		EnumFacing enumfacing = state.getValue(FACING);
		net.minecraft.block.BlockStairs.EnumShape blockstairs$enumshape = state.getValue(SHAPE);

		switch (mirrorIn)
		{
			case LEFT_RIGHT:

				if (enumfacing.getAxis() == EnumFacing.Axis.Z)
				{
					switch (blockstairs$enumshape)
					{
						case OUTER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.OUTER_LEFT);
						case INNER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.INNER_LEFT);
						case INNER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.INNER_RIGHT);
						default:
							return state.withRotation(Rotation.CLOCKWISE_180);
					}
				}

				break;
			case FRONT_BACK:

				if (enumfacing.getAxis() == EnumFacing.Axis.X)
				{
					switch (blockstairs$enumshape)
					{
						case OUTER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.OUTER_LEFT);
						case INNER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.INNER_RIGHT);
						case INNER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, net.minecraft.block.BlockStairs.EnumShape.INNER_LEFT);
						case STRAIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180);
					}
				}
		}

		return super.withMirror(state, mirrorIn);
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, HALF, SHAPE);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
			return super.doesSideBlockRendering(state, world, pos, face);

		if (state.isOpaqueCube())
			return true;

		state = this.getActualState(state, world, pos);

		net.minecraft.block.BlockStairs.EnumHalf half = state.getValue(HALF);
		EnumFacing side = state.getValue(FACING);
		net.minecraft.block.BlockStairs.EnumShape shape = state.getValue(SHAPE);
		if (face == EnumFacing.UP) return half == net.minecraft.block.BlockStairs.EnumHalf.TOP;
		if (face == EnumFacing.DOWN) return half == net.minecraft.block.BlockStairs.EnumHalf.BOTTOM;
		if (shape == net.minecraft.block.BlockStairs.EnumShape.OUTER_LEFT || shape == net.minecraft.block.BlockStairs.EnumShape.OUTER_RIGHT)
			return false;
		if (face == side) return true;
		if (shape == net.minecraft.block.BlockStairs.EnumShape.INNER_LEFT && face.rotateY() == side) return true;
		return shape == net.minecraft.block.BlockStairs.EnumShape.INNER_RIGHT && face.rotateYCCW() == side;
	}
}
