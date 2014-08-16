using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using KonaneLib; 
namespace KonaneGame
{

    class KonaneGame
    {
        static Agent player1;
        static Agent player2;
        static GameState gameState;
        static void AssignPlayers()
        {
            Console.WriteLine("Assign Players");
            Console.WriteLine("Player 1: (C)omputer, (H)uman");
            string line = Console.ReadLine();
            while (line.Length == 0 ||
                (line.ToLower()[0] != 'c' &&
                 line.ToLower()[0] != 'h'))
            {
                Console.WriteLine("Incorrect Key, Player 1: (C)omputer, (H)uman");
                line = Console.ReadLine();
            }
            if (line.ToLower()[0] == 'c')
            {
                int depth = 0;
                do
                {
                    Console.WriteLine("Enter Depth: ");
                    line = Console.ReadLine();
                    int.TryParse(line, out depth);
                } while (depth == 0);

                Console.WriteLine("(m)inimax or (a)lpha Beta: "); 
                line = "";
                do
                {
                    line = Console.ReadLine();
                } while (line.Length < 1 && line.ToLower()[0] != 'a' &&
                line.ToLower()[0] != 'm');
                player1 = new Agent(gameState, PlayerTurn.Player1, depth,
                    line.ToLower()[0] == 'a' ? true : false);
            }
            else
            {
                player1 = null;
            }
            Console.WriteLine("Player 2: (C)omputer, (H)uman");
            line = Console.ReadLine();
            while (line.Length == 0 ||
                (line.ToLower()[0] != 'c' &&
                 line.ToLower()[0] != 'h'))
            {
                Console.WriteLine("Incorrect Key, Player 2: (C)omputer, (H)uman");
                line = Console.ReadLine();
            }
            if (line.ToLower()[0] == 'c')
            {
                int depth = 0;
                do
                {
                    Console.WriteLine("Enter Depth: ");
                    line = Console.ReadLine();
                    int.TryParse(line, out depth);
                } while (depth == 0);
                
                Console.WriteLine("(M)inimax or (A)lpha Beta: ");
                line = "";
                do
                {
                    line = Console.ReadLine();
                } while (line.Length < 1 && line.ToLower()[0] != 'a' &&
                line.ToLower()[0] != 'm');

                player2 = new Agent(gameState, PlayerTurn.Player2, depth,
                    line.ToLower()[0] == 'a' ? true : false);
            }
            else { player2 = null; }

        }

        static void CheckMoves()
        {
            List<Move> moves = gameState.CheckMoves(gameState.Player);
            foreach (Move m in moves)
            {
                Console.WriteLine("{0}, ({1},{2})  ({3},{4})",
                    m.Player, m.Start.X, m.Start.Y, m.End.X, m.End.Y);
            }
        }

        static void MovePiece(string[] args)
        {

            while (args.Length < 2)
            {
                Console.WriteLine("Incorrect, x y x1 y1");
                string line = Console.ReadLine();
                args = line.Split(new char[] { ' ' });
            }
            int x = int.Parse(args[1]);
            int y = int.Parse(args[2]);
            if (gameState.Turn < 2)
            {
                if (gameState.Move(x, y, x, y))
                {

                }
                else
                {
                    Console.WriteLine("Bad Move");
                }


            }
            else
            {
                int x1 = int.Parse(args[3]);
                int y1 = int.Parse(args[4]);
                if (gameState.Move(x, y, x1, y1))
                {

                }
                else
                {
                    Console.WriteLine("Bad Move");
                }

            }

        }

        static void MakeComputerMove(Agent a)
        {            
            int score = 0;
            Move m = a.Suggest(out score);

            Console.WriteLine(gameState.Player + " Move ({0},{1}) to ({2},{3}) (with Score {4})", m.Start.X, m.Start.Y,
                                m.End.X, m.End.Y, score);
            Console.WriteLine(gameState.Player + " has evaluated " + a.TotalNodesExpanded + " total nodes"); 
            gameState.Move(m.Start.X, m.Start.Y, m.End.X, m.End.Y);
            GameState.PrintBoard(gameState);
        }

        static bool MakeMove()
        {
            if (gameState.Player == PlayerTurn.Player1 &&
                player1 != null)
            {
                MakeComputerMove(player1);
            }
            else if (gameState.Player == PlayerTurn.Player2 &&
                player2 != null)
            {
                MakeComputerMove(player2);
            }
            else
            {
                //Human Player
                Console.WriteLine(gameState.Player + "'s Turn");
                while (true)
                {
                    try
                    {
                        return HumanMove();

                    }
                    catch (Exception)
                    {
                        Console.WriteLine("Incorrect Input, try again");
                    }
                }
            }
            return true;
        }

        static bool HumanMove()
        {
            Console.WriteLine("(C)heck Moves, (M)ove Piece, (P)rint Board, (Q)uit");
            string line = Console.ReadLine();
            string[] a = line.Split(new char[] { ' ' });

            if (a[0].ToLower()[0] == 'c')
            {
                CheckMoves();
            }
            else if (a[0].ToLower()[0] == 'm')
            {
                MovePiece(a);
                GameState.PrintBoard(gameState);
            }
            else if (a[0].ToLower()[0] == 'p')
            {
                GameState.PrintBoard(gameState);
            }
            else if (a[0].ToLower()[0] == 'q')
            {
                return false;
            }

            return true;
        }


        static void GetBoardSize()
        {
            int boardSize = 0;
            while (boardSize != 4 && boardSize != 6 && boardSize != 8)
            {
                Console.WriteLine("Enter Board Size (4,6,8)");
                string line = Console.ReadLine();
                int.TryParse(line, out boardSize);
            }

            gameState = new GameState(boardSize);
        }


        public static void RunGame(string[] args)
        {
            

            while (true)
            {
                GetBoardSize();

                AssignPlayers();

                while (true)
                {
                    if (!MakeMove())
                        break;
                    if (gameState.CheckMoves(gameState.Player).Count == 0)
                    {
                        Console.WriteLine(gameState.Player + " Loses!");
                        string line = Console.ReadLine();
                        if (line == "q")
                            return;
                        break;
                    }
                }
            }



        }
    }
}
