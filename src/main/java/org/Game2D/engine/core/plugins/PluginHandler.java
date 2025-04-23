package org.Game2D.engine.core.plugins;

import org.Game2D.engine.flags.plugins.FlagProcessor;
import org.Game2D.engine.interfaces.plugins.PluginInterface;

import java.util.LinkedHashSet;
import java.util.Set;

public class PluginHandler {

    private static Set<PluginInterface> plugins = new LinkedHashSet<>();

    public static void addPlugin(PluginInterface plugin) {

        FlagProcessor.processFlags(plugin);

        plugins.add(plugin);

    }

}
