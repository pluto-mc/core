package com.plutomc.core.common.world;

import com.plutomc.core.Core;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

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
public class WorldGenNecromanteion extends WorldGenerator
{
	private static final ResourceLocation STRUCTURE = new ResourceLocation(Core.MOD_ID, "necromanteion");

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos)
	{
		Random random = worldIn.getChunkFromBlockCoords(pos).getRandomWithSeed(worldIn.getSeed());
		MinecraftServer server = worldIn.getMinecraftServer();
		TemplateManager tm = worldIn.getSaveHandler().getStructureTemplateManager();
		Template template = tm.getTemplate(server, STRUCTURE);

		Rotation[] rotations = Rotation.values();
		Rotation rotation = rotations[random.nextInt(rotations.length)];
		BlockPos size = template.transformedSize(rotation);
		int x = random.nextInt(16 - size.getX() % 16);
		int z = random.nextInt(16 - size.getZ() % 16);
		int y = pos.getY() + size.getY();
		ChunkPos chunkPos = new ChunkPos(pos);

		StructureBoundingBox structureBB = new StructureBoundingBox(chunkPos.getXStart() + x, 0, chunkPos.getZStart() + z, chunkPos.getXEnd() + x + size.getX(), 255, chunkPos.getZEnd() + z + size.getZ());
		PlacementSettings structureSettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(structureBB).setRandom(random);

		BlockPos originPos = template.getZeroPositionWithTransform(pos.add(x, y, z), Mirror.NONE, rotation);
		template.addBlocksToWorld(worldIn, originPos, structureSettings, 20);
		return true;
	}
}
