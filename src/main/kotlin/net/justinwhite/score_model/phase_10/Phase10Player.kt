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

import net.justinwhite.score_model.Player
import net.justinwhite.score_model.phase_10.Phase10Game.*

class Phase10Player(_name: String, _phases: Array<Phase>) : Player(_name) {

    val phases: Array<Phase>
    var currentPhase: Int = 0
        private set

    @JvmOverloads constructor(_name: String = "Player X", phasePreset: PhaseSet = PhaseSet.ALL) : this(_name, Companion.getPhasePreset(phasePreset)) {
    }

    init {
        phases = Phase10Game.getPhasePreset()
        setActivePhases(_phases)
        currentPhase = 0
    }

    override fun toString(): String {
        return "%s; Phase %d".format(super.toString(), currentPhase)
    }

    fun setActivePhases(_phases: Array<Phase>) {
        System.arraycopy(_phases, 0, phases, 0, _phases.size)
    }

    fun setActivePhases(_phases: Array<Boolean>) {
        for (i in 0..Phase10Game.MAX_PHASE) {
            if (_phases[i]) {
                phases[i] = Phase.ACTIVE
            } else {
                phases[i] = Phase.INACTIVE
            }
        }
    }

    fun resetCurrentPhase() {
        currentPhase = 0
    }

    fun completeCurrentPhase() {
        if (phases[currentPhase] === Phase.ACTIVE) {
            phases[currentPhase] = Phase.COMPLETED
        }
        do {
            currentPhase++
            if (currentPhase > Phase10Game.MAX_PHASE) {
                currentPhase = Phase10Game.MAX_PHASE
                break
            }
        } while (phases[currentPhase] !== Phase.ACTIVE)
    }

    fun addScore(_score: Int?) {
        score += _score!!
    }
}
