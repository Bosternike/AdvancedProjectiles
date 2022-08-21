package net.boster.advancedprojectiles;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public abstract class ProjectileRunner<T> {

    @NotNull protected final CustomProjectile projectile;

    public abstract void move();
    public abstract void setDirection(@NotNull T t);
}
