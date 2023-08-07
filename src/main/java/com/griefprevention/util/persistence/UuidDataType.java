package com.griefprevention.util.persistence;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.UUID;

/**
 * A {@link PersistentDataType} for storing and retrieving {@link UUID UUIDs}.
 */
class UuidDataType implements PersistentDataType<byte[], UUID>
{

    @Override
    public @NotNull Class<byte[]> getPrimitiveType()
    {
        return byte[].class;
    }

    @Override
    public @NotNull Class<UUID> getComplexType()
    {
        return UUID.class;
    }

    @Override
    public byte @NotNull [] toPrimitive(@NotNull UUID complex, @NotNull PersistentDataAdapterContext context)
    {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(complex.getMostSignificantBits());
        buffer.putLong(complex.getLeastSignificantBits());
        return buffer.array();
    }

    @Override
    public @NotNull UUID fromPrimitive(byte @NotNull [] primitive, @NotNull PersistentDataAdapterContext context)
    {
        LongBuffer buffer = ByteBuffer.wrap(primitive).asLongBuffer();

        if (!buffer.hasRemaining())
        {
            // Handle malformed tag gracefully
            return new UUID(0, 0);
        }

        long mostSigBits = buffer.get();

        if (buffer.hasRemaining())
        {
            // Valid tag, create stored UUID
            return new UUID(mostSigBits, buffer.get());
        }

        // Malformed tag, use known data.
        return new UUID(mostSigBits, 0);
    }

}
