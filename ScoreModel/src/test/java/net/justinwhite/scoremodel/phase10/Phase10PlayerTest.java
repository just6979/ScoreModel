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

package net.justinwhite.scoremodel.phase10;

import net.justinwhite.scoremodel.VersionKt;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("FieldCanBeLocal")
public class Phase10PlayerTest {
    private final String name = "Test Phase10Player";
    private final Integer score = 11;
    private final Integer phase = 1;
    private Phase10Player player;

    @Before
    public void setUp() {
        player = new Phase10Player();
    }

    @Test
    public void testConstructors() throws Exception {
        System.out.println("Testing: " + VersionKt.getVersion() + ": Phase10Player");
        assertArrayEquals(Phase10Game.Companion.getPhasePreset(Phase10Game.PhaseSet.ALL), player.getPhases());
        player = new Phase10Player(name, Phase10Game.PhaseSet.EVEN);
        assertArrayEquals(Phase10Game.Companion.getPhasePreset(Phase10Game.PhaseSet.EVEN), player.getPhases());
    }

    @Test
    public void testToString() throws Exception {
        player.setName(name);
        player.addScore(score);
        player.completeCurrentPhase();
        assertEquals(String.format(
                "Name '%s'; Score %s; Phase %d",
                name,
                score,
                phase
                ), player.toString()
        );
    }

    @Test
    public void testSetPhases() throws Exception {
        player.setActivePhases(Phase10Game.Companion.getPhasePreset(Phase10Game.PhaseSet.ALL));
        player.completeCurrentPhase();
        assertEquals(1, player.getCurrentPhase());
        player.setActivePhases(Phase10Game.Companion.getPhasePreset(Phase10Game.PhaseSet.ODD));
        player.resetCurrentPhase();
        player.completeCurrentPhase();
        assertEquals(1, player.getCurrentPhase());
        player.completeCurrentPhase();
        assertEquals(3, player.getCurrentPhase());
    }

    @Test
    public void testAddScore() throws Exception {
        player.addScore(score);
        assertEquals(score, Integer.valueOf(player.getScore()));
        player.addScore(score);
        assertEquals(Integer.valueOf(score * 2), Integer.valueOf(player.getScore()));
    }

    @Test
    public void testCompleteCurrentPhase() throws Exception {
        player.completeCurrentPhase();
        for (int i = phase + 1; i <= Phase10Game.MAX_PHASE; i++) {
            player.completeCurrentPhase();
            assertEquals(i, player.getCurrentPhase());
        }
        assertEquals(Phase10Game.MAX_PHASE, player.getCurrentPhase());
        player.completeCurrentPhase();
        player.completeCurrentPhase();
        assertEquals(Phase10Game.MAX_PHASE, player.getCurrentPhase());
    }
}
