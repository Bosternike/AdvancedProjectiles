package net.boster.advancedprojectiles;

import lombok.Getter;
import lombok.Setter;
import net.boster.advancedprojectiles.action.CustomProjectileAction;
import net.boster.advancedprojectiles.runners.MathProjectileRunner;
import net.boster.advancedprojectiles.runners.VectorProjectileRunner;
import net.boster.advancedprojectiles.sender.ProjectileEntitySender;
import net.boster.advancedprojectiles.sender.ProjectileSender;
import net.boster.advancedprojectiles.events.CustomProjectileLaunchEvent;
import net.boster.advancedprojectiles.events.CustomProjectileStrikeBlockEvent;
import net.boster.advancedprojectiles.events.CustomProjectileStrikeEntityEvent;
import net.boster.advancedprojectiles.particles.ParticleCreator;
import net.boster.advancedprojectiles.provider.TaskProvider;
import net.boster.advancedprojectiles.task.ProjectileTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CustomProjectile {

    @NotNull protected final Location initialLocation;
    @NotNull protected Location location;
    @Nullable protected ParticleCreator particleCreator;

    @Getter @Setter @Nullable protected ProjectileSender sender;

    protected double damage = 0;

    protected double maxDistance = -1;

    protected double width = 0.4;

    @NotNull protected List<Entity> untouchableEntities = new ArrayList<>();
    @NotNull protected List<Material> fatalBlocks = new ArrayList<>();

    @NotNull CustomProjectileActions actions = new CustomProjectileActions();

    protected boolean ignoringBlocks = false;
    protected boolean ignoringEntities = false;
    protected boolean ignoringEntityVulnerability = false;

    @NotNull protected final Map<String, Object> projectileData = new HashMap<>();

    @Nullable protected LivingEntity lastDamagedEntity;

    protected ProjectileTask runnable;
    protected ProjectileRunner<?> runner;

    /**
     * true if projectile is alive and false if dead.
     */
    private boolean valid = true;

    public CustomProjectile(@NotNull Location loc, @Nullable ParticleCreator particleCreator, @Nullable ProjectileSender sender) {
        this.initialLocation = loc.clone();
        this.location = loc.clone();
        this.particleCreator = particleCreator;
        this.sender = sender;
    }

    public CustomProjectile(@NotNull Location location, @Nullable ParticleCreator particleCreator) {
        this(location, particleCreator, null);
    }

    public CustomProjectile(@NotNull Location location, @Nullable ProjectileSender sender) {
        this(location, null, sender);
    }

    public CustomProjectile(@NotNull Location location) {
        this(location, null, null);
    }

    public boolean runDeathAction() {
        return runActionIfExists(actions.getDeathAction());
    }

    /**
     * @return false if action is null and true if action is not null.
     */
    public boolean runActionIfExists(@Nullable CustomProjectileAction action) {
        if(action != null) {
            action.run(this);
            return true;
        } else {
            return false;
        }
    }

    public boolean runInteractFatalBlockAction() {
        return runActionIfExists(actions.getInteractFatalBlockAction());
    }

    public boolean runDamageAction() {
        return runActionIfExists(actions.getDamageAction());
    }

    public boolean runInteractSolidBlockAction() {
        return runActionIfExists(actions.getInteractSolidBlockAction());
    }

    public boolean runTickAction() {
        return runActionIfExists(actions.getTickAction());
    }

    /**
     * Launch the Projectile.
     *
     * @param dir Direction.
     * @param speed Speed.
     */
    public void run(@NotNull Location dir, double speed) {
        run(new MathProjectileRunner(this, speed, dir));
    }

    /**
     * Launch the Projectile.
     *
     * @param offSet Direction. For example, you can create it using:
     *               Location#getDirection().clone().multiply(0.5);
     */
    public void run(@NotNull Vector offSet) {
        run(new VectorProjectileRunner(this, offSet));
    }

    /**
     *
     * @param runner The main part of projectile. It will move your projectile.
     *               You can create your own one or use already existing one.
     */
    public void run(@NotNull ProjectileRunner<?> runner) {
        this.runner = runner;

        CustomProjectileLaunchEvent e = new CustomProjectileLaunchEvent(this);
        Bukkit.getPluginManager().callEvent(e);

        if(!e.isCancelled()) {
            runnable = new TaskProvider() {
                @Override
                public void run() {
                    tick();
                }
            }.runTask(0);
        }
    }

    protected void tick() {
        runner.move();

        runTickAction();

        if(particleCreator != null) {
            particleCreator.spawn(location);
        }

        if(!blockActions()) return;
        if(!damageActions()) return;

        checkDistance();
    }

    protected boolean blockActions() {
        if(ignoringBlocks) return true;

        Block b = location.getBlock();
        if(fatalBlocks.contains(b.getType())) {
            runInteractFatalBlockAction();
        }

        if(b.getType().isSolid() || b.getType().name().contains("LEAVES")) {
            CustomProjectileStrikeBlockEvent e = new CustomProjectileStrikeBlockEvent(this, location.getBlock());
            Bukkit.getPluginManager().callEvent(e);

            if(!e.isCancelled()) {
                runInteractSolidBlockAction();
            }
        }

        return valid;
    }

    protected boolean damageActions() {
        if(ignoringEntities) return true;

        double rw = width + 5;

        Vector min = new Vector(
                location.getX() - width,
                location.getY() - width,
                location.getZ() - width);
        Vector max = new Vector(
                location.getX() + width,
                location.getY() + width,
                location.getZ() + width);

        for(Entity ent : location.getWorld().getNearbyEntities(location, rw, rw, rw)) {
            if(untouchableEntities.contains(ent)) continue;

            if(ent.getBoundingBox().overlaps(min, max)) {
                CustomProjectileStrikeEntityEvent e = new CustomProjectileStrikeEntityEvent(this, ent);
                Bukkit.getPluginManager().callEvent(e);

                if(!e.isCancelled()) {
                    if(ent instanceof LivingEntity) {
                        lastDamagedEntity = (LivingEntity) ent;
                        if(damage > -1) {
                            damageLastHitLivingEntity();
                        }
                        runDamageAction();
                    }

                    return valid = false;
                }
            }
        }

        return valid;
    }

    protected void checkDistance() {
        if(maxDistance != 1 && initialLocation.distance(location) >= maxDistance) {
            cancelTask();
        }
    }

    public void damageLastHitLivingEntity() {
        if(lastDamagedEntity == null) return;
        if(!ignoringEntityVulnerability && lastDamagedEntity.isInvulnerable()) return;

        if(sender instanceof ProjectileEntitySender && ((ProjectileEntitySender) sender).getSender().isValid()) {
            lastDamagedEntity.damage(damage, ((ProjectileEntitySender) sender).getSender());
        } else {
            lastDamagedEntity.damage(damage);
        }

        lastDamagedEntity.setVelocity(location.getDirection().multiply(1));
    }

    /**
     * Remove the Projectile.
     */
    public void destroy() {
        valid = false;
        cancelTask();
        runDeathAction();
    }

    /**
     * Cancel the task.
     */
    public void cancelTask() {
        runnable.cancel();
    }
}
