package com.griefprevention.module;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A container for configurable behaviors for GriefPrevention.
 */
public final class ModuleManager
{

    private final @NotNull @UnmodifiableView Set<Module> modules;

    /**
     * Construct a new {@code ModuleManager} instance.
     *
     * @param plugin the {@link GriefPrevention} plugin instance
     */
    public ModuleManager(@NotNull GriefPrevention plugin)
    {
        Set<Module> modules = new HashSet<>();
        // TODO modules go here
        this.modules = Collections.unmodifiableSet(modules);
    }

    /**
     * Load modules' configurations.
     *
     * @param in the user-editable configuration
     * @param out the sanitized configuration copy
     */
    public void loadConfig(@NotNull Configuration in, @NotNull Configuration out)
    {
        for (Module module : modules)
        {
            // Use "modules" section for all modules.
            String path = "modules." + module.getName();
            ConfigurationSection inSection = in.getConfigurationSection(path);
            if (inSection == null)
            {
                inSection = in.createSection(path);
            }

            // Out config is entirely plugin-written, no need to look for existing data.
            module.getConfig().load(inSection, out.createSection(path));
        }
    }

    /**
     * Enable or disable modules based on their configuration.
     */
    public void updateModules()
    {
        for (Module module : modules)
        {
            // Enable or disable modules based on config.
            // Modules track state and will not attempt to enable or disable if already in correct state.
            if (module.getConfig().enabled())
            {
                module.enable();
            }
            else
            {
                module.disable();
            }
        }
    }

    /**
     * Get an unmodifiable {@link Set} of all available modules.
     *
     * @return the available modules
     */
    public @NotNull @UnmodifiableView Set<Module> getModules()
    {
        return modules;
    }

    /**
     * Get an unmodifiable {@link Set} of all enabled modules.
     *
     * @return the available modules
     */
    public @NotNull @UnmodifiableView Set<Module> getEnabledModules()
    {
        return modules.stream().filter(Module::isEnabled).collect(Collectors.toUnmodifiableSet());
    }

}
