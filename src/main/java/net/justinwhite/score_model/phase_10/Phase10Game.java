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

package net.justinwhite.score_model.phase_10;

import net.justinwhite.score_model.Game;

public class Phase10Game extends Game<Phase10Player> {
    public static final int MAX_PHASE;

    static {
        MAX_PHASE = 10;
    }

    static boolean[] makeDefaultPhases() {
        boolean[] defaultPhases = new boolean[MAX_PHASE + 1];
        for (int i = 1; i <= MAX_PHASE; i++) { defaultPhases[i] = true; }
        return defaultPhases;
    }

    private final boolean[] activePhases;

    public Phase10Game() {
        this(0);
    }

    public Phase10Game(int _numPlayers) {
        this(_numPlayers, makeDefaultPhases());
    }

    public Phase10Game(int _numPlayers, boolean[] _phases) {
        super(Phase10Player.class, _numPlayers);
        activePhases = _phases;
    }

    public boolean[] getActivePhases() {
        return activePhases;
    }

    public int[] getPhases() {
        int[] phases = new int[getNumPlayers()];
        int count = 0;
        for (Phase10Player p : getPlayerList()) {
            phases[count++] = p.getPhase();
        }
        return phases;
    }

    public String getScoresPhasesText() {
        String out = "";
        for (Phase10Player p : getPlayerList()) {
            out += String.format("%s: %4d Points", p.getName(), p.getScore());
            if (p.getPhase() > 0) {
                out += String.format(", Phase #%d completed\n", p.getPhase());
            } else {
                out += "\n";
            }
        }
        return out;
    }

    @Override
    public void findWinner() {
        // TODO: scores _might_ be the same sometimes. What is the 2nd tie-breaker?
        int lowScore = Integer.MAX_VALUE;
        for (Phase10Player p : getPlayerList()) {
            if (p.getPhase() == MAX_PHASE) {
                int curScore = p.getScore();
                if (curScore < lowScore) {
                    lowScore = p.getScore();
                    setWinner(p);
                }
            }
        }
    }

}