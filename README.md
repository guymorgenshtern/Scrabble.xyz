# Scrabble.xyz

## How to run:
- Java version 15 or greater is needed to run Scrabble.xyz
- To play the scrabbleModel, execute the .jar file provided and follow the instructions in the terminal

## How to play:
1. 2-4 are needed to play Scrabble.xyz
2. After launching, follow along with the prompts outputted in the terminal
3. First, you will be asked to input how many players will be playing
4. Next, each player will be prompted to input a display name for themselves
5. Once that is done, the board is initialized, the players recieve their letters, and the scrabbleModel begins

### The scrabbleModel:
- The player whose turn it currently is will have their name, score, and rack displayed on screen
- The player is prompted to input a word they would like to play, if it will be played horizontally or vertically, and the starting coordinates of the word
- The coordinates can be found along the left and top side of the board
- Currently, the criteria for a valid word are:
    - Player has all necessary letters for word they want to play
    - None of the spots to be occupied by the word inputted are already occupied 
    - The word inputted is a valid word according to the word list 
- Upon a successful move, the board is updated and the player is dealt a few new letters to keep their rack at 7 letters 
- The board will be reprinted with the updated words
- The next player will be prompted to input a word and the scrabbleModel repeats
- The scrabbleModel currently continues with both players accumilating score until the bank of letters runs out
