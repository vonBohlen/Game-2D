package org.Game2D.engine.utils;

import org.Game2D.engine.core.managers.RenderMan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AssetMan {

    public static List<Image> loadAssets(String[] paths) {
        List<Image> assets = new ArrayList<>();

        try {
            for (int i = paths.length; i > 0; ) {
                assets.add(ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource(paths[i - 1]))));
                i--;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return assets;
    }

    public static Image loadAsset(String path) {
        Image asset = null;

        try {
            asset = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return asset;
    }

}
