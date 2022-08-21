package net.boster.advancedprojectiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.boster.advancedprojectiles.sender.ProjectileEntitySender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PlayerSender implements ProjectileEntitySender {

    @Getter @NotNull private final Player sender;
}
