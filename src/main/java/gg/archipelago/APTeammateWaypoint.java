package gg.archipelago;

import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.worldmap.WorldMapPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class APTeammateWaypoint extends WorldMapPoint {

    private final ArchipelagoPlugin plugin;
    private final BufferedImage apTeammateImage;
    private final Point apTeammatePoint;

    public APTeammateWaypoint(final WorldPoint worldPoint, ArchipelagoPlugin plugin)
    {
        super(worldPoint, null);

        apTeammateImage = new BufferedImage(plugin.getMapArrow().getWidth(), plugin.getMapArrow().getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = apTeammateImage.getGraphics();
        graphics.drawImage(plugin.getMapArrow(), 0, 0, null);
        graphics.drawImage(plugin.getMapAPLogo(), 0, 0, null);
        apTeammatePoint = new Point(
                apTeammateImage.getWidth() / 2,
                apTeammateImage.getHeight());

        this.plugin = plugin;
        this.setSnapToEdge(true);
        this.setJumpOnClick(true);
        this.setName("Clue Scroll");
        this.setImage(apTeammateImage);
        this.setImagePoint(apTeammatePoint);
    }

    @Override
    public void onEdgeSnap()
    {
        this.setImage(plugin.getAPLogo());
        this.setImagePoint(null);
    }

    @Override
    public void onEdgeUnsnap()
    {
        this.setImage(apTeammateImage);
        this.setImagePoint(apTeammatePoint);
    }
}

