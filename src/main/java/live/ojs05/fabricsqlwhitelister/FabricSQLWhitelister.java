package live.ojs05.fabricsqlwhitelister;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class FabricSQLWhitelister implements ModInitializer {
    @Override
    public void onInitialize() {
        DatabaseManager.setupDatabase();

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> DatabaseManager.updateTimeSince(handler.player.getUuid()));
    }
}
