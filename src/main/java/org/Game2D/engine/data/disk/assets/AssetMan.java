package org.Game2D.engine.data.disk.assets;

import org.Game2D.engine.graphics.loops.RenderLoop;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AssetMan {

    public static final List<Image> loadedAssets = Collections.synchronizedList(new ArrayList<>());

//    public static  void preLoadAssets() {
//        synchronized (loadedAssets) {
//            loadedAssets.addAll(ImageIO.read(Objects.requireNonNull(RenderLoop.class.getClassLoader().))
//        }
//    }

    public static Image loadAsset(String path) {
        Image asset;

        try {
            asset = ImageIO.read(Objects.requireNonNull(RenderLoop.class.getClassLoader().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return asset;
    }

}
