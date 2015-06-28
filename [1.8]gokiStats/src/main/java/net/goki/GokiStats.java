package net.goki;

import net.goki.handlers.packet.PacketPipeline;
import net.goki.handlers.packet.PacketStatAlter;
import net.goki.handlers.packet.PacketStatSync;
import net.goki.handlers.packet.PacketSyncStatConfig;
import net.goki.handlers.packet.PacketSyncXP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;

@Mod(modid = "gokiStats", name = "gokiStats", version = "1.0.5")
public class GokiStats
{
	public static final PacketPipeline packetPipeline = new PacketPipeline();

	@Mod.Instance("gokiStats")
	public static GokiStats instance;

	@SidedProxy(clientSide = "net.goki.client.ClientProxy", serverSide = "net.goki.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;

		proxy.iniConfig(event);

		proxy.registerKeybinding();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		packetPipeline.initialise();
		packetPipeline.registerPacket(PacketStatSync.class);
		packetPipeline.registerPacket(PacketStatAlter.class);
		packetPipeline.registerPacket(PacketSyncXP.class);
		packetPipeline.registerPacket(PacketSyncStatConfig.class);

		proxy.registerHandlers();

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		packetPipeline.postInitialise();

		proxy.saveConfig();
	}

	@Mod.EventHandler
	public void serverStart(FMLServerStartingEvent event)
	{
		MinecraftServer server = MinecraftServer.getServer();
		ICommandManager command = server.getCommandManager();
		ServerCommandManager serverCommand = (ServerCommandManager) command;
		serverCommand.registerCommand(new StatsCommand());
		// TODO notice it's a reversion
	}
}