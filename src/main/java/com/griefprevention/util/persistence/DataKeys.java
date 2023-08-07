package com.griefprevention.util.persistence;

import org.bukkit.NamespacedKey;

import java.util.Objects;

public final class DataKeys
{
    public static final NamespacedKey PROTECTED_ITEM = key("death_drop_owner");
    public static final NamespacedKey PORTAL_TRAP = key("portal_trap_entry");
    public static final NamespacedKey FALLING_BLOCK = key("falling_block_spawn");

    private static NamespacedKey key(String value)
    {
        return Objects.requireNonNull(NamespacedKey.fromString("griefprevention:" + value));
    }

    private DataKeys()
    {
        throw new IllegalStateException("Cannot instantiate static utility classes!");
    }

}
