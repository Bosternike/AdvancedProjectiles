package net.boster.advancedprojectiles.action;

import net.boster.advancedprojectiles.CustomProjectile;
import org.jetbrains.annotations.NotNull;

public class SimpleDestroyAction implements CustomProjectileAction {

    @Override
    public void run(@NotNull CustomProjectile projectile) {
        projectile.destroy();
    }
}
