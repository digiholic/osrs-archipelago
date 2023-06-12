package com.archipelago;

import gg.archipelago.APClient.APClient;
import gg.archipelago.APClient.Print.APPrint;
import gg.archipelago.APClient.events.ConnectionAttemptEvent;
import gg.archipelago.APClient.events.ConnectionResultEvent;
import gg.archipelago.APClient.network.BouncedPacket;
import gg.archipelago.APClient.parts.NetworkItem;

import java.util.ArrayList;

public class OSRSClient extends APClient {
    public OSRSClient(String saveID, int slotID) {
        super(saveID, slotID);
    }

    @Override
    public void onConnectResult(ConnectionResultEvent connectionResultEvent) {

    }

    @Override
    public void onJoinRoom() {

    }

    @Override
    public void onPrint(String s) {

    }

    @Override
    public void onPrintJson(APPrint apPrint, String s, int i, NetworkItem networkItem) {

    }

    @Override
    public void onBounced(BouncedPacket bouncedPacket) {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onClose(String s, int i) {

    }

    @Override
    public void onReceiveItem(NetworkItem networkItem) {

    }

    @Override
    public void onLocationInfo(ArrayList<NetworkItem> arrayList) {

    }

    @Override
    public void onLocationChecked(long l) {

    }

    @Override
    public void onAttemptConnection(ConnectionAttemptEvent connectionAttemptEvent) {

    }
}
