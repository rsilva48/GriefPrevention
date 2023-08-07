package me.ryanhamshire.GriefPrevention;

import com.griefprevention.util.persistence.DataKeys;
import com.griefprevention.util.persistence.DataTypes;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BlockEventHandlerTest
{
    private static final UUID PLAYER_UUID = UUID.fromString("fa8d60a7-9645-4a9f-b74d-173966174739");

    @Test
    void verifyNormalHopperPassthrough()
    {
        // Verify that we don't cancel events for unprotected items.

        Item item = mock(Item.class);
        PersistentDataContainer container = mock(PersistentDataContainer.class);
        when(item.getPersistentDataContainer()).thenReturn(container);
        Inventory inventory = mock(Inventory.class);
        InventoryPickupItemEvent event = mock(InventoryPickupItemEvent.class);
        when(inventory.getType()).thenReturn(InventoryType.HOPPER);
        when(event.getItem()).thenReturn(item);
        when(event.getInventory()).thenReturn(inventory);
        BlockEventHandler handler = new BlockEventHandler(null);

        handler.onInventoryPickupItem(event);

        verify(event, never()).setCancelled(true);
    }

    @Test
    void verifyNoHopperPassthroughWhenItemIsProtected()
    {
        // Verify that we DO cancel events for items that are protected.

        Item item = mock(Item.class);
        PersistentDataContainer container = mock(PersistentDataContainer.class);
        when(item.getPersistentDataContainer()).thenReturn(container);
        when(container.get(DataKeys.PROTECTED_ITEM, DataTypes.UUID)).thenReturn(PLAYER_UUID);
        Inventory inventory = mock(Inventory.class);
        when(inventory.getType()).thenReturn(InventoryType.HOPPER);
        DataStore dataStore = mock(DataStore.class);
        when(dataStore.getPlayerData(PLAYER_UUID)).thenReturn(new PlayerData());
        BlockEventHandler handler = new BlockEventHandler(dataStore);
        InventoryPickupItemEvent event = mock(InventoryPickupItemEvent.class);
        when(event.getInventory()).thenReturn(inventory);
        when(event.getItem()).thenReturn(item);
        Server server = mock(Server.class);
        when(server.getPlayer(PLAYER_UUID)).thenReturn(mock(Player.class));

        try (var bukkit = mockStatic(Bukkit.class))
        {
            bukkit.when(Bukkit::getServer).thenReturn(server);

            handler.onInventoryPickupItem(event);
        }

        verify(event).setCancelled(true);
    }

    @Test
    void verifyHopperPassthroughWhenItemIsProtectedButOwnerIsOffline()
    {
        // Verify that we don't cancel events for items that are protected, but where
        // the owner of those items is not logged in.
        // This behaviour matches older versions of GriefPrevention.

        Item item = mock(Item.class);
        PersistentDataContainer container = mock(PersistentDataContainer.class);
        when(item.getPersistentDataContainer()).thenReturn(container);
        when(container.get(DataKeys.PROTECTED_ITEM, DataTypes.UUID)).thenReturn(PLAYER_UUID);
        Inventory inventory = mock(Inventory.class);
        when(inventory.getType()).thenReturn(InventoryType.HOPPER);
        BlockEventHandler handler = new BlockEventHandler(null);
        InventoryPickupItemEvent event = mock(InventoryPickupItemEvent.class);
        when(event.getInventory()).thenReturn(inventory);
        when(event.getItem()).thenReturn(item);
        Server server = mock(Server.class);
        when(server.getPlayer(PLAYER_UUID)).thenReturn(null);

        try (var bukkit = mockStatic(Bukkit.class))
        {
            bukkit.when(Bukkit::getServer).thenReturn(server);

            handler.onInventoryPickupItem(event);
        }

        verify(event, never()).setCancelled(true);
    }
}
