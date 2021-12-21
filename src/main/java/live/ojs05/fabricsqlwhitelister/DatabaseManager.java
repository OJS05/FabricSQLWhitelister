package live.ojs05.fabricsqlwhitelister;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import pro.husk.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {

    private static MySQL mySQL;

    public static boolean checkIfWhitelisted(GameProfile profile){
        UUID uuid = profile.getId();
        if(CacheManager.checkIfCached(uuid)){
            return true;
        }else{
            ResultSet resultSet = null;
            try {
                resultSet = mySQL.query("SELECT * FROM whitelist WHERE uuid = '" + uuid.toString().replace("-","") + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(resultSet.next()){
                    CacheManager.add(uuid, profile.getName());
                }
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void setupDatabase(){
        try {
            String createTable = "CREATE TABLE IF NOT EXISTS `whitelist` ( `username` TEXT NULL DEFAULT NULL , `uuid` TEXT NULL DEFAULT NULL , `timesince` TIMESTAMP NULL DEFAULT NULL ) ENGINE = InnoDB;";
            mySQL = new MySQL(ConfigManager.DATABASE_URL, ConfigManager.USERNAME, ConfigManager.PASSWORD);

            mySQL.updateAsync(createTable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateTimeSince(UUID uuid){
        mySQL.updateAsync("UPDATE whitelist SET timesince = CURRENT_TIMESTAMP WHERE uuid = '" + uuid.toString().replace("-","") + "'");
    }

    public static void addPlayer(GameProfile profile){
        mySQL.updateAsync("INSERT IGNORE INTO whitelist (username, uuid) VALUES ('" + profile.getName().toLowerCase() + "', '" + profile.getId().toString().replace("-", "") + "')");
    }

    public static void removePlayer(GameProfile profile){
        CacheManager.remove(profile.getId());
        mySQL.updateAsync("DELETE FROM whitelist WHERE uuid = '" + profile.getId().toString().replace("-","") + "'");
    }

    public static void listPlayers(ServerCommandSource source){
        ResultSet resultSet = null;
        List<String> players = new ArrayList<>();
        try {
            resultSet = mySQL.query("SELECT * FROM whitelist");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(resultSet.next()){
                players.add(resultSet.getString("username"));
            }
            resultSet.close();
            source.sendFeedback(new LiteralText("Whitelisted players: " + players), false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
