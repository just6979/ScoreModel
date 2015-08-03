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

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    private Player testPlayer;
    private String testName = "Player 1";
    private String testInitials = "P1";
    private int testScore = 111;

    // implement the abstract class for testing
    private class SimplePlayer extends Player {
        public SimplePlayer(String _name) {
            super(_name);
        }
    }

    @Before
    public void setUp() throws Exception {
        testPlayer = new SimplePlayer(testName);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(String.format(
                        "Name '%s'; Score %s",
                        testName,
                        0
                ), testPlayer.toString()
        );
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(testName, testPlayer.getName());
    }

    @Test
    public void testSetName() throws Exception {
        String name = "Foo Bar";
        testPlayer.setName(name);
        assertEquals(name, testPlayer.getName());
        testPlayer.setName(testName);
    }

    @Test
    public void testGetInitials() throws Exception {
        assertEquals(testInitials, testPlayer.getInitials());
    }

    @Test
    public void testGetScore() throws Exception {
        assertEquals(0, testPlayer.getScore());
    }

    @Test
    public void testSetScore() throws Exception {
        testPlayer.setScore(testScore);
        assertEquals(testScore, testPlayer.getScore());
    }
}