package net.edenor.lightmaker.util;

import net.edenor.lightmaker.LightMaker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HandWatcher {



    public static void startWatching(Plugin plugin){
        Bukkit.getServer().getGlobalRegionScheduler().runAtFixedRate(plugin, val -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Bukkit.getServer().getRegionScheduler().run(plugin, p.getLocation(), v -> {
                    if(Lights.isLight(p.getInventory().getItemInMainHand()) || Lights.isLight(p.getInventory().getItemInOffHand())){
                        LightMaker.projector.add(p);
                    }else{
                        LightMaker.projector.remove(p);
                    }
                });
            }
        }, 1L, LightMaker.watchPeriod);
    }
}

