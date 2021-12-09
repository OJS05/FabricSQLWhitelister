# FabricSQLWhitelister
This is a mod intended to override the whitelist.json by instead using a MySQL database.

## How to use

### You will need:
1. Fabric API
2. The latest version of FabricSQLWhitelister
3. Fabric Loader version ``>=0.12.8`` (I may backport in the future when I have time to add LTS for 1.16.5 & 1.17.1)
4. Minecraft 1.18.*

### Instructions
1. Boot the server up with the mod installed. This may result in a crash, but that is expected.
2. Navigate to the ``whitelist.toml`` that was generated, and add in your MySQL database logins.
3. In ``server.properties``, set ``white-list=`` to true. 
4. You should be good to go! Reboot your server, and if all is well it'll start successfully.
5. Add and remove players via the ``/whitelist add <player>`` and ``/whitelist remove <player>`` commands.

## Notes
- At present, this overrides the ability for opped players to bypass the whitelist, although I may change this in a future patch.
- I have only tested this with a MariaDB server, but should hopefully work on any other conventional MySQL server.
- If you would like to write your own application to work in conjuction with this mod, it should be known that UUIDs are stored dehyphenated. This is because Mojang's REST api doesn't store the hyphenated player UUIDs, which make it hard to develop other apps to allow integration.
