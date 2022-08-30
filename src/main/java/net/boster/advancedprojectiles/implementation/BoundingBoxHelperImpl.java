package net.boster.advancedprojectiles.implementation;

import net.boster.advancedprojectiles.BoundingBoxHelper;
import net.boster.advancedprojectiles.CustomProjectile;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class BoundingBoxHelperImpl implements BoundingBoxHelper {

    private boolean old;

    public BoundingBoxHelperImpl() {
        try {
            Entity.class.getMethod("getBoundingBox");
            old = false;
        } catch (Exception e) {
            old = true;
        }
    }

    @Override
    public boolean isIn(@NotNull Entity ent, @NotNull CustomProjectile projectile) {
        return old ? oldCheck(ent, projectile) : newCheck(ent, projectile);
    }

    private boolean oldCheck(@NotNull Entity ent, @NotNull CustomProjectile projectile) {
        return ent.getLocation().distance(projectile.getLocation()) <= projectile.getWidth();
    }

    private boolean newCheck(@NotNull Entity ent, @NotNull CustomProjectile projectile) {
        Vector min = new Vector(
                projectile.getLocation().getX() - projectile.getWidth(),
                projectile.getLocation().getY() - projectile.getWidth(),
                projectile.getLocation().getZ() - projectile.getWidth());
        Vector max = new Vector(
                projectile.getLocation().getX() + projectile.getWidth(),
                projectile.getLocation().getY() + projectile.getWidth(),
                projectile.getLocation().getZ() + projectile.getWidth());

        min.subtract(projectile.getRunner().getSpeed());

        return ent.getBoundingBox().overlaps(min, max);
    }
}
