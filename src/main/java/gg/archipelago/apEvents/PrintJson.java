package gg.archipelago.apEvents;

import dev.koifysh.archipelago.Print.APPrint;
import dev.koifysh.archipelago.Print.APPrintPart;
import dev.koifysh.archipelago.events.ArchipelagoEventListener;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import dev.koifysh.archipelago.parts.NetworkItem;
import gg.archipelago.ArchipelagoPlugin;

public class PrintJson {

    @ArchipelagoEventListener
    public void onPrintJson(PrintJSONEvent event) {
        if ("Join".equals(event.apPrint.type) || "Tutorial".equals(event.apPrint.type)) return;
        StringBuilder msgBuilder = new StringBuilder();
        for (APPrintPart part : event.apPrint.parts){
            msgBuilder.append(part.text);
        }
        ArchipelagoPlugin.plugin.DisplayChatMessage(msgBuilder.toString());
    }
}
