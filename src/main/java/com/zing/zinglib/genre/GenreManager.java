package com.zing.zinglib.genre;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@EventBusSubscriber(modid = "zinglib")
public class GenreManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    
    // Maps a specific Item to its assigned GameGenre
    private static final Map<Item, GameGenre> ITEM_GENRES = new HashMap<>();

    public GenreManager() {
        // Tells Minecraft to look inside: data/<any_mod_id>/zinglib_genres/
        super(GSON, "zinglib_genres");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        ITEM_GENRES.clear(); // Reset on reload / world switch

        object.forEach((location, element) -> {
            try {
                JsonObject jsonObject = element.getAsJsonObject();
                
                // Parse the target item ID (e.g., "minecraft:diamond_sword")
                String itemIdStr = jsonObject.get("item").getAsString();
                ResourceLocation itemId = ResourceLocation.parse(itemIdStr);
                Item item = BuiltInRegistries.ITEM.get(itemId);

                // Parse the assigned genre (e.g., "rpg")
                String genreStr = jsonObject.get("genre").getAsString();
                Optional<GameGenre> genreOpt = GameGenre.byId(genreStr);

                if (item != null && genreOpt.isPresent()) {
                    ITEM_GENRES.put(item, genreOpt.get());
                }
            } catch (Exception e) {
                System.err.println("ZiNGLib: Failed to parse genre data file: " + location + " -> " + e.getMessage());
            }
        });
    }

    // Public getter to fetch an item's genre
    public static GameGenre getGenreForItem(Item item) {
        return ITEM_GENRES.getOrDefault(item, GameGenre.VANILLA_PLUS);
    }

    // Registers this custom file-listener to the NeoForge event loop
    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new GenreManager());
    }
}
