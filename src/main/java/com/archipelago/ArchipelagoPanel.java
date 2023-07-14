package com.archipelago;

import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ArchipelagoPanel extends PluginPanel {

    private final ArchipelagoPlugin plugin;
    private final ArchipelagoConfig config;

    private final TaskPanel taskListPanel;
    private final ItemPanel itemListPanel;

    private final JLabel messageLabel;

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

        final JPanel statusPanel = buildStatusPanel();
        layoutPanel.add(statusPanel);
        layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        messageLabel = new JLabel("");
        layoutPanel.add(messageLabel);

        taskListPanel = new TaskPanel(plugin);
        layoutPanel.add(taskListPanel);
        layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        itemListPanel = new ItemPanel(plugin);
        layoutPanel.add(itemListPanel);
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

        statusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return statusPanel;
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        taskListPanel.ConnectionStateChanged(connectionSuccessful);
        connectButton.setEnabled(!connectionSuccessful);
        connectButton.setText(connectionSuccessful ? "Connected!" : "Connect");
    }

    public void UpdateTaskStatus(){
        taskListPanel.UpdateTaskStatus();
    }

    public void UpdateItems(){
        itemListPanel.UpdateItems();
    }

    public void DisplayNetworkMessage(String message){
        String formattedMessage = String.format("<html><body style=\"text-align: justify;  text-justify: inter-word;\">%s</body></html>",message);
        messageLabel.setText(formattedMessage);
    }
}
