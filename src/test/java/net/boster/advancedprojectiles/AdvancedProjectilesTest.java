package net.boster.advancedprojectiles;

import com.google.common.collect.Lists;
import net.boster.advancedprojectiles.events.CustomProjectileStrikeBlockEvent;
import net.boster.advancedprojectiles.particles.SimpleParticleCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvancedProjectilesTest extends JavaPlugin implements Listener {

    public void onEnable() {
        /*Don't forget to declare the plugin instance*/
        AdvancedProjectiles.setProvider(this);

        getServer().getPluginManager().registerEvents(this, this);
    }

    public static void runProjectile(@NotNull Player p, @NotNull Particle particle, double width, double damage, double maxDist, double speed, @Nullable List<Material> fatalBlocks) {
        CustomProjectile proj = new CustomProjectile(p.getEyeLocation().clone(), new SimpleParticleCreator(particle), new PlayerSender(p));

        proj.getUntouchableEntities().add(p);

        if(width > -1) {
            proj.setWidth(width);
        }
        proj.setDamage(damage);
        proj.setMaxDistance(maxDist);

        proj.getActions().setDeathAction((projectile) -> {
            proj.cancelTask();
            proj.getLocation().getWorld().playSound(proj.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
        });

        if(fatalBlocks != null) {
            proj.setFatalBlocks(fatalBlocks);
        }

        proj.run(AdvancedProjectiles.getPlayerDirection(p).clone(), speed);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
            runProjectile(p, Particle.FLAME, 0.4, 10, 300, 1, Lists.newArrayList(Material.WATER));
        } else if(e.getAction() == Action.LEFT_CLICK_AIR) {
            runProjectile(p, Particle.SOUL_FIRE_FLAME, 0.4, 12, 500, 1.2, null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onStrike(CustomProjectileStrikeBlockEvent e) {
        Location loc = e.getProjectile().getLocation();
        loc.getWorld().createExplosion(loc, 1, true, true);
    }
}
