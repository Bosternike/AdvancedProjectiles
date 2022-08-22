package net.boster.advancedprojectiles;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface BoundingBoxHelper {

    boolean isIn(@NotNull Entity ent, @NotNull CustomProjectile projectile);
}
