package gg.archipelago;

import com.google.gson.Gson;
import dev.koifysh.archipelago.flags.ItemsHandling;
import gg.archipelago.apEvents.ConnectionResult;
import gg.archipelago.apEvents.PrintJson;
import gg.archipelago.apEvents.ReceiveItem;
import dev.koifysh.archipelago.Client;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import net.runelite.client.eventbus.EventBus;

import java.net.URISyntaxException;

@Slf4j
public class APClient extends Client {

    private ArchipelagoPlugin plugin;

    public APClient(ArchipelagoPlugin plugin, Gson gson, EventBus bus){
        super(RuneLite.RUNELITE_DIR + "/APData/DataPackage.ser", gson, bus);
        this.setGame("Old School Runescape");
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

        getEventBus().register(connectionListener);
        getEventBus().register(itemListener);
        getEventBus().register(printListener);

        try {
            connect(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
        getEventBus().unregister(connectionListener);
        getEventBus().unregister(itemListener);
        getEventBus().unregister(printListener);

    }
}
