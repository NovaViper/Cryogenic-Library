package com.novaviper.cryolib.common.init;

import com.novaviper.cryolib.common.CommonProxy;
import com.novaviper.cryolib.lib.ModReference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Main loading class for the mod
 */
@Mod(modid = ModReference.MOD_ID, version = ModReference.MOD_VERSION, name = ModReference.MOD_NAME, updateJSON = ModReference.UPDATE_JSON, acceptedMinecraftVersions = ModReference.MC_VERSION)
public class CryogenicLibrary {

    @Instance(value = ModReference.MOD_ID)
    public static CryogenicLibrary INSTANCE;

    @SidedProxy(clientSide = ModReference.CLIENT_PROXY, serverSide = ModReference.SERVER_PROXY)
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.onPreInit(event);
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.onInit(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.onPostInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.onServerStarting(event);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.onServerStarted(event);
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.onServerStopping(event);
    }

    @EventHandler
    public void serverStop(FMLServerStoppedEvent event) {
        proxy.onServerStopped(event);
    }
}