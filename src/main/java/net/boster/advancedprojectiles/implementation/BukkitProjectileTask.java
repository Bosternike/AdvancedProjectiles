package net.boster.advancedprojectiles.implementation;

import lombok.RequiredArgsConstructor;
import net.boster.advancedprojectiles.task.ProjectileTask;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public class BukkitProjectileTask implements ProjectileTask {

    private final BukkitTask task;

    @Override
    public boolean isCancelled() {
        if(task == null) {
            return false;
        }

        return task.isCancelled();
    }

    @Override
    public void cancel() {
        if(task != null) {
            task.cancel();
        }
    }
}
