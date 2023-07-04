package com.archipelago;

import com.archipelago.data.ItemData;
import com.archipelago.data.ItemNames;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ItemPanel extends JPanel {
    private ArchipelagoPlugin plugin;
    private SpriteManager spriteManager;

    private HashMap<ItemData, JPanel> itemPanels = new HashMap<ItemData, JPanel>();

    public ItemPanel(ArchipelagoPlugin plugin, SpriteManager spriteManager){
        this.plugin = plugin;
        this.spriteManager = spriteManager;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(5, 5, 5, 10));
        setVisible(false);

        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel buildItemRow(String text, BufferedImage image){
        final JPanel taskRow = new JPanel();
        taskRow.setLayout(new BorderLayout());
        taskRow.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        taskRow.setPreferredSize(new Dimension(0, 30));
        taskRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel icon = new JLabel(new ImageIcon(image));
        taskRow.add(icon, BorderLayout.WEST);

        JLabel taskName = new JLabel(text);
        taskName.setForeground(Color.WHITE);
        taskRow.add(taskName, BorderLayout.CENTER);
        taskRow.setVisible(true);

        return taskRow;
    }

    public void UpdateItems(){
        //Wipe the panels and remake them
        itemPanels.clear();
        this.removeAll();

        for (ItemData item : ItemHandler.AllItems){
            int countInCollection = (int) plugin.getCollectedItems().stream().filter(it -> it.name.equals(item.name)).count();
            switch(item.name){
                case ItemNames.Progressive_Armor:
                    addItemPanel(item, String.format("Armor up to %s", ItemHandler.MetalTierByCount(countInCollection)));
                    break;
                case ItemNames.Progressive_Weapons:
                    addItemPanel(item, String.format("Weapons up to %s", ItemHandler.MetalTierByCount(countInCollection)));
                    break;
                case ItemNames.Progressive_Tools:
                    addItemPanel(item, String.format("Tools up to %s", ItemHandler.MetalTierByCount(countInCollection)));
                    break;
                case ItemNames.Progressive_Range_Armor:
                    addItemPanel(item, ItemHandler.RangeArmorTierByCount(countInCollection));
                    break;
                case ItemNames.Progressive_Range_Weapon:
                    addItemPanel(item, ItemHandler.RangeWeaponTierByCount(countInCollection));
                    break;
                case ItemNames.Progressive_Magic:
                    addItemPanel(item, ItemHandler.MagicTierByCount(countInCollection));
                    break;
                default:
                    if (countInCollection > 0){
                        addItemPanel(item, item.name);
                    }
            }
        }
    }

    private void addItemPanel(ItemData item, String text){
        BufferedImage image = spriteManager.getSprite(item.icon_id, item.icon_file);
        SwingUtilities.invokeLater(() -> {
            JPanel taskPanel = buildItemRow(text, image);
            itemPanels.put(item, taskPanel);
            add(taskPanel);
        });
    }
}
