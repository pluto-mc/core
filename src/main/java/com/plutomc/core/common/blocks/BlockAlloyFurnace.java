package com.plutomc.core.common.blocks;

import com.plutomc.core.Core;
import com.plutomc.core.common.tileentities.TileEntityAlloyFurnace;
import com.plutomc.core.init.BlockRegistry;
import com.plutomc.core.init.GuiHandler;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
public class BlockAlloyFurnace extends BaseBlock implements ITileEntityProvider
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	private final boolean isBurning;
	private static boolean keepInventory;

	public BlockAlloyFurnace(boolean isBurning)
	{
		super(isBurning ? BlockRegistry.Data.ALLOY_FURNACE_LIT : BlockRegistry.Data.ALLOY_FURNACE);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(3.5f);
		setHarvestLevel("pickaxe", 1);
		setLightLevel(isBurning ? 0.875f : 0);
		setResistance(17.5f);
		setSoundType(SoundType.STONE);

		isBlockContainer = true;
		this.isBurning = isBurning;
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityAlloyFurnace();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!keepInventory)
		{
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if (tileEntity instanceof TileEntityAlloyFurnace)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityAlloyFurnace) tileEntity);
				worldIn.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY)
	{
		if (!worldIn.isRemote)
		{
			playerIn.openGui(Core.instance(), GuiHandler.ALLOY_FURNACE, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}

		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityAlloyFurnace)
		{
			((TileEntityAlloyFurnace) tileEntity).setCustomInventoryName(stack.getDisplayName());
		}
	}

	@Nonnull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return BlockRegistry.ALLOY_FURNACE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (isBurning)
		{
			EnumFacing facing = stateIn.getValue(FACING);
			double d0 = pos.getX() + 0.5D;
			double d1 = pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
			double d2 = pos.getZ() + 0.5D;
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;

			if (rand.nextDouble() < 0.1d)
			{
				worldIn.playSound(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1, 1, false);
			}

			switch (facing)
			{
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
					break;
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	public static void setBurningAtPos(World worldIn, BlockPos pos, boolean burning)
	{
		IBlockState targetState = burning ? BlockRegistry.ALLOY_FURNACE_LIT.getBlock().getDefaultState() : BlockRegistry.ALLOY_FURNACE.getBlock().getDefaultState();
		IBlockState blockState = worldIn.getBlockState(pos);
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		keepInventory = true;
		worldIn.setBlockState(pos, targetState.withProperty(FACING, blockState.getValue(FACING)), 3);
		if (tileEntity != null)
		{
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}
		keepInventory = false;
	}
}
