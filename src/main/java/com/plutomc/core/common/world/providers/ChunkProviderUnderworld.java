package com.plutomc.core.common.world.providers;

import com.plutomc.core.common.events.world.ContextUnderworld;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nullable;

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
public class ChunkProviderUnderworld extends BaseChunkProvider
{
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
	private static final IBlockState BONE_BLOCK = Blocks.BONE_BLOCK.getDefaultState();
	private static final IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
	private static final IBlockState WATER = Blocks.WATER.getDefaultState();

	private NoiseGeneratorOctaves boneBlockNoise;
	private double[] boneBlockBuffer = new double[256];

	public ChunkProviderUnderworld(World world, boolean generateStructures)
	{
		super(world, generateStructures);
		world.setSeaLevel(63);

		this.boneBlockNoise = new NoiseGeneratorOctaves(rand, 8);

		ContextUnderworld context = new ContextUnderworld(lperlinNoise1, lperlinNoise2, perlinNoise, boneBlockNoise, scaleNoise, depthNoise);
		context = TerrainGen.getModdedNoiseGenerators(world, rand, context);
		getModdedNoiseGenerators(context);
		this.boneBlockNoise = context.getPerlin2();
	}

	@Override
	public double[] initNoiseField(int x, int z, int mod)
	{
		x = x * mod;
		int y = 0;
		z = z * mod;
		int i = 5;
		int j = 17;
		int k = 5;

		ChunkGeneratorEvent.InitNoiseField event = new ChunkGeneratorEvent.InitNoiseField(this, noiseField, x, y, z, i, j, k);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.getResult() == Event.Result.DENY)
		{
			return event.getNoisefield();
		}

		if (noiseField == null)
		{
			noiseField = new double[i * j * k];
		}

		double d0 = 684.412D;
		double d1 = 2053.236D;
		scaleBuffer = scaleNoise.generateNoiseOctaves(scaleBuffer, x, y, z, i, 1, k, 1D, 0D, 1D);
		depthBuffer = depthNoise.generateNoiseOctaves(depthBuffer, x, y, z, i, 1, k, 100D, 0D, 100D);
		perlinBuffer = perlinNoise.generateNoiseOctaves(perlinBuffer, x, y, z, i, j, k, 8.555150000000001D, 34.2206D, 8.555150000000001D);
		lperlinBuffer1 = lperlinNoise1.generateNoiseOctaves(lperlinBuffer1, x, y, z, i, j, k, d0, d1, d0);
		lperlinBuffer2 = lperlinNoise2.generateNoiseOctaves(lperlinBuffer2, x, y, z, i, j, k, d0, d1, d0);
		double[] bufferY = new double[j];
		int i1 = 0;

		for (int j1 = 0; j1 < j; j1++)
		{
			bufferY[j1] = Math.cos((double) j1 * Math.PI * 6D / (double) j) * 2D;
			double d2 = (double) j1;

			if (j1 > j / 2)
			{
				d2 = (double) (j - 1 - j1);
			}

			if (d2 < 4D)
			{
				d2 = 4D - d2;
				bufferY[j1] -= d2 * d2 * d2 * 10D;
			}
		}

		for (int i2 = 0; i2 < i; i2++)
		{
			for (int k1 = 0; k1 < k; k1++)
			{
				for (int j1 = 0; j1 < j; j1++)
				{
					double d4 = bufferY[j1];
					double d5 = lperlinBuffer1[i1] / 512D;
					double d6 = lperlinBuffer2[i1] / 512D;
					double d7 = (perlinBuffer[i1] / 10D + 1D) / 2D;
					double d8;

					if (d7 < 0D)
					{
						d8 = d5;
					}
					else if (d7 > 1D)
					{
						d8 = d6;
					}
					else
					{
						d8 = d5 + (d6 - d5) * d7;
					}

					d8 = d8 - d4;

					if (j1 > j - 4)
					{
						double d9 = (double) ((float) (j1 - (j - 4)) / 3F);
						d8 = d8 * (1D - d9) + -10D * d9;
					}

					if ((double) j1 < 0D)
					{
						double d10 = (0D - (double) j1) / 4D;
						d10 = MathHelper.clamp(d10, 0D, 1D);
						d8 = d8 * (1D - d10) + -10D * d10;
					}

					noiseField[i1] = d8;
					i1++;
				}
			}
		}

		return noiseField;
	}

	@Override
	public void generateTerrain(ChunkPrimer primer, int x, int z)
	{
		int j = world.getSeaLevel() / 2 + 1;
		double[] buffer = initNoiseField(x, z, 4);

		for (int j1 = 0; j1 < 4; ++j1)
		{
			for (int k1 = 0; k1 < 4; ++k1)
			{
				for (int l1 = 0; l1 < 16; ++l1)
				{
					double d0 = 0.125D;
					double d1 = buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 0];
					double d2 = buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 0];
					double d3 = buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 0];
					double d4 = buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 0];
					double d5 = (buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 1] - d1) * 0.125D;
					double d6 = (buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
					double d7 = (buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 1] - d3) * 0.125D;
					double d8 = (buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;

					for (int i2 = 0; i2 < 8; ++i2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * 0.25D;
						double d13 = (d4 - d2) * 0.25D;

						for (int j2 = 0; j2 < 4; ++j2)
						{
							double d14 = 0.25D;
							double d15 = d10;
							double d16 = (d11 - d10) * 0.25D;

							for (int k2 = 0; k2 < 4; ++k2)
							{
								IBlockState state = null;

								if (l1 * 8 + i2 < j)
								{
									state = BONE_BLOCK;
								}

								if (d15 > 0.0D)
								{
									state = SOUL_SAND;
								}

								int l2 = j2 + j1 * 4;
								int i3 = i2 + l1 * 8;
								int j3 = k2 + k1 * 4;
								primer.setBlockState(l2, i3, j3, state);
								d15 += d16;
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	@Override
	public void replaceBiomeBlocks(ChunkPrimer primer, int x, int z)
	{
		int j = world.getSeaLevel() + 1;
		double d0 = 0.03125D;
		boneBlockBuffer = boneBlockNoise.generateNoiseOctaves(boneBlockBuffer, x * 16, z * 16, 0, 16, 16, 1, d0, d0, 1);
		depthBuffer = depthNoise.generateNoiseOctaves(depthBuffer, x * 16, z * 16, 0, 16, 16, 1, d0 * 2, d0 * 2, d0 * 2);

		for (int i = 0; i < 16; i++)
		{
			for (int k = 0; k < 16; k++)
			{
				boolean flag = boneBlockBuffer[i + k * 16] + rand.nextDouble() * 0.2D > 0.0D;
				int l = (int)(depthBuffer[i + k * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int i1 = -1;
				IBlockState state1 = SOUL_SAND;
				IBlockState state2 = SOUL_SAND;

				for (int j1 = 127; j1 >= 0; j1--)
				{
					if (j1 < 127 - this.rand.nextInt(5) && j1 > this.rand.nextInt(5))
					{
						IBlockState state3 = primer.getBlockState(k, j1, i);

						if (state3.getBlock() != null && state3.getMaterial() != Material.AIR)
						{
							if (state3.getBlock() == Blocks.SOUL_SAND)
							{
								if (i1 == -1)
								{
									if (l <= 0)
									{
										state1 = AIR;
										state2 = SOUL_SAND;
									}
									else if (j1 >= j - 4 && j1 <= j + 1)
									{
										state1 = SOUL_SAND;
										state2 = SOUL_SAND;

										if (flag)
										{
											state1 = BONE_BLOCK;
											state2 = BONE_BLOCK;
										}
									}

									if (j1 < j && (state1 == null || state1.getMaterial() == Material.AIR))
									{
										state1 = WATER;
									}

									i1 = l;

									if (j1 >= j - 1)
									{
										primer.setBlockState(k, j1, i, state1);
									}
									else
									{
										primer.setBlockState(k, j1, i, state2);
									}
								}
								else if (i1 > 0)
								{
									i1--;
									primer.setBlockState(k, j1, i, state2);
								}
							}
						}
						else
						{
							i1 = -1;
						}
					}
					else
					{
						primer.setBlockState(k, j1, i, BEDROCK);
					}
				}
			}
		}
	}

	@Override
	public void populate(Biome biome, BlockPos blockPos, ChunkPos chunkPos)
	{

	}

	@Override
	public void decorate(Biome biome, BlockPos blockPos, ChunkPos chunkPos)
	{

	}

	@Nullable
	@Override
	public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_)
	{
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z)
	{

	}
}
