using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{
    public enum PositionState
    {
        Black, White, Empty
    }

    public class Board : ICloneable
    {
        PositionState[,] positions;
        int size;


        public object Clone()
        {
            PositionState[,] p = new PositionState[size, size]; 
            Array.Copy(positions, p, p.Length);
            Board b = new Board(p);
            b.size = size;
            return b; 
        }

        public PositionState[,] Positions
        {
            get { return positions; }
        }

        public Board(PositionState[,] p)
        {
            this.positions = p;
            this.size = p.GetLength(0); 
        }
        public Board(int size)
        {
            positions = new PositionState[size, size];
            this.size = size;
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    if ((i + j) % 2 == 0)
                        positions[i, j] = PositionState.Black;
                    else
                        positions[i, j] = PositionState.White;

                }
            }
        }
        public int Size
        {
            get { return size; }
        }
        public void SetState(int x, int y, PositionState p)
        {
            positions[x, y] = p;
        }

        public bool IsValidMove(int x, int y, int x1, int y1)
        {
            if (x < 0 || y < 0 || x1 < 0 || y1 < 0 ||
                x >= size || y >= size || x1 >= size || y1 >= size)
            {
                return false;
            }
            PositionState pState = positions[x, y];
            if (pState == PositionState.Empty)
            {
                return false;
            }

            if (x != x1)
            {
                if (y != y1)
                {
                    return false;
                }

                //Moving Horizontally
                int distance = Math.Abs(x - x1);
                if ((distance % 2) != 0)
                    return false;
                int dir = Math.Sign(x1 - x);
                for (int i = 0; i < distance / 2; i++)
                {
                    if (positions[x + (1 + (i * 2)) * dir, y] == PositionState.Empty ||
                        positions[x + (2 + (i * 2)) * dir, y] != PositionState.Empty)
                    {
                        return false;
                    }
                }

                return true;

            }

            if (y != y1)
            {
                if (x != x1)
                {
                    return false;
                }
                //Moving Vertically
                int distance = Math.Abs(y - y1);
                if ((distance % 2) != 0)
                    return false;

                int dir = Math.Sign(y1 - y);
                for (int i = 0; i < distance / 2; i++)
                {
                    if (positions[x, y + (1 + (i * 2)) * dir] == PositionState.Empty ||
                        positions[x, y + (2 + (i * 2)) * dir] != PositionState.Empty)
                    {
                        return false;
                    }
                }

                return true;
            }

            return false;
        }
    }
}
