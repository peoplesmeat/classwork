using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{

    public class Move
    {
        Position start;

        public Position Start
        {
            get { return start; }
            set { start = value; }
        }
        Position end;

        public Position End
        {
            get { return end; }
            set { end = value; }
        }
        KonaneLib.PlayerTurn player;

        public KonaneLib.PlayerTurn Player
        {
            get { return player; }
            set { player = value; }
        }
        public Move(Position p1, Position p2, KonaneLib.PlayerTurn player)
        {
            start = p1;
            end = p2;
            this.player = player;
        }


    }
}
