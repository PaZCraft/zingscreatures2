package com.zing.zinglib.siege;

import com.zing.zinglib.bossbar.ZingBossbarManager;
import com.zing.zinglib.genre.GameGenre;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActiveSiege {
    private final UUID id;
    private final BlockPos center;
    private final int totalWaves;
    private final GameGenre genre;
    
    private int currentWave = 1;
    private final List<UUID> activeEnemyUuids = new ArrayList<>();
    private int ticksSinceLastWave = 0;
    private boolean isFinished = false;

    public ActiveSiege(BlockPos center, int totalWaves, GameGenre genre) {
        this.id = UUID.randomUUID();
        this.center = center;
        this.totalWaves = totalWaves;
        this.genre = genre;
    }

    public void tick(ServerLevel level) {
        if (isFinished) return;

        // Clean up dead/removed entities from the tracking list
        activeEnemyUuids.removeIf(uuid -> {
            Entity entity = level.getEntity(uuid);
            return entity == null || !entity.isAlive();
        });

        // Update the custom bossbar with the current wave progression or health percentage
        updateBossbarDisplay();

        // If all enemies are dead, progress to the next wave or finish
        if (activeEnemyUuids.isEmpty()) {
            ticksSinceLastWave++;
            if (ticksSinceLastWave >= 100) { // 5-second delay between waves
                if (currentWave < totalWaves) {
                    currentWave++;
                    ticksSinceLastWave = 0;
                    spawnWave(level);
                } else {
                    endSiege();
                }
            }
        }
    }

    public void spawnWave(ServerLevel level) {
        // Broadcast message to players nearby
        Component msg = Component.literal("Wave " + currentWave + " has begun!");
        level.players().stream()
             .filter(p -> p.blockPosition().closerThan(center, 64))
             .forEach(p -> p.sendSystemMessage(msg));

        // Spawn a cluster of enemies around the siege center
        // (In a full library implementation, you can read these arrays from a registry or JSON file)
        for (int i = 0; i < (currentWave * 3); i++) {
            // Logic to fetch enemy EntityTypes based on the assigned GameGenre (e.g., RPG or Sci-Fi)
            // Example: Entity enemy = EntityType.ZOMBIE.create(level);
            // Assign a random offset around 'center', level.addFreshEntity(enemy), then activeEnemyUuids.add(enemy.getUUID());
        }
    }

    private void updateBossbarDisplay() {
        Component bossbarTitle = Component.literal("Siege Status - Wave " + currentWave + "/" + totalWaves);
        float remainingPercentage = totalWaves > 0 ? (float) (totalWaves - currentWave + 1) / totalWaves : 0.0f;
        
        // Dynamically feeding into our Custom Bossbars module we wrote earlier!
        ZingBossbarManager.registerBoss(this.id, bossbarTitle, remainingPercentage, this.genre);
    }

    public void endSiege() {
        this.isFinished = true;
        ZingBossbarManager.removeBoss(this.id);
    }

    public boolean isFinished() { return isFinished; }
    public UUID getId() { return id; }
    public BlockPos getCenter() { return center; }
}