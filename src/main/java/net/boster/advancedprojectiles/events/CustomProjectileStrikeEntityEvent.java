package net.boster.advancedprojectiles.events;

import lombok.Getter;
import net.boster.advancedprojectiles.CustomProjectile;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class CustomProjectileStrikeEntityEvent extends CustomProjectileStrikeEvent {

    @Getter @NotNull private final Entity entity;

    public CustomProjectileStrikeEntityEvent(@NotNull CustomProjectile projectile, @NotNull Entity entity) {
        super(projectile);
        this.entity = entity;
    }
}
