package kr.teamcocoa.freefight.storages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import kr.teamcocoa.core.network.cache.Cache;
import kr.teamcocoa.freefight.session.SessionResult;

public class SessionResultStorage {
    
    private static Cache<Integer, SessionResult> idToResult = new Cache<>(1, TimeUnit.DAYS);

    // priorityqueue 사용 이유 :
    // 들어갈때 마다 정렬이 되어야 하는데
    // heap 자료구조 성질을 띄는 컬렉션을 써야함
    // 그게 priorityqueue 임 근데 값들 중복 되면 안됨
    // 그건 수동 처리 해야함
    private static Cache<UUID, PriorityQueue<Integer>> uuidToIds = new Cache<>(1, TimeUnit.DAYS);

    public static void register(SessionResult result) {
        int id = result.getId();

        if(idToResult.readData(id) != null) {
            return;
        }

        UUID uuid1 = result.getResultPlayer1().getUuid();
        UUID uuid2 = result.getResultPlayer2().getUuid();

        idToResult.createData(id, result);

        PriorityQueue<Integer> uuid1List = uuidToIds.readData(uuid1);
        PriorityQueue<Integer> uuid2List = uuidToIds.readData(uuid2);

        if(uuid1List == null) {
            uuid1List = new PriorityQueue<>();
            uuidToIds.createData(uuid1, uuid1List);
        }

        if(uuid2List == null) {
            uuid2List = new PriorityQueue<>();
            uuidToIds.createData(uuid2, uuid2List);
        }

        uuid1List.add(id);
        uuid2List.add(id);
        
    }

    public static List<SessionResult> getUserPlayedSessionBy10(UUID uuid) {
        PriorityQueue<Integer> idsUserPlayed = uuidToIds.readData(uuid);

        if(idsUserPlayed == null) {
            return Collections.emptyList();
        }

        Integer[] ids = (Integer[]) idsUserPlayed.toArray();

        List<SessionResult> list = new ArrayList<>(
            ids.length < 10 ? ids.length : 10);

        for (Integer id : ids) {
            list.add(idToResult.readData(id));
        }

        return list;
    }

    public static SessionResult getResultById(int id) {
        return idToResult.readData(id);
    }

}
