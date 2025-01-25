package gg.archipelago;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;

import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;


public class IllegalRegionOverlay extends OverlayPanel {
    @Inject
    private Client client;

    @Inject
    public IllegalRegionOverlay(Client client){
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.MED);
        panelComponent.setPreferredSize(new Dimension(300, 0));
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        // Check if the player is in an illegal region
        int regionId = getRegionID();
        // Check if the current region is one we actually check for
        if (ItemHandler.ChunkIDStringToRegionNames.containsKey(Integer.toString(regionId))){
            // Then check if we have that item
            String requiredItemName = ItemHandler.ChunkIDStringToRegionNames.get(Integer.toString(regionId));
            if (ArchipelagoPlugin.plugin.getCollectedItems().stream().filter(it -> it.name.equals(requiredItemName)).count() == 0){
                final String text1 = "You have not unlocked this chunk yet!";
                final String text2 = requiredItemName+" is required to be here";
                final int textWidth1 = graphics.getFontMetrics().stringWidth(text1);
                final int textWidth2 = graphics.getFontMetrics().stringWidth(text2);
                final int textWidth = Math.max(textWidth1,textWidth2);

                final int textHeight = (graphics.getFontMetrics().getAscent() - graphics.getFontMetrics().getDescent()) * 2;
                final int width = (int) client.getRealDimensions().getWidth();
                java.awt.Point jPoint = new java.awt.Point((width / 2) - textWidth, textHeight + 75);
                panelComponent.getChildren().clear();
                panelComponent.getChildren().add(TitleComponent.builder().text(text1).color(Color.RED).build());
                panelComponent.getChildren().add(TitleComponent.builder().text(text2).color(Color.RED).build());
                panelComponent.setPreferredLocation(jPoint);
                return panelComponent.render(graphics);
            }
        }

        return null;
    }

    private int getRegionID(){
        Player player = client.getLocalPlayer();
        if (player == null) return -1;
        return WorldPoint.fromLocalInstance(client, player.getLocalLocation()).getRegionID();
    }

}
