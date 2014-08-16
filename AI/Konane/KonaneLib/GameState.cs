using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{
    public enum PlayerTurn
    {
        Player1,
        Player2
    }

    public class GameState : ICloneable
    {
        Board board;
        
        int turn = 0;

        private GameState()
        {

        }

        public Board Board
        {
            get { return board; }
        }
        public GameState(int k)
        {
            board = new Board(k);

        }
        public int Turn
        {
            get { return turn; }
        }

        public PlayerTurn Player
        {
            get
            {
                if ((turn % 2) == 0)
                    return PlayerTurn.Player1;
                else
                    return PlayerTurn.Player2; 

            }
        }
        private void IncrementTurn()
        {
            turn++;
        }

        public List<Move> CheckMoves(PlayerTurn player)
        {
            List<Move> moves = new List<Move>();
            if (turn == 0)
            {
                moves.Add(new Move(new Position(0, 0), new Position(0, 0), PlayerTurn.Player1));
                moves.Add(new Move(new Position(board.Size - 1, board.Size - 1), new Position(board.Size - 1, board.Size - 1), PlayerTurn.Player1));


                moves.Add(new Move(new Position((Board.Size / 2)-1, (Board.Size / 2)-1), new Position((Board.Size / 2)-1, (Board.Size / 2)-1), PlayerTurn.Player1));
                moves.Add(new Move(new Position(Board.Size / 2, Board.Size / 2), new Position(Board.Size / 2, Board.Size / 2), PlayerTurn.Player1));

            }
            else if (turn == 1)
            {
                for (int i = 0; i < board.Size; i++)
                {
                    for (int j = 0; j < board.Size; j++)
                    {
                        if (board.Positions[i, j] == PositionState.Empty)
                        {
                            if (i - 1 > 0)
                            {
                                moves.Add(new Move(new Position(i - 1, j), new Position(i - 1, j), PlayerTurn.Player2)); 
                            }
                            if (j - 1 > 0)
                            {
                                moves.Add(new Move(new Position(i, j-1), new Position(i , j-1), PlayerTurn.Player2)); 
                            }
                            if (i + 1 < board.Size)
                            {
                                moves.Add(new Move(new Position(i+1, j), new Position(i+1, j), PlayerTurn.Player2));
                            }
                            if (j + 1 < board.Size)
                            {
                                moves.Add(new Move(new Position(i, j+1), new Position(i , j+1), PlayerTurn.Player2));
                            }
                        }
                    }
                }
            }
            else
            {
               
                for (int i = 0; i < Board.Size; i++)
                {
                    for (int j = 0; j < Board.Size; j++)
                    {
                        for (int i1 = 0; i1 < Board.Size; i1++)
                        {
                            for (int j1 = 0; j1 < Board.Size; j1++)
                            {
                                PlayerTurn p; 
                                if (Board.IsValidMove(i, j, i1, j1))
                                {
                                    
                                    if ((i + j) % 2 == 0)
                                    {
                                        p = KonaneLib.PlayerTurn.Player1;
                                    }

                                    else
                                    {
                                        p = KonaneLib.PlayerTurn.Player2;
                                    }

                                    if (p == player)
                                    {
                                        moves.Add(new Move(new Position(i, j), new Position(i1, j1), player));
                                    }
                                    //Console.WriteLine("({0},{1}) ({2},{3})", i, j, i1, j1);
                                }
                            }
                        }
                    }
                }
            }
            return moves;
        }

        public bool Remove(int x, int y)
        {
            if (turn == 0)
            {
                if (x != y)
                    return false;
            }

            else if (turn == 1)
            {

            }
            board.SetState(x, y, PositionState.Empty);

            return true;
        }

        public bool Move(int x, int y, int x1, int y1)
        {
            if (Player == PlayerTurn.Player1)
            {
                if ((x + y) % 2 != 0)
                {
                    return false;
                }
            }
            else
            {
                if ((x + y) % 2 == 0)
                {
                    return false;
                }
            }


            if (turn > 1)
            {
                if (!board.IsValidMove(x, y, x1, y1))
                {
                    return false;
                }
                board = PerformMove(this.board, x, y, x1, y1);
            }
            else 
            {
                bool removed =  Remove(x, y);
                if (!removed)
                    return false; 
            }

            
            IncrementTurn();
            return true;


        }
        public static Board PerformMove(Board b, int x, int y, int x1, int y1)
        {
            PositionState[,] a = new PositionState[b.Size, b.Size];
            Array.Copy(b.Positions, a, a.Length);
            Board board = new Board(a);



            board.SetState(x1, y1, board.Positions[x, y]);
            board.SetState(x, y, PositionState.Empty);

            if (x != x1)
            {
                //Moving Horizontally
                int distance = Math.Abs(x - x1);
                int dir = Math.Sign(x1 - x);
                for (int i = 0; i < distance / 2; i++)
                {
                    board.SetState(x + (1 + (i * 2)) * dir, y, PositionState.Empty);
                }

            }

            if (y != y1)
            {
                //Moving Vertically
                int distance = Math.Abs(y - y1);
                int dir = Math.Sign(y1 - y);
                for (int i = 0; i < distance / 2; i++)
                {
                    board.SetState(x, y + (1 + (i * 2)) * dir, PositionState.Empty);
                }

            }


            return board;

        }

        public object Clone()
        {
            GameState g = new GameState();
            g.board = (Board)Board.Clone();
            g.turn = turn;           
            return g;
        }

        public static void PrintBoard(KonaneLib.GameState g)
        {
            for (int i = 0; i < g.Board.Size; i++)
            {
                for (int j = 0; j < g.Board.Size; j++)
                {
                    if (g.Board.Positions[j, i] == KonaneLib.PositionState.Black)
                        Console.Write("B|");
                    else if (g.Board.Positions[j, i] == KonaneLib.PositionState.White)
                    {
                        Console.Write("W|");
                    }
                    else
                        Console.Write(" |");
                }
                Console.WriteLine();
            }
            Console.WriteLine();
        }


    }
}
