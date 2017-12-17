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

package net.justinwhite.scoremodel;

import net.justinwhite.scoremodel.phase10.Phase10Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("FieldCanBeLocal")
public class GameTest {
    private final int numPlayers;
    private final Player[] playersArray;
    private final String newPlayerName;
    private final String[] playerNames;
    private Game<Player> game;
    private Integer[] scores;

    {
        numPlayers = 4;
        playerNames = new String[]{"Lauren K", "Justin W", "Tim C", "Denise B"};
        playersArray = new Player[numPlayers];
        newPlayerName = "Foo Bar";
        scores = new Integer[]{0, 0, 0, 0};
    }

    @Before
    public void setUp() {
        game = new Game<>(Player.class, numPlayers);
        assertEquals(numPlayers, game.getNumPlayers());
        for (Integer i = 0; i < numPlayers; i++) {
            // populate array for comparisons later
            playersArray[i] = game.getPlayer(i);
            // rename players for comparisons later
            game.getPlayer(i).setName(playerNames[i]);
        }
        // check that scores start at 0
        assertArrayEquals(scores, game.getScores().toArray());
        // set scores for later tests
        scores = new Integer[]{50, 100, 200, 150};
        for (Integer i = 0; i < scores.length; i++) {
            game.getPlayer(i).setScore(scores[i]);
        }
        // check the scores were set correctly
        assertArrayEquals(scores, game.getScores().toArray());
    }

    @Test
    public void testConstructors() throws Exception {
        System.out.println("Testing: " + VersionKt.getVersion() + ": Game");
        String name = "Test Game";
        game = new Game<>(Player.class);
        assertEquals(Game.MIN_PLAYERS, game.getNumPlayers());
        game = new Game<>(Player.class, Integer.MIN_VALUE);
        assertEquals(Game.MIN_PLAYERS, game.getNumPlayers());
        game = new Game<>(Player.class, Integer.MAX_VALUE);
        assertEquals(Game.MAX_PLAYERS, game.getNumPlayers());
        game = new Game<>(Player.class, numPlayers, name);
        assertEquals(name, game.getName());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format("Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s",
                game.getName(),
                game.getId(),
                game.getNumPlayers(),
                game.getPlayerList()
                ), game.toString()
        );

    }

    @Test
    public void testSetName() throws Exception {
        String tempName = "Foo Game";
        String builtName = Game.Companion.buildName();
        //assertEquals(builtName, game.getName());
        game.setName(tempName);
        assertEquals(tempName, game.getName());
        game.setName("");
        assertEquals(builtName, game.getName());
    }

    @Test
    public void testSetNumPlayersUp() throws Exception {
        int newNumPlayers = numPlayers + 1;
        game.setNumPlayers(newNumPlayers);
        assertEquals(newNumPlayers, game.getNumPlayers());
        // check last player's name to see if it matches the player count
        assertEquals("Player " + newNumPlayers, game.getPlayer(game.getNumPlayers() - 1).getName());
        game.setNumPlayers(Integer.MAX_VALUE);
        assertEquals(Phase10Game.MAX_PLAYERS, game.getNumPlayers());
    }


    @Test
    public void testSetNumPlayersDown() throws Exception {
        int newNumPlayers = numPlayers - 1;
        game.setNumPlayers(newNumPlayers);
        assertEquals(newNumPlayers, game.getNumPlayers());
        // check last player's name to see if it matches the player count
        assertSame(playersArray[newNumPlayers - 1], game.getPlayer(game.getNumPlayers() - 1));
        game.setNumPlayers(Integer.MIN_VALUE);
        assertEquals(Phase10Game.MIN_PLAYERS, game.getNumPlayers());
    }

    @Test
    public void testCheckPlayer() throws Exception {
        // remember we use zero indexed arrays. not 1 indexed
        // we should have a Player 1 & 2
        assertTrue(game.checkPlayer(0));
        assertTrue(game.checkPlayer(1));
        // Player -1 should not exist
        assertFalse(game.checkPlayer(-1));
        // nor should Player N + 1
        assertFalse(game.checkPlayer(numPlayers));
        // nor should other out of bounds player numbers.
        assertFalse(game.checkPlayer(Integer.MAX_VALUE));
        assertFalse(game.checkPlayer(Integer.MIN_VALUE));
    }

    @Test
    public void testGetPlayer() throws Exception {
        assertSame(playersArray[1], game.getPlayer(1));
        // out of bounds index low gives first player
        assertSame(playersArray[0], game.getPlayer(Integer.MIN_VALUE));
        // out of bounds index high gives last player
        assertSame(playersArray[numPlayers - 1], game.getPlayer(Integer.MAX_VALUE));
    }

    @Test
    public void testAddPlayer() throws Exception {
        // add 1 Player
        Player newPlayer = game.addPlayer(newPlayerName);
        assertSame(newPlayer, game.getPlayer(game.getNumPlayers() - 1));
        // add Players to the max
        for (Integer i = game.getNumPlayers(); i < Game.MAX_PLAYERS; i++) {
            assertNotNull(game.addPlayer());
        }
        // try to add one more and fail
        assertNull(game.addPlayer());
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
        // check last player's name to see if it matches
        assertEquals(
                playersArray[game.getNumPlayers() - 1].getName(),
                game.getPlayer(game.getNumPlayers() - 1).getName()
        );
        // remove Players, from the beginning, to the minimum
        for (Integer i = game.getNumPlayers(); i > Game.MIN_PLAYERS; i--) {
            assertNotNull(game.removePlayer(0));
        }
        // try to remove one more and fail
        assertNull(game.removePlayer());

    }

    @Test
    public void testGetScores() throws Exception {
        assertArrayEquals(scores, game.getScores().toArray());
    }

    @Test
    public void testGetScoresText() throws Exception {
        String result = "";
        for (Player p : game.getPlayerList()) {
            result += String.format("%s: %d Points; ", p.getName(), p.getScore());
        }
        assertEquals(result, game.getScoresText());
    }

    // TODO: test tie-scores
    @Test
    public void testCheckWinner() throws Exception {
        // check we have a winner
        // note: we always have a winner...
        assertTrue(game.checkWinner());
    }

    @Test
    public void testGetWinner() throws Exception {
        // check winner matches player set to win in setup
        assertSame(playersArray[2], game.getWinner());
    }

}
