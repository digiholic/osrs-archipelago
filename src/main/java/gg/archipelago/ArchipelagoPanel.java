package gg.archipelago;

import net.runelite.client.eventbus.EventBus;
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
        //layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel versionLabel = new JLabel("plugin version 2.1");
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        layoutPanel.add(versionLabel);

        messageLabel = new JLabel("");
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        messageLabel.setBorder(new EmptyBorder(5, 5, 5, 10));
        layoutPanel.add(messageLabel);

        taskListPanel = new TaskPanel(plugin);
        layoutPanel.add(taskListPanel);
        //layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        itemListPanel = new ItemPanel(plugin);
        layoutPanel.add(itemListPanel);
    }

    private JButton connectButton;

    private JPanel buildStatusPanel(){
        final JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(2, 1));
        statusPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        statusPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 80));
        statusPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        statusPanel.setVisible(true);

        JLabel instructionText = new JLabel("<html>Server address, port, and slot can be found in plugin settings.</html>");
        instructionText.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        instructionText.setBorder(new EmptyBorder(5, 5, 5, 10));

        connectButton = new JButton("Connect");
        connectButton.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));
        connectButton.setBorder(new EmptyBorder(5, 5, 5, 10));

        statusPanel.add(instructionText);
        statusPanel.add(connectButton);
        connectButton.addActionListener(e -> {
            plugin.ConnectToAPServer();
        });

        statusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return statusPanel;
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        taskListPanel.ConnectionStateChanged(connectionSuccessful);
        UpdateStatusButton(connectionSuccessful);
    }

    public void UpdateStatusButton(boolean connectionSuccessful){
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

    public void RegisterListeners(EventBus eventBus){
        eventBus.register(itemListPanel);
    }

    public void UnregisterListeners(EventBus eventBus){
        eventBus.unregister(itemListPanel);
    }
}
