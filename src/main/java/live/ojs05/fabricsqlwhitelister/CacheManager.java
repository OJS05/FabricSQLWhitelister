package live.ojs05.fabricsqlwhitelister;

import java.util.HashMap;
import java.util.UUID;

public class CacheManager {

    private static HashMap<UUID, String> cache = new HashMap<>();

    public static void add(UUID uuid, String username) {
        cache.put(uuid, username);
    }

    public static void remove(UUID uuid) {
        cache.remove(uuid);
    }

    public static HashMap<UUID, String> getCache(){
        return cache;
    }

    public static boolean checkIfCached(UUID uuid){
        return cache.containsKey(uuid);
    }

}
