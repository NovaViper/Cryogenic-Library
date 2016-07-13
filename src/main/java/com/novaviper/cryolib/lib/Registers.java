package com.novaviper.cryolib.lib;

import com.novaviper.cryolib.common.core.Logger;

import java.util.*;
import java.util.function.Function;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraft.world.storage.loot.properties.EntityPropertyManager;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author NovaViper <nova.gamez15@gmail.com>
 * @date 7/10/2016
 * @purpose Declares all registers
 */
public class Registers {

	public static final Set<Item> items = new HashSet<>();
	public static final Set<Block> blocks = new HashSet<>();
	static Logger log = Logger.createLogger(ModReference.MOD_NAME);

	/**
	 * Registers am {@link IWorldGenerator} - something that inserts new block types into the world
	 *
	 * @param generator           the generator
	 * @param modGenerationWeight a weight to assign to this generator. Heavy weights tend to sink to the bottom of
	 *                            list of world generators (i.e. they run later)
	 */
	public static void addWorldGenerator(IWorldGenerator generator, int modGenerationWeight) {
		GameRegistry.registerWorldGenerator(generator, modGenerationWeight);
	}

	/**
	 * Registers a fuel handler - something that adds the burning value for an item
	 *
	 * @param handler the generator
	 */
	public static void addFuelHandler(IFuelHandler handler) {
		GameRegistry.registerFuelHandler(handler);
	}

	/**
	 * Registers an {@link ICommand} command.
	 *
	 * @param event   The server starting event
	 * @param command The command to be registered
	 */
	public static void addCommand(FMLServerStartingEvent event, ICommand command) {
		Object name = command.getClass().getSimpleName();
		List<List<Object>> arrayList = new ArrayList<List<Object>>();
		List array = Arrays.asList(new Object[]{command});
		if (arrayList.contains(array)) {
			log.warn("Command Manager [" + name + "] is already registered!");
		} else {
			arrayList.add(array);
			log.info("Command Manager [" + name + "] has been registered");
			event.registerServerCommand(command);
		}
	}

	/**
	 * Registers an event
	 *
	 * @param target The event to be registered to the {@link MinecraftForge} EVENT_BUS
	 */
	public static void addEvent(Object target) {
		Object name = target.getClass().getSimpleName();
		List<List<Object>> arrayList = new ArrayList<List<Object>>();
		List array = Arrays.asList(new Object[]{target});
		if (arrayList.contains(array)) {
			log.warn("Event [" + name + "] is already registered!");
		} else {
			arrayList.add(array);
			log.info("Event [" + name + "] has been registered");
			MinecraftForge.EVENT_BUS.register(target);
		}
	}

	/**
	 * Registers a dimension and its provider
	 *
	 * @param type the dimension type
	 */
	public static void addDimensionAndProviderType(DimensionType type) {
		DimensionManager.registerDimension(DimensionManager.getNextFreeDimId(), type);
		DimensionManager.createProviderFor(DimensionManager.getNextFreeDimId());
	}

	/**
	 * Registers a {@link Block} with the default {@link ItemBlock} class.
	 *
	 * @param block The {@link Block} instance
	 * @param <T>   The {@link Block} type
	 * @return The {@link Block} instance
	 */
	public static <T extends Block> T addBlock(T block) {
		return addBlock(block, ItemBlock::new);
	}

	/**
	 * Registers a {@link Block} with a custom {@link ItemBlock} class.
	 *
	 * @param <BLOCK>     The {@link Block} type
	 * @param block       The {@link Block} instance
	 * @param itemFactory A function that creates the {@link ItemBlock} instance, or
	 *                    null if no {@link ItemBlock} should be created
	 * @return The {@link Block} instance
	 */
	public static <BLOCK extends Block> BLOCK addBlock(BLOCK block, @Nullable Function<BLOCK, ItemBlock> itemFactory) {
		Object name = block.getRegistryName();
		if (blocks.contains(block)) {
			log.warn("Block [" + name + "] is already registered!");
		} else {
			blocks.add(block);
			log.info("Block [" + name + "] has been registered");
			GameRegistry.register(block);
			if (itemFactory != null) {
				final ItemBlock itemBlock = itemFactory.apply(block);
				GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
			}
		}
		return block;
	}

	/**
	 * Registers an {@link Item}
	 *
	 * @param item The {@link Item} instance
	 * @param <T>  The {@link Item} type
	 * @return The {@link Item} instance
	 */
	public static <T extends Item> T addItem(T item) {
		Object name = item.getRegistryName();
		if (items.contains(item)) {
			log.warn("Item [" + name + "] is already registered!");
		} else {
			items.add(item);
			log.info("Item [" + name + "] has been registered");
			GameRegistry.register(item);
		}
		return item;
	}

	/**
	 * Registers a {@link Biome}
	 *
	 * @param biome     The {@link Biome} instance
	 * @param biomeName The name of the {@link Biome}
	 * @param biomeType The {@link BiomeManager.BiomeType}
	 * @param weight    The probaility of spawning the {@link Biome}
	 * @param types     The different {@link BiomeManager.BiomeType}s
	 * @param <T>       The {@link Biome} instance
	 * @return The {@link Biome} instance
	 */
	private static <T extends Biome> T addBiome(T biome, ResourceLocation biomeName, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
		GameRegistry.register(biome.setRegistryName(biomeName));
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));

		return biome;
	}

	/**
	 * Registers the variants for {@link Item}
	 *
	 * @param item  The {@link Item}
	 * @param names The variant resource locations
	 * @implNote Do not use for items that have overrides!!
	 */
	public static void addItemVariants(Item item, ResourceLocation... names) {
		ModelBakery.registerItemVariants(item, names);
	}

	// Entity Registers\\

	/**
	 * Registers the mod {@link Entity} and the egg for it
	 *
	 * @param entityClass   The {@link Entity} class
	 * @param entityName    A unique name for the {@link Entity}
	 * @param id            A mod specific ID for the {@link Entity}
	 * @param eggColor      Primary egg color
	 * @param eggSpotsColor Secondary egg color
	 * @param modInstance   The mod instance
	 */
	public static void addEntityWithEgg(Class entityClass, String entityName, int id, int eggColor, int eggSpotsColor, Object modInstance) {

		EntityRegistry.registerModEntity(entityClass, entityName, id, modInstance, 80, 3, false, eggColor, eggSpotsColor); //Last 3 Parameters monitors movement See EntityTracker.addEntityToTracker()
	}

	/**
	 * Registers a spawn entry for the supplied {@link Entity} in the supplied {@link Biome} list
	 *
	 * @param entityClass    {@link Entity} class added
	 * @param weightedProb   Probability
	 * @param min            Min spawn count
	 * @param max            Max spawn count
	 * @param typeOfCreature Type of spawn
	 * @param biomes         List of biomes
	 */
	public static void addEntitySpawn(Class entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, Biome... biomes) {
		EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
	}

	public static <T extends EntityProperty> void addProperty(EntityProperty.Serializer<? extends T> propertySerial) {
		Object name = propertySerial.getName();
		List<List<Object>> arrayList = new ArrayList<List<Object>>();
		List array = Arrays.asList(new Object[]{propertySerial});
		if (arrayList.contains(array)) {
			log.warn("Entity Property [" + name + "] is already registered!");
		} else {
			arrayList.add(array);
			log.info("Entity Property [" + name + "] has been registered");
			EntityPropertyManager.registerProperty(propertySerial);
		}
	}

	/**
	 * Registers a {@link TileEntity}
	 *
	 * @param entityTileClass The {@link TileEntity} class
	 * @param saveName        The {@link TileEntity}'s unlocalized name
	 */
	public static void addTileEntity(Class entityTileClass, String saveName) {
		GameRegistry.registerTileEntity(entityTileClass, saveName);
	}

	/**
	 * Registers a {projectile entity
	 *
	 * @param entityClass The projectile entity class
	 * @param saveName    The projectile entity's unlocalized name
	 * @param id          The projectile entity's ID
	 * @param modInstance The mod instance
	 */
	public static void addProjectileEntity(Class entityClass, String saveName, int id, Object modInstance) {
		EntityRegistry.registerModEntity(entityClass, saveName, id, modInstance, 128, 1, true);
	}

	/**
	 * Registers a {@link LootTable} with the specified ID.
	 *
	 * @param id The ID of the LootTable without the modID
	 * @return The ID of the LootTable
	 */
	public static ResourceLocation registerLootTable(String modid, String id, String location) {
		return LootTableList.register(new ResourceLocation(modid, location + "/" + id));
	}

	// Recipe Registers\\

	/**
	 * Registers a smelting recipe
	 *
	 * @param input  The object inputted
	 * @param output The resulting object
	 * @param xp     The XP gained
	 */
	public static void addSmelting(ItemStack input, ItemStack output, float xp) {
		GameRegistry.addSmelting(input, output, xp);
	}

	/**
	 * Registers a Shaped recipe - a recipe that has a specific arrangement in order to produce the result
	 *
	 * @param output The resulting object
	 * @param params The inputted objects
	 */
	public static void addRecipe(ItemStack output, Object... params) {
		GameRegistry.addRecipe(output, params);
	}

	/**
	 * Registers a Shapeless recipe - a recipe that does not have a specific arrangement in order to produce the result
	 *
	 * @param output The resulting object
	 * @param params The inputted objects
	 */
	public static void addShapelessRecipe(ItemStack output, Object... params) {
		GameRegistry.addShapelessRecipe(output, params);
	}

	/**
	 * Registers a {@link SoundEvent}.
	 *
	 * @param soundName The SoundEvent's name without the modID
	 * @return The {@link SoundEvent}
	 */
	public static SoundEvent registerSound(String modid, String soundName) {
		final ResourceLocation soundID = new ResourceLocation(modid, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
	}

	// Client Registers\\ NAV
	/**
	 * Registers the key bindings
	 *
	 * @param key The key binding handler
	 */
	@SideOnly(Side.CLIENT)
	public static void addKeyBinding(KeyBinding key) {
		ClientRegistry.registerKeyBinding(key);
	}

	/**
	 * Registers the {@link TileEntity} renderer
	 *
	 * @param tileentity The {@link TileEntity} class
	 * @param render     The renderer for the {@link TileEntity}
	 */
	@SideOnly(Side.CLIENT)
	public static void bindTileEntitySpecialRenderer(Class<? extends TileEntity> tileentity, TileEntitySpecialRenderer render) {
		ClientRegistry.bindTileEntitySpecialRenderer(tileentity, render);
	}

	/**
	 * Registers the {@link Entity} renderer
	 *
	 * @param entityClass   The {@link Entity} class
	 * @param renderFactory The renderer for the {@link Entity}
	 */
	@SideOnly(Side.CLIENT)
	public static void addEntityRender(Class entityClass, IRenderFactory renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

	/**
	 * Registers the {@link Item} renderer
	 *
	 * @param modid      The mod's ID
	 * @param item       The {@link Item} instance
	 * @param metadata   The {@link Item}'s metadata
	 * @param itemString The name of the {@link Item}
	 */
	@SideOnly(Side.CLIENT)
	public static void addItemRender(String modid, Item item, int metadata, String itemString) {

		ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(modid + ":" + itemString, "inventory"));
	}

	/**
	 * Registers the {@link Block} renderer
	 *
	 * @param modid       The mod's ID
	 * @param block       The {@link Block} instance
	 * @param metadata    The {@link Block}'s metadata
	 * @param blockString The name of the {@link Block}
	 */
	@SideOnly(Side.CLIENT)
	public static void addBlockRender(String modid, Block block, int metadata, String blockString, String location) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(modid + ":" + blockString, location));
	}

	/**
	 * Tells the game what property to ignore on a {@link Block}
	 *
	 * @param block    The {@link Block} instance
	 * @param property The property to ignore
	 */
	@SideOnly(Side.CLIENT)
	public void addStateMapperToIgnore(Block block, IProperty property) {
		ModelLoader.setCustomStateMapper(block, (new StateMap.Builder()).ignore(new IProperty[]{property}).build());
	}

	/**
	 * Registers the GUI handler
	 *
	 * @param modid   The mod's ID
	 * @param handler The
	 */
	@SideOnly(Side.CLIENT)
	public static void addGuiHandler(Object modid, IGuiHandler handler) {
		NetworkRegistry.INSTANCE.registerGuiHandler(modid, handler);
	}
}
