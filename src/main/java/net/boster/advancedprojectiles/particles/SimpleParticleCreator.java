package net.boster.advancedprojectiles.particles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
@AllArgsConstructor
public class SimpleParticleCreator implements ParticleCreator {

    @Getter @Setter @NotNull private Particle particle;
    @Getter @Setter @Nullable private Particle.DustOptions dustOptions;
    @Getter @Setter private int amount = 15;
    @Getter @Setter private double radius = 0.2;

    @Override
    public void spawn(@NotNull Location loc) {
        if(dustOptions == null) {
            loc.getWorld().spawnParticle(particle, loc, amount, radius, radius, radius, 0);
        } else {
            loc.getWorld().spawnParticle(particle, loc, amount, radius, radius, radius, dustOptions);
        }
    }
}
