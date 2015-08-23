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
    private final int numPlayers;
    private final String gameName;
    private final Player[] playersArray;
    private final String newPlayerName;
    private final String newPlayerInitials;
    private Game<Player> game;

    {
        numPlayers = 4;
        gameName = "P1P2P3P4";
        newPlayerName = "Foo Bar";
        newPlayerInitials = "FB";

        playersArray = new Player[numPlayers];
    }

    @Before
    public void setUp() {
        game = new Game<>(Player.class, numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            // populate array for comparisons later.
            playersArray[i] = game.getPlayer(i);

        }
    }

    @Test
    public void testGame() throws Exception {
        game = new Game<>(Player.class);
        assertEquals(Game.MIN_PLAYERS, game.getNumPlayers());
        game = new Game<>(Player.class, Integer.MIN_VALUE);
        assertEquals(Game.MIN_PLAYERS, game.getNumPlayers());
        game = new Game<>(Player.class, Integer.MAX_VALUE);
        assertEquals(Game.MAX_PLAYERS, game.getNumPlayers());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s\nPlayerMap: %s",
                        game.getName(),
                        game.getID(),
                        game.getNumPlayers(),
                        game.getPlayerList(),
                        game.getPlayerMap()
                ), game.toString()
        );

    }

    @Test
    public void testBuildName() throws Exception {
        game.addPlayer(newPlayerName);
        game.buildName();
        assertEquals(gameName + newPlayerInitials, game.getName());
    }

    @Test
    public void testSetNumPlayersUp() throws Exception {
        // fail if too many players
        assertFalse(game.setNumPlayers(Integer.MAX_VALUE));
        // increase player count by 1
        int newNumPlayers = numPlayers + 1;
        // check decrease was successful
        assertTrue(game.setNumPlayers(newNumPlayers));
        // and player count matches
        assertEquals(newNumPlayers, game.getNumPlayers());
        // check if the last player's initials were added to the game name
        assertEquals(gameName + "P" + newNumPlayers, game.getName());
        // check last player's name to see if it matches the player count
        assertEquals("Player " + newNumPlayers, game.getPlayer(game.getNumPlayers() - 1).getName());
    }

    @Test
    public void testSetNumPlayersDown() throws Exception {
        // fail if too few players
        assertFalse(game.setNumPlayers(0));
        //decrease player count by 1
        int newNumPlayers = numPlayers - 1;
        // check increase was successful and player count matches
        assertTrue(game.setNumPlayers(newNumPlayers));
        assertEquals(newNumPlayers, game.getNumPlayers());
        // check if the last player's initial were removed from the game name
        assertEquals(gameName.substring(0, newNumPlayers * 2), game.getName());
        // check last player's name to see if it matches the player count
        assertEquals("Player " + newNumPlayers, game.getPlayer(game.getNumPlayers() - 1).getName());
    }

    @Test
    public void testGetPlayerByIndex() throws Exception {
        assertSame(playersArray[0], game.getPlayer(0));
        assertNull(game.getPlayer(100));
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        assertSame(playersArray[0], game.getPlayer("Player 1"));
        assertNull(game.getPlayer("Player X"));
    }

    @Test
    public void testCheckPlayer() throws Exception {
        assertTrue(game.checkPlayer("Player 1"));
        assertFalse(game.checkPlayer("Some Player"));
    }

    @Test
    public void testAddPlayer() throws Exception {
        // add 1 Player
        Player newPlayer = game.addPlayer();
        assertSame(newPlayer, game.getPlayer(game.getNumPlayers() - 1));
        // check it's initials are on the game name
        assertEquals(gameName + "P" + (numPlayers + 1), game.getName());
        // add Players to the max
        for (int i = game.getNumPlayers(); i < Game.MAX_PLAYERS; i++) {
            assertNotNull(game.addPlayer());
        }
        // try to add one more and fail
        assertNull(game.addPlayer());
    }

    @Test
    public void testAddPlayerWithName() throws Exception {
        game.addPlayer(newPlayerName);
        assertEquals(gameName + newPlayerInitials, game.getName());
    }

    @Test
    public void testRemovePlayer() throws Exception {
        // save the last player for reference
        Player lastPlayer = game.getPlayer(game.getNumPlayers() - 1);
        // remove 1 Player
        Player oldPlayer = game.removePlayer();
        // check it's the same as the former last player
        assertSame(lastPlayer, oldPlayer);
        // check it's not the same as the new last player
        assertNotSame(oldPlayer, game.getPlayer(game.getNumPlayers() - 1));
        // check if the last player's initial were removed from the game name
        assertEquals(
                gameName.substring(0, game.getNumPlayers() * 2),
                game.getName()
        );
        // check last player's name to see if it matches the player count
        assertEquals(
                "Player " + game.getNumPlayers(),
                game.getPlayer(game.getNumPlayers() - 1).getName()
        );
        // remove Players to the minimum
        for (int i = game.getNumPlayers(); i > Game.MIN_PLAYERS; i--) {
            assertNotNull(game.removePlayer());
        }
        // try to remove one more and fail
        assertNull(game.removePlayer());

    }

    @Test
    public void testRenamePlayerByIndex() throws Exception {
        game.renamePlayer(0, newPlayerName);
        assertEquals(newPlayerName, game.getPlayer(0).getName());
    }

    @Test
    public void testRenamePlayerByName() throws Exception {
        game.renamePlayer("Player 1", newPlayerName);
        assertEquals(newPlayerName, game.getPlayer(newPlayerName).getName());
    }

}
