/*
 Copyright (c) 2015. Justin White CC-BY-SA
 */
package net.justinwhite.scoreit;

import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);

        print("Name your game: ");
        String name = stdin.nextLine();

        print("How many players? ");
        Integer numPlayers = stdin.nextInt();

        Game game = new Game(name, numPlayers);

        println(game.toString());
    }

    private static void print(String msg) {
        System.out.print(msg);
    }

    private static void println(String msg) {
        System.out.println(msg);
    }

    private static void println() {
        System.out.println();
    }
}
