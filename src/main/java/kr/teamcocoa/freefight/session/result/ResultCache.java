package kr.teamcocoa.freefight.session.result;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultCache {

    @Getter
    private static HashMap<Integer, SessionResult> resultCache = new HashMap<>();

}
