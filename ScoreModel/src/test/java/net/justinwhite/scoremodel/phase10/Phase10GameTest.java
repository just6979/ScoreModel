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

@SuppressWarnings("FieldCanBeLocal")
public class Phase10GameTest {
    private final Integer numPlayers;
    private final String[] playerNames;
    private final Phase10Player[] playersArray;

    private Phase10Game game;

    {
        numPlayers = 4;
        playerNames = new String[]{"Lauren K", "Justin W", "Tim C", "Denise B"};
        playersArray = new Phase10Player[numPlayers];
    }

    @Before
    public void setUp() {
        game = new Phase10Game(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            playersArray[i] = game.getPlayer(i);
            game.getPlayer(i).setName(playerNames[i]);
        }
    }

    @Test
    public void testConstructors() throws Exception {
        System.out.println("Testing: " + VersionKt.getVersion() + ": Phase10Game");
    }

    @Test
    public void testMakeDefaultPhases() throws Exception {

    }

    @Test
    public void testPhase10Game() throws Exception {

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testGetActivePhases() throws Exception {

    }

    @Test
    public void testGetPhases() throws Exception {

    }

    @Test
    public void testGetScoresPhasesText() throws Exception {

    }

    @Test
    public void testFindWinner() throws Exception {

    }
}
