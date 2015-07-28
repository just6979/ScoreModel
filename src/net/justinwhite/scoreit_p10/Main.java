/*
 Copyright (c) 2015 Justin White <jw@justinwhite.net>
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright notice,
 this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 3. Neither the name of the copyright holder nor the names of its
 contributors may be used to endorse or promote products derived from this
 software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 */

package net.justinwhite.scoreit_p10;

/*
This is pretty much a big unit test for the whole Game class.

Eventually this will be turned into a real unit test (junit likely)
to allow tools to run the tests even from within other projects.
For now, just build this project to "run the tests".
*/

import static net.justinwhite.scoreit_p10.Util.println;
import static net.justinwhite.scoreit_p10.Util.printlnln;

class Main {

    public static void main(String[] args) {
        Integer numPlayers = 4;

        Game game = new Game(numPlayers);

        println("Show the whole game:");
        println(game.toString());

        println("Show players individually:");
        for (Integer i = 0; i < game.getNumPlayers(); i++) {
            Player p;
            p = game.getPlayer(i);
            println(p.toString());
        }
        println();

        println("Use example player names...");
        String example_players[] = {"Justin W", "Lauren K", "Timmay C", "Denise B"};
        for (Integer i = 0; i < numPlayers; i++) {
            game.renamePlayer(String.format("Player %d", i + 1), example_players[i]);
        }

        println("New game name:");
        printlnln(game.getName());

        println("Show players via ArrayList iterator:");
        for (Player p : game.getPlayerList()) {
            println(p.toString());
        }
        println();

        Player JW = game.getPlayerByName("Justin W");
        Player LK = game.getPlayerByName("Lauren K");
        Player TC = game.getPlayerByName("Timmay C");
        Player DB = game.getPlayerByName("Denise B");

        JW.addScore(100);
        LK.addScore(10);
        LK.nextPhase();
        TC.addScore(80);
        DB.addScore(40);

        println("Round 1");
        println(game.getScores());

        JW.addScore(100);
        LK.addScore(0);
        LK.nextPhase();
        TC.addScore(60);
        DB.addScore(50);
        DB.nextPhase();

        println("Round 2");
        println(game.getScores());

        LK.nextPhase();
        LK.nextPhase();
        LK.nextPhase();
        LK.nextPhase();
        LK.nextPhase();
        LK.nextPhase();
        LK.nextPhase();
        LK.nextPhase();

        printlnln("...");
        println("Round 10");
        println(game.getScores());

        if (game.hasWinner()) {
            println("Winner:");
            println(game.getWinner().getName());
        }

    }
}
