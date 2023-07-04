package com.archipelago;

import com.archipelago.data.ItemData;
import com.archipelago.data.ItemNames;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ItemPanel extends JPanel {
    private final ArchipelagoPlugin plugin;

    private final HashMap<ItemData, ItemRow> itemPanels = new HashMap<>();

    public ItemPanel(ArchipelagoPlugin plugin){
        this.plugin = plugin;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(5, 5, 5, 10));
        setVisible(false);

        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void UpdateItems(){
        setVisible(true);

        for (ItemData item : ItemHandler.AllItems){
            int countInCollection = (int) plugin.getCollectedItems().stream().filter(it -> it.name.equals(item.name)).count();
            //Progressive items have special name formatting. Everything else uses the item name
            switch(item.name){
                case ItemNames.Progressive_Armor:
                    addOrUpdateItemPanel(item, String.format("Armor up to %s", ItemHandler.MetalTierByCount(countInCollection)));
                    break;
                case ItemNames.Progressive_Weapons:
                    addOrUpdateItemPanel(item, String.format("Weapons up to %s", ItemHandler.MetalTierByCount(countInCollection)));
                    break;
                case ItemNames.Progressive_Tools:
                    addOrUpdateItemPanel(item, String.format("Tools up to %s", ItemHandler.MetalTierByCount(countInCollection)));
                    break;
                case ItemNames.Progressive_Range_Armor:
                    addOrUpdateItemPanel(item, ItemHandler.RangeArmorTierByCount(countInCollection));
                    break;
                case ItemNames.Progressive_Range_Weapon:
                    addOrUpdateItemPanel(item, ItemHandler.RangeWeaponTierByCount(countInCollection));
                    break;
                case ItemNames.Progressive_Magic:
                    addOrUpdateItemPanel(item, ItemHandler.MagicTierByCount(countInCollection));
                    break;
                default:
                    if (countInCollection > 0){
                        addOrUpdateItemPanel(item, item.name);
                    }
            }
        }
        revalidate();
        repaint();
    }

    private void addOrUpdateItemPanel(ItemData item, String text){
        //The dict can have a null value, but if it does, we don't want to add it again (it's waiting for the ui thread)
        //So we just check the presence of the key here, then check for null inside the if
        if (itemPanels.containsKey(item)){
            if (itemPanels.get(item) != null)
                itemPanels.get(item).SetText(text);
        } else {
            itemPanels.put(item, null);
            SwingUtilities.invokeLater(() -> {
                ItemRow itemPanel = new ItemRow(text, ItemHandler.loadedSprites.get(item));
                itemPanels.put(item, itemPanel);
                add(itemPanel);
            });
        }
    }

    private static class ItemRow extends JPanel {

        private final JLabel labelText;

        public ItemRow(String text, BufferedImage image){
            super();
            setLayout(new BorderLayout());
            setBackground(ColorScheme.DARKER_GRAY_COLOR);
            setPreferredSize(new Dimension(0, 30));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            if (image != null){
                JLabel icon = new JLabel(new ImageIcon(image));
                add(icon, BorderLayout.WEST);
            }

            labelText = new JLabel(text);
            labelText.setForeground(Color.WHITE);
            add(labelText, BorderLayout.CENTER);
            setVisible(true);
        }

        public void SetText(String text){
            labelText.setText(text);
        }
    }
}
