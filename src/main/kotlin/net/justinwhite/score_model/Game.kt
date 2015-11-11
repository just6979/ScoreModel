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

package net.justinwhite.score_model

import java.text.SimpleDateFormat
import java.util.*

open class Game<T : Player>
constructor(private val playerClass: Class<T>, var _numPlayers: Int = Game.MIN_PLAYERS, _name: String?) {

    val ID: UUID
    private val playerList: MutableList<T>
    private var numPlayers: Int = 0
    // set the game name. If passed empty string or null, use buildName() to create name based current DateTime
    var name = _name
        set(value) {
            field = if (value == null || value == "") {
                Companion.buildName()
            } else {
                value
            }
        }
    private var _winner: T? = null
    open public var winner: T?
        get() {
            checkWinner()
            return _winner
        }
        set(value) {
            _winner = value
        }

    init {
        ID = UUID.randomUUID()
        // bounds check number of players
        if (_numPlayers < MIN_PLAYERS) {
            _numPlayers = MIN_PLAYERS
        } else if (_numPlayers > MAX_PLAYERS) {
            _numPlayers = MAX_PLAYERS
        }

        playerList = ArrayList<T>()
        setNumPlayers(_numPlayers)
    }

    constructor(_playerClass: Class<T>, _numPlayers: Int = Game.MIN_PLAYERS)
    : this(_playerClass, _numPlayers, "")

    constructor(_playerClass: Class<T>)
    : this(_playerClass, 0, "")

    override fun toString(): String {
        return "Game: %s\nUUID: %s\nPlayer count: %d\nPlayers: %s".format(name, ID, numPlayers, // List<> class handles toString()
                playerList)
    }

    fun getNumPlayers(): Int? {
        return numPlayers
    }

    fun getPlayerList(): List<T> {
        return playerList
    }

    // If increasing count, create a new blank player; if decreasing, delete last player
    fun setNumPlayers(newNumPlayers: Int): Boolean? {
        // make sure requested count is within limits
        if (newNumPlayers < MIN_PLAYERS || newNumPlayers > MAX_PLAYERS) {
            return false
        }
        // increasing count, add players
        if (newNumPlayers > numPlayers) {
            for (i in numPlayers..newNumPlayers - 1) {
                addPlayer()
            }
        }
        // decreasing count, remove players
        if (numPlayers > newNumPlayers) {
            for (i in numPlayers downTo newNumPlayers + 1) {
                removePlayer()
            }
        }
        // record new count
        numPlayers = playerList.size
        return true
    }

    // check if there is a player at the given index
    fun checkPlayer(index: Int): Boolean? {
        return index > 0 && index <= numPlayers && playerList[index] != null
    }

    // make a new Player [or subclass of player] using newInstance() and set it up for use
    private fun makePlayer(_name: String): T? {
        val newPlayer: T
        // to make a new Player [or subclass], use the class's instance factory
        try {
            newPlayer = playerClass.newInstance()
        } catch (e: InstantiationException) {
            // if the factory can't make a new object, something major is wrong
            e.printStackTrace()
            return null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return null
        }

        // successful instantiation, continue setting up the Player [or subclass]
        newPlayer.name = _name
        return newPlayer
    }

    // add a new player using _name
    @JvmOverloads fun addPlayer(_name: String = "Player " + (numPlayers + 1)): T? {
        if (numPlayers < MAX_PLAYERS) {
            val newPlayer = makePlayer(_name)
            playerList.add(newPlayer as T)
            numPlayers = playerList.size
            return newPlayer
        } else {
            return null
        }
    }

    // remove and return player specified by index. return null if index out of bounds
    @JvmOverloads fun removePlayer(index: Int? = numPlayers - 1): T? {
        if (numPlayers > MIN_PLAYERS) {
            val oldPlayer = playerList.removeAt(index!!.toInt())
            numPlayers = playerList.size
            return oldPlayer
        } else {
            return null
        }
    }

    // return player specified by index or null if index out of bounds
    fun getPlayer(index: Int): T? {
        if (0 <= index && index < playerList.size) {
            return playerList[index]
        } else {
            return null
        }
    }

    // return an array of raw scores
    val scores: List<Int>
        get() {
            val scores = ArrayList<Int>(playerList.size)
            for (p in playerList) {
                scores.add(p.score)
            }
            return scores
        }

    // return String of "Player1: Score1; Player2: Score2; etc"
    val scoresText: String
        get() {
            var out = ""
            for (p in playerList) {
                out += "%s: %d Points; ".format(p.name, p.score)
            }
            return out
        }

    // tie-breaker is which ever player was added to the game first
    open fun checkWinner(): Boolean? {
        val _players = ArrayList(playerList)
        Collections.sort(_players)
        _winner = _players[0]
        return _winner != null
    }

    companion object {
        val MIN_PLAYERS = 2
        val MAX_PLAYERS = 8

        fun buildName(): String {
            val format = "YYYY-MM-dd HH:mm"
            val sdf = SimpleDateFormat(format, Locale.US)
            return sdf.format(Date())
        }
    }

}// add a new player named "Player <X+1>"
// remove and return last player if no index specified
