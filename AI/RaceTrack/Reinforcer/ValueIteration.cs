using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Reinforcer
{

    /// <summary>
    /// Value iteration algorithm
    /// </summary>
    public class ValueIteration : ReinforcementLearner
    {
        public ValueIteration(RaceTrack rt) : base(rt)
        {

        }

        public override int IterateOnce()
        {
            int nodesExaminedInThisPass = 0; 
            lock (this)
            {
                for (int x = 0; x < ss.GetLength(0); x++)
                    for (int y = 0; y < ss.GetLength(1); y++)
                        for (int dx = 0; dx < ss.GetLength(2); dx++)
                            for (int dy = 0; dy < ss.GetLength(3); dy++)
                            {
                                if (rt.Squares[x, y] == SquareState.Wall)
                                    continue;
                                nodesExaminedInThisPass++; 
                                for (int i = 0; i < ss[x, y, dx, dy].Actions.Count; i++)
                                {
                                    Action a = ss[x, y, dx, dy].Actions[i];
                                    Vector2 position = new Vector2(x, y);
                                    Vector2 velocity = new Vector2(dx - 5, dy - 5);

                                    rt.Move(ref position, ref velocity);
                                    double utilityIfAccelerationFailed = ss[position.X, position.Y,
                                        velocity.X + 5, velocity.Y + 5].MinValue;

                                    position = new Vector2(x, y);
                                    velocity = new Vector2(dx - 5, dy - 5);
                                    RaceTrack.Apply(velocity, new Vector2(a.Dx, a.Dy));
                                    rt.Move(ref position, ref velocity);
                                    double utilityIfAccelerationSucceeds = ss[position.X, position.Y,
                                        velocity.X + 5, velocity.Y + 5].MinValue;

                                    ss[x, y, dx, dy].SetVk(i,
                                        rt.Cost(x, y, dx-5, dy-5)
                                        + 0.9 * utilityIfAccelerationSucceeds
                                        + 0.1 * utilityIfAccelerationFailed);


                                }
                             
                            }
                return nodesExaminedInThisPass; 
            }
        }
    }
}
