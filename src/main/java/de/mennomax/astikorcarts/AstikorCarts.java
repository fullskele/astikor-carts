package de.mennomax.astikorcarts;

import de.mennomax.astikorcarts.capabilities.IPull;
import de.mennomax.astikorcarts.capabilities.PullFactory;
import de.mennomax.astikorcarts.capabilities.PullStorage;
import de.mennomax.astikorcarts.config.ModConfig;
import de.mennomax.astikorcarts.handler.GuiHandler;
import de.mennomax.astikorcarts.handler.PacketHandler;
import de.mennomax.astikorcarts.proxy.IProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mod(modid = AstikorCarts.MODID, version = AstikorCarts.VERSION, acceptedMinecraftVersions = "[1.12,1.13)")
public class AstikorCarts
{
    public static final String MODID = "astikorcarts";
    public static final String VERSION = "1.12.2-0.1.2.7";

    @SidedProxy(clientSide = "de.mennomax.astikorcarts.proxy.ClientProxy", serverSide = "de.mennomax.astikorcarts.proxy.ServerProxy")
    public static IProxy proxy;

    @Instance(MODID)
    public static AstikorCarts instance;

    public static Map<IBlockState, IBlockState> hoeBlockConversionMap = new HashMap<>();
    public static Map<IBlockState, IBlockState> shovelBlockConversionMap = new HashMap<>();
    public static Set<IBlockState> foliageBreakSet = new HashSet<>();
    public static Map<IBlockState, Map<Item, IBlockState>> customConversionMap = new HashMap<>();
    public static Set<Item> allowedToolSet = new HashSet<>();


    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        PacketHandler.registerPackets();
        CapabilityManager.INSTANCE.register(IPull.class, new PullStorage(), PullFactory::new);
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        proxy.init();
        ModConfig.generateMappings();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }
}
