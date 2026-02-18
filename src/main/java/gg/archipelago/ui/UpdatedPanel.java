package gg.archipelago.ui;

import gg.archipelago.ArchipelagoPlugin;
import gg.archipelago.DataPackage;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.components.IconTextField;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

public class UpdatedPanel {
    private JPanel MainPanel;
    private JTabbedPane TabbedPanel;
    private JLabel TitlePanel;
    private JLabel ConnectionStatusPanel;
    private JTable StoredConnectionsTable;
    private JPanel StatusTab;
    private JPanel TaskTab;
    private JPanel RegionsTab;
    private JPanel UnlocksTab;
    public JPanel GoalTab;
    private JCheckBox showCompletedCheckBox;
    private JCheckBox autoSwapTabOnCheckBox;
    private JTabbedPane TaskCategoryPanel;
    private JPanel AllTasksTab;

    private JButton ConnectButton;
    private JLabel StatusText;
    private IconTextField searchText;

    private ArchipelagoPlugin plugin;
    private SkillIconManager skillIconManager;

    private final HashMap<String, JPanel> TaskCategoriesByname;

    public UpdatedPanel(ArchipelagoPlugin plugin, SkillIconManager skillIconManager){
        this.skillIconManager = skillIconManager;
        this.plugin = plugin;
        this.TaskCategoriesByname = new HashMap<>();

        MainPanel = new JPanel();
        MainPanel.setLayout(new GridBagLayout());
        MainPanel.setEnabled(true);
        MainPanel.setMaximumSize(new Dimension(400, 800));
        MainPanel.setPreferredSize(new Dimension(400, 800));
        TitlePanel = new JLabel();
        TitlePanel.setEnabled(true);
        TitlePanel.setPreferredSize(new Dimension(191, 30));
        TitlePanel.setText("Archipelago Multiworld Randomizer");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 10, 2, 2);
        MainPanel.add(TitlePanel, gbc);
        ConnectButton = new JButton();
        ConnectButton.setText("Connect");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.add(ConnectButton, gbc);
        StatusText = new JLabel();
        StatusText.setText("Status Text");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 4, 2, 4);
        MainPanel.add(StatusText, gbc);
        TabbedPanel = new JTabbedPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);
        MainPanel.add(TabbedPanel, gbc);
        StatusTab = new JPanel();
        StatusTab.setLayout(new GridBagLayout());
        TabbedPanel.addTab("Status", StatusTab);
        StoredConnectionsTable = new JTable();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane connectionScrollPane = new JScrollPane(StoredConnectionsTable);
        StatusTab.add(connectionScrollPane, gbc);
        TaskTab = new JPanel();
        TaskTab.setLayout(new GridBagLayout());
        TabbedPanel.addTab("Tasks", TaskTab);
        showCompletedCheckBox = new JCheckBox();
        showCompletedCheckBox.setText("Show Completed");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        TaskTab.add(showCompletedCheckBox, gbc);
        autoSwapTabOnCheckBox = new JCheckBox();
        autoSwapTabOnCheckBox.setText("Auto-swap Tab on gaining Skill XP");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        TaskTab.add(autoSwapTabOnCheckBox, gbc);
        searchText = new IconTextField();
        searchText.setIcon(IconTextField.Icon.SEARCH);
        searchText.setPreferredSize(new Dimension(searchText.getPreferredSize().width, 32));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        TaskTab.add(searchText, gbc);
        TaskCategoryPanel = new JTabbedPane();
        TaskCategoryPanel.setTabLayoutPolicy(1);
        TaskCategoryPanel.setTabPlacement(2);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        TaskTab.add(TaskCategoryPanel, gbc);

        RegionsTab = new JPanel();
        RegionsTab.setLayout(new BorderLayout());
        TabbedPanel.addTab("Regions", RegionsTab);

        UnlocksTab = new JPanel();
        UnlocksTab.setLayout(new BorderLayout());
        TabbedPanel.addTab("Unlocks", UnlocksTab);

        GoalTab = new JPanel();
        GoalTab.setLayout(new BorderLayout());
        TabbedPanel.addTab("Goal", GoalTab);

        RegionsTab.add(new JLabel("<html><div style='text-align:center;padding:2px'>Please just check your map this panel isn't done yet</div></html>"), BorderLayout.NORTH);
        UnlocksTab.add(new JLabel("<html><div style='text-align:center;padding:2px'>Current version does not have non-chunk unlocks.</div></html>"), BorderLayout.NORTH);
        SetUpListeners();
        SetUpTaskPanels();
        SetUpConnectionsTable();
    }

    private void SetUpListeners(){
        showCompletedCheckBox.addActionListener(e ->
                plugin.displayAllTasks = showCompletedCheckBox.isSelected());
        autoSwapTabOnCheckBox.addActionListener(e ->
                plugin.toggleCategoryOnXP = autoSwapTabOnCheckBox.isSelected());
        ConnectButton.addActionListener(e ->
                plugin.ConnectToAPServer());
        searchText.addActionListener(e ->
                plugin.taskSearchText = searchText.getText());
    }

    private void SetUpTaskPanel(String displayTitle, Icon icon, String... categoryNames){
        JPanel tab = new JPanel();
        tab.setPreferredSize(new Dimension(TaskCategoryPanel.getPreferredSize().width - 200, tab.getPreferredSize().height));
        tab.setLayout( new BoxLayout(tab, BoxLayout.Y_AXIS));
        for(String categoryName : categoryNames){
            TaskCategoriesByname.put(categoryName, tab);
        }
        JScrollPane tasksPane = new JScrollPane(tab);
        TaskCategoryPanel.addTab(displayTitle, icon, tasksPane);
    }
    private void SetUpTaskPanels(){
        /*
        AllTasksTab = new JPanel();
        AllTasksTab.setLayout( new BoxLayout(AllTasksTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("all", AllTasksTab);
        JScrollPane allTasksPane = new JScrollPane(AllTasksTab);
        TaskCategoryPanel.addTab("All", allTasksPane);
        */
        SetUpTaskPanel( "Quests", null, "quest");
        SetUpTaskPanel("Combat", null, "combat", "kill");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.ATTACK, true)), "attack");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.STRENGTH, true)), "strength");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.DEFENCE, true)), "defense");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.RANGED, true)), "ranged");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.MAGIC, true)), "magic");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.PRAYER, true)), "prayer");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.HITPOINTS, true)), "hitpoints");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.MINING, true)), "mining");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.SMITHING, true)), "smithing");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.FISHING, true)), "fishing");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.COOKING, true)), "cooking");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.WOODCUTTING, true)), "woodcutting");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.FIREMAKING, true)), "firemaking");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.CRAFTING, true)), "crafting");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.RUNECRAFT, true)), "runecraft");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.AGILITY, true)), "agility");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.HERBLORE, true)), "herblore");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.THIEVING, true)), "thieving");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.FLETCHING, true)), "fletching");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.SLAYER, true)), "slayer");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.FARMING, true)), "farming");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.CONSTRUCTION, true)), "construction");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.HUNTER, true)), "hunter");
        SetUpTaskPanel("", new ImageIcon(skillIconManager.getSkillImage(Skill.SAILING, true)), "sailing");
        SetUpTaskPanel("Stats", null, "stats");
        SetUpTaskPanel("Misc", null, "other");
    }

    private void SetUpConnectionsTable(){
        List<DataPackage> dataPackages = plugin.getAllDataPackages();
        String[] columnNames = {"Slot", "Character", "Port", "Delete?"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (DataPackage dataPackage : dataPackages){
            model.addRow(new Object[] {dataPackage.slotName, dataPackage.accountName, dataPackage.port, "X"});
        }
        StoredConnectionsTable.setModel(model);

        Action delete = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(MainPanel);
                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Delete this cached Slot Data?", "Delete Cached Slot",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.OK_OPTION){
                    JTable table = (JTable)e.getSource();
                    int modelRow = Integer.valueOf( e.getActionCommand() );
                    ((DefaultTableModel)table.getModel()).removeRow(modelRow);
                    plugin.DeleteDataPackage(dataPackages.get(modelRow).accountHash);
                }
            }
        };

        new ButtonColumn(StoredConnectionsTable, delete, 3);
    }
    public JPanel GetPanel(){
        return MainPanel;
    }

    public void UpdateStatusButton(boolean connectionSuccessful){
        ConnectButton.setEnabled(!connectionSuccessful);
        ConnectButton.setText(connectionSuccessful ? "Connected!" : "Connect");
    }

    public void DisplayNetworkMessage(String message){
        String formattedMessage = String.format("<html><body style=\"text-align: justify;  text-justify: inter-word;\">%s</body></html>",message);
        StatusText.setText(formattedMessage);
    }

    public JPanel GetTaskByCategory(String category){
        String lowerCategory = category.toLowerCase();
        if (TaskCategoriesByname.containsKey(lowerCategory)){
            return TaskCategoriesByname.get(lowerCategory);
        }
        return TaskCategoriesByname.get("other");
    }

    public void SetSelectedTaskCategory(String category){
        JPanel taskPanel = GetTaskByCategory(category);
        Component c = taskPanel.getParent().getParent();
        TaskCategoryPanel.setSelectedComponent(c);
    }
}
