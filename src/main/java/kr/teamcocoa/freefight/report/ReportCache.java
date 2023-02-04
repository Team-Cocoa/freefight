package kr.teamcocoa.freefight.report;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportCache {

    private static ConcurrentHashMap<Integer, CachedReport> cache = new ConcurrentHashMap<>();

    public static CachedReport getReport(int id) {
        return cache.getOrDefault(id, null);
    }

    public static Collection<CachedReport> getAllReports() {
        return cache.values();
    }

    public static void addReport(CachedReport cachedReport) {
        cache.put(cachedReport.getSessionId(), cachedReport);
    }

}
