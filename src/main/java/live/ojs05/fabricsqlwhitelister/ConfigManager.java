package live.ojs05.fabricsqlwhitelister;

import de.leonhard.storage.Toml;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class ConfigManager {


    public static void loadConfig(){
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "whitelistsql.toml");

        if(configFile.exists()){
            Toml config = new Toml(configFile);

        }else{
            Toml config = new Toml("whitelistsql.toml", FabricLoader.getInstance().getConfigDir().toString());
            config.setDefault("MYSQL_USERNAME", "user");
            config.setDefault("MYSQL_HOSTNAME", "hostname");
            config.setDefault("MYSQL_DATABASE", "database");
            config.setDefault("MYSQL_PORT", 3306);
        }
    }
}
