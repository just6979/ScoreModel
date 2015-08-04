/*
 * Copyright (c) 2015 Justin White <jw@justinwhite.net>
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *  3. Neither the name of the copyright holder nor the names of its
 *  contributors may be used to endorse or promote products derived from this
 *  software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package net.justinwhite.score_model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Game<T extends Player> {
    public static final int MIN_PLAYERS;
    public static final int MAX_PLAYERS;

    static {
        MIN_PLAYERS = 2;
        MAX_PLAYERS = 8;
    }

    protected final UUID id;
    protected int numPlayers;
    private String name;

    protected List<T> players;
    protected Map<String, T> playerMap;
    protected T winner;


    public Game() {
        id = UUID.randomUUID();
        name = id.toString();
        numPlayers = 0;
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d",
                getName(),
                getID(),
                getNumPlayers()
        );
    }

    public UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void buildName() {
        String _name = "";
        for (T p : players) {
            _name += p.getInitials();
        }
        setName(_name);
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int _numPlayers) {
        numPlayers = _numPlayers;
    }

    public void incrementNumPlayers() {
        numPlayers++;
    }

    public abstract List<T> getPlayerList();

    public abstract Map<String, T> getPlayerMap();

    public abstract T getPlayer(int index);

    public abstract T getPlayerByName(String _name);

    public abstract Boolean checkPlayer(String _name);

    public abstract void addPlayer(int _index, String _name);

    public abstract void renamePlayer(String oldName, String newName);

    public abstract String getScores();

    public abstract Boolean hasWinner();

    public abstract T getWinner();
}
