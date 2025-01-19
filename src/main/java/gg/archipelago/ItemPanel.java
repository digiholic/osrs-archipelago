package gg.archipelago;

import gg.archipelago.Tasks.APTask;
import gg.archipelago.data.ItemData;
import gg.archipelago.data.ItemNames;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.ColorScheme;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ItemPanel extends JPanel {
    private final ArchipelagoPlugin plugin;

    private final HashMap<ItemData, ItemRow> itemPanels = new HashMap<>();

    private final JPanel equipmentPanel;
    private final JPanel carePackPanel;
    private final JPanel areaPanel;
    private final JPanel junkPanel;
    private final JPanel claimedPackPanel;


    private boolean isPanelInitialized;

    public ItemPanel(ArchipelagoPlugin plugin){
        this.plugin = plugin;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(5, 5, 5, 10));
        setVisible(false);

        setAlignmentX(Component.LEFT_ALIGNMENT);

        equipmentPanel = new JPanel();
        equipmentPanel.setLayout(new BoxLayout(equipmentPanel, BoxLayout.Y_AXIS));
        add(equipmentPanel);

        carePackPanel = new JPanel();
        carePackPanel.setLayout(new BoxLayout(carePackPanel, BoxLayout.Y_AXIS));
        add(carePackPanel);

        areaPanel = new JPanel();
        areaPanel.setLayout(new BoxLayout(areaPanel, BoxLayout.Y_AXIS));
        add(areaPanel);

        junkPanel = new JPanel();
        junkPanel.setLayout(new BoxLayout(junkPanel, BoxLayout.Y_AXIS));
        add(junkPanel);

        claimedPackPanel = new JPanel();
        claimedPackPanel.setLayout(new BoxLayout(claimedPackPanel, BoxLayout.Y_AXIS));
        add(claimedPackPanel);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN && !isPanelInitialized)
        {
            SetConnectionState(true);
        }
        else if (gameStateChanged.getGameState() == GameState.LOGIN_SCREEN && isPanelInitialized)
        {
            SetConnectionState(false);
        }
    }

    @Subscribe
    public void onClientTick(ClientTick t){
        if (plugin.connected && isPanelInitialized){
            UpdateItems();
        }
    }

    public void SetConnectionState(boolean connectionStatus){
        if (connectionStatus){
            setVisible(true);
            isPanelInitialized = true;
            UpdateItems();
        } else {
            setVisible(false);
            isPanelInitialized = false;
            itemPanels.clear();
            equipmentPanel.removeAll();
            carePackPanel.removeAll();
            areaPanel.removeAll();
            junkPanel.removeAll();
        }
    }

    public void UpdateItems(){
        List<ItemData> items = ItemHandler.GetItems();
        if (items == null) return;
        for (ItemData item : items){
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
                ItemRow itemPanel = new ItemRow(text, item);
                itemPanels.put(item, itemPanel);
                switch(item.getItemType()) {
                    case "Area":
                        areaPanel.add(itemPanel);
                        break;
                    case "Item":
                        equipmentPanel.add(itemPanel);
                        break;
                    case "CarePack":
                        carePackPanel.add(itemPanel);
                        break;
                    default:
                        junkPanel.add(itemPanel);
                        break;
                }
            });
        }
    }

    private static class ItemRow extends JPanel {
        private final JLabel labelText;
        private final JLabel icon;
        private boolean isIconReady;
        private final ItemData itemData;
        public ItemRow(String text, ItemData item){
            super();
            itemData = item;
            setLayout(new BorderLayout());
            setBackground(ColorScheme.DARKER_GRAY_COLOR);
            setPreferredSize(new Dimension(0, 30));
            setAlignmentX(Component.LEFT_ALIGNMENT);

            icon = new JLabel();
            add(icon, BorderLayout.WEST);

            BufferedImage image = ItemHandler.loadedSprites.get(item);
            if (image != null){
                icon.setIcon(new ImageIcon(image));
                isIconReady = true;
            }

            labelText = new JLabel(text);
            labelText.setForeground(Color.WHITE);
            add(labelText, BorderLayout.CENTER);
            setVisible(true);
        }

        public void SetText(String text){
            labelText.setText(text);
            if (!isIconReady){
                BufferedImage image = ItemHandler.loadedSprites.get(itemData);
                if (image != null){
                    icon.setIcon(new ImageIcon(image));
                    isIconReady = true;
                }
            }
        }

        public void ClaimCarePack(){
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Claim this care pack?", "Claim Care Pack",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.OK_OPTION){
                itemData.Claim();
            }
        }
    }
}
