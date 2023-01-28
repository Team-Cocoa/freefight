package kr.teamcocoa.freefight.replay;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionReplayManager {

    private static ConcurrentHashMap<String, SessionReplay> map = new ConcurrentHashMap<>();

    public static SessionReplay getReplay(String id) {
        return map.getOrDefault(id, null);
    }

    public static void register(String id, SessionReplay replay) {
        if(map.containsKey(id)) {
            return;
        }
        map.put(id, replay);
    }

    public static Collection<SessionReplay> getAllReplays() {
        return map.values();
    }

    public static void unregister(String id) {
        if(!map.containsKey(id)) {
            return;
        }
        map.remove(id);
    }

}
