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
import java.util.List;
import java.util.Map;

public class ArchipelagoPanel extends PluginPanel {
    public static ArchipelagoPanel apPanel;


    private final JPanel connectionPanel;
    private final JPanel taskListPanel;

    private final ArchipelagoPlugin plugin;
    private final ArchipelagoConfig config;
    private SkillIconManager skillIconManager;
    private SpriteManager spriteManager;


    ArchipelagoPanel(final ArchipelagoPlugin plugin, final ArchipelagoConfig config, SkillIconManager skillIconManager, SpriteManager spriteManager)
    {
        this.plugin = plugin;
        this.config = config;
        this.skillIconManager = skillIconManager;
        this.spriteManager = spriteManager;

        apPanel = this;

        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        // Create layout panel for wrapping
        final JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
        add(layoutPanel, BorderLayout.NORTH);

        connectionPanel = buildConnectionPanel();
        //overallPanel = buildOverallPanel();
        taskListPanel = buildTaskPanel();

        // Create loot boxes wrapper
        //logsContainer.setLayout(new BoxLayout(logsContainer, BoxLayout.Y_AXIS));
        layoutPanel.add(connectionPanel);
        //layoutPanel.add(overallPanel);
        layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        layoutPanel.add(taskListPanel);
        //layoutPanel.add(logsContainer);

        // Add error pane
        //errorPanel.setContent("Loot tracker", "You have not received any loot yet.");
        //add(errorPanel);
    }

    private JTextField URLInput;
    private JTextField portInput;
    private JTextField slotInput;
    private JTextField passwordInput;
    public JLabel statusText;

    private JPanel buildConnectionPanel(){
        final JPanel connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridLayout(10,1));
        connectionPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        //connectionPanel.setPreferredSize(new Dimension(0, 30));
        connectionPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        connectionPanel.setVisible(true);

        //URL Entry
        JLabel URLLabel = new JLabel("Server Address");
        URLInput = new JTextField("localhost");
        connectionPanel.add(URLLabel);
        connectionPanel.add(URLInput);

        //Port
        JLabel portLabel = new JLabel("Port");
        portInput = new JTextField("38281", 5);
        connectionPanel.add(portLabel);
        connectionPanel.add(portInput);

        //Slot Name
        JLabel slotLabel = new JLabel("Slot Name");
        slotInput = new JTextField();
        connectionPanel.add(slotLabel);
        connectionPanel.add(slotInput);

        //Password
        JLabel passwordLabel = new JLabel("Server Password");
        passwordInput = new JTextField();
        connectionPanel.add(passwordLabel);
        connectionPanel.add(passwordInput);

        //Connect Button
        JButton connectButton = new JButton("Connect");
        connectionPanel.add(connectButton);
        connectButton.addActionListener(e -> {
            plugin.ConnectToAPServer(URLInput.getText(), Integer.parseInt(portInput.getText()), slotInput.getText(), passwordInput.getText());
        });

        statusText = new JLabel("");
        connectionPanel.add(statusText);

        return connectionPanel;
    }
    
    private JPanel buildTaskPanel(){
        final JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

        taskPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        //taskPanel.setPreferredSize(new Dimension(0, 60));
        taskPanel.setBorder(new EmptyBorder(5, 5, 5, 10));
        taskPanel.setVisible(false);

        taskPanel.add(buildTaskRow("test", 217, 0, true));

        return taskPanel;
    }

    private JPanel buildTaskRow(String text, int spriteID, int spriteFile, boolean completed){
        final JPanel taskRow = new JPanel();
        taskRow.setLayout(new BorderLayout());
        taskRow.setBackground(completed ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.GRAND_EXCHANGE_LIMIT);
        taskRow.setPreferredSize(new Dimension(0, 30));

        BufferedImage image = skillIconManager.getSkillImage(Skill.RUNECRAFT, true);
        //BufferedImage image = spriteManager.getSprite(spriteID, spriteFile);
        JLabel icon = new JLabel(new ImageIcon(image));
        taskRow.add(icon, BorderLayout.WEST);
        JLabel taskName = new JLabel(text);
        taskName.setForeground(completed ? Color.BLACK : Color.WHITE);
        taskRow.add(taskName, BorderLayout.CENTER);
        taskRow.setVisible(true);

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

    public void ConnectionStateChanged() {
        taskListPanel.setVisible(true);

        for (Map.Entry<LocationData,Boolean> data : plugin.LocationCheckStates.entrySet()) {
            if (data.getKey().display_in_panel){
                taskListPanel.add(buildTaskRow(data.getKey().name, data.getKey().icon_id, data.getKey().icon_file, data.getValue()));
            }
        }
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }
}
