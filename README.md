# Archipelago Randomizer
This plugin is meant to interface with [Archipelago.gg](https://archipelago.gg/) to allow you to connect to a
Multi-World Randomizer in OSRS. The plugin will provide you with tasks and milestones to complete that will unlock
items for anyone in the randomizer session, and you or other players will unlock map chunks or gear upgrades for you.

Your goal is to complete Dragon Slayer I. This game is meant to be played on a fresh free to play Ironman account,
although you may optionally use a members (limited to f2p areas) or a non-ironman account and provide gear upgrades as they are unlocked for a faster run.

This game mode can be played solo, in a group of other OSRS players, or in a group containing any of the dozens of
other games supported by Archipelago. It is encouraged, although in no way required, to play with other OSRS seeds in the same randomizer
in a group ironman. Note that, as short as it may seem to simply rush Dragon Slayer, OSRS f2p is still longer than every other
Archipelago game by a large margin, and as such is unsuitable for real-time synchronous runs.

## Setup: Generating a Randomized Game
This section will only need to be done by one player (the Host). This player will need to install [Archipelago](https://github.com/ArchipelagoMW/Archipelago/releases/).
OSRS does not have any additional requirements beyond the Generator and the Server (if you plan on self-hosting), but any other games
in the Multiworld might have installation requirements. Please see the installation instructions for other games
on the [Archipelago Website](https://archipelago.gg/).

To generate a Randomized game, gather the Options YAMLs from the players who will be participating (see "Creating an Options YAML" below)
into the `Players` directory in your Archipelago directory. Once they are in the directory, run the Archipelago Launcher and select "Generate". It will read the YAML settings from the `Players` directory and generate an output zip in the `output` folder.

**NOTE**: For more information about settings like hint costs, release/collect settings, see the [Archipelago Multiworld Setup Guide](https://archipelago.gg/tutorial/Archipelago/setup/en)

The output folder will be a `.zip` file containing your server file, spoiler logs (if enabled), and any patches or mod files
needed by other games that might be in your Archipelago (OSRS does not generate any artifacts and requires only the `.archipelago` server file)

If you want to host the server locally, simply extract the archive and run the `.archipelago` file to launch the Archipelago Server.
If you are playing solo, you can connect to it by using the address `localhost` in the plugin (See "Connecting to a game" below)

You can also use the Archipelago website can host a generated game. Simply go to the [Host an External Game](https://archipelago.gg/uploads)
page and uplaod your `.zip` file to create a room with your randomized game.

Once the server is started, see the next section for how to connect to a running Multiworld server!

## Setup: Connecting to a Multiworld Server
First, it goes without saying, but install this plugin through the Runelite plugin hub.
Open the Config settings for this plugin, and you will see the following options:
- **Server Address**: the URL of the server. If this is hosted locally, it will be `localhost`. If it is hosted on the Archipelago webite, it will likely be `archipelago.gg`. Otherwise, it will be the IP address of the host.
- **Server Port**: The port the server is listening for connections on. By default, this is port 38281 when self-hosting, and will be provided by the website when hosting online
- **Slot Name**: The name of the Player Slot (defined in your YAML, see below) to connect to
- **Server Password**: Optional. If your server requires a password to connect to, enter it here.
- **Display AP Messages in Chat**: Whether to display Archipelago messages, such as connect/disconnect messages and Archipelago chat, in the OSRS Chatbox

If you want to automatically track your unlocked chunks, installing the [Region Locker](https://github.com/slaytostay/region-locker) plugin is recommended.
This plugin will only adjust the chunks that are unlocked, all other options in the Region Locker plugin are maintained.

**Note:** This plugin will overwrite your unlocked chunk settings on the Region Locker plugin. Back up your chunks if you wish to
retain them after finishing your Randomizer run!

On the side panel of Runelite, you will see the Archipelago Panel. Until you are connected, you should see only a "Connect" button.
Clicking this button will attempt a connection to the server whose information you have entered in the config panel.
Once connected and logged in, you should see new panels listing your current Tasks, and below it your unlocked Items.

**Note**: Do **not** connect to the server on an account that has not been created for the Randomizer, or that character's stats and Quest Completion
might unlock checks in the Multiworld and send them to other players. This cannot be undone. After the first login, it is best to let
the auto-reconnect feature handle connecting to the server upon login of the correct character.

Once connected, Quests, Total Level, Combat Level, and Total XP tasks will be checked against your current
character status, but skill challenges must be completed while connected for the checks to count. This means that
playing without being connected to the server, such as through mobile, can only provide progress towards these tasks.
If you complete a task while not connected to the Archipelago server, you can click on the task next time you are connected
to manually mark it as complete.

## Generating an Options YAML
The Options YAML will include your player name and any special settings for your game.
There are many advanced options for working with the YAML file, such as weighted randomization and changing
the seed generation biases towards longer or shorter games. For a full rundown on these options, see [Advanced YAML Guide](https://archipelago.gg/tutorial/Archipelago/advanced_settings/en)
on the Archipelago website. This document will only cover the OSRS-specific options.

You can generate an Options YAML either on [The Website](https://archipelago.gg/games/Old%20School%20Runescape/player-options)
or by editing the file manually. A Template YAML can be found by running the Archipelago Launcher and selecting "Generate Template Options".
Once finished, it will open a window with template YAML files for every installed world, including Old School Runescape.

Provide your slot name under `name`. OSRS has the following Options:

- `starting_area` - Which chunks are available at the start. You may need to move through locked chunks to reach the starting
  area, but any areas that require quests, skills, or coins are not available as starting location.

  `any_bank` rolls a random region that contains a bank.
  `chunksanity` can start you in any chunk. Hope you like woodcutting!
- `brutal_grinds` - Whether to allow skill tasks without having reasonable access to the usual skill training path.
  For example, if enabled, you could be forced to train smithing without an anvil purely by smelting bars,
  or training fishing to high levels entirely on shrimp.
- `enable_duds` and `dud_count` - Whether to include "Duds", rewards that don't actually provide any benefit. Just get the drop!
- `enable_carepacks` - If enabled, "Care Pack" items will be added to the pool, which can be traded over from another account.
**Note:** This is all done manually, the plugin cannot provide the items for you. This option cannot be used by ironmen, and will require
another account to trade the items over.
- Additionally, there are `max_level`, `max_tasks`, and `task_weight` options for every skill, to customize
which tasks can be generated. Useful if you want to limit how much Runecrafting you'll need to do.


Edit the options to your liking by providing any weighted value for each option. When the seed is rolled, it will pick
a random option out of these choices with a given weight. The example above will start you in Lumbridge with Brutal Grinds disabled.
As an example, if you were to change `falador` and `draynor` to 1 as well, you would have a 1/3 chance of starting in
any of those chunks. Save the file with the extension `.yaml` and provide it to your Host player (see "Generating A Randomized Game" above)

## Support, Bug Reporting, and Group Finding
Support for the OSRS randomizer can be found on the [Archipelago Discord](https://discord.gg/8Z65BR2), 
under `#old-school-runescape`.
