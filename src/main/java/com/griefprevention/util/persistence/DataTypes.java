package com.griefprevention.util.persistence;

import org.bukkit.Location;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public final class DataTypes
{

    public static final PersistentDataType<PersistentDataContainer, Location> LOCATION = new LocationDataType();
    public static final PersistentDataType<byte[], UUID> UUID = new UuidDataType();

    private DataTypes()
    {
        throw new IllegalStateException("Cannot instantiate static utility classes!");
    }

}
