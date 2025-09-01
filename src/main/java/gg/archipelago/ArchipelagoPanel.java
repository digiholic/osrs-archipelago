package gg.archipelago;

import gg.archipelago.Tasks.APTask;
import gg.archipelago.ui.GoalPanel;
import gg.archipelago.ui.UpdatedPanel;
import gg.archipelago.ui.UpdatedTaskPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;

@Slf4j
public class ArchipelagoPanel extends PluginPanel {

    private final ArchipelagoPlugin plugin;
    private final ArchipelagoConfig config;
    private final UpdatedPanel updatedPanel;

    private final SkillIconManager skillIconManager;
    private final HashMap<Long, UpdatedTaskPanel> taskPanelsById;
    private final EventBus eventBus;

    public ArchipelagoPanel(final ArchipelagoPlugin plugin, final ArchipelagoConfig config, SkillIconManager iconManager, EventBus eventBus)
    {
        this.plugin = plugin;
        this.config = config;
        this.skillIconManager = iconManager;
        this.taskPanelsById = new HashMap<Long, UpdatedTaskPanel>();
        this.eventBus = eventBus;

        getParent().setLayout(new BorderLayout());
        getParent().add(this, BorderLayout.CENTER);

        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new GridLayout(1, 1));

        updatedPanel = new UpdatedPanel(plugin, skillIconManager);
        add(updatedPanel.GetPanel());
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        UpdateStatusButton(connectionSuccessful);
    }

    public void UpdateStatusButton(boolean connectionSuccessful){
        updatedPanel.UpdateStatusButton(connectionSuccessful);
    }

    public void DisplayNetworkMessage(String message){
        updatedPanel.DisplayNetworkMessage(message);
    }

    public void AddTaskPanel(APTask task){
        UpdatedTaskPanel taskPanel = new UpdatedTaskPanel(task, plugin);
        JPanel targetPanel = updatedPanel.GetTaskByCategory(task.GetCategory());
        targetPanel.add(taskPanel);
        //JPanel allPanel = updatedPanel.GetTaskByCategory("all");
        //allPanel.add(taskPanel);
        taskPanelsById.put(task.GetID(), taskPanel);
        eventBus.register(taskPanel);
    }

    public void AddGoalPanel(String goal){
        GoalPanel goalPanel = new GoalPanel(goal, plugin);
        updatedPanel.GoalTab.add(goalPanel);
    }

    public UpdatedTaskPanel GetPanelByTask(APTask task){
        if (!taskPanelsById.containsKey(task.GetID())) {
            log.warn("Attempting to get panel for "+task.GetName()+", but no task panel was found!");
            return null;
        }
        return taskPanelsById.get(task.GetID());
    }

    public void UnregisterListeners(){
        for (UpdatedTaskPanel taskPanel : taskPanelsById.values()){
            eventBus.unregister(taskPanel);
        }
    }

    public void SetSelectedTaskCategory(String category) {
        updatedPanel.SetSelectedTaskCategory(category);
    }
}
