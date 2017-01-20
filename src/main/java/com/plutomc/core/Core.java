package com.plutomc.core;

import com.plutomc.core.common.ICommonProxy;
import com.plutomc.core.init.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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
@Mod(modid = Core.MOD_ID, name = Core.NAME, version = Core.VERSION)
public class Core
{
	@Mod.Instance(Core.MOD_ID)
	private static Object instance;

	public static final String MOD_ID = "plutomc_core";
	public static final String NAME = "PlutoMC Core";
	public static final String VERSION = "0.1.0";

	public static final String CLIENT_PROXY_CLASS = "com.plutomc.core.client.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.plutomc.core.server.ServerProxy";

	@SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
	public static ICommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		BlockRegistry.preInit();
		ItemRegistry.preInit();
		ArmorRegistry.preInit();
		ToolRegistry.preInit();
		MobRegistry.preInit();

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		WorldRegistry.init();
		BiomeRegistry.init();
		DimensionRegistry.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}

	public static Object instance()
	{
		return instance;
	}
}
