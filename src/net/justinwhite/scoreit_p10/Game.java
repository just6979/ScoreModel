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
    private final Integer numPlayers;
    private String name = "";
    private ArrayList<Player> players = new ArrayList<Player>(0);

    public Game(Integer numPlayers) {
        this.id = UUID.randomUUID();
        this.numPlayers = numPlayers;
        players.ensureCapacity(numPlayers);
        Player newPlayer;
        for (Integer i = 0; i < numPlayers; i++) {
            newPlayer = new Player();
            players.add(i, newPlayer);
            newPlayer.setName(String.format("Player %d", i + 1));
        }
    }

    public String toString() {

        return String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\n",
                             name,
                             id.toString(),
                             numPlayers,
                             players.toString()
        );
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

    class Player {
        private String name;
        private Integer score = 0;
        private Integer phase = 1;

        public Player() {
            setName("Player X");
        }

        public Player(String _name) {
            setName(_name);
        }

        public String toString() {
            return String.format(
                    "Name: '%s', Score: %d, Phase: %d",
                    getName(),
                    getScore(),
                    getPhase()
            );
        }

        public void setName(String _name) {
            name = _name;
            Game.this.buildName();
        }

        public String getName() {
            return name;
        }

        public String getInitial() {
            return name.substring(0, 1);
        }

        public String getInitials() {
            String names[] = name.split(" ");
            if (names.length > 1) {
                return String.join("", names[0].substring(0, 1), names[1].substring(0, 1));
            } else {
                return getInitial();
            }
        }

        public Integer getScore() {
            return score;
        }

        public void addScore(Integer _score) {
            score += _score;
        }

        public Integer getPhase() {
            return phase;
        }

        public void completePhase(Integer _phase) {
            phase += _phase;
        }
    }

}
