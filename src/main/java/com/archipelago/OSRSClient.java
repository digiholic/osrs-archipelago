package com.archipelago;

import com.archipelago.apEvents.ConnectionResult;
import com.archipelago.apEvents.LocationInfo;
import com.archipelago.apEvents.ReceiveItem;
import gg.archipelago.client.ArchipelagoClient;
import gg.archipelago.client.ItemFlags;
import gg.archipelago.client.Print.APPrint;
import gg.archipelago.client.Print.APPrintPart;
import gg.archipelago.client.parts.NetworkItem;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;

@Slf4j
public class OSRSClient  extends ArchipelagoClient {

    //public static final Logger logger = LogManager.getLogger(OSRSClient.class.getName());

    private ArchipelagoPlugin plugin;
    public static OSRSClient apClient;

    public static boolean Connected(){
        if (apClient == null) return false;
        return apClient.isConnected();
    }

    public static void newConnection(ArchipelagoPlugin plugin, String address, String slotName, String password) {
        if (apClient != null) {
            apClient.close();
        }

        apClient = new OSRSClient();
        apClient.plugin = plugin;

        apClient.setPassword(password);
        apClient.setName(slotName);
        apClient.setItemsHandlingFlags(ItemFlags.SEND_ITEMS + ItemFlags.SEND_OWN_ITEMS + ItemFlags.SEND_STARTING_INVENTORY);

        apClient.getEventManager().registerListener(new ConnectionResult());
        apClient.getEventManager().registerListener(new LocationInfo());
        apClient.getEventManager().registerListener(new ReceiveItem());
        try {
            apClient.connect(address);
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

        ArchipelagoPanel.apPanel.statusText.setText("Server Error NL " + e.getMessage());
    }

    @Override
    public void onClose(String message, int i) {
        ArchipelagoPanel.apPanel.statusText.setText("Connection Closed NL " + message);
    }
}
