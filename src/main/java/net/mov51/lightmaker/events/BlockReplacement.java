package net.mov51.lightmaker.events;

import net.mov51.lightmaker.LightMaker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Light;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import static net.mov51.lightmaker.util.Lights.makeLight;

public class BlockReplacement implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        if(e.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE){return;}
        if(e.getBlockReplacedState().getType() == Material.LIGHT){
            Bukkit.getRegionScheduler().run(LightMaker.plugin, e.getBlock().getLocation(), val -> {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), makeLight(((Light) e.getBlockReplacedState().getBlockData()).getLevel()));
            });
        }
    }
}
