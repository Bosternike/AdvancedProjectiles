package net.boster.advancedprojectiles.sender;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface ProjectileEntitySender extends ProjectileSender {

    @NotNull Entity getSender();
}
