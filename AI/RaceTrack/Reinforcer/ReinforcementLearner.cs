using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{
    /// <summary>
    /// abstract base class for QLearning and Value-Iteration
    /// contains functions for generating state-space and for
    /// writing out training data
    /// </summary>
    public abstract class ReinforcementLearner
    {
        protected ValueNode[, , ,] ss;
        protected RaceTrack rt;
        protected static Random random = new Random();

        public void Run()
        {
            System.Threading.Thread t = new System.Threading.Thread(new System.Threading.ThreadStart(Go));
            t.Start();
        }
        private void Go()
        {
            while (true)
                IterateOnce();
        }

        public abstract int IterateOnce();

        public ValueNode[, , ,] Ss
        {
            get { return ss; }
            set { ss = value; }
        }

        public ReinforcementLearner(RaceTrack rt)
        {
            this.rt = rt;
            CreateStateSpace();
          
        }

        public Action GetBestAction(Vector2 pos, Vector2 vel)
        {
            return ss[pos.X, pos.Y, vel.X + 5, vel.Y + 5].BestAction;
        }

        void CreateStateSpace()
        {

            ss = new ValueNode[rt.Width, rt.Height, 11, 11];

            for (int i = 0; i < rt.Width; i++)
                for (int j = 0; j < rt.Height; j++)
                    for (int k = 0; k < 11; k++)
                        for (int l = 0; l < 11; l++)
                            ss[i, j, k, l] = new ValueNode();
        }

        public static int xBlockSize = 1;
        public static int yBlockSize = 1;
        public static int dxBlockSize = 1;
        public static int dyBlockSize = 1;

        public void WriteOut(System.IO.TextWriter sw)
        {
            sw.WriteLine("X, Y, DX, DY, Action");
            for (int i = 0; i < rt.Width; i++)
                for (int j = 0; j < rt.Height; j++)
                    for (int k = 0; k < 11; k++)
                        for (int l = 0; l < 11; l++)
                        {
                            if (rt.Squares[i, j] != SquareState.Wall)
                                sw.WriteLine("{0},{1},{2},{3},{4}",
                                     i,
                                    j , (k - 5), (l - 5), ss[i, j, k, l].BestAction.ToString() );
                        }
            
        }

        public void WriteOutTrainingData(string filename)
        {
            System.IO.StreamWriter sw = new System.IO.StreamWriter(filename);


            for (int i = 0; i < rt.Width; i++)
                for (int j = 0; j < rt.Height; j++)
                    for (int k = 0; k < 11; k++)
                        for (int l = 0; l < 11; l++)
                        {
                            if (rt.Squares[i, j] != SquareState.Wall)
                                sw.WriteLine("{0},{1},{2},{3},{4}",
                                    ss[i, j, k, l].BestActionIndex, i / xBlockSize,
                                    j / yBlockSize, (k - 5)/dxBlockSize, (l - 5)/dyBlockSize);
                        }
            sw.Close();
        }
    }
}
