package net.boster.advancedprojectiles;

import lombok.Getter;
import lombok.Setter;
import net.boster.advancedprojectiles.implementation.BoundingBoxHelperImpl;
import net.boster.advancedprojectiles.implementation.BukkitTaskProvider;
import net.boster.advancedprojectiles.provider.ProjectileTaskProvider;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class AdvancedProjectiles {

    @Getter @Setter private static Plugin provider;
    @Getter @Setter @NotNull private static ProjectileTaskProvider taskProvider = new BukkitTaskProvider();
    @Getter @Setter @NotNull private static BoundingBoxHelper boundingBoxHelper = new BoundingBoxHelperImpl();

    public static @NotNull Location getPlayerDirection(@NotNull Player p) {
        return getPlayerDirection(p, 3);
    }

    public static @NotNull Location getPlayerDirection(@NotNull Player p, double i) {
        return p.getEyeLocation().toVector().add(p.getLocation().getDirection().normalize().multiply(i)).toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
    }
}
