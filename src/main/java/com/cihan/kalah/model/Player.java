package com.cihan.kalah.model;

import lombok.Getter;

@Getter
public class Player {
    private PlayerId playerId;
    private String name;

    public Player(String name, PlayerId type) {
        this.name = name;
        this.playerId = type;
    }
}
