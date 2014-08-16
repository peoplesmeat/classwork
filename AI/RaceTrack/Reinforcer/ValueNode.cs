using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{
    /// <summary>
    /// Utility class for managing state space. Each node in 
    /// state space contains a ValueNode, which contains a list 
    /// of actions as well as values for each action. This is stored as 
    /// utility in ValueIteration and Q-Values in Q-Learning. Contains some
    /// locking mechanisms to allow for multi-threaded access
    /// 
    /// For this problem, there are 9 actions for x,y accleration in the range
    /// of (-1,1)
    /// </summary>
    public class ValueNode
    {
        private static Random random = new Random();
        
        /// <summary>
        /// Possible actions
        /// </summary>
        List<Action> actions = new List<Action>();
        
        /// <summary>
        /// Value associated with each action
        /// </summary>
        List<double> vk = new List<double>();

        public List<Action> Actions
        {
            get { return actions; }
            set { actions = value; }
        }


        public double MinValue
        {
            get
            {
                lock (vk)
                {
                    return vk.Min();
                }
            }
        }

        public void SetVk(int i, double d)
        {
            lock (vk)
            {
                vk[i] = d;
            }
        }

        public void SetVk(Action a, double d)
        {
            lock (vk)
            {
                for (int i = 0; i < actions.Count; i++)
                    if (actions[i] == a)
                    {
                        vk[i] = d;
                        return;
                    }
            }
            throw new Exception("Action Not Found");
        }

        public void SetAllVk(double val)
        {
            for (int i = 0; i < vk.Count; i++)
                vk[i] = val;
        }
        public List<double> Vk
        {
            get { return vk; }
            set { vk = value; }
        }

        public Action SelectRandomAction()
        {
            Random random = new Random();
            return actions[random.Next(actions.Count)];
        }

        public Action RandomAction()
        {
            return actions[random.Next(actions.Count)]; 
        }

        public Action BestAction
        {
            get
            {
                double k = vk[0];
                Action bestAction = actions[0];
                for (int i = 0; i < vk.Count; i++)
                {
                    if (vk[i] < k)
                    {
                        k = vk[i];
                        bestAction = actions[i];
                    }
                }
                return bestAction;
            }
        }

        public int BestActionIndex
        {
            get
            {
                double k = vk[0];
                int bestAction = 0;
                for (int i = 0; i < vk.Count; i++)
                {
                    if (vk[i] < k)
                    {
                        k = vk[i];
                        bestAction = i;
                    }
                }
                return bestAction;
            }
        }

        public ValueNode()
        {
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                {
                    actions.Add(new Action(i, j));
                    vk.Add(0.0);
                }
        }
    }
}
