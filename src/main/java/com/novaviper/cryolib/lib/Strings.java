package com.novaviper.cryolib.lib;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Defines and Lists all of the mod's language strings
 */
public class Strings {

	public static final String commandSepartor = "string.cryolib.commandSeparator";
	public static final String commandSepartorPart = "string.cryolib.commandSeparatorPart";

	public static String getKeyName(String modName, String keyName) {
		return modName.toLowerCase() + ".key." + keyName;
	}

	public static String getKeyCategory(String modName) {
		return modName.toLowerCase() + ".key.categories";
	}

	public static String getGuiName(String modName, String guiType, String guiName) {
		return modName.toLowerCase() + ".gui." + guiType + "." + guiName;
	}

	public static String getGuiTooltip(String modName, String guiType, String guiName) {
		return getGuiName(modName, guiType, guiName) + ".tooltip";
	}

	public static String getGuiProperty(String modName, String guiType, String propertyName) {
		return modName.toLowerCase() + ".gui." + guiType + ".property." + propertyName;
	}

	public static String getGUIPropertyTooltip(String modName, String guiType, String propertyName) {
		return getGuiProperty(modName, guiType, propertyName) + ".tooltip";
	}

	public static String getGuiTitle(String modName, String guiType) {
		return modName.toLowerCase() + ".gui." + guiType + ".title";
	}

	public static String getCommandUsage(String modName, String commandName) {
		return "commands." + modName.toLowerCase() + ":" + commandName + ".usage";
	}

	public static String getCommandSubPhrase(String modName, String commandName, String subPhrase) {
		return "commands." + modName.toLowerCase() + ":"+commandName+"."+ subPhrase;
	}

	public static String getCommandPhrase(String interactType) {
		return "commands.mod."+ interactType;
	}

	public static String getInteraction(String interactType) {
		return "interaction.mod." + interactType;
	}
}