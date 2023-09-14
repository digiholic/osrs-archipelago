package gg.archipelago;

import gg.archipelago.data.LocationData;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TaskPanel extends JPanel {
    private final ArchipelagoPlugin plugin;

    private final JCheckBox displayCompleted;
    private final HashMap<LocationData, TaskRow> locationPanels = new HashMap<LocationData, TaskRow>();

    public TaskPanel(ArchipelagoPlugin plugin){
        this.plugin = plugin;

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

    private class TaskRow extends JPanel{
        private final JLabel taskName;

        private boolean completed;
        public TaskRow(String text, BufferedImage image, boolean completed){
            this.completed = completed;

            setLayout(new BorderLayout());
            setBackground(completed ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
            setPreferredSize(new Dimension(0, 30));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            if (image != null){
                JLabel icon = new JLabel(new ImageIcon(image));
                add(icon, BorderLayout.WEST);
            }

            taskName = new JLabel(text);
            taskName.setForeground(completed ? Color.BLACK : Color.WHITE);
            add(taskName, BorderLayout.CENTER);
            setVisible(!completed || displayCompleted.isSelected());
        }

        public void UpdateCompleted(boolean completed){
            setBackground(completed ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
            taskName.setForeground(completed ? Color.BLACK : Color.WHITE);
            this.completed = completed;
        }

        public void UpdateDisplay(){
            setVisible(!completed || displayCompleted.isSelected());
        }
    }

    public void UpdateTaskStatus(){
        for (LocationData loc : LocationHandler.AllLocations){
            //boolean completed = plugin.LocationCheckStates.getOrDefault(loc, false);
            boolean completed = false;
            TaskRow taskPanel = locationPanels.getOrDefault(loc,null);
            if (taskPanel != null){
                taskPanel.UpdateCompleted(completed);
                taskPanel.UpdateDisplay();
            }
        }
    }

    private void AddOrUpdateTaskRow(LocationData loc){
        //boolean completed = plugin.LocationCheckStates.getOrDefault(loc,false);
        boolean completed = false;
        if (locationPanels.containsKey(loc)){
            locationPanels.get(loc).UpdateCompleted(completed);
            locationPanels.get(loc).UpdateDisplay();
        } else {
            TaskRow taskPanel = new TaskRow(loc.name, LocationHandler.loadedSprites.get(loc), completed);
            locationPanels.put(loc, taskPanel);
            add(taskPanel);
        }
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        if (connectionSuccessful){
            setVisible(true);

            for (LocationData loc : LocationHandler.AllLocations) {
                if (loc.display_in_panel){
                    SwingUtilities.invokeLater(() -> {
                        AddOrUpdateTaskRow(loc);
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
