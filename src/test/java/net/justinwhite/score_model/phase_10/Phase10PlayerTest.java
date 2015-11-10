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

package net.justinwhite.score_model.phase_10;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SuppressWarnings("FieldCanBeLocal")
public class Phase10PlayerTest {
    private final String name = "Test Phase10Player";
    private final Integer score = 11;
    private final Integer phase = 1;
    private Phase10Game game;
    private Phase10Player player;

    @Before
    public void setUp() throws Exception {
        game = new Phase10Game();
        player = new Phase10Player(name);
        player.setName(name);
        player.addScore(score);
        player.completeCurrentPhase();
    }

    @Test
    public void testPhase10Player() throws Exception {
        player = new Phase10Player(name, Phase10Game.PhaseSet.EVEN);
        player.completeCurrentPhase();
        assertEquals(Integer.valueOf(2), player.currentPhaseNumber());
        player.completeCurrentPhase();
        assertEquals(Integer.valueOf(4), player.currentPhaseNumber());
        player = new Phase10Player(name, Phase10Game.PhaseSet.ODD);
        player.completeCurrentPhase();
        assertEquals(Integer.valueOf(1), player.currentPhaseNumber());
        player.completeCurrentPhase();
        assertEquals(Integer.valueOf(3), player.currentPhaseNumber());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format(
                        "Name '%s'; Score %s; Phase %d",
                        name,
                        score,
                        phase
                ), player.toString()
        );
    }

    @Test
    public void testName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void testScore() {
        assertEquals(score, player.getScore());
        player.addScore(score);
        assertEquals(Integer.valueOf(score * 2), player.getScore());
    }

    @Test
    public void testPhase() throws Exception {
        for (Integer i = phase + 1; i <= Phase10Game.MAX_PHASE; i++) {
            player.completeCurrentPhase();
            assertEquals(i, player.currentPhaseNumber());
        }
        assertEquals(game.MAX_PHASE, player.currentPhaseNumber());
        player.completeCurrentPhase();
        player.completeCurrentPhase();
        assertEquals(game.MAX_PHASE, player.currentPhaseNumber());
    }

}
