package net.boster.advancedprojectiles.provider;

import net.boster.advancedprojectiles.AdvancedProjectiles;
import net.boster.advancedprojectiles.task.ProjectileTask;
import org.jetbrains.annotations.NotNull;

public abstract class TaskProvider implements Runnable {

    private ProjectileTask task;

    public void cancel() {
        task.cancel();
    }

    public @NotNull ProjectileTask runTask(int period) {
        return setup(AdvancedProjectiles.getTaskProvider().runTask(this, period));
    }

    public @NotNull ProjectileTask runTask(int delay, int period) {
        return setup(AdvancedProjectiles.getTaskProvider().runTask(this, delay, period));
    }

    private ProjectileTask setup(ProjectileTask projectileTask) {
        if(task != null) {
            throw new IllegalStateException("Task already run");
        }

        this.task = projectileTask;
        return task;
    }
}
