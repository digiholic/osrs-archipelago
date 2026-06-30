package dev.koifysh.archipelago;

import dev.koifysh.archipelago.events.ReceiveItemEvent;
import dev.koifysh.archipelago.network.client.SyncPacket;
import dev.koifysh.archipelago.parts.DataPackage;
import dev.koifysh.archipelago.parts.NetworkItem;

import java.util.ArrayList;

public class ItemManager {


    Client client;
    APWebSocket APWebSocket;

    ArrayList<NetworkItem> receivedItems = new ArrayList<>();

    int index;

    public ItemManager(Client client) {
        this.client = client;
    }

    public void receiveItems(ArrayList<NetworkItem> ids, int index) {
        if (index == 0) {
            receivedItems = new ArrayList<>();
        }
        if (receivedItems.size() == index) {
            receivedItems.addAll(ids);
            DataPackage dp = client.getDataPackage();
            int myTeam = client.getTeam();
            for (int i = this.index; i < receivedItems.size(); i++) {
                NetworkItem item = receivedItems.get(i);
                item.itemName = dp.getItem(item.itemID, client.getGame());
                item.locationName = dp.getLocation(item.locationID, client.getSlotInfo().get(item.playerID).game);
                item.playerName = client.getRoomInfo().getPlayer(myTeam,item.playerID).alias;
                client.getEventBus().post(new ReceiveItemEvent(item, i+1));
            }

            this.index = receivedItems.size();
        }
        else {
            if(APWebSocket != null) {
                APWebSocket.sendPacket(new SyncPacket());
                client.getLocationManager().resendAllCheckedLocations();
            }
        }
    }

    public void writeFromSave(ArrayList<NetworkItem> receivedItems, int index) {
        this.receivedItems = receivedItems;
        this.index = index;
    }

    public void setAPWebSocket(APWebSocket APWebSocket) {
        this.APWebSocket = APWebSocket;
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<NetworkItem> getReceivedItems() {
        return receivedItems;
    }

    public ArrayList<Long> getReceivedItemIDs() {
        ArrayList<Long> ids = new ArrayList<>();
        for (NetworkItem receivedItem : receivedItems) {
            ids.add(receivedItem.itemID);
        }
        return ids;
    }
}
