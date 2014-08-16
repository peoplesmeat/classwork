using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{
    public class Decision
    {
        GameState gameState;
        Move move;
        public GameState GameState
        {
            get { return gameState; } 
        }

        public Move Move
        {
            get { return move; } 
        }
        public Decision(GameState g)
        {
            this.gameState = g; 
        }
        public Decision(GameState g, Move m) : this(g)
        {           
            this.move = m;             
        }
    }
    public class Agent
    {
        public GameState gameState;
        int totalNodesExpanded = 0;
        int suggesions = 0; 
        int depth;

        PlayerTurn playerTurn;
        PlayerTurn oPlayerTurn;

        bool alpha; 
        public Agent(GameState g, PlayerTurn pTurn, int d, bool alpha)
        {
            gameState = g;
            this.depth = d;
            this.alpha = alpha; 
            playerTurn = pTurn;
            if (playerTurn == PlayerTurn.Player1)
                oPlayerTurn = PlayerTurn.Player2;
            else
                oPlayerTurn = PlayerTurn.Player1;
        }

 

        void Print(List<TreeNode<Decision>> path, TreeNode<Decision> node)
        {
            if (node.Children.Count == 0)
            {
                //Leaf Print
                GameState.PrintBoard(node.Data.GameState); 
            }

            foreach (TreeNode<Decision> tn in node.Children)
                Print(null, tn); 
        }

        public int ScoreNode(Decision d)
        {

            //Their Turn
            int myMoves = d.GameState.CheckMoves(playerTurn).Count;
            int theirMoves = d.GameState.CheckMoves(oPlayerTurn).Count;

            if (theirMoves == 0)
                return 30;
            else if (myMoves == 0)
            {
                return -30;
            }
            else
            {
                return myMoves - theirMoves;
            }
 
        }

        public Move Suggest(out int score)
        {
            TreeNode<Decision> g = new TreeNode<Decision>(new Decision((GameState)gameState.Clone()));
            MiniMaxAlgo m = null;
            if (!alpha)
            {
                m = new MiniMax(g, depth, new ScoreNodeDelegate(this.ScoreNode));
            }
            else
            {
                m = new AlphaBetaMiniMax(g, depth, new ScoreNodeDelegate(this.ScoreNode)); 
            }
           
            m.Score();
            score = g.Score;
            suggesions++; 
            totalNodesExpanded += m.NodesExpanded;

            List<TreeNode<Decision>> aList = new List<TreeNode<Decision>>(g.Children.Count);
            for (int i = 0; i < g.Children.Count; i++)
            {
                if (g.Children[i].Score == g.Score)
                    aList.Add(g.Children[i]); 
            }
            score = g.Score;
            TreeNode<Decision> theMove = aList[new Random().Next(aList.Count)];
            return theMove.Data.Move; 
           
        }

        public double AverageNodesExpanded
        {
            get { return (double)totalNodesExpanded / (double)suggesions; } 
        }
        public int TotalNodesExpanded
        {
            get { return totalNodesExpanded; } 
        }
    }
}
