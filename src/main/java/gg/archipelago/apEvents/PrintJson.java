package gg.archipelago.apEvents;

import dev.koifysh.archipelago.Print.APPrintPart;
import dev.koifysh.archipelago.events.PrintJSONEvent;
import gg.archipelago.ArchipelagoPlugin;
import net.runelite.client.eventbus.Subscribe;

public class PrintJson {

    @Subscribe
    public void onPrintJSONEvent(PrintJSONEvent event) {
        if ("Join".equals(event.apPrint.type.toString()) || "Tutorial".equals(event.apPrint.type.toString()))
            return;
        StringBuilder msgBuilder = new StringBuilder();
        for (APPrintPart part : event.apPrint.parts){
            msgBuilder.append(part.text);
        }
        String msg = msgBuilder.toString().replace("~|","").replace("|~","");
        // OSRS Chat messages have a maximum of 200 characters. With our prefix character and having a bit of wiggle room,
        // Let's call our practical limit 180 characters.

        // First, we split the message on spaces, and add each individual word until one of them would put us over 180,
        // and if it would, send the message as is and start it over again.
        String[] splitmsg = msg.split(" ");
        StringBuilder resultmsg = new StringBuilder();
        for (String msgPart : splitmsg){
            if (resultmsg.length() + msgPart.length() > 180){
                ArchipelagoPlugin.plugin.DisplayChatMessage(resultmsg.toString());
                resultmsg = new StringBuilder("      ");
            }
            resultmsg.append(msgPart);
            resultmsg.append(" ");
        }
        ArchipelagoPlugin.plugin.DisplayChatMessage(resultmsg.toString());
    }
}
