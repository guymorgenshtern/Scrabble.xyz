# Scrabble.xyz

## How to run:
- Java version 15 or greater is needed to run Scrabble.xyz
- To play the scrabbleModel, execute the .jar file provided and follow the initialization instructions when prompted

## How to play:
1. BotPlayers are available to play with. At least one human player must play with the bot. In total, one to four players are needed to play Scrabble.xyz.
2. First, you will be asked to input how many players will be playing in total.
3. Second, you will be asked to input how many BotPlayers you would like to play with.
4. Next, each human player will be prompted to input a name for themselves.
5. Once that is done, the board is initialized, the players receive their letters, and the game of Scrabble begins.

### The ScrabbleModel:
- The current player will have their name, score, and hand displayed at the top of the window.
- If a player would like to construct a word, first click the letter from the hand that you would like to place, then select where you would like to place the letter. Once you have constructed your word, click end turn.
    - If you accidentally misplaced a letter, you can click the end turn button to try again.
    - If there are no new letters placed on the board when you click end turn, you will skip your turn.
- Currently, the criteria for a valid word are:
    - Player has all necessary letters for word they want to play.
    - None of the spots to be occupied by the word inputted are preoccupied. 
    - The word inputted is a valid word according to the word list.
- Upon a successful move, the board is updated and the player is dealt new letters to maintain a hand of 7 letters.
- The scrabbleModel currently continues with all players accumulating score until the letter bag runs out tiles.
