package gg.archipelago;

import dev.koifysh.archipelago.flags.ItemsHandling;
import gg.archipelago.apEvents.ConnectionResult;
import gg.archipelago.apEvents.PrintJson;
import gg.archipelago.apEvents.ReceiveItem;
import dev.koifysh.archipelago.Client;

import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;

@Slf4j
public class APClient extends Client {

    private ArchipelagoPlugin plugin;

    public APClient(ArchipelagoPlugin plugin){
        this.plugin = plugin;
    }

    // These variables are stored so we can unsubscribe on disconnect
    private Object connectionListener, itemListener, printListener;
    public void newConnection(ArchipelagoPlugin plugin, String address, String slotName, String password) {
        setGame("Old School Runescape");
        setPassword(password);
        setName(slotName);
        setItemsHandlingFlags(ItemsHandling.SEND_ITEMS + ItemsHandling.SEND_OWN_ITEMS + ItemsHandling.SEND_STARTING_INVENTORY);


        connectionListener = new ConnectionResult();
        itemListener = new ReceiveItem();
        printListener = new PrintJson();

        getEventManager().registerListener(connectionListener);
        getEventManager().registerListener(itemListener);
        getEventManager().registerListener(printListener);

        try {
            connect(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private APClient() {
        super();
        this.setGame("Old School Runescape");
    }

    @Override
    public void onError(Exception e) {
        ArchipelagoPlugin.plugin.DisplayNetworkMessage("Server Error: " + e.getMessage());
    }

    @Override
    public void onClose(String message, int i) {
        //ArchipelagoPanel.apPanel.statusText.setText("Connection Closed NL " + message);
    }

    @Override
    public void disconnect(){
        super.disconnect();
        plugin.SetConnectionState(false);
        getEventManager().unRegisterListener(connectionListener);
        getEventManager().registerListener(itemListener);
        getEventManager().registerListener(printListener);

    }
}
