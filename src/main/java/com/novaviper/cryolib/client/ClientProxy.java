package com.novaviper.cryolib.client;

import com.novaviper.cryolib.common.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author NovaViper <nova.gamez15@gmail.com>
 */
public class ClientProxy extends CommonProxy {

    // Client Objects\\
    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
    }

    @Override
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    @Override
    public void onServerStarted(FMLServerStartedEvent event) {
    }

    @Override
    public void onServerStopping(FMLServerStoppingEvent event) {
    }

    @Override
    public void onServerStopped(FMLServerStoppedEvent event) {
    }

    // Client Objects\\
    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
    }

    @Override
    public EntityPlayer getPlayerEntity() {
        return Minecraft.getMinecraft().thePlayer;
    }

    /*@Override
    public void spawnName(World world, Entity entity) {
	FMLClientHandler.instance().getClient().effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.NameHere);
    }*/
}