/**
 * /engine/data/disk/assets/AssetMan.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.data.disk.assets;

import org.Game2D.engine.graphics.loops.RenderLoop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class AssetMan {

    private static final ConcurrentHashMap<String, Image> loadedAssets = new ConcurrentHashMap<>();

    public static void loadAsset(String path) {
        if (loadedAssets.containsKey(path)) return;
        Image asset;

        try {
            asset = ImageIO.read(Objects.requireNonNull(RenderLoop.class.getClassLoader().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadedAssets.put(path, asset);
    }

    public static void loadAssets(String[] paths) {
        for (String path : paths) loadAsset(path);
    }

    public static Image getFallback() {
        loadAsset("default.png");
        if (loadedAssets.containsKey("default.png")) return loadedAssets.get("default.png");
        throw new RuntimeException("Error while loading asset");
    }

    public static Image getAsset(String path) {
        loadAsset(path);
        if (loadedAssets.containsKey(path)) return loadedAssets.get(path);
        return getFallback();
    }

    public static ArrayList<Image> getAssets(String[] paths) {
        ArrayList<Image> assets = new ArrayList<>();
        loadAssets(paths);
        for (int i = paths.length; i > 0; ) {
            if (loadedAssets.containsKey(paths[i - 1])) assets.add(loadedAssets.get(paths[i - 1]));
            else assets.add(getFallback());
            i--;
        }
        return assets;
    }

}
