package net.boster.advancedprojectiles.particles;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public interface ParticleCreator {

    void spawn(@NotNull Location loc);
}
