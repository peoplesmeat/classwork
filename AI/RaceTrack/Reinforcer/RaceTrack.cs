using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{


    /// <summary>
    /// Represents the race track, contains functions for moving a car
    /// and determining cost of moves
    /// </summary>
    public class RaceTrack
    {
        SquareState[,] track;
        bool resetOnCrash = false;
        private static Random random = new Random(); 




        public RaceTrack(SquareState[,] track)
        {
            this.track = track; 
        }

        /// <summary>
        /// Determine a list of intermediate nodes, given a start square
        /// and a velocity according to a Bresenham's algorithm
        /// </summary>
        /// <param name="i_x1"></param>
        /// <param name="i_y1"></param>
        /// <param name="i_x2"></param>
        /// <param name="i_y2"></param>
        /// <returns></returns>
        public List<Vector2> GetAllIntermediateSquares(
    int i_x1, int i_y1, int i_x2, int i_y2)
        {
            int steps = 20;
            List<Vector2> positions = new List<Vector2>();

            double x1 = i_x1 + 0.5, y1 = i_y1 + 0.5, x2 = i_x2 + 0.5, y2 = i_y2 + 0.5;
            double dxf = (x2 - x1) / (double)steps;
            double dyf = (y2 - y1) / (double)steps;

            for (int i = 0; i < steps; i++)
            {
                double tx = x1 + dxf * i;
                double ty = y1 + dyf * i;
                bool add = true;
                foreach (Vector2 p in positions)
                {
                    if (p.X == (int)tx && p.Y == (int)ty)
                    {
                        add = false;
                    }
                }
                if (add)
                {
                    positions.Add(new Vector2(
                        (int)(tx), (int)(ty)
                        ));
                }
            }
            return positions;
        }

        public bool ResetOnCrash
        {
            get { return resetOnCrash; }
            set { resetOnCrash = value; }
        }

        public SquareState[,] Squares
        {
            get { return track; } 
        }

        public int Width
        {
            get
            {
                return track.GetLength(0); 
            }
        }

        public int Height
        {
            get
            {
                return track.GetLength(1);
            }
        }

        public List<Vector2> GetStates(SquareState toMatch)
        {
            List<Vector2> startStates = new List<Vector2>(); 
            for (int i = 0; i < this.Squares.GetLength(0); i++)
            {
                for (int j = 0; j < this.Squares.GetLength(1); j++)
                {
                    if (Squares[i, j] == toMatch)
                    {
                        startStates.Add(new Vector2(i, j));
                    }

                }
            }
            return startStates;  
        }

        public List<Vector2> GetAllvalid()
        {
            List<Vector2> a = GetStates(SquareState.Finish);
            a.AddRange(GetStates(SquareState.Road));
            a.AddRange(GetStates(SquareState.Start));
            return a;
        }



        public Vector2 FindStartSquare()
        {
            List<Vector2> startStates = GetStates(SquareState.Start);
            return startStates[random.Next(startStates.Count)]; 
          
        }

        public void Move(ref Vector2 pos, ref Vector2 vel)
        {
            List<Vector2> positions = GetAllIntermediateSquares(pos.X, pos.Y, pos.X + vel.X, pos.Y + vel.Y);


            for (int i=0;i<positions.Count; i++) 
            {
                SquareState s  =this.track[positions[i].X, positions[i].Y] ;
                if (s == SquareState.Finish)
                {
                    pos.X = positions[i].X;
                    pos.Y = positions[i].Y;
                    return; 
                }
                else if (s == SquareState.Wall)
                {
                    
                    if (!resetOnCrash)
                    {
                        vel.X = 0;
                        vel.Y = 0;
                        pos.X = positions[i - 1].X;
                        pos.Y = positions[i - 1].Y;
                    }
                    else
                    {
                        vel.X = 0;
                        vel.Y = 0;
                        Vector2 newPos = FindStartSquare();
                        pos.X = newPos.X;
                        pos.Y = newPos.Y; 


                    }
                    return; 
                }
              
            }
            pos.X = positions[positions.Count - 1].X;
            pos.Y = positions[positions.Count - 1].Y;
        }

        public int Cost(int x, int y, int dx, int dy)
        {
            List<Vector2> positions = GetAllIntermediateSquares(x, y, x + dx, y + dy);
            foreach (Vector2 p in positions)
            {
                if (this.Squares[p.X, p.Y] == SquareState.Wall)
                    return 1; 
                if (this.Squares[p.X, p.Y] == SquareState.Finish)
                    return 0;

               
            }
            return 1; 
        }

        public static void ApplyProbabalistically(Vector2 speed, Vector2 acceleration)
        {
            if (random.NextDouble() > 0.9)
                return; 
            if (speed.X + acceleration.X <= 5 &&
                speed.X + acceleration.X >= -5)
                speed.X += acceleration.X;
            if (speed.Y + acceleration.Y <= 5 &&
                speed.Y + acceleration.Y >= -5)
                speed.Y += acceleration.Y;
        }

        public static void Apply(Vector2 speed, Vector2 acceleration)
        {
            if (speed.X + acceleration.X <= 5 &&
                speed.X + acceleration.X >= -5)
                speed.X += acceleration.X;
            if (speed.Y + acceleration.Y <= 5 &&
                speed.Y + acceleration.Y >= -5)
                speed.Y += acceleration.Y; 
        }



   
    }
}
