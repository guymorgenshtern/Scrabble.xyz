# Scrabble.xyz

## How to run:
- Java version 15 or greater is needed to run Scrabble.xyz
- To play Scrabble.xyz you must execute the .jar file provided
---
## How to play:
1. When the game starts you will have the ability to do the following 1. Play Game - This starts a regular Scrabble.xyz game. 2. Load Game - This allows the user to play a pre-existing Scrabble.xyz game accepting only games with the naming convention of "_sxyz.ser" 3. Custom Game - This allows you to build a custom Scrabble.xyz game board! The possibilities are endless. 
2. BotPlayers are available to play with. At least one human player must play with the bot. In total, one to four players are needed to play Scrabble.xyz.
3. First, you will be asked to input how many players will be playing in total.
4. Second, you will be asked to input how many BotPlayers you would like to play with.
5. Next, each human player will be prompted to input a name for themselves.
6. Once that is done, the board is initialized, the players receive their letters, and the game of Scrabble begins.
---
## The ScrabbleModel:
- The current player will have their name, score, and hand displayed at the top of the window.
- In addition, all players in the game will have their names and score displayed at the top of the window. 
- If a player would like to construct a word, first click the letter from the hand that you would like to place, then select where you would like to place the letter. Once you have constructed your word, click end turn.
    - If you accidentally misplaced a letter, you can click the end turn button to try again.
    - If there are no new letters placed on the board when you click end turn, you will skip your turn.
- Currently, the criteria for a valid word are:
    - Player has all necessary letters for word they want to play.
    - None of the spots to be occupied by the word inputted are preoccupied. 
    - The word inputted is a valid word according to the word list.
- Upon a successful move, the board is updated and the player is dealt new letters to maintain a hand of 7 letters.
- The scrabbleModel currently continues with all players accumulating score until the letter bag runs out tiles OR if players have done 100 moves.
---
## Scrabble.xyz V4.0.0 Features

### Save
Once you play Scrabble.xyz you will have the ability to save your game progress by clicking the 
save button. This will then prompt you on what you would like to name your file after you have selected a location.

### Load
As mentioned above if you have a previously saved Scrabble.xyz game you will be able to load that game when you play Scrabble.xyz again
in the initial Scrabble.xyz welcome frame.

### Undo/Redo

The following feature allows real players to undo and redo their previous and or currently placed plays. This feature also takes into
account players scores, therefore affecting players scores.

### AI/Robot Players

Players will be able to face robot players if they choose to after inputting how many players they would like to play with.
Once this is done the game will begin. Do not underestimate the A.I's ability to play Scrabble.xyz it can win. In short the A.I/Robot Player
finds the longest word it can form given a word that is placed on the board. 
**NOTE: Players will not be able to undo/redo their turns if they are playing against robot players. THIS IS BY DESIGN**

---

## LICENSING AGREEMENT

This Game License Agreement ("Scrabble.XYZ") is made and entered into on December 9, 2022 by and between HASBRO.INC ("Hasbro") and [AEGF.SOLN] ("AEGF Solutions").

1. Grant of License. Licensor hereby grants to Licensee a limited, non-exclusive, non-transferable, 
revocable license to use, reproduce, distribute, and display the game [Scrabble.XYZ] and its components (Scrabble) for [commercial use].
2. Duration. This license shall remain in effect for [10 years].
3. Limitations. Licensee shall not use the Game for any purpose other than as expressly permitted by this Agreement, and shall not modify, adapt, translate, or create derivative works based on the Game without the prior written consent of Licensor. Licensee shall not sublicense, assign, or transfer this license or the Game to any third party without the prior written consent of Licensor.
4. Fees and Royalties. Licensee shall pay to Licensor a fee of [10, 000 USD] for the use of the Game, and shall also pay to Licensor [15%] of any revenues derived from the use of the Game.
5. Termination. Licensor may terminate this license at any time if Licensee breaches any of the terms of this Agreement. Upon termination, Licensee shall immediately cease all use of the Game and shall destroy all copies of the Game in its possession.
6. Warranties and Disclaimers. Licensor warrants that it has the right to grant the license granted in this Agreement. THE GAME IS PROVIDED "AS IS," AND LICENSOR DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
7. Indemnification. Licensee shall indemnify, defend, and hold Licensor harmless from and against any and all claims, damages, losses, liabilities, and expenses arising from or related to the use of the Game by Licensee.
8. Governing Law. This Agreement shall be governed by and construed in accordance with the laws of the Province of [Ontario].
9. Entire Agreement. This Agreement constitutes the entire agreement between the parties with respect to the subject matter hereof, and supersedes all prior and contemporaneous agreements and understandings, both written and oral. This Agreement may not be amended or modified except in writing signed by both parties.

**IN WITNESS WHEREOF, the parties have executed this Agreement as of the date first written above.**

Licensor: HASBRO.INC
Licensee: [AEGF.SOLN]


