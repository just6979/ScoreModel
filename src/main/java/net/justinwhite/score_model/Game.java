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

import java.text.SimpleDateFormat;
import java.util.*;

public class Game<T extends Player> {
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 8;

    private final UUID id;
    private final Class<T> playerClass;
    private final List<T> playerList;
    private int numPlayers;
    private String name;
    private T winner;
    private boolean hasName = false;

    public Game(Class<T> _class) {
        this(_class, MIN_PLAYERS, null);
    }

    public Game(Class<T> _class, int _numPlayers) {
        this(_class, _numPlayers, null);
    }

    public Game(Class<T> _class, int _numPlayers, String _name) {
        playerClass = _class;

        // bounds check number of players
        if (_numPlayers < MIN_PLAYERS) {
            _numPlayers = MIN_PLAYERS;
        } else if (_numPlayers > MAX_PLAYERS) {
            _numPlayers = MAX_PLAYERS;
        }

        id = UUID.randomUUID();
        playerList = new ArrayList<>();
        winner = null;

        setNumPlayers(_numPlayers);
        setName(_name);
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s",
                name,
                id,
                numPlayers,
                // List<> class handles toString()
                playerList
        );
    }

    public String getName() {
        return name;
    }

    // set the game name. If passed empty string or null, use buildName() to create name based current DateTime
    public void setName(String _name) {
        if (_name == null || _name.equals("")) {
            hasName = false;
            buildName();
        } else {
            name = _name;
            hasName = true;
        }
    }

    public UUID getID() {
        return id;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<T> getPlayerList() {
        return playerList;
    }

    // If increasing count, create a new blank player; if decreasing, delete last player
    public boolean setNumPlayers(int newNumPlayers) {
        // make sure requested count is within limits
        if (newNumPlayers < MIN_PLAYERS || newNumPlayers > MAX_PLAYERS) {
            return false;
        }
        // increasing count, add players
        if (newNumPlayers > numPlayers) {
            for (int i = numPlayers; i < newNumPlayers; i++) {
                addPlayer();
            }
        }
        // decreasing count, remove players
        if (numPlayers > newNumPlayers) {
            for (int i = numPlayers; i > newNumPlayers; i--) {
                removePlayer();
            }
        }
        // record new count
        numPlayers = playerList.size();
        return true;
    }

    // check if there is a player at the given index
    public Boolean checkPlayer(int index) {
        return index > 0 && index <= numPlayers && playerList.get(index) != null;
    }

    // make a new Player [or subclass of player] using newInstance() and set it up for use
    private T makePlayer(String _name) {
        T newPlayer;
        // to make a new Player [or subclass], use the class's instance factory
        try {
            newPlayer = playerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // if the factory can't make a new object, something major is wrong
            e.printStackTrace();
            return null;
        }
        // successful instantiation, continue setting up the Player [or subclass]
        newPlayer.setName(_name);
        return newPlayer;
    }

    // add a new player named "Player <X+1>"
    public T addPlayer() {
        return addPlayer("Player " + (numPlayers + 1));
    }

    // add a new player using _name
    public T addPlayer(String _name) {
        if (numPlayers < MAX_PLAYERS) {
            T newPlayer = makePlayer(_name);

            playerList.add(newPlayer);

            numPlayers = playerList.size();
            buildName();

            return newPlayer;
        } else {
            return null;
        }
    }

    // remove and return last player if no index specified
    public T removePlayer() {
        return removePlayer(numPlayers - 1);
    }

    // remove and return player specified by index. return null if index out of bounds
    public T removePlayer(int index) {
        if (numPlayers > MIN_PLAYERS) {
            T oldPlayer = playerList.remove(index);

            numPlayers = playerList.size();
            buildName();

            return oldPlayer;
        } else {
            return null;
        }
    }

    // return player specified by index or null if index out of bounds
    public T getPlayer(int index) {
        if (0 <= index && index < playerList.size()) {
            return playerList.get(index);
        } else {
            return null;
        }
    }

    // Create a game name based on the players' initials
    public void buildName() {
        if (!hasName) {
            String format = "YYYY-MM-dd HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            name = sdf.format(new Date());
        }
    }

    // return an array of raw scores
    public int[] getScores() {
        int[] scores = new int[playerList.size()];
        int i = 0;
        for (Player p : playerList) {
            scores[i++] = p.getScore();
        }
        return scores;
    }

    // return String of "Player1: Score1; Player2: Score2; etc"
    public String getScoresText() {
        String out = "";
        for (T p : playerList) {
            out += String.format("%s: %d Points; ", p.getName(), p.getScore());
        }
        return out;
    }

    // TODO: handle tie scores
    public boolean checkWinner() {
        winner = null;
        int highScore = 0;
        int curScore;
        for (T curPlayer : playerList) {
            curScore = curPlayer.getScore();
            if (curScore > highScore) {
                highScore = curScore;
                winner = curPlayer;
            }
        }
        return winner != null;
    }

    public T getWinner() {
        return winner;
    }

    protected void setWinner(T _winner) {
        winner = _winner;
    }
}
