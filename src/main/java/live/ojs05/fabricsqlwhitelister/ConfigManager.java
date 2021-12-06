package live.ojs05.fabricsqlwhitelister;

import de.leonhard.storage.Toml;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;

public class ConfigManager {

    public static final String DATABASE_URL = "jdbc:mysql://" + loadConfig().get("MYSQL_HOSTNAME") + ":" + loadConfig().get("MYSQL_PORT") + "/" + loadConfig().get("MYSQL_DATABASE");
    public static final String USERNAME = loadConfig().get("MYSQL_USERNAME").toString();
    public static final String PASSWORD = loadConfig().get("MYSQL_PASSWORD").toString();

    public static Toml loadConfig(){
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "whitelistsql.toml");
        Toml config;

        if(configFile.exists()){
            config = new Toml(configFile);
        }else{
            config = new Toml("whitelistsql.toml", FabricLoader.getInstance().getConfigDir().toString());
            config.setDefault("MYSQL_USERNAME", "user");
            config.setDefault("MYSQL_PASSWORD", "password");
            config.setDefault("MYSQL_HOSTNAME", "hostname");
            config.setDefault("MYSQL_DATABASE", "database");
            config.setDefault("MYSQL_PORT", 3306);
        }

        return config;

    }
}
