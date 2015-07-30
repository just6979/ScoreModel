/*
 Copyright (c) 2015 Justin White <jw@justinwhite.net>
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 3. Neither the name of the copyright holder nor the names of its
 contributors may be used to endorse or promote products derived from this
 software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 */

package net.justinwhite.score_model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class Phase10Game extends Game {
    public static final int MAX_PHASE;
    public static final int MIN_PLAYERS;
    public static final int MAX_PLAYERS;

    static {
        MAX_PHASE = 10;
        MIN_PLAYERS = 2;
        MAX_PLAYERS = 8;
    }

    private List<Phase10Player> players;
    private Map<String, Phase10Player> playerMap;
    private Phase10Player winner;

    public Phase10Game(int _numPlayers) {
        int numPlayers = _numPlayers;
        // sanity check number of players
        if (numPlayers < MIN_PLAYERS) {
            numPlayers = MIN_PLAYERS;
        } else if (numPlayers > MAX_PLAYERS) {
            numPlayers = MAX_PLAYERS;
        }

        players = new ArrayList<Phase10Player>(numPlayers);
        playerMap = new TreeMap<String, Phase10Player>();

        // add default players
        for (int i = 0; i < numPlayers; i++) {
            addPlayer(i, String.format("Player %d", i + 1));
        }
    }

    @Override
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

    public void buildName() {
        String _name = "";
        for (Phase10Player p : players) {
            _name += p.getInitials();
        }
        setName(_name);
    }

    @Override
    public List<Phase10Player> getPlayerList() {
        return players;
    }

    @Override
    public Map<String, Phase10Player> getPlayerMap() {
        return playerMap;
    }

    @Override
    public Phase10Player getPlayer(int index) {
        return players.get(index);
    }

    @Override
    public Phase10Player getPlayerByName(String _name) {
        return playerMap.get(_name);
    }

    @Override
    public Boolean checkPlayer(String _name) {
        return playerMap.containsKey(_name);
    }

    @Override
    public void addPlayer(int _index, String _name) {
        Phase10Player newPlayer = new Phase10Player(_name);

        ((ArrayList) players).ensureCapacity(getNumPlayers());
        players.add(_index, newPlayer);

        playerMap.put(_name, newPlayer);

        incrementNumPlayers();
        buildName();
    }

    @Override
    public void renamePlayer(String oldName, String newName) {
        Phase10Player p = playerMap.remove(oldName);
        if (p != null) {
            p.setName(newName);
            playerMap.put(newName, p);
        }
        buildName();
    }

    @Override
    public String getScores() {
        String out = "";
        for (Phase10Player p : players) {
            out += String.format("%s: %4d Points", p.getName(), p.getScore());
            if (p.getPhase() > 0) {
                out += String.format(", Phase #%d completed\n", p.getPhase());
            } else {
                out += "\n";
            }
        }
        return out;
    }

    // TODO: handle multiple winners: tie-break on score
    @Override
    public Boolean hasWinner() {
        for (Phase10Player p : players) {
            if (Phase10Player.winner == p) {
                winner = p;
                return true;
            }
        }
        return false;

    }

    @Override
    public Phase10Player getWinner() {
        return winner;
    }
}
