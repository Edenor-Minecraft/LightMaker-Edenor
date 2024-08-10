package net.edenor.lightmaker.util;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import net.edenor.lightmaker.LightMaker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Highlighter {

    protected LightMaker plugin;
    private final Map<UUID, ParticleRender> particleMapper = new HashMap<>();
    private ScheduledTask projectorTask;

    public Highlighter(LightMaker plugin) {
        this.plugin = plugin;
        start();
    }

    public void start() {
        if (projectorTask != null) {
            projectorTask.cancel();
            projectorTask = null;
        }
        int updateRate = 1;
        projectorTask = Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, v -> {
            for (ParticleRender visual : particleMapper.values()) {
                visual.update();
            }
        }, updateRate, LightMaker.watchPeriod);
    }

    public void add(Player player) {
        if (player == null || this.contains(player.getUniqueId())) return;
        ParticleRender visual = new ParticleRender(this, player.getUniqueId());
        particleMapper.put(player.getUniqueId(), visual);
    }

    public void remove(Player player) {
        if (player == null) return;
        this.remove(player.getUniqueId());
    }

    public void remove(UUID uuid) {
        particleMapper.remove(uuid);
    }

    public boolean contains(UUID uuid) {
        return particleMapper.containsKey(uuid);
    }
}
