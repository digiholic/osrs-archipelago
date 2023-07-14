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
```
NOTE: At this time, OSRS Archipelago is in beta, and is not available on the website. Installation will require some additional 
downloads to get the in-development beta builds. The following steps are only
necessary until such a time as the testing period is over, at which point, these instructions will be updated on how to
generate and host through the Archipelago Website.
```

This section will only need to be done by one player (the Host). This player will need to install [Archipelago](https://github.com/ArchipelagoMW/Archipelago/releases/).
OSRS does not have any additional requirements beyond the Generator and the Server (if you plan on self-hosting), but any other games
in the Multiworld might have installation requirements. Please see the installation instructions for other games
on the [Archipelago Website](https://archipelago.gg/).

To Generate a game, open the Archipelago directory (default location `C:\ProgramData\Archipelago`) and create a directory named
`worlds` if it does not already exist. Download the [OSRS APWorld file]() from the beta branch of Archipelago, and place it in the `worlds` directory.
This will allow you to generate randomized games including OSRS.

To generate a Randomized game, gather the Settings YAMLs from the players who will be participating (see "Creating a Settings YAML" below)
into the `Players` directory in your Archipelago directory. From there, run `ArchipelagoGenerate.exe` from the Archipelago
directory. It will read the YAML settings from the `Players` directory and generate an output zip in the `output` folder.

**NOTE**: For more information about settings like hint costs, release/collect settings, see the [Archipelago Multiworld Setup Guide](https://archipelago.gg/tutorial/Archipelago/setup/en)

The output folder will be a `.zip` file containing your server file, spoiler logs (if enabled), and any patches or mod files
needed by other games that might be in your Archipelago (OSRS does not generate any artifacts and requires only the `.archipelago` server file)

If you want to host the server locally, simply extract the archive and run the `.archipelago` file to launch `ArchipelagoServer.exe`.
If you are playing solo, you can connect to it by using the address `localhost` in the plugin (See "Connecting to a game" below)

Even before OSRS is officially supported, the Archipelago website can host a generated game. Simply go to the [Host an External Game](https://archipelago.gg/uploads)
page and uplaod your `.zip` file to create a room with your randomized game.

Once the server is started, see the next section for how to connect to a running Multiworld server!

## Setup: Connecting to a Multiworld Server
First, it goes without saying, but install this plugin through the Runelite plugin hub.
Open the Config settings for this plugin, and you will see the following options:
- **Server Address**: the URL of the server. If this is hosted locally, it will be `localhost`. If it is hosted on the Archipelago webite, it will likely be `archipelago.gg`. Otherwise, it will be the IP address of the host.
- **Server Port**: The port the server is listening for connections on. By default, this is port 38281 when self-hosting, and will be provided by the website when hosting online
- **Slot Name**: The name of the Player Slot (defined in your YAML, see below) to connect to
- **Server Password**: Optional. If your server requires a password to connect to, enter it here.
- **Auto-Reconnect On Login For**: Whenever you log in with a character with this name, the plugin will automatically
  attempt to connect to the server. If this field is blank, it will be populated with the first character name that is logged in while connected.

If you want to automatically track your unlocked chunks, installing the [Region Locker]() plugin is recommended.
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

## Generating a Settings YAML
The Settings YAML will include your player name and any special settings for your game.
There are many advanced options for working with the YAML file, such as weighted randomization and changing
the seed generation biases towards longer or shorter games. For a full rundown on these options, see [Advanced YAML Guide](https://archipelago.gg/tutorial/Archipelago/advanced_settings/en)
on the Archipelago website. This document will only cover the OSRS-specific options.

This is a basic OSRS YAML template:

```yaml
name: Player{number}
description: Default OSRS Template

game: Old School Runescape
requires:
  version: 0.4.1 # Version of Archipelago required for this yaml to work as expected.

Old School Runescape:
  starting_area: 
    lumbridge: 1
    al_kharid: 0
    varrock_east: 0
    varrock_west: 0
    edgeville: 0
    falador: 0
    draynor: 0
    wilderness: 0
    any_bank: 0
    chunksanity: 0
  
  brutal_grinds: 
    false: 1
    true: 0
```

Provide your slot name under `name`. OSRS has the following Options:

- `starting_area` - Which chunks are available at the start. You may need to move through locked chunks to reach the starting
  area, but any areas that require quests, skills, or coins are not available as starting location.

  `any_bank` rolls a random region that contains a bank.
  `chunksanity` can start you in any chunk. Hope you like woodcutting!
- `brutal_grinds` - Whether to allow skill tasks without having reasonable access to the usual skill training path.
  For example, if enabled, you could be forced to train smithing without an anvil purely by smelting bars,
  or training fishing to high levels entirely on shrimp.

Edit the options to your liking by providing any weighted value for each option. When the seed is rolled, it will pick
a random option out of these choices with a given weight. The example above will start you in Lumbridge with Brutal Grinds disabled.
As an example, if you were to change `falador` and `draynor` to 1 as well, you would have a 1/3 chance of starting in
any of those chunks. Save the file with the extension `.yaml` and provide it to your Host player (see "Generating A Randomized Game" above)

## Support, Bug Reporting, and Group Finding
Support for the OSRS randomizer can be found on the [Archipelago Discord](https://discord.gg/8Z65BR2), 
under `#future-game-design`, in the Old School Runescape thread.

As this plugin and Randomizer are in beta, feedback is appreciated. It is possible a seed might be unbeatable,
in such cases, please hang on to your server files (particularly the spoiler log), it would be a great help to diagnose
any logic issues that might be present.