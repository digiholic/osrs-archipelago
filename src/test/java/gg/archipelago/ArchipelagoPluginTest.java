package gg.archipelago;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ArchipelagoPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ArchipelagoPlugin.class);
		RuneLite.main(args);
	}
}