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

import java.util.Scanner;

import static net.justinwhite.scoreit_p10.Util.print;
import static net.justinwhite.scoreit_p10.Util.println;
import static net.justinwhite.scoreit_p10.Util.printlnln;

class Main {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        print("How many players? ");
        Integer numPlayers = stdin.nextInt();

        Game game = new Game(numPlayers);

        println("Show the whole game:");
        println(game.toString());

        println("Show players individually:");
        for (Integer i = 0; i < game.getNumPlayers(); i++) {
            Game.Player p;
            p = game.getPlayer(i);
            println(p.toString());
        }
        println();

        printlnln("Change player names to example data...");
        game.getPlayer(0).setName("Justin W");
        game.getPlayer(1).setName("Lauren K");
        game.getPlayer(2).setName("Timmay C");
        game.getPlayer(3).setName("Denise B");

        println("Show players via ArrayList iterator:");
        for (Game.Player p : game.getPlayerList()) {
            println(p.toString());
        }
        println();
    }
}
