package com.plutomc.core.common.world;

import com.plutomc.core.common.blocks.BlockUnderworldGate;
import com.plutomc.core.common.blocks.util.BlockPosDirection;
import com.plutomc.core.common.world.structures.IWorldStructure;
import com.plutomc.core.common.world.structures.StructureUnderworldGate;
import com.plutomc.core.init.BlockRegistry;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

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
public abstract class BaseTeleporter extends Teleporter
{
	protected final WorldServer oldWorldServer, toWorldServer;
	protected final BlockPos collisionPos;
	protected final Random random;
	protected final Long2ObjectMap<PortalPosition> destCoordCache;

	public BaseTeleporter(WorldServer oldWorldServer, WorldServer toWorldServer, BlockPos collisionPos)
	{
		super(toWorldServer);
		this.oldWorldServer = oldWorldServer;
		this.toWorldServer = toWorldServer;
		this.collisionPos = collisionPos;
		this.random = new Random(toWorldServer.getSeed());
		this.destCoordCache = new Long2ObjectOpenHashMap<PortalPosition>(4096);
	}

	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
		if (!placeInExistingPortal(entityIn, rotationYaw))
		{
			makePortal(entityIn);
			placeInExistingPortal(entityIn, rotationYaw);
		}
	}

	@Override
	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
	{
		double d0 = -1D;
		int j = MathHelper.floor(entityIn.posX);
		int k = MathHelper.floor(entityIn.posZ);
		boolean flagAddCoordToCache = true;
		BlockPos pos = BlockPos.ORIGIN;
		long l = ChunkPos.asLong(j, k);

		if (destCoordCache.containsKey(l))
		{
			Teleporter.PortalPosition portalPos = destCoordCache.get(l);
			d0 = 0D;
			pos = portalPos;
			portalPos.lastUpdateTime = toWorldServer.getTotalWorldTime();
			flagAddCoordToCache = false;
		}
		else
		{
			BlockPos entityPos = new BlockPos(entityIn);

			for (int i1 = -128; i1 <= 128; ++i1)
			{
				BlockPos nextPortalPos;

				for (int j1 = -128; j1 <= 128; ++j1)
				{
					for (BlockPos portalPos = entityPos.add(i1, toWorldServer.getActualHeight() - 1 - entityPos.getY(), j1); portalPos.getY() >= 0; portalPos = nextPortalPos)
					{
						nextPortalPos = portalPos.down();

						if (toWorldServer.getBlockState(portalPos).getBlock() == BlockRegistry.UNDERWORLD_GATE)
						{
							for (nextPortalPos = portalPos.down(); toWorldServer.getBlockState(nextPortalPos).getBlock() == BlockRegistry.UNDERWORLD_GATE; nextPortalPos = nextPortalPos.down())
							{
								portalPos = nextPortalPos;
							}

							double d1 = portalPos.distanceSq(entityPos);

							if (d0 < 0D || d1 < d0)
							{
								d0 = d1;
								pos = portalPos;
							}
						}
					}
				}
			}
		}

		if (d0 >= 0D)
		{
			if (flagAddCoordToCache)
			{
				destCoordCache.put(l, new Teleporter.PortalPosition(pos, toWorldServer.getTotalWorldTime()));
			}

			BlockPosDirection portalPos = getPortalStructure().getOriginPos(toWorldServer, pos);
			if (portalPos == null)
			{
				return false;
			}

			double d5 = Math.floor(portalPos.getY());
			boolean flagIsAxisZ = portalPos.getDirection().getAxis() == EnumFacing.Axis.Z;
			BlockPosDirection forwardPos = new BlockPosDirection(flagIsAxisZ ? portalPos.north() : portalPos.east(), portalPos.getDirection().rotateY());
			double d6 = (double) forwardPos.getX() + 0.5D;
			double d7 = (double) forwardPos.getZ() + 0.5D;
			entityIn.rotationYaw = rotationYaw - (forwardPos.getDirection().getHorizontalIndex() * 90F);

			if (entityIn instanceof EntityPlayerMP)
			{
				((EntityPlayerMP) entityIn).connection.setPlayerLocation(d6, d5, d7, entityIn.rotationYaw, entityIn.rotationPitch);
			}
			else
			{
				entityIn.setLocationAndAngles(d6, d5, d7, entityIn.rotationYaw, entityIn.rotationPitch);
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean makePortal(Entity entityIn)
	{
		double d0 = -1D;
		int j = MathHelper.floor(entityIn.posX);
		int k = MathHelper.floor(entityIn.posY);
		int l = MathHelper.floor(entityIn.posZ);
		int j10 = j;
		int k10 = k;
		int l10 = l;
		int l1 = 0;
		int kRand = random.nextInt(4);
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

		for (int j2 = j - 16; j2 <= j + 16; ++j2)
		{
			double d1 = (double) j2 + 0.5D - entityIn.posX;

			for (int l2 = l - 16; l2 <= l + 16; ++l2)
			{
				double d2 = (double) l2 + 0.5D - entityIn.posZ;
				labelLoopY:

				for (int k2 = toWorldServer.getActualHeight() - 1; k2 >= 0; --k2)
				{
					if (toWorldServer.isAirBlock(mutablePos.setPos(j2, k2, l2)))
					{
						while (k2 > 0 && toWorldServer.isAirBlock(mutablePos.setPos(j2, k2 - 1, l2)))
						{
							--k2;
						}

						for (int k3 = kRand; k3 < kRand + 4; ++k3)
						{
							int l3 = k3 % 2;
							int l6 = 1 - l3;

							if (k3 % 4 >= 2)
							{
								l3 = -l3;
								l6 = -l6;
							}

							for (int j4 = 0; j4 < 3; ++j4)
							{
								for (int l4 = 0; l4 < 4; ++l4)
								{
									for (int k4 = -1; k4 < 4; ++k4)
									{
										int j5 = j2 + (l4 - 1) * l3 + j4 * l6;
										int k5 = k2 + k4;
										int l5 = l2 + (l4 - 1) * l6 - j4 * l3;
										mutablePos.setPos(j5, k5, l5);

										if (k4 < 0 && !toWorldServer.getBlockState(mutablePos).getMaterial().isSolid() || k4 >= 0 && !toWorldServer.isAirBlock(mutablePos))
										{
											continue labelLoopY;
										}
									}
								}
							}

							double d5 = (double) k2 + 0.5D - entityIn.posY;
							double d7 = d1 * d1 + d5 * d5 + d2 * d2;

							if (d0 < 0D || d7 < d0)
							{
								d0 = d7;
								j10 = j2;
								k10 = k2;
								l10 = l2;
								l1 = k3 % 4;
							}
						}
					}
				}
			}
		}

		if (d0 < 0D)
		{
			for (int j2 = j - 16; j2 <= j + 16; ++j2)
			{
				double d3 = (double) j2 + 0.5D - entityIn.posX;

				for (int l2 = l - 16; l2 <= l + 16; ++l2)
				{
					double d4 = (double) l2 + 0.5D - entityIn.posZ;
					labelLoopY:

					for (int k2 = toWorldServer.getActualHeight() - 1; k2 >= 0; --k2)
					{
						if (toWorldServer.isAirBlock(mutablePos.setPos(j2, k2, l2)))
						{
							while (k2 > 0 && toWorldServer.isAirBlock(mutablePos.setPos(j2, k2 - 1, l2)))
							{
								--k2;
							}

							for (int k3 = kRand; k3 < kRand + 2; ++k3)
							{
								int j3 = k3 % 2;
								int j6 = 1 - j3;

								for (int j4 = 0; j4 < 4; ++j4)
								{
									for (int k4 = -1; k4 < 4; ++k4)
									{
										int j5 = j2 + (j4 - 1) * j3;
										int k5 = k2 + k4;
										int l5 = l2 + (j4 - 1) * j6;
										mutablePos.setPos(j5, k5, l5);

										if (k4 < 0 && !toWorldServer.getBlockState(mutablePos).getMaterial().isSolid() || k4 >= 0 && !toWorldServer.isAirBlock(mutablePos))
										{
											continue labelLoopY;
										}
									}
								}

								double d6 = (double) k2 + 0.5D - entityIn.posY;
								double d8 = d3 * d3 + d6 * d6 + d4 * d4;

								if (d0 < 0D || d8 < d0)
								{
									d0 = d8;
									j10 = j2;
									k10 = k2;
									l10 = l2;
									l1 = k3 % 2;
								}
							}
						}
					}
				}
			}
		}

		int x = j10;
		int y = k10;
		int z = l10;
		int xMod = l1 % 2;
		int zMod = 1 - xMod;
		if (l1 % 4 >= 2)
		{
			xMod = -xMod;
			zMod = -zMod;
		}
		IBlockState oldPortalState = oldWorldServer.getBlockState(collisionPos);
		if (oldPortalState == null || oldPortalState.getBlock() != BlockRegistry.UNDERWORLD_GATE)
		{
			return false;
		}
		EnumFacing direction = StructureUnderworldGate.AXIS_HORIZONTALS[oldPortalState.getValue(BlockUnderworldGate.AXIS) == EnumFacing.Axis.Z ? 0 : 1];

		getPortalStructure().generate(toWorldServer, new BlockPos(x, (y - 1) + (getPortalStructure().getHeight() - 1), z), direction, xMod, zMod);

		return true;
	}

	public abstract IWorldStructure getPortalStructure();
}
