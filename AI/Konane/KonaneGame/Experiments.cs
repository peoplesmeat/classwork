using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using KonaneLib;
namespace KonaneGame
{
    class Experiments
    {
        static void Experiment2()
        {
            int maxDepth = 5;
            int boardSize = 8;
            int trials = 20;
            double[,] d = new double[maxDepth, maxDepth];
            int games = 0;
            for (int i = 1; i < maxDepth; i++)
            {
                for (int j = 1; j < maxDepth; j++)
                {
                    for (int k = 0; k < trials; k++)
                    {

                        KonaneMatch m = new KonaneMatch(boardSize, i, j, true, true);

                        if (m.Run() == PlayerTurn.Player2)
                        {
                            d[i, j] += 1;
                        }
                        games++;
                    }
                }
            }

            for (int i = 1; i < maxDepth; i++)
            {
                Console.Write(i + " & ");
                for (int j = 1; j < maxDepth; j++)
                {
                    Console.Write(" " + (d[i, j] / trials).ToString("F"));
                    if (j < maxDepth - 1)
                        Console.Write(" & ");
                }
                Console.WriteLine(" \\\\");
                Console.WriteLine("\\hline");
            }



            Console.ReadLine();


        }

        static void Experiment()
        {
            int p1 = 0;
            int p2 = 0;

            int maxDepth = 1;
            int boardSize = 4;

            for (maxDepth = 7; maxDepth < 9; maxDepth++)
            {
                boardSize = 4;
                KonaneMatch m = new KonaneMatch(boardSize, maxDepth, maxDepth, false, true);
                m.Run();

                KonaneMatch m2 = new KonaneMatch(boardSize, maxDepth, maxDepth, true, false);
                m2.Run();

                Console.WriteLine("Depth:{0} Size:{1} Minimax:{2} AlphaBeta: {3}",
                    maxDepth, boardSize, (m.Player1.TotalNodesExpanded + m2.Player2.TotalNodesExpanded) / 2.0,
                    (m.Player2.TotalNodesExpanded + m2.Player1.TotalNodesExpanded) / 2.0);

                boardSize = 6;
                m = new KonaneMatch(boardSize, maxDepth, maxDepth, true, true);
                m.Run();

                m2 = new KonaneMatch(boardSize, maxDepth, maxDepth, true, true);
                m2.Run();

                Console.WriteLine("Depth:{0} Size:{1} Minimax:{2} AlphaBeta: {3}",
                    maxDepth, boardSize, (m.Player1.TotalNodesExpanded + m2.Player2.TotalNodesExpanded) / 2.0,
                    (m.Player2.TotalNodesExpanded + m2.Player1.TotalNodesExpanded) / 2.0);


            }
            Console.WriteLine("Player 1 Wins: " + p2 + "   Player 2 Wins: " + p1);

            /* for (maxDepth = 6; maxDepth < 8; maxDepth++)
 {
     boardSize = 4;
     KonaneMatch m = new KonaneMatch(boardSize, maxDepth, maxDepth, false, true);
     m.Run();

     KonaneMatch m2 = new KonaneMatch(boardSize, maxDepth, maxDepth, true, false);
     m2.Run();

     Console.WriteLine("Depth:{0} Size:{1} Minimax:{2} AlphaBeta: {3}",
         maxDepth, boardSize, (m.Player1.TotalNodesExpanded + m2.Player2.TotalNodesExpanded) / 2.0,
         (m.Player2.TotalNodesExpanded + m2.Player1.TotalNodesExpanded) / 2.0);

     boardSize = 6;
     m = new KonaneMatch(boardSize, maxDepth, maxDepth, false, true);
     m.Run();

     m2 = new KonaneMatch(boardSize, maxDepth, maxDepth, true, false);
     m2.Run();

     Console.WriteLine("Depth:{0} Size:{1} Minimax:{2} AlphaBeta: {3}",
         maxDepth, boardSize, (m.Player1.TotalNodesExpanded + m2.Player2.TotalNodesExpanded) / 2.0,
         (m.Player2.TotalNodesExpanded + m2.Player1.TotalNodesExpanded) / 2.0);

     boardSize = 8;
     m = new KonaneMatch(boardSize, maxDepth, maxDepth, true, true);
     m.Run();

     m2 = new KonaneMatch(boardSize, maxDepth, maxDepth, true, true);
     m2.Run();

     Console.WriteLine("Depth:{0} Size:{1} Minimax:{2} AlphaBeta: {3}",
         maxDepth, boardSize, (m.Player1.TotalNodesExpanded + m2.Player2.TotalNodesExpanded) / 2.0,
         (m.Player2.TotalNodesExpanded + m2.Player1.TotalNodesExpanded) / 2.0);
 }*/
            /*for (int i = 0; i < 1; i++)
            {
                KonaneMatch m = new KonaneMatch(4,1,1 ,false, true);
                if (m.Run() == PlayerTurn.Player1)
                    p1++;
                else
                    p2++; 
               

            }*/
            /*  for (int i = 0; i < 1; i++)
              {
                  KonaneMatch m = new KonaneMatch(6, 1, 5, true);
                  if (m.Run() == PlayerTurn.Player1)
                      p1++;
                  else
                      p2++;

              }*/
        }
    }
}
