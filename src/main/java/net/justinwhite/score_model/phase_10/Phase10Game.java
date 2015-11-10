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
    public static final Integer MIN_PHASE = 0;
    public static final Integer MAX_PHASE = 10;
    private Phase[] activePhases;

    public Phase10Game() {
        this(0, PhaseSet.ALL, null);
    }

    public Phase10Game(Integer _numPlayers) {
        this(_numPlayers, PhaseSet.ALL, null);
    }

    public Phase10Game(Integer _numPlayers, PhaseSet _phasePreset) {
        this(_numPlayers, _phasePreset, null);
    }

    public Phase10Game(Integer _numPlayers, PhaseSet _phasePreset, String _name) {
        super(Phase10Player.class, _numPlayers, _name);
        activePhases = getPhasePreset(_phasePreset);
    }

    public static Phase[] getPhasePreset(PhaseSet phasePreset) {
        Phase[] defaultPhases;

        switch (phasePreset) {
            case EVEN:
                defaultPhases = new Phase[]{Phase.UNUSED,
                        Phase.INACTIVE, Phase.ACTIVE,
                        Phase.INACTIVE, Phase.ACTIVE,
                        Phase.INACTIVE, Phase.ACTIVE,
                        Phase.INACTIVE, Phase.ACTIVE,
                        Phase.INACTIVE, Phase.ACTIVE
                };
                break;
            case ODD:
                defaultPhases = new Phase[]{Phase.UNUSED,
                        Phase.ACTIVE, Phase.INACTIVE,
                        Phase.ACTIVE, Phase.INACTIVE,
                        Phase.ACTIVE, Phase.INACTIVE,
                        Phase.ACTIVE, Phase.INACTIVE,
                        Phase.ACTIVE, Phase.INACTIVE
                };
                break;
            case FIRST_5:
                defaultPhases = new Phase[]{Phase.UNUSED,
                        Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE,
                        Phase.INACTIVE, Phase.INACTIVE, Phase.INACTIVE, Phase.INACTIVE, Phase.INACTIVE
                };
                break;
            case LAST_5:
                defaultPhases = new Phase[]{Phase.UNUSED,
                        Phase.INACTIVE, Phase.INACTIVE, Phase.INACTIVE, Phase.INACTIVE, Phase.INACTIVE,
                        Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE
                };
                break;
            case ALL:
            default:
                defaultPhases = new Phase[]{Phase.UNUSED,
                        Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE,
                        Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE, Phase.ACTIVE};
                break;
        }
        return defaultPhases;
    }

    public Phase[] getActivePhases() {
        return activePhases;
    }

    public void setActivePhases(PhaseSet _phasePreset) {
        getPhasePreset(_phasePreset);
    }

    public void setActivePhases(Phase[] _activePhases) {
        activePhases = _activePhases;
    }

    public Boolean isPhaseActive(Integer _phase) {
        return activePhases[_phase] == Phase.ACTIVE;
    }

    public Integer[] getPhases() {
        Integer[] phases = new Integer[getNumPlayers()];
        Integer count = 0;
        for (Phase10Player p : getPlayerList()) {
            phases[count++] = p.getCurrentPhase();
        }
        return phases;
    }

    public String getScoresPhasesText() {
        String out = "";
        for (Phase10Player p : getPlayerList()) {
            out += String.format("%s: %4d Points", p.getName(), p.getScore());
            if (p.getCurrentPhase() > 0) {
                out += String.format(", Phase #%d completed\n", p.getCurrentPhase());
            } else {
                out += "\n";
            }
        }
        return out;
    }

    @Override
    public Boolean checkWinner() {
        // TODO: scores _might_ be the same sometimes. What is the 2nd tie-breaker?
        Integer lowScore = Integer.MAX_VALUE;
        for (Phase10Player p : getPlayerList()) {
            if (p.getCurrentPhase().equals(MAX_PHASE)) {
                Integer curScore = p.getScore();
                if (curScore < lowScore) {
                    lowScore = p.getScore();
                    winner = p;
                }
            }
        }
        return getWinner() != null;
    }

    public enum Phase {
        UNUSED, INACTIVE, ACTIVE, COMPLETED
    }

    public enum PhaseSet {
        ALL, EVEN, ODD, FIRST_5, LAST_5
    }

}
