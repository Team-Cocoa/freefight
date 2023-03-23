package kr.teamcocoa.freefight.session.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResultPlayer {

    private UUID uuid;
    private String name;
    private double health;
    private double hunger;
    private double saturation;
    private double damageInComing;
    private double damageOutComing;

}
