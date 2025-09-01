package gg.archipelago.Tasks;

import gg.archipelago.data.NameOrIDDataSource;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuOptionClicked;

public class EquipItemTask extends APTask{
    private final long _ID;
    private final String _name;
    private final String _category;
    private final NameOrIDDataSource _sprite;
    private final NameOrIDDataSource _item;
    private boolean _isCompleted = false;

    public EquipItemTask(long ID, String name, String category, NameOrIDDataSource sprite, NameOrIDDataSource item){
        _ID = ID;
        _name = name;
        _category = category;
        _sprite = sprite;
        _item = item;
    }
    @Override
    public String GetName() {
        return _name;
    }

    @Override
    public long GetID() {
        return _ID;
    }

    @Override
    public String GetCategory() { return _category; }

    @Override
    public boolean IsCompleted() {
        return _isCompleted;
    }

    @Override
    public void SetCompleted() {
        _isCompleted = true;
    }

    @Override
    public int GetSpriteID() {
        if (_sprite.isID)
            return _sprite.idValue;
        else return APTask.IconByName(_sprite.nameValue);
    }

    @Override
    public boolean ShouldDisplayPanel() {
        return true;
    }

    @Override
    public void CheckChatMessage(Client client, ChatMessage event) { }
    @Override
    public void CheckMobKill(Client client, NPC npc) { }
    @Override
    public void CheckPlayerStatus(Client client) { }

    @Override
    public void OnGameTick(Client client) {
        ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipment == null){
            return;
        }
        if (_item.isID){
            //If it's already an item ID, check if it's equipped.
            if (equipment.contains(_item.idValue)) _isCompleted = true;
        }
        else {
            //If it's not an ID, it's a name. We don't really have any better way than check the name of every equipped
            //item to see if it matches. As far as I know.
            for(Item item : equipment.getItems()){
                String itemName = client.getItemDefinition(item.getId()).getName();
                if (itemName.equalsIgnoreCase(_item.nameValue)) _isCompleted = true;
            }
        }
    }

    @Override
    public void OnMenuOption(Client client, MenuOptionClicked event) { }

    @Override
    public boolean CanManuallyActivate() { return true; }
}
