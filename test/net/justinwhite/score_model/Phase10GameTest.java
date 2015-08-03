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

public class Phase10GameTest {
    private final int testNumPlayers;
    private final String testInitialName;
    private final String[] testPlayerNames;
    private final String testName;
    private final Phase10Player[] testPlayersArray;
    private Phase10Game testPhase10Game;

    {
        testNumPlayers = 4;
        testInitialName = "P1P2P3P4";
        testPlayerNames = new String[]{"Justin W", "Lauren K", "Timmay C", "Denise B"};
        testName = "JWLKTCDB";
        testPlayersArray = new Phase10Player[testNumPlayers];
    }

    @Before
    public void setUp() {
        testPhase10Game = new Phase10Game(testNumPlayers);
        assertEquals(testInitialName, testPhase10Game.getName());
        for (int i = 0; i < testNumPlayers; i++) {
            testPlayersArray[i] = testPhase10Game.getPlayer(i);
            testPhase10Game.renamePlayer(String.format("Player %d", i + 1), testPlayerNames[i]);
        }
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\nPlayerMap: %s",
                        testPhase10Game.getName(),
                        testPhase10Game.getID(),
                        testPhase10Game.getNumPlayers(),
                        testPhase10Game.getPlayerList(),
                        testPhase10Game.getPlayerMap()
                ), testPhase10Game.toString()
        );

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(testName, testPhase10Game.getName());
    }

    @Test
    public void testGetNumPlayers() throws Exception {
        assertEquals(testNumPlayers, testPhase10Game.getNumPlayers());
    }

    @Test
    public void testBuildName() throws Exception {

    }

    @Test
    public void testGetPlayerList() throws Exception {

    }

    @Test
    public void testGetPlayerMap() throws Exception {

    }

    @Test
    public void testGetPlayer() throws Exception {
        assertEquals(testPlayersArray[0], testPhase10Game.getPlayer(0));
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        assertEquals(testPlayersArray[0], testPhase10Game.getPlayerByName("Justin W"));
    }

    @Test
    public void testCheckPlayer() throws Exception {
        assertTrue(testPhase10Game.checkPlayer("Justin W"));
        assertFalse(testPhase10Game.checkPlayer("Carl Sagan"));
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