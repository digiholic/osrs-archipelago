package gg.archipelago;

import gg.archipelago.apEvents.ConnectionResult;
import gg.archipelago.apEvents.LocationInfo;
import gg.archipelago.apEvents.ReceiveItem;
import gg.archipelago.client.ArchipelagoClient;
import gg.archipelago.client.ItemFlags;
import gg.archipelago.client.Print.APPrint;
import gg.archipelago.client.Print.APPrintPart;
import gg.archipelago.client.parts.NetworkItem;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;

@Slf4j
public class OSRSClient  extends ArchipelagoClient {

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
        getEventManager().registerListener(new LocationInfo());
        getEventManager().registerListener(new ReceiveItem());
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
    public void onPrint(String s) {

    }

    @Override
    public void onPrintJson(APPrint apPrint, String s, int i, NetworkItem networkItem) {
        if ("Join".equals(apPrint.type) || "Tutorial".equals(apPrint.type)) return;
        StringBuilder msgBuilder = new StringBuilder();
        for (APPrintPart part : apPrint.parts){
            msgBuilder.append(part.text);
        }
        plugin.DisplayChatMessage(msgBuilder.toString());
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
