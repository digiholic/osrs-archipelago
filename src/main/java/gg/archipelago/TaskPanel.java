package gg.archipelago;

import gg.archipelago.Tasks.APTask;
import gg.archipelago.data.LocationData;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@Slf4j
public class TaskPanel extends JPanel {
    private final ArchipelagoPlugin plugin;

    private final JCheckBox displayCompleted;
    private final HashMap<APTask, TaskRow> locationPanels = new HashMap<APTask, TaskRow>();

    public TaskPanel(ArchipelagoPlugin plugin){
        this.plugin = plugin;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 10));
        setVisible(false);

        displayCompleted = new JCheckBox("Display Completed Tasks");
        displayCompleted.setAlignmentX(Component.LEFT_ALIGNMENT);
        displayCompleted.addActionListener(e -> SwingUtilities.invokeLater(this::UpdateTaskStatus));

        add(displayCompleted);
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private class TaskRow extends JPanel{
        private JComponent taskName;

        private APTask task;
        public TaskRow(APTask task){
            this.task = task;

            BufferedImage image = TaskLists.loadedSprites.get(task);
            setLayout(new BorderLayout());
            setBackground(task.IsCompleted() ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
            setPreferredSize(new Dimension(0, 36));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            if (image != null){
                JLabel icon = new JLabel(new ImageIcon(image));
                icon.setPreferredSize(new Dimension(32,0));
                add(icon, BorderLayout.WEST);
            }

            // For the time being, disable the "Can Manually Activate" check and let users click any of the task buttons.
            // The state-based tasks _should_ be working now, but no sense letting a glitch prevent someone's progress
            // if (!task.IsCompleted() && task.CanManuallyActivate()){
            if (!task.IsCompleted()){
                JButton taskButton = new JButton("<html><div style='text-align:center'>"+task.GetName()+"</div></html>");
                taskButton.setHorizontalAlignment(SwingConstants.CENTER);
                taskButton.addActionListener(e -> ManuallyComplete());

                taskName = taskButton;
            } else {
                taskName = new JLabel("<html><div style='text-align:center'>"+task.GetName()+"</div></html>", SwingConstants.CENTER);
            }

            taskName.setForeground(task.IsCompleted() ? Color.BLACK : Color.WHITE);
            add(taskName, BorderLayout.CENTER);
            setVisible(!task.IsCompleted() || displayCompleted.isSelected());
        }

        public void UpdateCompleted(boolean completed){
            if (completed && taskName instanceof JButton) {
                remove(taskName);
                taskName = new JLabel("<html><div style='text-align:center'>"+task.GetName()+"</div></html>", SwingConstants.CENTER);
                add(taskName, BorderLayout.CENTER);
            }
            setBackground(completed ? ColorScheme.PROGRESS_COMPLETE_COLOR : ColorScheme.DARKER_GRAY_COLOR);
            taskName.setForeground(completed ? Color.BLACK : Color.WHITE);
        }

        public void UpdateDisplay(){
            setVisible(!task.IsCompleted() || displayCompleted.isSelected());
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
    }

    public void UpdateTaskStatus(){
        for (APTask task : plugin.activeTasks){
            TaskRow taskPanel = locationPanels.getOrDefault(task, null);
            if (taskPanel != null){
                taskPanel.UpdateCompleted(task.IsCompleted());
                taskPanel.UpdateDisplay();
            }
        }
    }

    private void AddOrUpdateTaskRow(APTask task){
        //boolean completed = plugin.LocationCheckStates.getOrDefault(loc,false);
        if (locationPanels.containsKey(task)){
            locationPanels.get(task).UpdateCompleted(task.IsCompleted());
            locationPanels.get(task).UpdateDisplay();
        } else {
            TaskRow taskPanel = new TaskRow(task);
            locationPanels.put(task, taskPanel);
            add(taskPanel);
        }
    }

    public void ConnectionStateChanged(boolean connectionSuccessful) {
        if (connectionSuccessful){
            setVisible(true);

            for (APTask task : plugin.activeTasks){
                if (task.ShouldDisplayPanel()){
                    SwingUtilities.invokeLater(() -> {
                        AddOrUpdateTaskRow(task);
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
