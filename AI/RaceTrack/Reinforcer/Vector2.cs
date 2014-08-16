using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{
    /// <summary>
    /// Utility class for representing a two dimensional 
    /// integer vector, useful for positions, velocities and 
    /// accelerations
    /// </summary>

    public class Vector2
    {
        int x;

        public int X
        {
            get { return x; }
            set { x = value; }
        }
        int y;

        public int Y
        {
            get { return y; }
            set { y = value; }
        }

        public Vector2(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

    }
}
