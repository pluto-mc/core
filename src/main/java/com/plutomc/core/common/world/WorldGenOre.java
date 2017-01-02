package com.plutomc.core.common.world;

import com.google.common.base.Predicate;
import com.plutomc.core.common.data.IDataOre;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

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
public class WorldGenOre extends WorldGenerator
{
	private static class StonePredicate implements Predicate<IBlockState>
	{
		public boolean apply(IBlockState state)
		{
			if (state != null && state.getBlock() == Blocks.STONE)
			{
				BlockStone.EnumType type = state.getValue(BlockStone.VARIANT);
				return type.isNatural();
			}
			else
			{
				return false;
			}
		}
	}

	private final IDataOre oreData;
	private final IBlockState block;
	private final int numberOfBlocks;
	private final Predicate<IBlockState> predicate;

	public WorldGenOre(IDataOre oreData)
	{
		this.oreData = oreData;
		this.block = oreData.getBlock().getDefaultState();
		this.numberOfBlocks = oreData.getBlockCount();
		this.predicate = new StonePredicate();
	}

	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		float f = rand.nextFloat() * (float)Math.PI;
		double d0 = (double)((float)(position.getX() + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
		double d1 = (double)((float)(position.getX() + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
		double d2 = (double)((float)(position.getZ() + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
		double d3 = (double)((float)(position.getZ() + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
		double d4 = (double)(position.getY() + rand.nextInt(3) - 2);
		double d5 = (double)(position.getY() + rand.nextInt(3) - 2);

		for (int i = 0; i < this.numberOfBlocks; ++i)
		{
			float f1 = (float)i / (float)this.numberOfBlocks;
			double d6 = d0 + (d1 - d0) * (double)f1;
			double d7 = d4 + (d5 - d4) * (double)f1;
			double d8 = d2 + (d3 - d2) * (double)f1;
			double d9 = rand.nextDouble() * (double)this.numberOfBlocks / 16.0D;
			double d10 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
			double d11 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
			int j = MathHelper.floor(d6 - d10 / 2.0D);
			int k = MathHelper.floor(d7 - d11 / 2.0D);
			int l = MathHelper.floor(d8 - d10 / 2.0D);
			int i1 = MathHelper.floor(d6 + d10 / 2.0D);
			int j1 = MathHelper.floor(d7 + d11 / 2.0D);
			int k1 = MathHelper.floor(d8 + d10 / 2.0D);

			for (int l1 = j; l1 <= i1; ++l1)
			{
				double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D)
				{
					for (int i2 = k; i2 <= j1; ++i2)
					{
						double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D)
						{
							for (int j2 = l; j2 <= k1; ++j2)
							{
								double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
								{
									BlockPos blockpos = new BlockPos(l1, i2, j2);

									IBlockState state = worldIn.getBlockState(blockpos);
									if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, this.predicate))
									{
										worldIn.setBlockState(blockpos, this.getBlockForPlacement(rand), 2);
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

	protected IBlockState getBlock()
	{
		return block;
	}

	public IBlockState getBlockForPlacement(Random rand)
	{
		return this.getBlock();
	}

	public IDataOre getOreData()
	{
		return oreData;
	}
}
