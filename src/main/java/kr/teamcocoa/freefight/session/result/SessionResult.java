package kr.teamcocoa.freefight.session.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionResult {

    /*
        0 = 무승부
        1 = 1번째 플레이어가 승리
        2 = 2번째 플레이어가 승리
     */
    private int winner;

    private ResultPlayer resultPlayer1;
    private ResultPlayer resultPlayer2;

}
