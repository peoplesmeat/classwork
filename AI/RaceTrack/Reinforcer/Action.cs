using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{
    /// <summary>
    /// Represents a possible action, which in this 
    /// case is an acceleration
    /// </summary>
    public class Action
    {
        public int Dx;
        public int Dy;
        public Action(int x, int y)
        {
            Dx = x;
            Dy = y;
        }

        public override string ToString()
        {
            return string.Format("({0},{1})", Dx, Dy);
        }
    }
}
