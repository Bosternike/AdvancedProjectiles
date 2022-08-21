package net.boster.advancedprojectiles;

import lombok.Getter;
import lombok.Setter;
import net.boster.advancedprojectiles.action.CustomProjectileAction;
import net.boster.advancedprojectiles.action.SimpleDestroyAction;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class CustomProjectileActions {

    @Nullable private CustomProjectileAction interactFatalBlockAction = new SimpleDestroyAction();
    @Nullable private CustomProjectileAction deathAction = CustomProjectile::cancelTask;
    @Nullable private CustomProjectileAction damageAction = new SimpleDestroyAction();
    @Nullable private CustomProjectileAction interactSolidBlockAction = new SimpleDestroyAction();
    @Nullable private CustomProjectileAction tickAction;
}
