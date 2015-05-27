/*
 Copyright (c) 2015. Justin White CC-BY-SA
 */
package net.justinwhite.scoreit;

import java.util.UUID;

class Game {
    private final UUID id;
    private final String name;
    private final Integer numPlayers;

    public Game(String name, Integer numPlayers) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numPlayers = numPlayers;
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\n",
                             name,
                             id.toString(),
                             numPlayers
        );
    }
}
