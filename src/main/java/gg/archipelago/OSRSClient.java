package gg.archipelago;

import gg.archipelago.apEvents.ConnectionResult;
import gg.archipelago.apEvents.PrintJson;
import gg.archipelago.apEvents.ReceiveItem;
import dev.koifysh.archipelago.Client;
import dev.koifysh.archipelago.ItemFlags;
import dev.koifysh.archipelago.Print.APPrint;
import dev.koifysh.archipelago.Print.APPrintPart;
import dev.koifysh.archipelago.parts.NetworkItem;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;

@Slf4j
public class OSRSClient  extends Client {

    private ArchipelagoPlugin plugin;

    public OSRSClient(ArchipelagoPlugin plugin){
        this.plugin = plugin;
    }

    public void newConnection(ArchipelagoPlugin plugin, String address, String slotName, String password) {
        setGame("Old School Runescape");
        setPassword(password);
        setName(slotName);
        setItemsHandlingFlags(ItemFlags.SEND_ITEMS + ItemFlags.SEND_OWN_ITEMS + ItemFlags.SEND_STARTING_INVENTORY);

        getEventManager().registerListener(new ConnectionResult());
        getEventManager().registerListener(new ReceiveItem());
        getEventManager().registerListener(new PrintJson());

        try {
            connect(address);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private OSRSClient() {
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
    }
}
