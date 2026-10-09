package com.zing.zinglib.siege;

import com.zing.zinglib.genre.GameGenre;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "zinglib")
public class ZingSiegeEngine {
    private static final List<ActiveSiege> ACTIVE_SIEGES = new ArrayList<>();

    /**
     * Public API method allowing external mods to kickstart a raid event programmatically
     */
    public static void startSiege(ServerLevel level, BlockPos pos, int waves, GameGenre genre) {
        ActiveSiege newSiege = new ActiveSiege(pos, waves, genre);
        newSiege.spawnWave(level);
        ACTIVE_SIEGES.add(newSiege);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        // We only want to execute our ticking framework once per server cycle
        if (ACTIVE_SIEGES.isEmpty()) return;

        // Run this logic on the overworld instance to safely retrieve the global server reference
        ServerLevel level = event.getServer().overworld();
        if (level == null) return;

        // Tick every active siege registry item
        for (int i = ACTIVE_SIEGES.size() - 1; i >= 0; i--) {
            ActiveSiege siege = ACTIVE_SIEGES.get(i);
            
            // Get the specific level/dimension where the siege center is located
            ServerLevel targetDim = event.getServer().getLevel(level.dimension());
            if (targetDim != null) {
                siege.tick(targetDim);
            }

            // Purge from active system queue once complete
            if (siege.isFinished()) {
                ACTIVE_SIEGES.remove(i);
            }
        }
    }
}