package com.plutomc.core.init;

import com.plutomc.core.Core;
import com.plutomc.core.common.blocks.*;
import com.plutomc.core.common.data.IDataBlock;
import com.plutomc.core.common.items.BaseItemBlock;
import com.plutomc.core.common.tileentities.TileEntityAlloyFurnace;
import com.plutomc.core.common.tileentities.TileEntityQuernStone;
import com.plutomc.core.common.tileentities.TileEntityUnderworldGate;
import com.plutomc.core.client.renderers.RendererUnderworldGate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
public class BlockRegistry
{
	public enum Data implements IDataBlock
	{
		ALLOY_FURNACE("alloy_furnace", null, CreativeTabs.DECORATIONS, Material.ROCK, TileEntityAlloyFurnace.class),
		ALLOY_FURNACE_LIT("alloy_furnace_lit", null, null, Material.ROCK, null),
		CROCOITE("crocoite", null, CreativeTabs.MATERIALS, Material.GLASS, null),
		QUERN_STONE("quern_stone", null, CreativeTabs.DECORATIONS, Material.ROCK, TileEntityQuernStone.class),
		ROTTEN_FLESH_BLOCK("rotten_flesh_block", null, CreativeTabs.BUILDING_BLOCKS, Materials.FLESH, null),
		UNDERWORLD_GATE("underworld_gate", null, null, Material.PORTAL, TileEntityUnderworldGate.class),

		BASALT("basalt", null, CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),
		BASALT_STAIRS("stairs_basalt", null, CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),
		BASALT_BRICK("basalt_brick", null, CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),
		BASALT_BRICK_STAIRS("stairs_basalt_brick", null, CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),
		BASALT_PILLAR("pillar_basalt", null, CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),

		COPPER_BLOCK("copper_block", "blockCopper", CreativeTabs.DECORATIONS, Material.ROCK, null),
		COPPER_ORE("copper_ore", "oreCopper", CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),

		TIN_BLOCK("tin_block", "blockTin", CreativeTabs.DECORATIONS, Material.ROCK, null),
		TIN_ORE("tin_ore", "oreTin", CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),

		BRONZE_BLOCK("bronze_block", "blockBronze", CreativeTabs.DECORATIONS, Material.ROCK, null),

		LEAD_BLOCK("lead_block", "blockLead", CreativeTabs.DECORATIONS, Material.ROCK, null),
		LEAD_ORE("lead_ore", "oreLead", CreativeTabs.BUILDING_BLOCKS, Material.ROCK, null),

		STEEL_BLOCK("steel_block", "blockSteel", CreativeTabs.DECORATIONS, Material.ROCK, null);

		private final String name;
		private final String oreDictName;
		private final CreativeTabs tab;
		private final Material material;
		private final Class<? extends TileEntity> tileEntity;
		private int tileEntityID;

		Data(String name, String oreDictName, CreativeTabs tab, Material material, Class<? extends TileEntity> tileEntity)
		{
			this.name = name;
			this.oreDictName = oreDictName;
			this.tab = tab;
			this.material = material;
			this.tileEntity = tileEntity;
		}

		@Override
		public String getUnlocalizedName()
		{
			return name;
		}

		@Override
		public String getRegistryName()
		{
			return name;
		}

		@Override
		public String getOreDictName()
		{
			return oreDictName;
		}

		@Override
		public CreativeTabs getCreativeTab()
		{
			return tab;
		}

		@Override
		public Material getMaterial()
		{
			return material;
		}

		@Override
		public Class<? extends TileEntity> getTileEntityClass()
		{
			return tileEntity;
		}

		@Override
		public String getTileEntityName()
		{
			return Core.MOD_ID + ":" + getRegistryName() + "_tileentity";
		}

		@Override
		public void setTileEntityID(int tileEntityID)
		{
			this.tileEntityID = tileEntityID;
		}

		@Override
		public int getTileEntityID()
		{
			return tileEntityID;
		}
	}

	public static final BaseBlock ALLOY_FURNACE = new BlockAlloyFurnace(false);
	public static final BaseBlock ALLOY_FURNACE_LIT = new BlockAlloyFurnace(true);
	public static final BaseBlock CROCOITE = new BlockCrocoite();
	public static final BaseBlock QUERN_STONE = new BlockQuernStone();
	public static final BaseBlock ROTTEN_FLESH_BLOCK = new BlockRottenFlesh();
	public static final BaseBlock UNDERWORLD_GATE = new BlockUnderworldGate();
	public static final BaseBlock BASALT = new BlockBasalt(Data.BASALT);
	public static final BaseBlock BASALT_STAIRS = new BlockStairs(Data.BASALT_STAIRS, BASALT);
	public static final BaseBlock BASALT_BRICK = new BlockBasalt(Data.BASALT_BRICK);
	public static final BaseBlock BASALT_BRICK_STAIRS = new BlockStairs(Data.BASALT_BRICK_STAIRS, BASALT_BRICK);
	public static final BaseBlock BASALT_PILLAR = new BlockPillar(Data.BASALT_PILLAR, BASALT);
	public static final BaseBlock COPPER_BLOCK = new BlockMetal(Data.COPPER_BLOCK);
	public static final BaseBlock COPPER_ORE = new BlockOre(Data.COPPER_ORE);
	public static final BaseBlock TIN_BLOCK = new BlockMetal(Data.TIN_BLOCK);
	public static final BaseBlock TIN_ORE = new BlockOre(Data.TIN_ORE);
	public static final BaseBlock BRONZE_BLOCK = new BlockMetal(Data.BRONZE_BLOCK);
	public static final BaseBlock LEAD_BLOCK = new BlockMetal(Data.LEAD_BLOCK);
	public static final BaseBlock LEAD_ORE = new BlockLeadOre();
	public static final BaseBlock STEEL_BLOCK = new BlockMetal(Data.STEEL_BLOCK);
	
	private static final BaseItemBlock ITEM_ALLOY_FURNACE = new BaseItemBlock(ALLOY_FURNACE);
	private static final BaseItemBlock ITEM_ALLOY_FURNACE_LIT = new BaseItemBlock(ALLOY_FURNACE_LIT);
	private static final BaseItemBlock ITEM_CROCOITE = new BaseItemBlock(CROCOITE);
	private static final BaseItemBlock ITEM_QUERN_STONE = new BaseItemBlock(QUERN_STONE);
	private static final BaseItemBlock ITEM_ROTTEN_FLESH_BLOCK = new BaseItemBlock(ROTTEN_FLESH_BLOCK);
	private static final BaseItemBlock ITEM_UNDERWORLD_GATE = new BaseItemBlock(UNDERWORLD_GATE);
	private static final BaseItemBlock ITEM_BASALT = new BaseItemBlock(BASALT);
	private static final BaseItemBlock ITEM_BASALT_STAIRS = new BaseItemBlock(BASALT_STAIRS);
	private static final BaseItemBlock ITEM_BASALT_BRICK = new BaseItemBlock(BASALT_BRICK);
	private static final BaseItemBlock ITEM_BASALT_BRICK_STAIRS = new BaseItemBlock(BASALT_BRICK_STAIRS);
	private static final BaseItemBlock ITEM_BASALT_PILLAR = new BaseItemBlock(BASALT_PILLAR);
	private static final BaseItemBlock ITEM_COPPER_BLOCK = new BaseItemBlock(COPPER_BLOCK);
	private static final BaseItemBlock ITEM_COPPER_ORE = new BaseItemBlock(COPPER_ORE);
	private static final BaseItemBlock ITEM_TIN_BLOCK = new BaseItemBlock(TIN_BLOCK);
	private static final BaseItemBlock ITEM_TIN_ORE = new BaseItemBlock(TIN_ORE);
	private static final BaseItemBlock ITEM_BRONZE_BLOCK = new BaseItemBlock(BRONZE_BLOCK);
	private static final BaseItemBlock ITEM_LEAD_BLOCK = new BaseItemBlock(LEAD_BLOCK);
	private static final BaseItemBlock ITEM_LEAD_ORE = new BaseItemBlock(LEAD_ORE);
	private static final BaseItemBlock ITEM_STEEL_BLOCK = new BaseItemBlock(STEEL_BLOCK);

	private static int TILEENTITY_ID = 1;

	public static void preInit()
	{
		register(ITEM_ALLOY_FURNACE);
		register(ITEM_ALLOY_FURNACE_LIT);
		register(ITEM_CROCOITE);
		register(ITEM_QUERN_STONE);
		register(ITEM_ROTTEN_FLESH_BLOCK);
		register(ITEM_UNDERWORLD_GATE);
		register(ITEM_BASALT);
		register(ITEM_BASALT_STAIRS);
		register(ITEM_BASALT_BRICK);
		register(ITEM_BASALT_BRICK_STAIRS);
		register(ITEM_BASALT_PILLAR);
		register(ITEM_COPPER_BLOCK);
		register(ITEM_COPPER_ORE);
		register(ITEM_TIN_BLOCK);
		register(ITEM_TIN_ORE);
		register(ITEM_BRONZE_BLOCK);
		register(ITEM_LEAD_BLOCK);
		register(ITEM_LEAD_ORE);
		register(ITEM_STEEL_BLOCK);
	}

	public static void register(BaseItemBlock block)
	{
		IDataBlock data = block.getBlock().getData();

		GameRegistry.register(block);
		GameRegistry.register(block.getBlock());
		if (data.getTileEntityClass() != null)
		{
			GameRegistry.registerTileEntity(data.getTileEntityClass(), data.getTileEntityName());
			data.setTileEntityID(TILEENTITY_ID++);
		}

		String oreDictName = block.getBlock().getData().getOreDictName();
		if (oreDictName != null && oreDictName.length() > 0)
		{
			OreDictionary.registerOre(oreDictName, block.getBlock());
		}
	}

	public static void registerRenders()
	{
		registerRender(ITEM_ALLOY_FURNACE);
		registerRender(ITEM_ALLOY_FURNACE_LIT);
		registerRender(ITEM_CROCOITE);
		registerRender(ITEM_QUERN_STONE);
		registerRender(ITEM_ROTTEN_FLESH_BLOCK);
		registerRender(ITEM_COPPER_BLOCK);
		registerRender(ITEM_BASALT);
		registerRender(ITEM_BASALT_STAIRS);
		registerRender(ITEM_BASALT_BRICK);
		registerRender(ITEM_BASALT_BRICK_STAIRS);
		registerRender(ITEM_BASALT_PILLAR);
		registerRender(ITEM_COPPER_ORE);
		registerRender(ITEM_TIN_BLOCK);
		registerRender(ITEM_TIN_ORE);
		registerRender(ITEM_BRONZE_BLOCK);
		registerRender(ITEM_LEAD_BLOCK);
		registerRender(ITEM_LEAD_ORE);
		registerRender(ITEM_STEEL_BLOCK);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUnderworldGate.class, new RendererUnderworldGate());
	}

	public static void registerRender(BaseItemBlock block)
	{
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
