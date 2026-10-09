package com.zing.zinglib.genre;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;

public class ZingLibAPI {

    /**
     * Checks if a specific item stack belongs to a requested genre.
     * Example usage by another mod: if (ZingLibAPI.isGenre(playerStack, GameGenre.RPG)) { ... }
     */
    public static boolean isGenre(ItemStack stack, GameGenre genre) {
        if (stack.isEmpty()) return false;
        return getGenre(stack.getItem()) == genre;
    }

    /**
     * Returns the assigned genre of an item. Defaults to VANILLA_PLUS if unassigned.
     */
    public static GameGenre getGenre(Item item) {
        return GenreManager.getGenreForItem(item);
    }
}