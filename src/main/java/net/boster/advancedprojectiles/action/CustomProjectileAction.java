package net.boster.advancedprojectiles.action;

import net.boster.advancedprojectiles.CustomProjectile;
import org.jetbrains.annotations.NotNull;

public interface CustomProjectileAction {

    void run(@NotNull CustomProjectile projectile);
}
