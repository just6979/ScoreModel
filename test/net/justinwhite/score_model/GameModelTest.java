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
public class GameModelTest {
    private final int testNumPlayers;
    private final String[] testPlayerNames;
    private final String testGameName;
    private final PlayerModel[] testPlayersArray;
    private final String testNewPlayerName;
    private String testNewPlayerInitials;
    private GameModel<PlayerModel> testGame;

    {
        testNumPlayers = 4;
        testPlayerNames = new String[]{"Justin W", "Lauren K", "Timmay C", "Denise B"};
        testGameName = "JWLKTCDB";
        testNewPlayerName = "Foo Bar";
        testNewPlayerInitials = "FB";

        testPlayersArray = new PlayerModel[testNumPlayers];
    }

    @Before
    public void setUp() {
        testGame = new GameModel<PlayerModel>(PlayerModel.class, testNumPlayers);
        for (int i = 0; i < testNumPlayers; i++) {
            testPlayersArray[i] = testGame.getPlayer(i);
            testGame.renamePlayer(String.format("Player %d", i + 1), testPlayerNames[i]);

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

    @Test
    public void testBuildName() throws Exception {
        testGame.addPlayer(testNewPlayerName);
        testGame.buildName();
        assertEquals(testGameName + testNewPlayerInitials, testGame.getName());
    }

    @Test
    public void testSetNumPlayers() throws Exception {
        testGame.setNumPlayers(testNumPlayers + 1);
        assertEquals(testGameName + "P" + (testNumPlayers + 1), testGame.getName());
    }

    @Test
    public void testGetPlayerByIndex() throws Exception {
        assertSame(testPlayersArray[0], testGame.getPlayer(0));
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        assertSame(testPlayersArray[0], testGame.getPlayerByName(testPlayerNames[0]));
    }

    @Test
    public void testCheckPlayer() throws Exception {
        assertTrue(testGame.checkPlayer(testPlayerNames[0]));
        assertFalse(testGame.checkPlayer("Some Player"));
    }

    @Test
    public void testAddPlayer() throws Exception {
        testGame.addPlayer();
        assertEquals(testGameName + "P" + (testNumPlayers + 1), testGame.getName());
    }

    @Test
    public void testAddPlayerWithName() throws Exception {
        testGame.addPlayer(testNewPlayerName);
        assertEquals(testGameName + testNewPlayerInitials, testGame.getName());
    }

    @Test
    public void testRenamePlayerByIndex() throws Exception {
        testGame.renamePlayer(0, testNewPlayerName);
        assertEquals(testNewPlayerName, testGame.getPlayer(0).getName());
    }

    @Test
    public void testRenamePlayerByName() throws Exception {
        testGame.renamePlayer(testPlayerNames[0], testNewPlayerName);
        assertEquals(testNewPlayerName, testGame.getPlayerByName(testNewPlayerName).getName());
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