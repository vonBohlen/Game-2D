package org.Game2D.engine.ui.elements;

import lombok.NonNull;
import org.Game2D.engine.data.disk.assets.AssetMan;
import org.Game2D.engine.events.events.UiElementEvents;

import java.awt.*;
import java.util.UUID;

public abstract class UiElement {

    // Identifier
    public final UUID uuid = UUID.randomUUID();

    // Flags
    public boolean renderEnabled;

    // Dimensions
    @NonNull Rectangle dimensions;

    // Texture
    @NonNull public Image texture = AssetMan.loadAsset("default.png");

    // Render offset
    public int renderOffsetX = 0;
    public int renderOffsetY = 0;

    public UiElement(boolean renderEnabled, @NonNull Rectangle dimensions) {

        this.renderEnabled = renderEnabled;
        this.dimensions = dimensions;

        UiElementEvents.callEvent(
                handler ->
                        handler.handelUiElementCreationEvent(
                                this
                        )
        );

    }

    public abstract void onClick();

    public abstract void onHover();

    public void setRenderData(Graphics2D g2) {
        g2.drawImage(texture, dimensions.x + renderOffsetX, dimensions.y + renderOffsetY, dimensions.width, dimensions.height, null);
    }

    public void setHitBoxRenderData(Graphics2D g2) {
        g2.drawRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);
    }

    public void delete() {

        UiElementEvents.callEvent(
                handler ->
                        handler.handelUiElementDeletionEvent(
                                this
                        )
        );

    }

}
