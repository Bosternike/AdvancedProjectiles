package net.boster.advancedprojectiles.provider;

import net.boster.advancedprojectiles.task.ProjectileTask;
import org.jetbrains.annotations.NotNull;

public interface ProjectileTaskProvider {
    
    @NotNull public abstract ProjectileTask runTask(@NotNull Runnable runnable, int period);
    @NotNull public abstract ProjectileTask runTask(@NotNull Runnable runnable, int delay, int period);
}
