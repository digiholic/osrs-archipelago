package com.archipelago;

import com.archipelago.data.LocationData;
import joptsimple.util.KeyValuePair;
import net.runelite.api.Skill;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArchipelagoPanel extends PluginPanel {
    private final TaskPanel taskListPanel;

    private final ArchipelagoPlugin plugin;
    private final ArchipelagoConfig config;
    private SpriteManager spriteManager;

    ArchipelagoPanel(final ArchipelagoPlugin plugin, final ArchipelagoConfig config, SpriteManager spriteManager)
    {
        this.plugin = plugin;
        this.config = config;
        this.spriteManager = spriteManager;

        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        // Create layout panel for wrapping
        final JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
        add(layoutPanel, BorderLayout.NORTH);

        final JPanel statusPanel = buildStatusPanel();
        layoutPanel.add(statusPanel);

        layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        taskListPanel = new TaskPanel(plugin, spriteManager);
        layoutPanel.add(taskListPanel);
    }

    private JButton connectButton;

    private JPanel buildStatusPanel(){
        final JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        statusPanel.setPreferredSize(new Dimension(0, 30));
        statusPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        statusPanel.setVisible(true);

        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(0, 30));
        connectButton.setBorder(new EmptyBorder(5, 5, 5, 10));

        statusPanel.add(connectButton);
        connectButton.addActionListener(e -> {
            plugin.ConnectToAPServer();
        });

        return statusPanel;
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        taskListPanel.ConnectionStateChanged(connectionSuccessful);
        connectButton.setEnabled(connectionSuccessful);
        if (connectionSuccessful){
            connectButton.setText("Connected!");
        } else {
            connectButton.setText("Connect");
        }
    }

    public void UpdateTaskStatus(){
        taskListPanel.UpdateTaskStatus();
    }
}
