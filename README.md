# Chess game #

#### this is my effort of implementing a chess engine using a MinMaxing algorythem ####

Currently the program has implemented the game logic as ground work before it is possible to implement the bot to play aggainst.

## how to play ##
#### player input ####
The program will ask for player input in the command line. It will ask what to move and where. the game squares are named and a name could be for example ___e3___ which means that the piece that is inrow ___3___ and column named ___e___.
#### input format ####
To give input you must use this format: ___e3 d4___. Which tells that I want to move the piece that is in square named ___e3___ to ___d3___.

### how the program works ####
After giving the input the program will first check if you have selected the right colored piece for the turn (meaning white or black) and if there is a piece in the given spot.
After verifying the piece that you want to move he program calculates all the possible moves for the piece and checks if your input move maches with the possible ones.
Assuming you picked a valid move the gameboard will update and it is the opponents time to move.

## Program logic ##

### Cheking logic ###

After a valid move the program will check if the oponents king is checked in this new location by calculating the possible moves for a second time and if a second move is able to capture the king then there is a cheking situation
The opponent that is checked can not make an illegal move if the king is checked. Possible moves are only to move the king to a safe place or capture the checking piece.
