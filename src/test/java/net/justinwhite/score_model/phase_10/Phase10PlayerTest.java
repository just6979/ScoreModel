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
    private final int score = 11;
    private final int phase = 1;
    private Phase10Game game;
    private Phase10Player player;

    @Before
    public void setUp() throws Exception {
        game = new Phase10Game();
        player = new Phase10Player();
        assertNull(player.getGame());
        player.setGame(game);
        player.setName(name);
        player.addScore(score);
        player.completePhase();
    }

    @Test
    public void testPhase10Player() throws Exception {
        game = new Phase10Game(2, Phase10Game.PHASES_EVEN);
        player = new Phase10Player(game);
        player.setName(name);
        player.completePhase();
        assertEquals(2, player.getPhase());
        player.completePhase();
        assertEquals(4, player.getPhase());
        game = new Phase10Game(2, Phase10Game.PHASES_ODD);
        player = new Phase10Player(game, name);
        player.completePhase();
        assertEquals(1, player.getPhase());
        player.completePhase();
        assertEquals(3, player.getPhase());
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
    public void testGame() {
        assertEquals(game, player.getGame());
    }

    @Test
    public void testName() {
        assertEquals(name, player.getName());
    }

    @Test
    public void testScore() {
        assertEquals(score, player.getScore());
        player.addScore(score);
        assertEquals(score * 2, player.getScore());
    }

    @Test
    public void testPhase() throws Exception {
        for (int i = phase + 1; i <= Phase10Game.MAX_PHASE; i++) {
            player.completePhase();
            assertEquals(i, player.getPhase());
        }
        assertEquals(game.MAX_PHASE, player.getPhase());
        player.completePhase();
        player.completePhase();
        assertEquals(game.MAX_PHASE, player.getPhase());
    }

}
