package com.griefprevention.module;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Similar to a PluginLogger, a small Logger extension that prefixes lines with the module name.
 */
final class ModuleLogger extends Logger
{

    private final String prefix;

    /**
     * Create a new ModuleLogger that extracts the name from a module.
     *
     * @param module the module
     */
    ModuleLogger(@NotNull Module module)
    {
        super(module.getClass().getCanonicalName(), null);
        this.prefix = "[" + module.getPlugin().getName() + "/" + module.getName() + "] ";
        setParent(module.getPlugin().getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(@NotNull LogRecord logRecord)
    {
        logRecord.setMessage(prefix + logRecord.getMessage());
        super.log(logRecord);
    }

}
