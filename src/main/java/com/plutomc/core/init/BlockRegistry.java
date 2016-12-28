package com.plutomc.core.init;

import com.plutomc.core.Core;
import com.plutomc.core.common.blocks.*;
import com.plutomc.core.common.data.IDataBlock;
import com.plutomc.core.common.items.BaseItemBlock;
import com.plutomc.core.common.tileentities.TileEntityAlloyFurnace;
import com.plutomc.core.common.tileentities.TileEntityQuernStone;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
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
		BASALT("basalt", null, CreativeTabs.MATERIALS, Material.ROCK, null),
		CROCOITE("crocoite", null, CreativeTabs.MATERIALS, Material.GLASS, null),
		QUERN_STONE("quern_stone", null, CreativeTabs.DECORATIONS, Material.ROCK, TileEntityQuernStone.class),

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

	public static final BaseItemBlock ALLOY_FURNACE = new BaseItemBlock(new BlockAlloyFurnace(false));
	public static final BaseItemBlock ALLOY_FURNACE_LIT = new BaseItemBlock(new BlockAlloyFurnace(true));
	public static final BaseItemBlock BASALT = new BaseItemBlock(new BlockBasalt());
	public static final BaseItemBlock CROCOITE = new BaseItemBlock(new BlockCrocoite());
	public static final BaseItemBlock QUERN_STONE = new BaseItemBlock(new BlockQuernStone());
	public static final BaseItemBlock COPPER_BLOCK = new BaseItemBlock(new BlockMetal(Data.COPPER_BLOCK));
	public static final BaseItemBlock COPPER_ORE = new BaseItemBlock(new BlockOre(Data.COPPER_ORE));
	public static final BaseItemBlock TIN_BLOCK = new BaseItemBlock(new BlockMetal(Data.TIN_BLOCK));
	public static final BaseItemBlock TIN_ORE = new BaseItemBlock(new BlockOre(Data.TIN_ORE));
	public static final BaseItemBlock BRONZE_BLOCK = new BaseItemBlock(new BlockMetal(Data.BRONZE_BLOCK));
	public static final BaseItemBlock LEAD_BLOCK = new BaseItemBlock(new BlockMetal(Data.LEAD_BLOCK));
	public static final BaseItemBlock LEAD_ORE = new BaseItemBlock(new BlockLeadOre());
	public static final BaseItemBlock STEEL_BLOCK = new BaseItemBlock(new BlockMetal(Data.STEEL_BLOCK));

	private static int TILEENTITY_ID = 1;

	public static void preInit()
	{
		register(ALLOY_FURNACE);
		register(ALLOY_FURNACE_LIT);
		register(BASALT);
		register(CROCOITE);
		register(QUERN_STONE);
		register(COPPER_BLOCK);
		register(COPPER_ORE);
		register(TIN_BLOCK);
		register(TIN_ORE);
		register(BRONZE_BLOCK);
		register(LEAD_BLOCK);
		register(LEAD_ORE);
		register(STEEL_BLOCK);
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
		registerRender(ALLOY_FURNACE);
		registerRender(ALLOY_FURNACE_LIT);
		registerRender(BASALT);
		registerRender(CROCOITE);
		registerRender(QUERN_STONE);
		registerRender(COPPER_BLOCK);
		registerRender(COPPER_ORE);
		registerRender(TIN_BLOCK);
		registerRender(TIN_ORE);
		registerRender(BRONZE_BLOCK);
		registerRender(LEAD_BLOCK);
		registerRender(LEAD_ORE);
		registerRender(STEEL_BLOCK);
	}

	public static void registerRender(BaseItemBlock block)
	{
		ModelLoader.setCustomModelResourceLocation(block, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
