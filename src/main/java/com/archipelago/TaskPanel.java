package com.archipelago;

import com.archipelago.data.LocationData;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TaskPanel extends JPanel {
    private ArchipelagoPlugin plugin;
    private SpriteManager spriteManager;

    private JCheckBox displayCompleted;
    private HashMap<LocationData, JPanel> locationPanels = new HashMap<LocationData, JPanel>();

    public TaskPanel(ArchipelagoPlugin plugin, SpriteManager spriteManager){
        this.plugin = plugin;
        this.spriteManager = spriteManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(5, 5, 5, 10));
        setVisible(false);

        displayCompleted = new JCheckBox("Display Completed Tasks");
        displayCompleted.setAlignmentX(Component.LEFT_ALIGNMENT);
        displayCompleted.addActionListener(e -> SwingUtilities.invokeLater(this::UpdateTaskStatus));

        add(displayCompleted);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel buildTaskRow(String text, BufferedImage image, boolean completed){
        final JPanel taskRow = new JPanel();
        taskRow.setLayout(new BorderLayout());
        taskRow.setBackground(completed ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
        taskRow.setPreferredSize(new Dimension(0, 30));
        taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel(new ImageIcon(image));
        taskRow.add(icon, BorderLayout.WEST);

        JLabel taskName = new JLabel(text);
        taskName.setForeground(completed ? Color.BLACK : Color.WHITE);
        taskRow.add(taskName, BorderLayout.CENTER);
        taskRow.setVisible(true);

        return taskRow;
    }

    public void UpdateTaskStatus(){
        for (LocationData loc : LocationHandler.AllLocations){
            boolean completed = plugin.LocationCheckStates.getOrDefault(loc, false);
            JPanel taskPanel = locationPanels.getOrDefault(loc,null);
            if (taskPanel != null){
                taskPanel.setBackground(completed ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
                taskPanel.setVisible(!completed || displayCompleted.isSelected());
            }
        }
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        if (connectionSuccessful){
            setVisible(true);

            for (LocationData loc : LocationHandler.AllLocations) {
                if (loc.display_in_panel){
                    BufferedImage image = spriteManager.getSprite(loc.icon_id, loc.icon_file);
                    SwingUtilities.invokeLater(() -> {
                        JPanel taskPanel = buildTaskRow(loc.name, image, plugin.LocationCheckStates.getOrDefault(loc,false));
                        locationPanels.put(loc, taskPanel);
                        add(taskPanel);
                    });
                }
            }
            revalidate();
            repaint();
        } else {
            setVisible(false);
            //Empty the panel, but put the checkbox back for later
            removeAll();
            add(displayCompleted);
        }
    }
}
