using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{
    public delegate int ScoreNodeDelegate(Decision d);

    public abstract class MiniMaxAlgo
    {
        protected int maxDepth;
        protected TreeNode<Decision> root;

        protected ScoreNodeDelegate ScoreNode;


        protected int nodesExpanded = 0;

        public int NodesExpanded
        {
            get { return nodesExpanded; }         
        }
        protected int childrenScored = 0;
        public MiniMaxAlgo(TreeNode<Decision> root, int depth, ScoreNodeDelegate s)
        {

            maxDepth = depth;
            this.root = root;
            this.ScoreNode = s; 

        }

        public abstract int Score(); 
    }

    public class AlphaBetaMiniMax : MiniMaxAlgo
    {
        public AlphaBetaMiniMax(TreeNode<Decision> root, int depth, ScoreNodeDelegate s)
            : base(root, depth, s)
        {

        }

        private int MinValue(TreeNode<Decision> node, int alpha, int beta, int depth)
        {
            nodesExpanded++;
            List<Move> moves = node.Data.GameState.CheckMoves(node.Data.GameState.Player);
            if (moves.Count == 0 || depth >= maxDepth)
            {
                childrenScored++;

                node.Score = ScoreNode(node.Data);

                return node.Score;
            }

            foreach (Move m in moves)
            {
                GameState g = (GameState)node.Data.GameState.Clone();
                g.Move(m.Start.X, m.Start.Y, m.End.X, m.End.Y);
                TreeNode<Decision> t = new TreeNode<Decision>(new Decision(g, m));
                node.AddChild(t);
            }

            int v = int.MaxValue;
            foreach (TreeNode<Decision> n in node.Children)
            {
                v = Math.Min(v, MaxValue(n, alpha, beta, depth + 1));

                if (v < alpha)
                    break;
                beta = Math.Min(beta, v); 
            }
            node.Score = v;
            return v;

        }

        public int MaxValue(TreeNode<Decision> node, int alpha, int beta, int depth)
        {
            nodesExpanded++;
            List<Move> moves = node.Data.GameState.CheckMoves(node.Data.GameState.Player);
            if (moves.Count == 0 || depth >= maxDepth)
            {
                childrenScored++;

                node.Score = ScoreNode(node.Data);

                return node.Score;
            }

            foreach (Move m in moves)
            {
                GameState g = (GameState)node.Data.GameState.Clone();
                g.Move(m.Start.X, m.Start.Y, m.End.X, m.End.Y);
                TreeNode<Decision> t = new TreeNode<Decision>(new Decision(g, m));
                node.AddChild(t);
            }


            int v = int.MinValue;
            foreach (TreeNode<Decision> n in node.Children)
            {
                v = Math.Max(v, MinValue(n, alpha, beta, depth + 1));

                if (v > beta)
                    break;
                alpha = Math.Max(alpha, v); 
            }
            node.Score = v;
            return v;
        }

        public override int Score()
        {
            root.Score = MaxValue(root, int.MinValue, int.MaxValue, 0);

            /*Console.WriteLine("Nodes {0}, Children {1}, Score {2}",
                NodesExpanded, childrenScored, root.Score);*/

            return root.Score;
        }
    }
    public class MiniMax : MiniMaxAlgo
    {


        private int MaxValue(TreeNode<Decision> node, int depth)
        {
            nodesExpanded++;
            List<Move> moves = node.Data.GameState.CheckMoves(node.Data.GameState.Player);
            if (moves.Count == 0 || depth >= maxDepth)
            {
                childrenScored++;

                node.Score = ScoreNode(node.Data);

                return node.Score;
            }

            foreach (Move m in moves)
            {
                GameState g = (GameState)node.Data.GameState.Clone();
                g.Move(m.Start.X, m.Start.Y, m.End.X, m.End.Y);
                TreeNode<Decision> t = new TreeNode<Decision>(new Decision(g, m));
                node.AddChild(t);
            }


            int v = int.MinValue;
            foreach (TreeNode<Decision> n in node.Children)
            {
                v = Math.Max(v, MinValue(n, depth + 1));
            }
            node.Score = v;
            return v;
        }

        private int MinValue(TreeNode<Decision> node, int depth)
        {
            nodesExpanded++;
            List<Move> moves = node.Data.GameState.CheckMoves(node.Data.GameState.Player);
            if (moves.Count == 0 || depth >= maxDepth)
            {
                childrenScored++;

                node.Score = ScoreNode(node.Data); 

                return node.Score;
            }

            foreach (Move m in moves)
            {
                GameState g = (GameState)node.Data.GameState.Clone();
                g.Move(m.Start.X, m.Start.Y, m.End.X, m.End.Y);
                TreeNode<Decision> t = new TreeNode<Decision>(new Decision(g, m));
                node.AddChild(t);
            }

            int v = int.MaxValue;
            foreach (TreeNode<Decision> n in node.Children)
            {
                v = Math.Min(v, MaxValue(n, depth + 1));
            }
            node.Score = v;
            return v;

        }

        public override int Score()
        {
            root.Score = MaxValue(root, 0);

           /* Console.WriteLine("Nodes {0}, Children {1}, Score {2}",
                NodesExpanded, childrenScored, root.Score);*/

            return root.Score;
        }


        public MiniMax(TreeNode<Decision> root, int depth, ScoreNodeDelegate s)
            : base(root, depth, s)
        {
        }
    }
}
