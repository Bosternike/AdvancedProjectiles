package net.boster.advancedprojectiles.implementation;

import net.boster.advancedprojectiles.AdvancedProjectiles;
import net.boster.advancedprojectiles.provider.ProjectileTaskProvider;
import net.boster.advancedprojectiles.task.ProjectileTask;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class BukkitTaskProvider implements ProjectileTaskProvider {

    @Override
    public @NotNull ProjectileTask runTask(@NotNull Runnable runnable, int period) {
        return runTask(runnable, 0, period);
    }

    @Override
    public @NotNull ProjectileTask runTask(@NotNull Runnable runnable, int delay, int period) {
        return new BukkitProjectileTask(new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(AdvancedProjectiles.getProvider(), delay, period));
    }
}
