package com.archipelago;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Archipelago")
public interface ArchipelagoConfig extends Config
{
	@ConfigItem(
		keyName = "address",
		name = "Server Address",
		description = "The URL of the server to connect to"
	)
	default String address()
	{
		return "localhost";
	}

	@ConfigItem(
			keyName = "port",
			name = "Server Port",
			description = "The Port of the server to connect to"
	)
	default String port()
	{
		return "38281";
	}

	@ConfigItem(
			keyName = "slotname",
			name = "Slot Name",
			description = "The Player slot to connect to"
	)
	default String slotname()
	{
		return "";
	}

	@ConfigItem(
			keyName = "password",
			name = "Server Password",
			description = "The Password of the server to connect to"
	)
	default String password()
	{
		return "";
	}

	@ConfigItem(
			keyName = "autoreconnect",
			name = "Auto Reconnect On Login For",
			description = "Whenever a character with this name logs in, automatically attempt connecting to the server."
	)
	default String autoreconnect()
	{
		return "";
	}
}
