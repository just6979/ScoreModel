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

package net.justinwhite.scoreit_p10;

class Player {
    private Game game;
    private Integer index;
    private String name;
    private Integer score = 0;
    private Integer phase = 0;

    public Player(Game _game, Integer _index) {
        this(_game, _index, "Player X");
    }

    public Player(Game _game, Integer _index, String _name) {
        game = _game;
        index = _index;
        setName(_name);
    }

    public String toString() {
        return String.format(
                "Name '%s'; Score %s; Phase %d",
                getName(),
                getScore(),
                getPhase()
        );
    }

    public Integer getIndex() {
        return index;
    }

    public void setName(String _name) {
        name = _name;
        game.buildName();
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

    public void addScore(Integer _score) {
        score += _score;
    }

    public Integer getScore() {
        return score;
    }

    public void nextPhase() {
        phase++;
        if (phase >= 10) {
            game.setWinner(this);
        }
    }

    public Integer getPhase() {
        return phase;
    }
}