using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{
    public class Position
    {
        int x, y;

        public int Y
        {
            get { return y; }
            set { y = value; }
        }

        public int X
        {
            get { return x; }
            set { x = value; }
        }
        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
