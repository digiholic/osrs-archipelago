package gg.archipelago;

import gg.archipelago.Tasks.APTask;
import gg.archipelago.ui.UpdatedTaskPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ActiveTaskList {
    private final List<APTask> activeTasks;
    private final ArchipelagoPlugin plugin;
    private final ArchipelagoPanel panel;

    public ActiveTaskList(ArchipelagoPlugin plugin, ArchipelagoPanel panel){
        this.activeTasks  = new ArrayList<>();
        this.plugin = plugin;
        this.panel = panel;
    }

    public void AddActiveTask(APTask task){
        //First, check if there's a task in the list already with the same ID. If there is, remove it first.
        for (APTask oldTask : activeTasks) {
            if (oldTask == task) return; //If this exact task is already added, don't waste time adding it again
            if (oldTask.GetID() == task.GetID()){
                RemoveActiveTask(oldTask);
            }
        }
        activeTasks.add(task);
        panel.AddTaskPanel(task);
    }

    public void RemoveActiveTask(APTask task){
        activeTasks.remove(task);
        UpdatedTaskPanel taskPanel = panel.GetPanelByTask(task);
        taskPanel.getParent().remove(taskPanel);

    }

    public void ClearTaskList(){
        for (APTask task : activeTasks){
            UpdatedTaskPanel taskPanel = panel.GetPanelByTask(task);
            taskPanel.getParent().remove(taskPanel);
        }
        activeTasks.clear();
    }

    public List<APTask> GetActiveTasks(){
        return activeTasks;
    }
}
