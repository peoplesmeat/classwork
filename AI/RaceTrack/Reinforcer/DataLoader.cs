using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO; 
namespace Reinforcer
{
    /// <summary>
    /// The four possible race track squares
    /// </summary>
    public enum SquareState : byte
    {
        Wall, 
        Road,
        Start, 
        Finish

    }

    /// <summary>
    /// Loads a race track from a text file
    /// </summary>
    public static class DataLoader
    {

        private static string dataDirectory = "../../../data/";
        public static string LTrack = dataDirectory+"L-track.txt"; 
        public static string OTrack = dataDirectory+"O-track.txt"; 
        public static string RTrack = dataDirectory+"R-track.txt";

        private static SquareState ParseSquare(char c)
        {
            switch (c)
            {
                case '#': return SquareState.Wall;
                case '.': return SquareState.Road;
                case 'S': return SquareState.Start;
                case 'F': return SquareState.Finish; 
            }

            throw new Exception("Invalid Square " + c); 
        }
        public static RaceTrack LoadTrack(string track)
        {
            StreamReader sr = new StreamReader(track);
            string line = sr.ReadLine();
            string[] size = line.Split(new char[] { ',' });
            int height = int.Parse(size[0]);
            int width = int.Parse(size[1]);

            SquareState[,] board = new SquareState[width, height];
            for (int i = 0; i < height; i++) 
            {
                line = sr.ReadLine();
                for (int j = 0; j < width; j++)
                {
                    board[j, i] = ParseSquare(line[j]); 
                    
                }
            }

            return new RaceTrack(board); 
        }
    }
}
