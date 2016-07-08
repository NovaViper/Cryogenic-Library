package com.novaviper.cryolib.lib;

import com.novaviper.cryolib.core.Logger;
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
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author NovaViper <nova.gamez15@gmail.com>
 */
public class Registers {

    public static final Set<Item> ITEMS = new HashSet<>();
    public static final Set<Block> BLOCKS = new HashSet<>();
    static Logger log = Logger.createLogger(ModReference.MOD_NAME);

    public static void addWorldGenerator(IWorldGenerator generator, int modGenerationWeight) {
        GameRegistry.registerWorldGenerator(generator, modGenerationWeight);
    }

    public static void addFuelHandler(IFuelHandler handler) {
        GameRegistry.registerFuelHandler(handler);
    }

    /**
     * Register the commands.
     *
     * @param event The server starting event
     * @param command The command to be registered
     */
    public static void addCommandManager(FMLServerStartingEvent event, ICommand command) {
        Object name = command.getClass().getSimpleName();
        List<List<Object>> arrayList = new ArrayList<>();
        List array = Arrays.asList(new Object[]{command});
        if (arrayList.contains(array)) {
            log.warn("Command Manager [" + name + "] is already registered!");
        } else {
            arrayList.add(array);
            log.info("Command Manager [" + name + "] has been registered");
            event.registerServerCommand(command);
        }
    }

    public static void addEvent(Object target) {
        Object name = target.getClass().getSimpleName();
        List<List<Object>> arrayList = new ArrayList<>();
        List array = Arrays.asList(new Object[]{target});
        if (arrayList.contains(array)) {
            log.warn("Event [" + name + "] is already registered!");
        } else {
            arrayList.add(array);
            log.info("Event [" + name + "] has been registered");
            MinecraftForge.EVENT_BUS.register(target);
        }
    }

    public static void addDimensionProviderType(int id, DimensionType type) {
        DimensionManager.registerDimension(DimensionManager.getNextFreeDimId(), type);
        DimensionManager.createProviderFor(DimensionManager.getNextFreeDimId());
    }

    /**
     * Register a Block with the default ItemBlock class.
     *
     * @param block The Block instance
     * @param <T> The Block type
     * @return The Block instance
     */
    public static <T extends Block> T addBlock(T block) {
        return addBlock(block, ItemBlock::new);
    }

    /**
     * Register a Block with a custom ItemBlock class.
     *
     * @param <BLOCK> The Block type
     * @param block The Block instance
     * @param itemFactory A function that creates the ItemBlock instance, or
     * null if no ItemBlock should be created
     * @return The Block instance
     */
    public static <BLOCK extends Block> BLOCK addBlock(BLOCK block, @Nullable Function<BLOCK, ItemBlock> itemFactory) {
        Object name = block.getRegistryName();
        if (BLOCKS.contains(block)) {
            log.warn("Block [" + name + "] is already registered!");
        } else {
            BLOCKS.add(block);
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
     * Register an Item
     *
     * @param item The Item instance
     * @param <T> The Item type
     * @return The Item instance
     */
    public static <T extends Item> T addItem(T item) {
        Object name = item.getRegistryName();
        if (ITEMS.contains(item)) {
            log.warn("Item [" + name + "] is already registered!");
        } else {
            ITEMS.add(item);
            log.info("Item [" + name + "] has been registered");
            GameRegistry.register(item);
        }
        return item;
    }

    private static <T extends Biome> T addBiome(T biome, ResourceLocation biomeName, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
        GameRegistry.register(biome.setRegistryName(biomeName));
        BiomeDictionary.registerBiomeType(biome, types);
        BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));

        return biome;
    }

    /**
     * Register variants for Items
     *
     * @param item The Item
     * @param names The variant resource locations
     * @implNote Do not use for items that have overrides!!
     */
    public static void addItemVariants(Item item, ResourceLocation... names) {
        ModelBakery.registerItemVariants(item, names);
    }

    // Entity Registers\\
    public static void addEntityWithEgg(Class entityClass, String entityName, int id, int eggColor, int eggSpotsColor, Object modInstance) {

        EntityRegistry.registerModEntity(entityClass, entityName, id, modInstance, 80, 3, false, eggColor, eggSpotsColor); //Last 3 Parameters monitors movement See EntityTracker.addEntityToTracker()
    }

    public static void addEntitySpawn(Class entityClass, int weightedProb, int min, int max, EnumCreatureType typeOfCreature, Biome... biomes) {
        EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature, biomes);
    }

    public static <T extends EntityProperty> void addProperty(EntityProperty.Serializer<? extends T> propertySerial) {
        Object name = propertySerial.getName();
        List<List<Object>> arrayList = new ArrayList<>();
        List array = Arrays.asList(new Object[]{propertySerial});
        if (arrayList.contains(array)) {
            log.warn("Entity Property [" + name + "] is already registered!");
        } else {
            arrayList.add(array);
            log.info("Entity Property [" + name + "] has been registered");
            EntityPropertyManager.registerProperty(propertySerial);
        }
    }

    public static void addTileEntity(Class entityTileClass, String saveName) {
        GameRegistry.registerTileEntity(entityTileClass, saveName);
    }

    public static void addProjectileEntity(Class entityClass, String saveName, int id, Object mod) {
        EntityRegistry.registerModEntity(entityClass, saveName, id, mod, 128, 1, true);
    }

    /**
     * Register a {@link LootTable} with the specified ID.
     *
     * @param modid The mod ID
     * @param id The ID of the LootTable without the modID
     * @param location The location of the loot table
     * @return The ID of the LootTable
     */
    public static ResourceLocation registerLootTable(String modid, String id, String location) {
        return LootTableList.register(new ResourceLocation(modid, location + "/" + id));
    }

    // Recipe Registers\\
    public static void addSmelting(ItemStack input, ItemStack output, float xp) {
        GameRegistry.addSmelting(input, output, xp);
    }

    public static void addShapedRecipe(ItemStack output, Object... params) {
        GameRegistry.addShapedRecipe(output, params);
    }

    public static void addShapelessRecipe(ItemStack output, Object... params) {
        GameRegistry.addShapelessRecipe(output, params);
    }

    /**
     * Register a {@link SoundEvent}.
     *
     * @param modid The mod ID
     * @param soundName The SoundEvent's name without the modID
     * @return The SoundEvent
     */
    public static SoundEvent registerSound(String modid, String soundName) {
        final ResourceLocation soundID = new ResourceLocation(modid, soundName);
        return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
    }

    public static void addRecipe(ItemStack output, Object... params) {
        GameRegistry.addRecipe(output, params);
    }

    // Client Registers\\ NAV
    @SideOnly(Side.CLIENT)
    public static void addKeyBinding(KeyBinding key) {
        ClientRegistry.registerKeyBinding(key);
    }

    @SideOnly(Side.CLIENT)
    public static void bindTileEntitySpecialRenderer(Class<? extends TileEntity> tileentity, TileEntitySpecialRenderer render) {
        ClientRegistry.bindTileEntitySpecialRenderer(tileentity, render);
    }

    @SideOnly(Side.CLIENT)
    public static void addEntityRender(Class entityClass, IRenderFactory renderFactory) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }

    @SideOnly(Side.CLIENT)
    public static void addItemRender(String modid, Item item, int metadata, String itemString) {

        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(modid + ":" + itemString, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void addBlockRender(String modid, Block block, int metadata, String blockString, String location) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(modid + ":" + blockString, location));
    }

    @SideOnly(Side.CLIENT)
    public void addStateMapperToIgnore(Block block, IProperty property) {
        ModelLoader.setCustomStateMapper(block, (new StateMap.Builder()).ignore(new IProperty[]{property}).build());
    }

    @SideOnly(Side.CLIENT)
    public static void addGuiHandler(Object mod, IGuiHandler handler) {
        NetworkRegistry.INSTANCE.registerGuiHandler(mod, handler);
    }
}
