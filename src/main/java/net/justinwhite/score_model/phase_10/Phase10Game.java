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
    public static final int MIN_PHASE = 0;
    public static final int MAX_PHASE = 10;

    public static final int PHASES_ALL = 0;
    public static final int PHASES_EVEN = 1;
    public static final int PHASES_ODD = 2;
    public static final int PHASES_FIRST_5 = 3;
    public static final int PHASES_LAST_5 = 4;
    private boolean[] activePhases;

    public Phase10Game() {
        this(0, PHASES_ALL);
    }

    public Phase10Game(int _numPlayers, int _phasePreset) {
        super(Phase10Player.class, _numPlayers);
        activePhases = makePhasePreset(_phasePreset);
    }

    private static boolean[] makePhasePreset(int phasePreset) {
        boolean[] defaultPhases;

        switch (phasePreset) {
            case PHASES_EVEN:
                defaultPhases = new boolean[]{false,
                        false, true,
                        false, true,
                        false, true,
                        false, true,
                        false, true
                };
                break;
            case PHASES_ODD:
                defaultPhases = new boolean[]{false,
                        true, false,
                        true, false,
                        true, false,
                        true, false,
                        true, false
                };
                break;
            case PHASES_FIRST_5:
                defaultPhases = new boolean[]{false,
                        true, true, true, true, true,
                        false, false, false, false, false
                };
                break;
            case PHASES_LAST_5:
                defaultPhases = new boolean[]{false,
                        false, false, false, false, false,
                        true, true, true, true, true
                };
                break;
            case PHASES_ALL:
            default:
                defaultPhases = new boolean[]{false,
                        true, true, true, true, true,
                        true, true, true, true, true};
                break;
        }
        return defaultPhases;
    }

    public Phase10Game(int _numPlayers) {
        this(_numPlayers, PHASES_ALL);
    }

    public boolean[] getActivePhases() {
        return activePhases;
    }

    public void setActivePhases(int _phasePreset) {
        makePhasePreset(_phasePreset);
    }

    public void setActivePhases(boolean[] _activePhases) {
        activePhases = _activePhases;
    }

    public boolean isPhaseActive(int _phase) {
        return activePhases[_phase];
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
    public boolean checkWinner() {
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
        return getWinner() != null;
    }

}
