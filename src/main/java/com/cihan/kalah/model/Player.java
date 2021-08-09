package com.cihan.kalah.model;
import lombok.Data;

@Data
public class Player {
    private PlayerId playerId;
    private String name;

    public Player(String name, PlayerId type) {
        this.name = name;
        this.playerId = type;
    }

}
