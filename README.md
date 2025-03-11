# Java chess engine #
## minimax and alpha beta pruning ##

This is my project for my bachelors thesis and on the date of 21.2.2025 you can play against the bot implemented in the program. There is still work to be done but the bulk of the project is implemented and working. To be added is a checkmate indicator. Currently the program just runs untill you or the bot can no longer move. Also I have strugled with bugs when implementing en passant into the code so currently I have this rule commented out in the project. other than that the project is complete in its current form.

## how to play ##
#### player input ####
The program will ask for player input in the command line. It will ask what to move and where. the game squares are named and a name could be for example ___e3___ which means that the piece that is inrow ___3___ and column named ___e___.
#### input format ####
To give input you must use this format: ___e3 d4___. Which tells that I want to move the piece that is in square named ___e3___ to ___d3___.
#### The bot ####
As a default you are white and the bot is black. After first running the project and taking the first move a swhite the minimax algo will automatically start calculating its move. This process takes a few seconds. Aftrer the bot has moved it is your turn again, and this goes on untill you or the bot have been checkmated and cant move anymore.

### how the program works ####
After giving the input the program will first check if you have selected the right colored piece for the turn (meaning white or black) and if there is a piece in the given spot.
After verifying the piece that you want to move he program calculates all the possible moves for the piece and checks if your input move maches with the possible ones.
Assuming you picked a valid move the gameboard will update and it is the opponents time to move.

## Program logic ##

The program uses standard moving logic as in chess. Bot decision making is dome by a minimax search tree and alpha beta pruning. After finding a endstate for the game or a max depth the bot will evaluate who good the current board is relative to the maximizing player and minimizing.

### GUI.Board evaluation ###

The board evaluation is don by calculating and adding all the values of the players side and subtracting the oppionents. Also piece placement is been taken in account. I have used the same weight values as in this article: Madake, J., Deotale, C., Charde, G., & Bhatlawande, S. (2023) ___CHESS AI: Machine Learning and Minimax Based Chess Engine.___

## Objective of this project ##

I came up with this idea to test my coding skills and try to make a MinMax algo for chess by utilizing as least as possble 3rd party resourses like AI or Stack Overflow. I got into a habit of using mostly only AI to help me code, which made coding stale and boring for me.
I hope this project will revive my passion for coding and problem solving. Currently on the date of typing this (4.1.2025) I have used these resourses a hand full of times. AI for minor bug fixes and syntax checking in the begining of the project. Some move calculating methods for individual chess pieces

## Runing the code ##

This project was made in Intellij IDEA as a maven project. After making a maven project, it is ready for running.

### External libreries ###

I use the junit library for testin

pom.xml
```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
</dependency>
```
