package net.boster.advancedprojectiles.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.boster.advancedprojectiles.CustomProjectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class CustomProjectileEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    @Getter @NotNull private CustomProjectile projectile;

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
