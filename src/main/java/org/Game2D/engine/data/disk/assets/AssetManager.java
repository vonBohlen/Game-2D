/**
 * /engine/data/disk/assets/AssetMan.java
 *
 * Utility class for managing image assets.
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.data.disk.assets;

import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for managing image assets.
 */
public class AssetManager {

    /**
     * Hashmap of all loaded assets.
     */
    private static final ConcurrentHashMap<String, Image> loadedAssets = new ConcurrentHashMap<>();

    /**
     * Loads an asset from the resource folder into memory.
     *
     * @param path Path of the asset in the resource folder
     */
    public static void loadAsset(@NonNull String path) {
        if (loadedAssets.containsKey(path)) return;
        Image asset;
        try {
            asset = ImageIO.read(Objects.requireNonNull(AssetManager.class.getClassLoader().getResource(path)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        loadedAssets.put(path, asset);
    }

    /**
     * Loads multiple assets from the resource folder into memory.
     *
     * @param paths Paths of the assets in the resource folder
     */
    public static void loadAssets(@NonNull String[] paths) {
        for (String path : paths) loadAsset(path);
    }

    /**
     * Removes a loaded asset from memory.
     *
     * @param path Path of the asset in the resource folder
     */
    public static void unloadAsset(@NonNull String path) {
        loadedAssets.remove(path);
    }

    /**
     * Removes multiple loaded assets from memory.
     *
     * @param paths Paths of the assets in the resource folder
     */
    public static void unloadAssets(@NonNull String[] paths) {
        for (String path : paths) loadedAssets.remove(path);
    }

    /**
     * Get the fallback asset.
     *
     * @return Fallback asset
     */
    public static Image getFallback() {
        loadAsset("default.png");
        if (loadedAssets.containsKey("default.png")) return loadedAssets.get("default.png");
        throw new RuntimeException("Error while loading asset");
    }

    /**
     * Get a loaded asset, loads asset if not already loaded.
     *
     * @param path Path of the asset in the resource folder
     *
     * @return Wanted asset
     */
    public static Image getAsset(@NonNull String path) {
        loadAsset(path);
        if (loadedAssets.containsKey(path)) return loadedAssets.get(path);
        return getFallback();
    }

    /**
     * Get multiple loaded assets, loads assets if not already loaded.
     *
     * @param paths Paths of the assets in the resource folder
     *
     * @return Wanted assets
     */
    public static ArrayList<Image> getAssets(@NonNull String[] paths) {
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
