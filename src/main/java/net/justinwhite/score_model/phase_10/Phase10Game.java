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

    public enum PhaseSet {
        ALL, EVEN, ODD, FIRST_5, LAST_5
    }
    private boolean[] activePhases;

    public static boolean[] getPhasePreset(PhaseSet phasePreset) {
        boolean[] defaultPhases;

        switch (phasePreset) {
            case EVEN:
                defaultPhases = new boolean[]{false,
                        false, true,
                        false, true,
                        false, true,
                        false, true,
                        false, true
                };
                break;
            case ODD:
                defaultPhases = new boolean[]{false,
                        true, false,
                        true, false,
                        true, false,
                        true, false,
                        true, false
                };
                break;
            case FIRST_5:
                defaultPhases = new boolean[]{false,
                        true, true, true, true, true,
                        false, false, false, false, false
                };
                break;
            case LAST_5:
                defaultPhases = new boolean[]{false,
                        false, false, false, false, false,
                        true, true, true, true, true
                };
                break;
            case ALL:
            default:
                defaultPhases = new boolean[]{false,
                        true, true, true, true, true,
                        true, true, true, true, true};
                break;
        }
        return defaultPhases;
    }

    public Phase10Game() {
        this(0, PhaseSet.ALL, null);
    }

    public Phase10Game(int _numPlayers) {
        this(_numPlayers, PhaseSet.ALL, null);
    }

    public Phase10Game(int _numPlayers, PhaseSet _phasePreset) {
        this(_numPlayers, _phasePreset, null);
    }

    public Phase10Game(int _numPlayers, PhaseSet _phasePreset, String _name) {
        super(Phase10Player.class, _numPlayers, _name);
        activePhases = getPhasePreset(_phasePreset);
    }

    public boolean[] getActivePhases() {
        return activePhases;
    }

    public void setActivePhases(PhaseSet _phasePreset) {
        getPhasePreset(_phasePreset);
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
            phases[count++] = p.currentPhaseNumber();
        }
        return phases;
    }

    public String getScoresPhasesText() {
        String out = "";
        for (Phase10Player p : getPlayerList()) {
            out += String.format("%s: %4d Points", p.getName(), p.getScore());
            if (p.currentPhaseNumber() > 0) {
                out += String.format(", Phase #%d completed\n", p.currentPhaseNumber());
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
            if (p.currentPhaseNumber() == MAX_PHASE) {
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
