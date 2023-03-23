package kr.teamcocoa.freefight.session.result;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PotResultPlayer extends ResultPlayer {

    private int leftPot;

    public PotResultPlayer(
            UUID uuid,
            String name,
            double health,
            double hunger,
            double saturation,
            double damageInComing,
            double damageOutComing,
            int leftPot) {
        super(uuid, name, health, hunger, saturation, damageInComing, damageOutComing);
        this.leftPot = leftPot;
    }



}
