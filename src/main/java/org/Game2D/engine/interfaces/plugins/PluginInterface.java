package org.Game2D.engine.interfaces.plugins;

import org.Game2D.engine.flags.plugins.Flags;

import java.util.LinkedHashSet;

public interface PluginInterface {

    LinkedHashSet<Flags> getFlags();

    void initialize();

    void start();

    void stop();
    
}
