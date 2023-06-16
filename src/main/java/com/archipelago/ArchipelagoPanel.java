package com.archipelago;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArchipelagoPanel extends PluginPanel {
    // When there is no loot, display this
    private final PluginErrorPanel errorPanel = new PluginErrorPanel();

    // Handle loot boxes
    private final JPanel logsContainer = new JPanel();

    // Handle overall session data
    //private final JPanel overallPanel;
    private final JLabel overallKillsLabel = new JLabel();
    private final JLabel overallGpLabel = new JLabel();
    private final JLabel overallIcon = new JLabel();

    // Details and navigation
    private final JPanel connectionPanel;
    private final JLabel detailsTitle = new JLabel();
    private final JButton backBtn = new JButton();
    private final JToggleButton viewHiddenBtn = new JToggleButton();
    private final JRadioButton singleLootBtn = new JRadioButton();
    private final JRadioButton groupedLootBtn = new JRadioButton();
    private final JButton collapseBtn = new JButton();

    private final ArchipelagoPlugin plugin;
    private final ArchipelagoConfig config;

    ArchipelagoPanel(final ArchipelagoPlugin plugin, final ArchipelagoConfig config)
    {
        this.plugin = plugin;
        this.config = config;

        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        // Create layout panel for wrapping
        final JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
        add(layoutPanel, BorderLayout.NORTH);

        connectionPanel = buildConnectionPanel();
        //overallPanel = buildOverallPanel();

        // Create loot boxes wrapper
        //logsContainer.setLayout(new BoxLayout(logsContainer, BoxLayout.Y_AXIS));
        layoutPanel.add(connectionPanel);
        //layoutPanel.add(overallPanel);
        //layoutPanel.add(logsContainer);

        // Add error pane
        //errorPanel.setContent("Loot tracker", "You have not received any loot yet.");
        //add(errorPanel);
    }

    private JPanel buildConnectionPanel(){
        final JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new BorderLayout());
        connectionPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        connectionPanel.setPreferredSize(new Dimension(0, 30));
        connectionPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        connectionPanel.setVisible(true);

        //URL Entry
        //Port
        //Slot Name
        //Password

        return connectionPanel;
    }

    private JPanel buildTaskPanel(){
        final JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());
        taskPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        taskPanel.setPreferredSize(new Dimension(0, 30));
        taskPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        taskPanel.setVisible(false);

        //Task Rows

        return taskPanel;
    }

    private JPanel buildTaskRow(String text, String icon){
        final JPanel taskRow = new JPanel();

        return taskRow;
    }
    private JPanel buildStatusPanel(){
        final JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        statusPanel.setPreferredSize(new Dimension(0, 30));
        statusPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        statusPanel.setVisible(false);

        return statusPanel;
    }
}
