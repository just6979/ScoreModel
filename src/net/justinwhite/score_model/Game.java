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

public class Game<T extends Player> {
    public static final int MIN_PLAYERS;
    public static final int MAX_PLAYERS;

    static {
        MIN_PLAYERS = 2;
        MAX_PLAYERS = 8;
    }

    private Class<T> curClass;

    protected final UUID id;
    protected int numPlayers;
    protected String name;

    protected List<T> players;
    protected Map<String, T> playerMap;
    protected T winner = null;

    public Game(Class<T> _class) {
        this(_class, 0);
    }

    public Game(Class<T> _class, int _numPlayers) {
        this.curClass = _class;

        // sanity check number of players
        if (_numPlayers < MIN_PLAYERS) {
            _numPlayers = MIN_PLAYERS;
        } else if (_numPlayers > MAX_PLAYERS) {
            _numPlayers = MAX_PLAYERS;
        }

        id = UUID.randomUUID();
        setName(id.toString());
        setNumPlayers(0);

        players = new ArrayList<T>();
        playerMap = new TreeMap<String, T>();

        // add default players
        for (int i = 0; i < _numPlayers; i++) {
            addPlayer(i, String.format("Player %d", i + 1));
        }
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\nPlayerMap: %s",
                getName(),
                getID(),
                getNumPlayers(),
                // List<> and Map<> classes handle toString() themselves
                players,
                playerMap
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

    private void incrementNumPlayers() {
        numPlayers++;
    }

    public List<T> getPlayerList() {
        return players;
    }

    public Map<String, T> getPlayerMap() {
        return playerMap;
    }

    public T getPlayer(int index) {
        return players.get(index);
    }

    public T getPlayerByName(String _name) {
        return playerMap.get(_name);
    }

    public Boolean checkPlayer(String _name) {
        return playerMap.containsKey(_name);
    }

    public void addPlayer(int _index, String _name) {
        T newPlayer = null;
        try {
            newPlayer = curClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        newPlayer.setName(_name);
        ((ArrayList) players).ensureCapacity(getNumPlayers());
        players.add(_index, newPlayer);

        playerMap.put(_name, newPlayer);

        if (players.size() > numPlayers) {
            incrementNumPlayers();
        }
        buildName();
    }

    // TODO: implement for Player
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
        for (T p : players) {
            out += String.format("%s: %4d Points; ", p.getName(), p.getScore());
        }
        return out;
    }

    public void findWinner() {
        int highScore = 0;
        int curScore;
        for (T p : players) {
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
