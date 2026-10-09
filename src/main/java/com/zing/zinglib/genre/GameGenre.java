package com.zing.zinglib.genre;

import java.util.Arrays;
import java.util.Optional;

public enum GameGenre {
    RPG("rpg"),
    SCI_FI("sci_fi"),
    STEAMPUNK("steampunk"),
    HORROR("horror"),
    VANILLA_PLUS("vanilla_plus");

    private final String id;

    GameGenre(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static Optional<GameGenre> byId(String id) {
        return Arrays.stream(values())
                .filter(genre -> genre.id.equalsIgnoreCase(id))
                .findFirst();
    }
}
