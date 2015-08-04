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
public class GameTest {
    private final int testNumPlayers;
    private final String testInitialName;
    private final String[] testPlayerNames;
    private final String testName;
    private final Player[] testPlayersArray;
    private Game<Player> testGame;

    {
        testNumPlayers = 4;
        testInitialName = "P1P2P3P4";
        testPlayerNames = new String[]{"Justin W", "Lauren K", "Timmay C", "Denise B"};
        testName = "JWLKTCDB";
        testPlayersArray = new Player[testNumPlayers];
    }

    @Before
    public void setUp() {
        testGame = new Game<Player>(Player.class, testNumPlayers);
        for (int i = 0; i < testNumPlayers; i++) {
            testPlayersArray[i] = testGame.getPlayer(i);
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\nPlayerMap: %s",
                        testGame.getName(),
                        testGame.getID(),
                        testGame.getNumPlayers(),
                        testGame.getPlayerList(),
                        testGame.getPlayerMap()
                ), testGame.toString()
        );

    }

    // TODO
    @Test
    public void testBuildName() throws Exception {

    }

    // TODO
    @Test
    public void testSetNumPlayers() throws Exception {

    }

    @Test
    public void testGetPlayer() throws Exception {
        assertEquals(testPlayersArray[0], testGame.getPlayer(0));
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        assertEquals(testPlayersArray[0], testGame.getPlayerByName("Player 1"));
    }

    @Test
    public void testCheckPlayer() throws Exception {
        assertTrue(testGame.checkPlayer("Player 1"));
        assertFalse(testGame.checkPlayer("Some Player"));
    }

    // TODO
    @Test
    public void testAddPlayer() throws Exception {

    }

    // TODO
    @Test
    public void testRenamePlayer() throws Exception {

    }

    // TODO
    @Test
    public void testGetScores() throws Exception {

    }

    // TODO
    @Test
    public void testFindWinner() throws Exception {

    }

    // TODO
    @Test
    public void testHasWinner() throws Exception {

    }

}