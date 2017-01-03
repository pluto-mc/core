package com.plutomc.core.common.world.providers;

import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

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
public abstract class BaseChunkProvider implements IChunkGenerator
{
	protected final World world;
	protected final Random rand;
	protected final long seed;
	protected final boolean generateStructures;

	protected NoiseGeneratorOctaves lperlinNoise1;
	protected NoiseGeneratorOctaves lperlinNoise2;
	protected NoiseGeneratorOctaves perlinNoise;
	protected NoiseGeneratorOctaves scaleNoise;
	protected NoiseGeneratorOctaves depthNoise;
	protected double[] lperlinBuffer1;
	protected double[] lperlinBuffer2;
	protected double[] perlinBuffer;
	protected double[] scaleBuffer;
	protected double[] depthBuffer;
	protected double[] noiseField;

	public BaseChunkProvider(World world, boolean generateStructures)
	{
		this.world = world;
		this.rand = world.rand;
		this.seed = world.getSeed();
		this.generateStructures = generateStructures;

		this.lperlinNoise1 = new NoiseGeneratorOctaves(rand, 16);
		this.lperlinNoise2 = new NoiseGeneratorOctaves(rand, 16);
		this.perlinNoise = new NoiseGeneratorOctaves(rand, 8);
		this.scaleNoise = new NoiseGeneratorOctaves(rand, 10);
		this.depthNoise = new NoiseGeneratorOctaves(rand, 16);
	}

	@Nonnull
	@Override
	public Chunk provideChunk(int x, int z)
	{
		long seedMod = (long) (Math.pow(x, 2) * Math.pow(z, 2));
		rand.setSeed(Long.MAX_VALUE / seed * (seedMod == 0 ? 1 : seedMod));

		ChunkPrimer primer = new ChunkPrimer();
		generateTerrain(primer, x, z);
		if (ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, world))
		{
			replaceBiomeBlocks(primer, x, z);
		}
		Chunk chunk = new Chunk(world, primer, x, z);

		Biome[] biomes = world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
		byte[] bytes = chunk.getBiomeArray();
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) Biome.getIdForBiome(biomes[i]);
		}

		chunk.resetRelightChecks();
		return chunk;
	}

	@Override
	public void populate(int x, int z)
	{
		int i = x * 16;
		int j = z * 16;
		BlockPos blockPos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(blockPos.add(16, 0, 16));
		ChunkPos chunkPos = new ChunkPos(x, z);

		BlockFalling.fallInstantly = true;
		ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);

		populate(biome, blockPos, chunkPos);

		ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.world, this.rand, blockPos));

		decorate(biome, blockPos, chunkPos);
		biome.decorate(world, rand, blockPos);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(this.world, this.rand, blockPos));
		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z)
	{
		return false;
	}

	@Nonnull
	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	{
		return world.getBiome(pos).getSpawnableList(creatureType);
	}

	public void getModdedNoiseGenerators(InitNoiseGensEvent.Context context)
	{
		this.lperlinNoise1 = context.getLPerlin1();
		this.lperlinNoise2 = context.getLPerlin2();
		this.perlinNoise = context.getPerlin();
		this.scaleNoise = context.getScale();
		this.depthNoise = context.getDepth();
	}

	public abstract double[] initNoiseField(int x, int z, int mod);

	public abstract void generateTerrain(ChunkPrimer primer, int x, int z);

	public abstract void replaceBiomeBlocks(ChunkPrimer primer, int x, int z);

	public abstract void populate(Biome biome, BlockPos blockPos, ChunkPos chunkPos);

	public abstract void decorate(Biome biome, BlockPos blockPos, ChunkPos chunkPos);
}
