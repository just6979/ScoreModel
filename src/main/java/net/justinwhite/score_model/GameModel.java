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

import java.util.*;

public class GameModel<T extends PlayerModel> {
    public static final int MIN_PLAYERS;
    public static final int MAX_PLAYERS;

    static {
        MIN_PLAYERS = 2;
        MAX_PLAYERS = 8;
    }

    private final UUID id;
    protected int numPlayers;
    protected String name;
    protected final List<T> playerList;
    protected final Map<String, T> playerMap;
    protected T winner = null;
    private final Class<T> curClass;

    public GameModel(Class<T> _class) {
        this(_class, MIN_PLAYERS);
    }

    public GameModel(Class<T> _class, int _numPlayers) {
        curClass = _class;

        // bounds check number of players
        if (_numPlayers < MIN_PLAYERS) {
            _numPlayers = MIN_PLAYERS;
        } else if (_numPlayers > MAX_PLAYERS) {
            _numPlayers = MAX_PLAYERS;
        }

        id = UUID.randomUUID();

        playerList = new ArrayList<T>();
        playerMap = new TreeMap<String, T>();

        setNumPlayers(_numPlayers);
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\nPlayerMap: %s",
                getName(),
                getID(),
                getNumPlayers(),
                // List<> and Map<> classes handle toString() themselves
                getPlayerList(),
                getPlayerMap()
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

    /*
        Create a game name based on the players' initials
    */
    public void buildName() {
        String _name = "";
        for (T p : playerList) {
            _name += p.getInitials();
        }
        setName(_name);
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    /*
         If increasing, create a blank player
         If decreasing, delete last player added
    */
    public void setNumPlayers(int _numPlayers) {
        if (_numPlayers > numPlayers) {
            for (int i = numPlayers; i < _numPlayers; i++) {
                addPlayer();
            }
        } else if (_numPlayers < numPlayers) {
            for (int i = _numPlayers; i < numPlayers; i++) {
                removePlayer();
            }
        }
        numPlayers = playerList.size();

    }

    public List<T> getPlayerList() {
        return playerList;
    }

    public Map<String, T> getPlayerMap() {
        return playerMap;
    }

    public T getPlayer(int index) {
        if (0 <= index && index < numPlayers) {
            return playerList.get(index);
        } else {
            return null;
        }
    }

    public T getPlayerByName(String _name) {
        return playerMap.get(_name);
    }

    public Boolean checkPlayer(String _name) {
        return playerMap.containsKey(_name);
    }

    protected T makePlayer(String _name) {
        T newPlayer = null;
        try {
            newPlayer = curClass.newInstance();
            newPlayer.setName(_name);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newPlayer;
    }

    public T addPlayer() {
        return addPlayer("Player " + (numPlayers + 1));
    }

    public T addPlayer(String _name) {
        if (numPlayers < MAX_PLAYERS) {
            T newPlayer = makePlayer(_name);

            playerList.add(newPlayer);
            playerMap.put(_name, newPlayer);

            numPlayers = playerList.size();
            buildName();

            return newPlayer;
        } else {
            return null;
        }
    }

    public T removePlayer() {
        return removePlayer(numPlayers - 1);
    }

    public T removePlayer(int index) {
        if (numPlayers > MIN_PLAYERS) {
            T oldPlayer = playerList.remove(index);

            playerMap.remove(oldPlayer.getName());

            numPlayers = playerList.size();
            buildName();

            return oldPlayer;
        } else {
            return null;
        }
    }

    public void renamePlayer(int index, String newName) {
        T p = playerList.get(index);
        if (p != null) {
            p.setName(newName);
            buildName();
        }
    }

    public void renamePlayer(String oldName, String newName) {
        T p = playerMap.remove(oldName);
        if (p != null) {
            p.setName(newName);
            playerMap.put(newName, p);
        }
        buildName();
    }

    public String getScores() {
        String out = "";
        for (T p : playerList) {
            out += String.format("%s: %d Points; ", p.getName(), p.getScore());
        }
        return out;
    }

    public void findWinner() {
        int highScore = 0;
        int curScore;
        for (T p : playerList) {
            curScore = p.getScore();
            if (curScore > highScore && curScore > 0) {
                highScore = curScore;
                winner = p;
            }
        }
    }

    public Boolean hasWinner() {
        if (winner == null) {
            findWinner();
        }
        return winner != null;
    }

    public T getWinner() {
        return winner;
    }

}
