package gg.archipelago;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("Archipelago")
public interface ArchipelagoConfig extends Config
{
	@ConfigItem(
		keyName = "address",
		name = "Server Address",
		description = "The URL of the server to connect to",
			position = 0
	)
	default String address()
	{
		return "localhost";
	}

	@ConfigItem(
			keyName = "port",
			name = "Server Port",
			description = "The Port of the server to connect to",
			position = 1
	)
	default String port()
	{
		return "38281";
	}

	@ConfigItem(
			keyName = "slotname",
			name = "Slot Name",
			description = "The Player slot to connect to",
			position = 2
	)
	default String slotname()
	{
		return "";
	}

	@ConfigItem(
			keyName = "password",
			name = "Server Password",
			description = "The Password of the server to connect to",
			secret = true,
			position = 3
	)
	default String password()
	{
		return "";
	}

	@ConfigItem(
			keyName = "apMessages",
			name = "Display AP Messages in Chat",
			description = "Whether or not to display messages, such as sending and receiving items, in chat.",
			position = 5
	)
	default boolean apMessages() { return true; }
}
