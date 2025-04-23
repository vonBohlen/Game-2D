package org.Game2D.engine.flags.plugins;

import org.Game2D.engine.interfaces.plugins.PluginInterface;

import java.util.LinkedHashSet;

public class FlagProcessor {

    public static void processFlags(PluginInterface plugin) {

        LinkedHashSet<Flags> flags;

        if (plugin.getFlags() == null) return;

        flags = plugin.getFlags();

        for (Flags flag : flags) {
            switch (flag) {
                case mainLoopExecution -> System.out.println("Requires main loop execution");
                default -> System.err.println("Critical error while loading plugin");
            }
        }
    }

}
