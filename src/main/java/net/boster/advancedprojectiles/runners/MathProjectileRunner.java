package net.boster.advancedprojectiles.runners;

import net.boster.advancedprojectiles.CustomProjectile;
import net.boster.advancedprojectiles.ProjectileRunner;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class MathProjectileRunner extends ProjectileRunner<Location> {

    public double xp, yp, zp = 0;
    public double speed;
    public Location direction;

    public MathProjectileRunner(@NotNull CustomProjectile projectile, double speed) {
        super(projectile);
        this.speed = speed;
    }

    public MathProjectileRunner(@NotNull CustomProjectile projectile) {
        this(projectile, 1);
    }

    public MathProjectileRunner(@NotNull CustomProjectile projectile, double speed, @NotNull Location direction) {
        this(projectile, speed);
        setDirection(direction);
    }

    public void move() {
        projectile.getLocation().add(xp, yp, zp);
    }

    public void setDirection(@NotNull Location dir) {
        direction = dir;

        double bigest = Math.abs(projectile.getLocation().getX() - dir.getX());
        if(bigest < Math.abs(projectile.getLocation().getY() - dir.getY())) bigest = Math.abs(projectile.getLocation().getY() - dir.getY());
        if(bigest < Math.abs(projectile.getLocation().getZ() - dir.getZ())) bigest = Math.abs(projectile.getLocation().getZ() - dir.getZ());

        xp = (projectile.getLocation().getX() - dir.getX()) / bigest * -speed;
        yp = (projectile.getLocation().getY() - dir.getY()) / bigest * -speed;
        zp = (projectile.getLocation().getZ() - dir.getZ()) / bigest * -speed;
    }

    @Override
    public Vector getSpeed() {
        return new Vector(speed, speed, speed);
    }
}
