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

package net.justinwhite.scoreit_p10;

import java.util.ArrayList;
import java.util.UUID;

class Game {
    private final UUID id;
    private Integer numPlayers = 0;
    private String name = "";
    private ArrayList<Player> players = new ArrayList<Player>(0);
    private Player winner;

    public Game(Integer _numPlayers) {
        this.id = UUID.randomUUID();
        for (Integer i = 0; i < _numPlayers; i++) {
            addPlayer(i);
        }
    }

    public String toString() {
        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\n",
                             getName(),
                             getID(),
                             getNumPlayers(),
                             players
        );
    }

    public UUID getID() {
        return id;
    }

    public void buildName() {
        name = "";
        for (Player p : players) {
            name += p.getInitials();
        }
    }

    public String getName() {
        return name;
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public void addPlayer(Integer _index, String _name) {
        players.ensureCapacity(numPlayers + 1);
        Player newPlayer = new Player(this, _index);
        players.add(numPlayers++, newPlayer);
        newPlayer.setName(_name);
    }

    public void addPlayer(Integer _index) {
        addPlayer(_index, String.format("Player %d", numPlayers + 1));
    }

    public ArrayList<Player> getPlayerList() {
        return players;
    }

    public Player getPlayer(Integer index) {
        return players.get(index);
    }

    public String getScores() {
        String out = "";
        for (Player p : players) {
            out += String.format("%s: %4d Points", p.getName(), p.getScore());
            if (p.getPhase() > 0) {
                out += String.format(", Phase #%d completed\n", p.getPhase());
            } else {
                out += "\n";
            }
        }
        return out;
    }

    public Boolean hasWinner() {
        return winner != null;
    }

    public Player getWinner() {
        return (winner != null) ? winner : null;
    }

    public void setWinner(Player _winner) {
        winner = _winner;
    }

}
