package com.archipelago;

import com.archipelago.apEvents.ConnectionResult;
import com.archipelago.apEvents.LocationInfo;
import com.archipelago.apEvents.ReceiveItem;
import gg.archipelago.client.ArchipelagoClient;
import gg.archipelago.client.ItemFlags;
import gg.archipelago.client.Print.APPrint;
import gg.archipelago.client.parts.NetworkItem;

import java.net.URISyntaxException;

public class OSRSClient  extends ArchipelagoClient {

    //public static final Logger logger = LogManager.getLogger(OSRSClient.class.getName());

    public static OSRSClient apClient;

    public static void newConnection(String address, String slotName, String password) {
        if (apClient != null) {
            apClient.close();
        }
        apClient = new OSRSClient();
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
