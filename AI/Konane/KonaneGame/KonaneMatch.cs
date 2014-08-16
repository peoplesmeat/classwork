using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using KonaneLib; 

namespace KonaneGame
{
    /***
     * This class is used to match to computer players against each other
     */
    class KonaneMatch
    {
        Agent player1;
        Agent player2;
        GameState gameState;
        public KonaneMatch(int boardSize, int p1Depth, int p2Depth, bool alphap1, bool alphap2)
        {
            gameState = new GameState(boardSize);
            player1 = new Agent(gameState, PlayerTurn.Player1, p1Depth, alphap1);
            player2 = new Agent(gameState, PlayerTurn.Player2, p2Depth, alphap2);
        }

        public Agent Player1
        {
            get { return player1; }
        }

        public Agent Player2
        {
            get { return player2; }
        }
        void MakeComputerMove(Agent a)
        {
            //Player 2 is a computer
            int score = 0;
            Move m = a.Suggest(out score);

            //Console.WriteLine(gameState.Player + " Move ({0},{1}) to ({2},{3}) (with Score {4})", m.Start.X, m.Start.Y,
            //                    m.End.X, m.End.Y, score);

            gameState.Move(m.Start.X, m.Start.Y, m.End.X, m.End.Y);
            //GameState.PrintBoard(gameState);
        }

        bool MakeMove()
        {
            if (gameState.Player == PlayerTurn.Player1 &&
                player1 != null)
            {
                MakeComputerMove(player1);
            }
            else if (gameState.Player == PlayerTurn.Player2 &&
                player2 != null)
            {
                MakeComputerMove(player2);
            }
            //GameState.PrintBoard(gameState); 
            return true;
        }
        public PlayerTurn Run()
        {
            while (true)
            {
                if (!MakeMove())
                    break;
                if (gameState.CheckMoves(gameState.Player).Count == 0)
                {
                    // Console.WriteLine(gameState.Player + " Loses!");
                    // Console.WriteLine("Player 1 Nodes ({0}), Avg Nodes ({1})", player1.TotalNodesExpanded, player1.AverageNodesExpanded);
                    // Console.WriteLine("Player 2 Nodes ({0}), Avg Nodes ({1})", player2.TotalNodesExpanded, player2.AverageNodesExpanded); 
                    return gameState.Player;

                }
            }

            return gameState.Player;
        }
    }
}
