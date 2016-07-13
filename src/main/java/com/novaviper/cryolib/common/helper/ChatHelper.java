package com.novaviper.cryolib.common.helper;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Helper for making chat messages
 */
public class ChatHelper {

	public static TextComponentTranslation getChatComponentTranslation(String message, Object... format) {
		return new TextComponentTranslation(message, format);
	}

	public static TextComponentString getChatComponent(String message) {
		return new TextComponentString(message);
	}
}