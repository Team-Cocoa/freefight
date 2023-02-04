package kr.teamcocoa.freefight.report;

import kr.teamcocoa.freefight.kits.Kits;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class CachedReport {

    private int id;
    private UUID reporter;
    private int sessionId;
    private int reportedTime;
    private Kits kits;

    @Setter
    private boolean handled;

    public CachedReport(int id, UUID reporter, int sessionId, int reportedTime, int kitInt, boolean handled) {
        this.id = id;
        this.reporter = reporter;
        this.sessionId = sessionId;
        this.reportedTime = reportedTime;
        this.kits = Kits.getKitByInt(kitInt);
        this.handled = handled;
    }

}
