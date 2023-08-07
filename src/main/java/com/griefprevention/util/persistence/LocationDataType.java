package com.griefprevention.util.persistence;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link PersistentDataType} for storing and retrieving {@link Location Locations}.
 */
class LocationDataType implements PersistentDataType<PersistentDataContainer, Location>
{

    private final NamespacedKey world = Objects.requireNonNull(NamespacedKey.fromString("location:world"));
    private final NamespacedKey x = Objects.requireNonNull(NamespacedKey.fromString("location:x"));
    private final NamespacedKey y = Objects.requireNonNull(NamespacedKey.fromString("location:y"));
    private final NamespacedKey z = Objects.requireNonNull(NamespacedKey.fromString("location:z"));
    private final NamespacedKey pitch = Objects.requireNonNull(NamespacedKey.fromString("location:pitch"));
    private final NamespacedKey yaw = Objects.requireNonNull(NamespacedKey.fromString("location:yaw"));

    @Override
    public @NotNull Class<PersistentDataContainer> getPrimitiveType()
    {
        return PersistentDataContainer.class;
    }

    @Override
    public @NotNull Class<Location> getComplexType()
    {
        return Location.class;
    }

    @Override
    public @NotNull PersistentDataContainer toPrimitive(
            @NotNull Location complex,
            @NotNull PersistentDataAdapterContext context)
    {
        PersistentDataContainer container = context.newPersistentDataContainer();
        World world = complex.getWorld();
        if (world != null)
        {
            container.set(this.world, STRING, world.getName());
        }
        container.set(x, DOUBLE, complex.getX());
        container.set(y, DOUBLE, complex.getY());
        container.set(z, DOUBLE, complex.getZ());
        container.set(pitch, FLOAT, complex.getPitch());
        container.set(yaw, FLOAT, complex.getYaw());
        return container;
    }

    @Override
    public @NotNull Location fromPrimitive(
            @NotNull PersistentDataContainer primitive,
            @NotNull PersistentDataAdapterContext context)
    {
        World storedWorld = null;
        String name = primitive.get(world, STRING);
        if (name != null)
        {
            storedWorld = Bukkit.getWorld(name);
        }
        double storedX = Objects.requireNonNullElse(primitive.get(x, DOUBLE), 0.0D);
        double storedY = Objects.requireNonNullElse(primitive.get(y, DOUBLE), 0.0D);
        double storedZ = Objects.requireNonNullElse(primitive.get(z, DOUBLE), 0.0D);
        float storedPitch = Objects.requireNonNullElse(primitive.get(pitch, FLOAT), 0.0F);
        float storedYaw = Objects.requireNonNullElse(primitive.get(yaw, FLOAT), 0.0F);

        return new Location(storedWorld, storedX, storedY, storedZ, storedPitch, storedYaw);
    }

}
