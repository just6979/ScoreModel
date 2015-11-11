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

package net.justinwhite.score_model.phase_10

import net.justinwhite.score_model.Game

class Phase10Game @JvmOverloads
constructor(
        _numPlayers: Int = 0,
        _phasePreset: Phase10Game.PhaseSet = Phase10Game.PhaseSet.ALL,
        _name: String? = null
) : Game<Phase10Player>(Phase10Player::class.java, _numPlayers, _name) {
    var activePhases: Array<Phase> = getPhasePreset(_phasePreset)

    fun setActivePhases(_phasePreset: PhaseSet) {
        getPhasePreset(_phasePreset)
    }

    fun setActivePhases(_phases: Array<Boolean>) {
        activePhases = Array(MAX_PHASE, { i -> Phase.ACTIVE })
        for (i in 0..Phase10Game.MAX_PHASE) {
            if (_phases[i]) {
                activePhases[i] = Phase.ACTIVE
            } else {
                activePhases[i] = Phase.INACTIVE
            }
        }
    }

    fun isPhaseActive(_phase: Int): Boolean? {
        return activePhases[_phase] == Phase.ACTIVE
    }

    val phases: Array<Phase>
        get() {
            val phases = getPhasePreset()
            var count: Int = MIN_PHASE
            for (p in playerList) {
                phases[count++] = p.phases[p.currentPhase]
            }
            return phases
        }

    val scoresPhasesText: String
        get() {
            var out = ""
            for (p in playerList) {
                out += "%s: %4d Points".format(p.name, p.score)
                if (p.currentPhase > 0) {
                    out += ", Phase #%d completed\n".format(p.currentPhase)
                } else {
                    out += "\n"
                }
            }
            return out
        }

    override fun checkWinner(): Boolean {
        // TODO: scores _might_ be the same sometimes. What is the 2nd tie-breaker?
        var lowScore: Int? = Integer.MAX_VALUE
        for (p in playerList) {
            if (p.currentPhase == MAX_PHASE) {
                val curScore = p.score
                if (curScore < lowScore as Int) {
                    lowScore = p.score
                    winner = p
                }
            }
        }
        return winner != null
    }

    enum class Phase {
        UNUSED, INACTIVE, ACTIVE, COMPLETED
    }

    enum class PhaseSet {
        ALL, EVEN, ODD, FIRST_5, LAST_5
    }

    companion object {
        const val MIN_PHASE = 0
        const val MAX_PHASE = 10

        fun getPhasePreset(phasePreset: PhaseSet = PhaseSet.ALL): Array<Phase> = when (phasePreset) {
            PhaseSet.EVEN -> arrayOf(Phase.UNUSED,
                    Phase.INACTIVE, Phase.ACTIVE,
                    Phase.INACTIVE, Phase.ACTIVE,
                    Phase.INACTIVE, Phase.ACTIVE,
                    Phase.INACTIVE, Phase.ACTIVE,
                    Phase.INACTIVE, Phase.ACTIVE
            )
            PhaseSet.ODD -> arrayOf(Phase.UNUSED,
                    Phase.ACTIVE, Phase.INACTIVE,
                    Phase.ACTIVE, Phase.INACTIVE,
                    Phase.ACTIVE, Phase.INACTIVE,
                    Phase.ACTIVE, Phase.INACTIVE,
                    Phase.ACTIVE, Phase.INACTIVE
            )
            PhaseSet.FIRST_5 -> arrayOf(Phase.UNUSED,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.INACTIVE,
                    Phase.INACTIVE, Phase.INACTIVE,
                    Phase.INACTIVE, Phase.INACTIVE
            )
            PhaseSet.LAST_5 -> arrayOf(Phase.UNUSED,
                    Phase.INACTIVE, Phase.INACTIVE,
                    Phase.INACTIVE, Phase.INACTIVE,
                    Phase.INACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE
            )
        // PhaseSet.ALL included below
            else -> arrayOf(Phase.UNUSED,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE,
                    Phase.ACTIVE, Phase.ACTIVE
            )
        }
    }

}
