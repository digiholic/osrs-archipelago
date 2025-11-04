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
    private JPanel QuestsTasksTab;
    private JPanel CombatTasksTab;
    private JPanel AttackTasksTab;
    private JPanel StrengthTaskTab;
    private JPanel DefenseTaskTab;
    private JPanel RangedTaskTab;
    private JPanel MagicTaskTab;
    private JPanel PrayerTaskTab;
    private JPanel HitpointsTaskTab;
    private JPanel MiningTaskTab;
    private JPanel SmithingTaskTab;
    private JPanel FishingTaskTab;
    private JPanel CookingTaskTab;
    private JPanel WoodcuttingTaskTab;
    private JPanel FiremakingTaskTab;
    private JPanel CraftingTaskTab;
    private JPanel RunecraftTaskTab;
    private JPanel AgilityTaskTab;
    private JPanel HerbloreTaskTab;
    private JPanel ThievingTaskTab;
    private JPanel FletchingTaskTab;
    private JPanel SlayerTaskTab;
    private JPanel FarmingTaskTab;
    private JPanel ConstructionTaskTab;
    private JPanel HunterTaskTab;
    private JPanel StatsTaskTab;
    private JPanel MiscTaskTab;
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

    private void SetUpTaskPanels(){
        /*
        AllTasksTab = new JPanel();
        AllTasksTab.setLayout( new BoxLayout(AllTasksTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("all", AllTasksTab);
        JScrollPane allTasksPane = new JScrollPane(AllTasksTab);
        TaskCategoryPanel.addTab("All", allTasksPane);
        */
        QuestsTasksTab = new JPanel();
        QuestsTasksTab.setLayout( new BoxLayout(QuestsTasksTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("quest", QuestsTasksTab);
        JScrollPane questTasksPane = new JScrollPane(QuestsTasksTab);
        TaskCategoryPanel.addTab("Quests", questTasksPane);


        CombatTasksTab = new JPanel();
        CombatTasksTab.setLayout( new BoxLayout(CombatTasksTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("combat", CombatTasksTab);
        TaskCategoriesByname.put("kill", CombatTasksTab);
        JScrollPane combatTasksPane = new JScrollPane(CombatTasksTab);
        TaskCategoryPanel.addTab("Combat", combatTasksPane);

        AttackTasksTab = new JPanel();
        AttackTasksTab.setLayout( new BoxLayout(AttackTasksTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("attack", AttackTasksTab);
        JScrollPane attackTasksPane = new JScrollPane(AttackTasksTab);
        TaskCategoryPanel.addTab("",  new ImageIcon(skillIconManager.getSkillImage(Skill.ATTACK, true)), attackTasksPane);

        StrengthTaskTab = new JPanel();
        StrengthTaskTab.setLayout( new BoxLayout(StrengthTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("strength", StrengthTaskTab);
        JScrollPane strengthTasksPane = new JScrollPane(StrengthTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.STRENGTH, true)), strengthTasksPane);

        DefenseTaskTab = new JPanel();
        DefenseTaskTab.setLayout( new BoxLayout(DefenseTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("defense", DefenseTaskTab);
        JScrollPane defenseTasksPane = new JScrollPane(DefenseTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.DEFENCE, true)), defenseTasksPane);

        RangedTaskTab = new JPanel();
        RangedTaskTab.setLayout( new BoxLayout(RangedTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("ranged", RangedTaskTab);
        JScrollPane rangedTasksPane = new JScrollPane(RangedTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.RANGED, true)), rangedTasksPane);

        MagicTaskTab = new JPanel();
        MagicTaskTab.setLayout( new BoxLayout(MagicTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("magic", MagicTaskTab);
        JScrollPane magicTasksPane = new JScrollPane(MagicTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.MAGIC, true)), magicTasksPane);

        PrayerTaskTab = new JPanel();
        PrayerTaskTab.setLayout( new BoxLayout(PrayerTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("prayer", PrayerTaskTab);
        JScrollPane prayerTasksPane = new JScrollPane(PrayerTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.PRAYER, true)), prayerTasksPane);

        HitpointsTaskTab = new JPanel();
        HitpointsTaskTab.setLayout( new BoxLayout(HitpointsTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("hitpoints", HitpointsTaskTab);
        JScrollPane hitpointsTasksPane = new JScrollPane(HitpointsTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.HITPOINTS, true)), hitpointsTasksPane);

        MiningTaskTab = new JPanel();
        MiningTaskTab.setLayout( new BoxLayout(MiningTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("mining", MiningTaskTab);
        JScrollPane miningTasksPane = new JScrollPane(MiningTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.MINING, true)), miningTasksPane);

        SmithingTaskTab = new JPanel();
        SmithingTaskTab.setLayout( new BoxLayout(SmithingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("smithing", SmithingTaskTab);
        JScrollPane smithingTasksPane = new JScrollPane(SmithingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.SMITHING, true)), smithingTasksPane);

        FishingTaskTab = new JPanel();
        FishingTaskTab.setLayout( new BoxLayout(FishingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("fishing", FishingTaskTab);
        JScrollPane fishingTasksPane = new JScrollPane(FishingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.FISHING, true)), fishingTasksPane);

        CookingTaskTab = new JPanel();
        CookingTaskTab.setLayout( new BoxLayout(CookingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("cooking", CookingTaskTab);
        JScrollPane cookingTasksPane = new JScrollPane(CookingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.COOKING, true)), cookingTasksPane);

        WoodcuttingTaskTab = new JPanel();
        WoodcuttingTaskTab.setLayout( new BoxLayout(WoodcuttingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("woodcutting", WoodcuttingTaskTab);
        JScrollPane woodcuttingTasksPane = new JScrollPane(WoodcuttingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.WOODCUTTING, true)), woodcuttingTasksPane);

        FiremakingTaskTab = new JPanel();
        FiremakingTaskTab.setLayout( new BoxLayout(FiremakingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("firemaking", FiremakingTaskTab);
        JScrollPane firemakingTasksPane = new JScrollPane(FiremakingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.FIREMAKING, true)), firemakingTasksPane);

        CraftingTaskTab = new JPanel();
        CraftingTaskTab.setLayout( new BoxLayout(CraftingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("crafting", CraftingTaskTab);
        JScrollPane craftingTasksPane = new JScrollPane(CraftingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.CRAFTING, true)), craftingTasksPane);

        RunecraftTaskTab = new JPanel();
        RunecraftTaskTab.setLayout( new BoxLayout(RunecraftTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("runecraft", RunecraftTaskTab);
        JScrollPane runecraftTasksPane = new JScrollPane(RunecraftTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.RUNECRAFT, true)), runecraftTasksPane);

        AgilityTaskTab = new JPanel();
        AgilityTaskTab.setLayout( new BoxLayout(AgilityTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("agility", AgilityTaskTab);
        JScrollPane agilityTasksPane = new JScrollPane(AgilityTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.AGILITY, true)), agilityTasksPane);

        HerbloreTaskTab = new JPanel();
        HerbloreTaskTab.setLayout( new BoxLayout(HerbloreTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("herblore", HerbloreTaskTab);
        JScrollPane herbloreTasksPane = new JScrollPane(HerbloreTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.HERBLORE, true)), herbloreTasksPane);

        ThievingTaskTab = new JPanel();
        ThievingTaskTab.setLayout( new BoxLayout(ThievingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("thieving", ThievingTaskTab);
        JScrollPane thievingTasksPane = new JScrollPane(ThievingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.THIEVING, true)), thievingTasksPane);

        FletchingTaskTab = new JPanel();
        FletchingTaskTab.setLayout( new BoxLayout(FletchingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("fletching", FletchingTaskTab);
        JScrollPane fletchingTasksPane = new JScrollPane(FletchingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.FLETCHING, true)), fletchingTasksPane);

        SlayerTaskTab = new JPanel();
        SlayerTaskTab.setLayout( new BoxLayout(SlayerTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("slayer", SlayerTaskTab);
        JScrollPane slayerTasksPane = new JScrollPane(SlayerTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.SLAYER, true)), slayerTasksPane);

        FarmingTaskTab = new JPanel();
        FarmingTaskTab.setLayout( new BoxLayout(FarmingTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("farming", FarmingTaskTab);
        JScrollPane farmingTasksPane = new JScrollPane(FarmingTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.FARMING, true)), farmingTasksPane);

        ConstructionTaskTab = new JPanel();
        ConstructionTaskTab.setLayout( new BoxLayout(ConstructionTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("construction", ConstructionTaskTab);
        JScrollPane constructionTasksPane = new JScrollPane(ConstructionTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.CONSTRUCTION, true)), constructionTasksPane);

        HunterTaskTab = new JPanel();
        HunterTaskTab.setLayout( new BoxLayout(HunterTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("hunter", HunterTaskTab);
        JScrollPane hunterTasksPane = new JScrollPane(HunterTaskTab);
        TaskCategoryPanel.addTab("", new ImageIcon(skillIconManager.getSkillImage(Skill.HUNTER, true)), hunterTasksPane);

        StatsTaskTab = new JPanel();
        StatsTaskTab.setLayout( new BoxLayout(StatsTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("stats", StatsTaskTab);
        JScrollPane statsTasksPane = new JScrollPane(StatsTaskTab);
        TaskCategoryPanel.addTab("Stats", statsTasksPane);

        MiscTaskTab = new JPanel();
        MiscTaskTab.setLayout( new BoxLayout(MiscTaskTab, BoxLayout.Y_AXIS));
        TaskCategoriesByname.put("other", MiscTaskTab);
        JScrollPane miscTasksPane = new JScrollPane(MiscTaskTab);
        TaskCategoryPanel.addTab("Misc", new ImageIcon(), miscTasksPane);
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
