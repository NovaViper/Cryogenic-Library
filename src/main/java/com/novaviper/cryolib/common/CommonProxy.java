package com.novaviper.cryolib.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author NovaViper <nova.gamez15@gmail.com>
 */
public class CommonProxy {

    public void onPreInit(FMLPreInitializationEvent event) {
    }

    public void onInit(FMLInitializationEvent event) {
    }

    public void onPostInit(FMLPostInitializationEvent event) {
    }

    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public void onServerStarted(FMLServerStartedEvent event) {
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
    }

    public void onServerStopped(FMLServerStoppedEvent event) {
    }

    // Client Objects\\
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity;
    }

    public EntityPlayer getPlayerEntity() {
        return null;
    }

    //Particles
    //public void spawnName(World world, Entity entity) {}
}
