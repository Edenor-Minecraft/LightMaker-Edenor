package net.edenor.lightmaker.util;

import net.edenor.lightmaker.LightMaker;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.UUID;


public class ParticleRender {

    private final UUID PLAYER_UUID;
    private Highlighter highlight;
    private Player player;

    public ParticleRender(Highlighter projector, UUID uuid) {
        this.highlight = projector;
        PLAYER_UUID = uuid;
        player = Bukkit.getPlayer(uuid);
    }

    public static void summonMarker(Player player, Block block){
        Bukkit.getRegionScheduler().run(LightMaker.plugin, player.getLocation(), val -> {
            player.spawnParticle(Particle.BLOCK_MARKER, block.getLocation().toCenterLocation(), 1, block.getBlockData());
        });
    }

    public void update() {
        render();
    }

    private void render() {
        if (this.player == null) {
            highlight.remove(PLAYER_UUID);
            return;
        }
        Location loc = player.getLocation().clone();
        Bukkit.getServer().getRegionScheduler().run(highlight.plugin, loc, val -> {
            World world = loc.getWorld();
            int px = loc.getBlockX(), py = loc.getBlockY(), pz = loc.getBlockZ();

            int radA = 10;
            int radH = 10;

            for (int z = -radA; z <= radA; z++) {

                for (int x = -radA; x <= radA; x++) {

                    for (int y = -radH; y <= radH; y++) {
                        if (Math.sqrt((x * x) + (y * y) + (z * z)) > radA) continue;
                        assert world != null;
                        Location l = new Location(world, px + x, py + y, pz + z);
                        int finalX = x;
                        int finalY = y;
                        int finalZ = z;
                        Bukkit.getServer().getRegionScheduler().run(highlight.plugin, l, v -> {
                            Block block = world.getBlockAt(px + finalX, py + finalY, pz + finalZ);

                            if(block.getType() == Material.LIGHT){
                                summonMarker(player,block);
                            }
                        });
                    }
                }
            }
        });
    }
}
