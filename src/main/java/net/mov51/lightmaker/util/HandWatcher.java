package net.mov51.lightmaker.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static net.mov51.lightmaker.LightMaker.projector;
import static net.mov51.lightmaker.LightMaker.watchPeriod;
import static net.mov51.lightmaker.util.Lights.isLight;


public class HandWatcher {



    public static void startWatching(Plugin plugin){
        Bukkit.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, val -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Bukkit.getServer().getRegionScheduler().run(plugin, p.getLocation(), v -> {
                    if(isLight(p.getInventory().getItemInMainHand()) || isLight(p.getInventory().getItemInOffHand())){
                        projector.add(p);
                    }else{
                        projector.remove(p);
                    }
                });
            }
        }, 1L, watchPeriod);
    }
}

