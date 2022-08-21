package net.boster.advancedprojectiles.events;

import lombok.Getter;
import lombok.Setter;
import net.boster.advancedprojectiles.CustomProjectile;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class CustomProjectileStrikeEvent extends CustomProjectileEvent implements Cancellable {

    @Getter @Setter private boolean cancelled = false;

    public CustomProjectileStrikeEvent(@NotNull CustomProjectile projectile) {
        super(projectile);
    }
}
