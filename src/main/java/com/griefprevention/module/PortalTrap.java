package com.griefprevention.module;

import com.griefprevention.util.persistence.DataTypes;
import me.ryanhamshire.GriefPrevention.CustomLogEntryTypes;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Feature: Prevent players from being trapped inside a portal,
 * unable to break blocks or open chat to call for help.
 */
public class PortalTrap implements Listener
{

    public static final NamespacedKey PORTAL_TRAP = Objects.requireNonNull(NamespacedKey.fromString("griefprevention:portal_trap_entry"));
    public static final NamespacedKey PORTAL_TRAP_EXIT = Objects.requireNonNull(NamespacedKey.fromString("griefprevention:portal_trap_exit"));
    private static final int MAX_PORTAL_SIZE = 23;

    private final GriefPrevention instance;

    // Pending cancellable rescues
    private final ConcurrentHashMap<UUID, BukkitTask> portalReturnTaskMap = new ConcurrentHashMap<>();

    public PortalTrap(GriefPrevention instance)
    {
        this.instance = instance;
    }

    @EventHandler
    private void onPlayerJoin(@NotNull PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        // Are they stuck in a portal?
        Location portalRescueLocation = player.getPersistentDataContainer().get(PORTAL_TRAP, DataTypes.LOCATION);
        if (portalRescueLocation != null)
        {
            // If so, let them know and rescue in 30 seconds.
            GriefPrevention.sendMessage(player, TextMode.Info, Messages.NetherPortalTrapDetectionMessage, 20L);
            startRescueTask(player);
        }
        // Otherwise reset cooldown in case they happened to log out again
        else
            player.setPortalCooldown(0);
    }

    @EventHandler
    private void onPlayerQuit(@NotNull PlayerQuitEvent event)
    {
        // If player is not trapped in a portal and has a pending rescue task, remove the associated metadata
        if (event.getPlayer().getPortalCooldown() < 1)
        {
            portalReturnTaskMap.remove(event.getPlayer().getUniqueId());
            event.getPlayer().getPersistentDataContainer().remove(PORTAL_TRAP);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerPortal(@NotNull PlayerPortalEvent event)
    {
        // If the player isn't going anywhere, take no action
        if (event.getTo() == null || event.getTo().getWorld() == null)
            return;

        // If the player isn't going through a nether portal, not a nether portal trap
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)
            return;

        Player player = event.getPlayer();
        // Store teleport data in player's data
        player.getPersistentDataContainer().set(PORTAL_TRAP, DataTypes.LOCATION, player.getLocation());
        player.getPersistentDataContainer().set(PORTAL_TRAP_EXIT, DataTypes.LOCATION, event.getTo());

        // Start task to rescue player
        startRescueTask(player);
    }

    private void startRescueTask(@NotNull Player player)
    {
        // Schedule task to rescue the player after 30 seconds.
        // While this is a long wait if you're stuck, it is the maximum client timeout time, so it's
        // the only way we can guarantee that chunks have had as much time to load as possible.
        // This gives players the best chance to avoid erroneous rescues.
        BukkitTask task = new CheckForPortalTrapTask(player).runTaskLater(instance, 600L);

        // Cancel existing rescue task
        BukkitTask existing = portalReturnTaskMap.put(player.getUniqueId(), task);
        if (existing != null)
            existing.cancel();
    }

    private class CheckForPortalTrapTask extends BukkitRunnable
    {
        //player who recently teleported via nether portal
        private final Player player;

        public CheckForPortalTrapTask(@NotNull Player player)
        {
            this.player = player;
            //where to send the player back to if he hasn't left the portal frame
        }

        @Override
        public void run()
        {
            // Player is not online or no longer inside a portal.
            if (!player.isOnline() || player.getPortalCooldown() < 1)
            {
                return;
            }

            PersistentDataContainer container = player.getPersistentDataContainer();
            if (isOutOfOriginalPortal(player))
            {
                container.remove(PORTAL_TRAP_EXIT);
                container.remove(PORTAL_TRAP);
                portalReturnTaskMap.remove(player.getUniqueId());
                return;
            }

            Location location = container.get(PORTAL_TRAP, DataTypes.LOCATION);
            if (location != null)
            {
                GriefPrevention.AddLogEntry("Rescued " + player.getName() + " from a nether portal.\nTeleported from " + player.getLocation() + " to " + location, CustomLogEntryTypes.Debug);
                player.teleport(location);
                container.remove(PORTAL_TRAP);
            }

            portalReturnTaskMap.remove(player.getUniqueId());
        }

        private boolean isOutOfOriginalPortal(@NotNull Player player)
        {
            Location portalExit = player.getPersistentDataContainer().get(PORTAL_TRAP_EXIT, DataTypes.LOCATION);

            // Portal trap is from an addon or predates portal exit tagging.
            if (portalExit == null)
                return false;
            // If player is no longer in the same world as the portal they emerged from, they are entering a new portal.
            if (!player.getWorld().equals(portalExit.getWorld()))
                return true;

            // If player is outside of maximum portal size, they are entering a new portal.
            Location playerLocation = player.getLocation();
            if (Math.abs(portalExit.getX() - playerLocation.getX()) >= MAX_PORTAL_SIZE)
                return true;
            if (Math.abs(portalExit.getZ() - playerLocation.getZ()) >= MAX_PORTAL_SIZE)
                return true;
            return Math.abs(portalExit.getY() - playerLocation.getY()) >= MAX_PORTAL_SIZE;
        }
    }

}
