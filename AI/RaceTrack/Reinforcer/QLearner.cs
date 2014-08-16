using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{
    /// <summary>
    /// The Q-Learning Algorithm
    /// </summary>
    public class QLearning : ReinforcementLearner
    {
        public static double learningRate = 0.9;

        public QLearning(RaceTrack rt)
            : base(rt)
        {
        }

        double temp = 100; 

        /// <summary>
        /// Boltzman exploration (not the most efficient implementation
        /// </summary>
        /// <param name="v"></param>
        /// <returns></returns>
        public int Boltzman(ValueNode v)
        {

            double sum = v.Vk.Sum();
            if (sum == 0)
                return random.Next(v.Actions.Count);

            double[] prob = new double[v.Actions.Count];
            if (temp>1) temp -= 0.00005; 
            for (int i = 0; i < v.Actions.Count; i++)
            {
                prob[i] = Math.Pow(Math.E, ((-v.Vk[i]) / temp)); 
            }
            sum = prob.Sum(); 
            for (int i = 0; i < v.Actions.Count; i++)
            {
                prob[i] = prob[i] / sum; 
            }
            
            for (int i = 1; i < prob.Length; i++)
            {
                prob[i] = prob[i] + prob[i - 1]; 
            }

            double r = random.NextDouble();
            int selection = 0;
            while (prob[selection] < r) selection++; 
            return selection; 
        }
        int totalNodes = 0; 

        /// <summary>
        /// One iteration of QLearning, which navigates from a random start 
        /// node to a finish node
        /// </summary>
        /// <returns>The number of nodes explored</returns>
        public override int IterateOnce()
        {
            Vector2 st1 = rt.FindStartSquare(); 
            Vector2 vt1 = new Vector2(0,0);
            int nodes = 0; 
            while (rt.Squares[st1.X, st1.Y] != SquareState.Finish)
            {
                Vector2 vt = new Vector2(vt1.X, vt1.Y); 
                Vector2 pt = new Vector2(st1.X, st1.Y);

                int a1 ;
                Action a;
                a1 = Boltzman(ss[st1.X, st1.Y, vt1.X + 5, vt1.Y + 5]);
                a = ss[st1.X, st1.Y, vt1.X + 5, vt1.Y + 5].Actions[a1];

                nodes++; 

                RaceTrack.ApplyProbabalistically(vt1, new Vector2(a.Dx, a.Dy)); 
                rt.Move(ref st1, ref vt1); 

                ValueNode qt = ss[pt.X, pt.Y, vt.X + 5, vt.Y + 5];
                ValueNode qt1 = ss[st1.X, st1.Y, vt1.X + 5, vt1.Y + 5];
                double q1 = qt.Vk[a1] + 0.5 * (rt.Cost(pt.X, pt.Y, vt.X, vt.Y) + 0.5 * (qt1.MinValue - qt.Vk[a1]));
                
                qt.SetVk(a,q1);
 

            }
            totalNodes += nodes; 
            return nodes; 
        }
        
       
    }
}
