package gg.archipelago.ui;

import gg.archipelago.ArchipelagoPlugin;
import gg.archipelago.TaskLists;
import gg.archipelago.Tasks.APTask;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class UpdatedTaskPanel extends JPanel {
    private JComponent taskName;
    private APTask task;
    private final ArchipelagoPlugin plugin;

    public UpdatedTaskPanel(APTask task, ArchipelagoPlugin plugin){
        this.task = task;
        this.plugin = plugin;

        setLayout(new BorderLayout());
        setBackground(task.IsCompleted() ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
        setAlignmentX(Component.LEFT_ALIGNMENT);

        // For the time being, disable the "Can Manually Activate" check and let users click any of the task buttons.
        // The state-based tasks _should_ be working now, but no sense letting a glitch prevent someone's progress
        // if (!task.IsCompleted() && task.CanManuallyActivate()){
        if (!task.IsCompleted()){
            JButton taskButton = new JButton(WrapTaskNameText(task.GetName()));
            taskButton.setHorizontalAlignment(SwingConstants.CENTER);
            taskButton.addActionListener(e -> ManuallyComplete());

            taskName = taskButton;
        } else {
            taskName = new JLabel(WrapTaskNameText(task.GetName()), SwingConstants.CENTER);
        }

        taskName.setForeground(task.IsCompleted() ? Color.BLACK : Color.WHITE);
        add(taskName, BorderLayout.CENTER);

        taskName.setPreferredSize(taskName.getPreferredSize());
        if (taskName.getPreferredSize().height > getPreferredSize().height){
            setMinimumSize(taskName.getPreferredSize());
            setMaximumSize(taskName.getPreferredSize());
        }
    }

    public void ManuallyComplete(){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        int result = JOptionPane.showConfirmDialog(
                frame,
                "Mark this task as completed?", "Manually Complete",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            log.info("Completed task "+task.GetName());
            task.SetCompleted();
        }
    }
    @Subscribe
    public void onGameTick(GameTick tick){
        Update();
    }

    public void Update(){
        if (task.IsCompleted() && taskName instanceof JButton) {
            remove(taskName);
            taskName = new JLabel(WrapTaskNameText(task.GetName()), SwingConstants.CENTER);
            add(taskName, BorderLayout.CENTER);
            taskName.setPreferredSize(taskName.getPreferredSize());
            if (taskName.getPreferredSize().height > getPreferredSize().height){
                setMinimumSize(taskName.getPreferredSize());
                setMaximumSize(taskName.getPreferredSize());
            }
        }
        setBackground(task.IsCompleted() ? ColorScheme.GRAND_EXCHANGE_PRICE : ColorScheme.DARKER_GRAY_COLOR);
        taskName.setForeground(task.IsCompleted() ? Color.BLACK : Color.WHITE);

        boolean isSearchVisible = true;
        if (plugin.taskSearchText != null && !plugin.taskSearchText.isBlank()){
            isSearchVisible = task.GetName().toLowerCase().contains(plugin.taskSearchText.toLowerCase());
        }
        setVisible(isSearchVisible && (!task.IsCompleted() || plugin.displayAllTasks));
    }

    private String WrapTaskNameText(String taskName){
        StringBuilder sb = new StringBuilder("<html><div style='text-align:center;padding:2px'>");
        taskName = taskName.replace("~|","<font color='#ffff00'>").replace("|~","</font>");
        sb.append(taskName);
        sb.append("</div></html>");
        return sb.toString();
    }
}
