package net.boster.advancedprojectiles.events;

import lombok.Getter;
import net.boster.advancedprojectiles.CustomProjectile;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class CustomProjectileStrikeBlockEvent extends CustomProjectileStrikeEvent {

    @Getter @NotNull private final Block block;

    public CustomProjectileStrikeBlockEvent(@NotNull CustomProjectile projectile, @NotNull Block block) {
        super(projectile);
        this.block = block;
    }
}
