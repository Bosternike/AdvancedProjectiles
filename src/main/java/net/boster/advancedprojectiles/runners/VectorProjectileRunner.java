package net.boster.advancedprojectiles.runners;

import net.boster.advancedprojectiles.CustomProjectile;
import net.boster.advancedprojectiles.ProjectileRunner;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class VectorProjectileRunner extends ProjectileRunner<Vector> {

    public Vector vecOffSet;

    public VectorProjectileRunner(@NotNull CustomProjectile projectile, @NotNull Vector vecOffSet) {
        super(projectile);
        this.vecOffSet = vecOffSet;
    }

    public VectorProjectileRunner(@NotNull CustomProjectile projectile, @NotNull Location direction, double speed) {
        this(projectile, direction.getDirection().clone().multiply(speed));
    }

    public VectorProjectileRunner(@NotNull CustomProjectile projectile, @NotNull Location direction) {
        this(projectile, direction, 0.5);
    }

    @Override
    public void move() {
        projectile.getLocation().add(vecOffSet);
    }

    @Override
    public void setDirection(@NotNull Vector vector) {
        vecOffSet = vector;
    }

    @Override
    public Vector getSpeed() {
        return vecOffSet.clone();
    }
}
