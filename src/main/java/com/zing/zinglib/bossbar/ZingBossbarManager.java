package com.zing.zinglib.bossbar;

import com.zing.zinglib.genre.GameGenre;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZingBossbarManager {
    private static final Map<UUID, ActiveBossInfo> ACTIVE_BOSSES = new HashMap<>();
    private static final Map<GameGenre, BossbarStyle> GENRE_STYLES = new HashMap<>();

    // Standard Default RPG Texture
    private static final BossbarStyle DEFAULT_RPG_STYLE = new BossbarStyle(
        ResourceLocation.fromNamespaceAndPath("zinglib", "textures/gui/bossbars/rpg_default.png"),
        256, 64, 32, 20, 192, 12
    );

    static {
        GENRE_STYLES.put(GameGenre.RPG, DEFAULT_RPG_STYLE);
        // You can register custom styles for SCI_FI, HORROR, etc. here
    }

    public static void registerBoss(UUID bossUuid, Component name, float healthPercent, GameGenre genre) {
        BossbarStyle style = GENRE_STYLES.getOrDefault(genre, DEFAULT_RPG_STYLE);
        ACTIVE_BOSSES.put(bossUuid, new ActiveBossInfo(name, healthPercent, style));
    }

    public static void updateHealth(UUID bossUuid, float healthPercent) {
        if (ACTIVE_BOSSES.containsKey(bossUuid)) {
            ACTIVE_BOSSES.get(bossUuid).setHealthPercent(healthPercent);
        }
    }

    public static void removeBoss(UUID bossUuid) {
        ACTIVE_BOSSES.remove(bossUuid);
    }

    public static Map<UUID, ActiveBossInfo> getActiveBosses() {
        return ACTIVE_BOSSES;
    }

    public static class ActiveBossInfo {
        private final Component name;
        private float healthPercent;
        private final BossbarStyle style;

        public ActiveBossInfo(Component name, float healthPercent, BossbarStyle style) {
            this.name = name;
            this.healthPercent = healthPercent;
            this.style = style;
        }

        public Component getName() { return name; }
        public float getHealthPercent() { return healthPercent; }
        public void setHealthPercent(float percent) { this.healthPercent = percent; }
        public BossbarStyle getStyle() { return style; }
    }
}