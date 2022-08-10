# sp21-cs242-assignment1

Game of Uno written in Java. 

## Design 

Split the code into 3 packages that handle different aspects of the game. 

1. `deck` - consists of the Card and CardDeck class
2. `game` - consists of the Game class
3. `player` - consists of the Player class

The `Card` class constructs different types of cards based on 3 parameters - color, type, and number. The `CardDeck` 
class initializes the draw and discard piles and allows modifications to both decks. The `Game` class runs a game and 
keeps track of the game state. The `Player` class keeps track of each player's hand and is given an id to 
differentiate amongst players.

## Testing

Conducted testing by configuring the IntelliJ IDEA for unit testing with jUnit. Tested every public method by using 
`assert()` functions.

## GUI

Created GUI using `javax.swing.*;` package. UI comprised of different scenes comprising of the following classes: 

1. Start Scene 
    - `Start` - start scene of the game, which allows the user to specify the number of players in the game
    - `AddAI` - scene allowing user to specify number of AIs in the game
2. In Game Scene
    - `GameState` - panel that controls features that change the game's state   
    - `Draw` - create a draw pile - deck of cards where players can draw cards
    - `Discard` - create a discard pile where player can see current game state, such as the color, number and 
  symbol of the latest card in the discard pile
    - `PlayerHand` - create a panel that displays the current player's hand
3. End Scene
    - `End` - game ending scene to indicate the winner


