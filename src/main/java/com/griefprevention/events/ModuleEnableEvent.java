package com.griefprevention.events;

import com.griefprevention.module.Module;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link Event} called when a {@link Module} is enabled.
 */
public class ModuleEnableEvent extends Event
{

    // Listenable event requirements
    private static final HandlerList HANDLERS = new HandlerList();
    private final @NotNull Module module;

    /**
     * Construct a new {@code ModuleEnableEvent}.
     *
     * @param module the module
     */
    public ModuleEnableEvent(@NotNull Module module)
    {
        this.module = module;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    /**
     * Get the {@link Module}.
     *
     * @return the module
     */
    public @NotNull Module getModule()
    {
        return module;
    }

    @Override
    public @NotNull HandlerList getHandlers()
    {
        return HANDLERS;
    }

}
