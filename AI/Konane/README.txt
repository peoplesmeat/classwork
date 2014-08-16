Konane
Bill Davis

This is an implementation of a Konane Game Playing Agent. 
It is implementated as two visual studio projects

KonaneGame - Is a command line project which handles input validation
and setting up the actual game

KonaneLib - Contains all Konane specific logic like Board, GameState, 
Moves and the Minimax implementations. 

The actual Minimax Algorithm and Alpha-Beta pruning implementation resides in 
KonaneLib\MiniMax.cs. The scoring Algorithm is located in KonaneLib\Agent.cs

Here is a brief description of all the files.
KonaneGame\Experiments - Functions which set up various game scenarios for 
experimental purposes

KonaneGame\KonaneGame - A class which handles prompting the user for game information
and then running the game including gathering the moves and executing the moves of 
computer players

KonaneGame\KonaneMatch - A class which is used for experimental purposes to pit
two computer players against each other and determine the winner

KonaneGame\Program - Main, contains wrapper to launch either experiments or the 
actual Game


KonaneLib\Agent - An agent class. Contains the scoring algorithm and a suggest 
function which returns the best move to make given the current board state

KonaneLib\Board - Keeps track of current board state, including all of the 
states of the squares on the board

KonaneLib\GameState - Keeps track of the game state including the current turn, 
the available moves for the current player

KonaneLib\Minimax - The Minimax and Minimax with Alpha-Beta pruning Implementations

KonaneLib\Move - Helper class which provides data on a specific Konane Move

KonaneLib\Position - Helper class which operates like a vector

KonaneLib\TreeNode - Helper classes which is used in the Minimax algorithm to enumerate 
the search tree

The program can be built with either Visual C# 2008 Express 
(available at http://www.microsoft.com/express/) 
or by installing the .Net Framework V3.5 and executing the command

msbuild

From the Konane directory. The executable will then be located in KonaneGame\bin\debug

I am also including the executable. 