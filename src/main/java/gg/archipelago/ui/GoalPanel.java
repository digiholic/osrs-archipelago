package gg.archipelago.ui;

import gg.archipelago.ArchipelagoPlugin;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class GoalPanel extends JPanel{
    private JButton taskName;
    private final ArchipelagoPlugin plugin;

    public String goal;

    public GoalPanel(String goal, ArchipelagoPlugin plugin){
        this.plugin = plugin;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 50));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        taskName = new JButton(WrapTaskNameText(goal));
        Font f = taskName.getFont();
        taskName.setFont(new Font(f.getName(), f.getStyle(), 32));
        taskName.setHorizontalAlignment(SwingConstants.CENTER);
        taskName.addActionListener(e -> ManuallyComplete());

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
                "Complete your Goal?", "Goal Complete",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION){
            log.info("Completed task "+goal);
            plugin.goalCompleted = true;
        }
    }

    private String WrapTaskNameText(String taskName){
        StringBuilder sb = new StringBuilder("<html><div style='text-align:center;padding:2px'>");
        sb.append("Your goal is: <br />");
        taskName = taskName.replace("~|","<font color='#ffff00'>").replace("|~","</font>");
        sb.append(taskName);
        sb.append("</div></html>");
        return sb.toString();
    }
}
