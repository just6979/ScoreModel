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

package net.justinwhite.scoreit_p10;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private final int testNumPlayers = 4;
    private Game testGame;

    private final String[] testPlayerNames = {"Justin W", "Lauren K", "Timmay C", "Denise B"};
    private final String testName = "JWLKTCDB";
    private final Player[] testPlayers = new Player[testNumPlayers];

    @Before
    public void setUp() throws Exception {
        testGame = new Game(testNumPlayers);
        for (int i = 0; i < testNumPlayers; i++) {
            testPlayers[i] = testGame.getPlayer(i);
            testGame.renamePlayer(String.format("Player %d", i + 1), testPlayerNames[i]);
        }
    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(testGame.getName(), testName);
    }

    @Test
    public void testGetNumPlayers() throws Exception {
        assertEquals(testGame.getNumPlayers(), testNumPlayers);
    }

    @Test
    public void testGetPlayerList() throws Exception {

    }

    @Test
    public void testGetPlayer() throws Exception {
        assertEquals(testGame.getPlayer(0), testPlayers[0]);
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        assertEquals(testGame.getPlayerByName("Justin W"), testPlayers[0]);
    }

    @Test
    public void testCheckPlayer() throws Exception {
        assertTrue(testGame.checkPlayer("Justin W"));
        assertFalse(testGame.checkPlayer("Carl Sagan"));
    }

    @Test
    public void testAddPlayer() throws Exception {

    }

    @Test
    public void testRenamePlayer() throws Exception {

    }

    @Test
    public void testGetScores() throws Exception {

    }

    @Test
    public void testHasWinner() throws Exception {

    }

    @Test
    public void testGetWinner() throws Exception {

    }
}
