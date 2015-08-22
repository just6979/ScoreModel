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

package net.justinwhite.score_model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("FieldCanBeLocal")
public class PlayerTest {
    private final String name = "First Last";
    private final String initials = "FL";
    private int score = 88;
    private Game<Player> game;
    private Player player;
    private String newName = "OnlyFirst";
    private String newInitials = "O_";

    @Before
    public void setUp() throws Exception {
        game = new Game<>(Player.class);
        player = new Player(game, name);
        player.setScore(score);
    }

    // test all the remaining constructors
    @Test
    public void testPlayer() throws Exception {
        player = new Player();
        assertNull(player.getGame());
        assertEquals("Player X", player.getName());
        game = new Game<>(Player.class);
        player = new Player(game);
        assertSame(game, player.getGame());
        assertEquals("Player X", player.getName());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format(
                        "Name '%s'; Score %s",
                        name,
                        score
                ), player.toString()
        );
    }

    @Test
    public void testGetGame() throws Exception {
        assertSame(game, player.getGame());
    }

    @Test
    public void testGetInitials() throws Exception {
        assertEquals(initials, player.getInitials());
        player.setName(newName);
        assertEquals(newInitials, player.getInitials());
    }

}