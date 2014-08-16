using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

using Reinforcer;
namespace RaceTrackConsole
{
    /// <summary>
    /// Main Program Responsable for firing off the multiple tests
    /// </summary>
    class Program
    {
        static Random random = new Random();

        static double DriveCar(RaceTrack raceTrack,
    Classifier.EntropyDecisionTree e, int maxTrials,
    TextWriter sw)
        {
            List<Reinforcer.Action> actions = new List<Reinforcer.Action>();
            for (int i = -1; i < 2; i++)
                for (int j = -1; j < 2; j++)
                {
                    actions.Add(new Reinforcer.Action(i, j));
                }


            double avgCost = 0.0;

            for (int i = 0; i < maxTrials; i++)
            {
                int n = 0;
                Vector2 pos = raceTrack.FindStartSquare();
                Vector2 vel = new Vector2(0, 0);

                while (n++ < 100 &&
                    raceTrack.Squares[pos.X, pos.Y] != SquareState.Finish)
                {
                    int comparisons = 0;
                    Reinforcer.Action a = actions[int.Parse(
                        e.Classify(new string[] { (pos.X/ReinforcementLearner.xBlockSize).ToString(), 
                            (pos.Y/ReinforcementLearner.yBlockSize).ToString(), 
                        (vel.X/ReinforcementLearner.dxBlockSize).ToString(), (vel.Y/ReinforcementLearner.dyBlockSize).ToString()}, out comparisons))];
                    if (sw != null) sw.WriteLine("Position: ({0},{1}) Velocity: ({2},{3}) Acceleration: {4}",
                        pos.X, pos.Y, vel.X, vel.Y, a.ToString());

                    RaceTrack.ApplyProbabalistically(vel, new Vector2(a.Dx, a.Dy));

                    raceTrack.Move(ref pos, ref vel);
                    if (raceTrack.Squares[pos.X, pos.Y] == SquareState.Finish)
                    {
                        if (sw != null) sw.WriteLine("Complete in {0} moves" + Environment.NewLine, n);
                    }
                }
                avgCost += (double)n / (double)maxTrials;
            }
            return avgCost;
        }

        static double DriveCar(RaceTrack raceTrack,
            ReinforcementLearner r, int maxTrials,
            TextWriter sw)
        {

            double avgCost = 0.0;

            for (int i = 0; i < maxTrials; i++)
            {
                int n = 0;
                Vector2 pos = raceTrack.FindStartSquare();
                Vector2 vel = new Vector2(0, 0);

                while (n++ < 100 &&
                    raceTrack.Squares[pos.X, pos.Y] != SquareState.Finish)
                {
                    Reinforcer.Action a = r.GetBestAction(pos, vel);
                    if (sw != null) sw.WriteLine("Position: ({0},{1}) Velocity: ({2},{3}) Acceleration: {4}",
                        pos.X, pos.Y, vel.X, vel.Y, a.ToString());

                    RaceTrack.ApplyProbabalistically(vel, new Vector2(a.Dx, a.Dy));

                    raceTrack.Move(ref pos, ref vel);
                    if (raceTrack.Squares[pos.X, pos.Y] == SquareState.Finish)
                    {
                        if (sw != null) sw.WriteLine("Complete in {0} moves" + Environment.NewLine, n);
                    }
                }
                avgCost += (double)n / (double)maxTrials;
            }
            return avgCost;
        }

        static void RunReinforcementLearner(
            ReinforcementLearner v,
            RaceTrack raceTrack, TextWriter output, string vTest)
        {
            int nodes = 0;
            int maxIterations = v is QLearning ? 20000 : 40;
            raceTrack.ResetOnCrash = false;
            output.WriteLine("Running with no Crash Penalty");
            for (int i = 0; i < maxIterations; i++)
            {
                nodes += v.IterateOnce();

                double avgCost = DriveCar(raceTrack, v, 100, null);
                if (maxIterations < 100 || i % 250 == 0)
                    output.WriteLine("Iterations: {0},  Nodes Examined So Far: {1},  Average Cost: {2}",
                    i, nodes, avgCost);
                output.Flush();
            }
            output.WriteLine("--Writing State Space--");
            v.WriteOut(output);

            output.WriteLine("--Finished Writing State Space--");
            output.WriteLine();
            output.WriteLine("--Driving Cars to test space--");
            DriveCar(raceTrack, v, 8, output);
            output.WriteLine();

            v.WriteOutTrainingData("vi-NoReset-" + vTest + " " + v.GetType().Name + ".txt");


            if (v is QLearning) v = new QLearning(raceTrack);
            else v = new ValueIteration(raceTrack);
            raceTrack.ResetOnCrash = true;
            output.WriteLine("Running with Crash Penalty");
            nodes = 0;
            for (int i = 0; i < maxIterations; i++)
            {
                nodes += v.IterateOnce();

                double avgCost = DriveCar(raceTrack, v, 100, null);
                if (maxIterations < 100 || i % 250 == 0)
                    output.WriteLine("Iterations: {0},  Nodes Examined So Far: {1},  Average Cost: {2}",
                    i, nodes, avgCost);
                output.Flush();
            }
            output.WriteLine("--Writing State Space--");
            v.WriteOut(output);

            output.WriteLine("--Finished Writing State Space--");
            output.WriteLine();
            output.WriteLine("--Driving Cars to test space--");
            DriveCar(raceTrack, v, 8, output);
            output.WriteLine();

            v.WriteOutTrainingData("vi-WithReset" + vTest + " " + v.GetType().Name + ".txt");
        }

        static void TestValueIteration()
        {
            StreamWriter output;
            RaceTrack raceTrack;
            ReinforcementLearner v;

            //Run Through All Data For Value-Iteration
            output = new StreamWriter("Value-Iteration-Ltrack.txt");
            output.WriteLine("Running LTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.LTrack);
            v = new ValueIteration(raceTrack);
            RunReinforcementLearner(v, raceTrack, output, "ltrack");
            output.Flush();
            output.Close();

            output = new StreamWriter("Value-Iteration-Otrack.txt");
            output.WriteLine("Running OTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.OTrack);
            v = new ValueIteration(raceTrack);
            RunReinforcementLearner(v, raceTrack, output, "otrack");
            output.Flush();
            output.Close();

            output = new StreamWriter("Value-Iteration-Rtrack.txt");
            output.WriteLine("Running RTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.RTrack);
            v = new ValueIteration(raceTrack);
            RunReinforcementLearner(v, raceTrack, output, "rtrack");
            output.Flush();
            output.Close();
        }

        static void RunReinforcementLearner2(
        ReinforcementLearner v,
        RaceTrack raceTrack, TextWriter output, string vTest)
        {
            int nodes = 0;
            int maxIterations = v is QLearning ? 20000 : 40;

            raceTrack.ResetOnCrash = true;
            output.WriteLine("Running with Crash Penalty");
            nodes = 0;
            for (int i = 0; i < maxIterations; i++)
            {
                nodes += v.IterateOnce();

                double avgCost = DriveCar(raceTrack, v, 100, null);
                if (maxIterations < 100 || i % 50 == 0)
                    output.WriteLine("{0}, {1}, {2}",
                    i, nodes, avgCost);
                output.Flush();
            }
            output.WriteLine("--Writing State Space--");
            v.WriteOut(output);

            output.WriteLine("--Finished Writing State Space--");
            output.WriteLine();
            output.WriteLine("--Driving Cars to test space--");
            DriveCar(raceTrack, v, 8, output);
            output.WriteLine();

            v.WriteOutTrainingData("vi-WithReset" + vTest + " " + v.GetType().Name + ".txt");
        }

        static void TestQLearning2()
        {
            StreamWriter output;
            RaceTrack raceTrack;
            ReinforcementLearner v;

            output = new StreamWriter("QLearning-Otrack-in detail.csv");
            output.WriteLine("Running OTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.OTrack);
            v = new QLearning(raceTrack);
            RunReinforcementLearner2(v, raceTrack, output, "otrack");
            output.Flush();
            output.Close();

        }

        static void TestQLearning()
        {
            StreamWriter output;
            RaceTrack raceTrack;
            ReinforcementLearner v;

            //Run Through All Data For QLearning
            output = new StreamWriter("QLearning-Ltrack.txt");
            output.WriteLine("Running LTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.LTrack);
            v = new QLearning(raceTrack);
            RunReinforcementLearner(v, raceTrack, output, "ltrack");
            output.Flush();
            output.Close();

            output = new StreamWriter("QLearning-Otrack.txt");
            output.WriteLine("Running OTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.OTrack);
            v = new QLearning(raceTrack);
            RunReinforcementLearner(v, raceTrack, output, "otrack");
            output.Flush();
            output.Close();

            output = new StreamWriter("QLearning-Rtrack.txt");
            output.WriteLine("Running RTrack");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.RTrack);
            v = new QLearning(raceTrack);
            RunReinforcementLearner(v, raceTrack, output, "rtrack");
            output.Flush();
            output.Close();
        }

        static void TestEntropyTree()
        {
            StreamWriter output;
            RaceTrack raceTrack;
            List<Classifier.ExampleInstance> examples;
            Classifier.EntropyDecisionTree e;
            Classifier.EntropyDecisionTree e2;
            double avg1;
            double avg2;

            bool pruning = false;

            output = new StreamWriter("DecisionTree-Ltrack.txt");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.LTrack);
            raceTrack.ResetOnCrash = false;
            examples = Classifier.DataLoader.Load("vi-NoReset-ltrack ValueIteration.txt");
            e = new Classifier.EntropyDecisionTree(
                examples, pruning);
            avg1 = DriveCar(raceTrack, e, 100, output);

            output.WriteLine();
            output.WriteLine("Turning Track Reset On");
            examples = Classifier.DataLoader.Load("vi-WithResetltrack ValueIteration.txt");
            e2 = new Classifier.EntropyDecisionTree(
                examples, pruning);
            raceTrack.ResetOnCrash = true;
            avg2 = DriveCar(raceTrack, e2, 100, output);

            output.WriteLine("Average Cost no Reset {0}", avg1);
            output.WriteLine(" Node Count {0}", e.NodeCount);
            output.WriteLine("Average Cost with Reset {0}", avg2);
            output.WriteLine(" Node Count {0}", e2.NodeCount);
            output.Close();


            output = new StreamWriter("DecisionTree-Otrack.txt");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.OTrack);
            raceTrack.ResetOnCrash = false;
            examples = Classifier.DataLoader.Load("vi-NoReset-otrack ValueIteration.txt");
            e = new Classifier.EntropyDecisionTree(
                examples, pruning);
            avg1 = DriveCar(raceTrack, e, 100, output);

            output.WriteLine();
            output.WriteLine("Turning Track Reset On");
            examples = Classifier.DataLoader.Load("vi-WithResetotrack ValueIteration.txt");
            e2 = new Classifier.EntropyDecisionTree(
                examples, pruning);
            raceTrack.ResetOnCrash = true;
            avg2 = DriveCar(raceTrack, e2, 100, output);

            output.WriteLine("Average Cost no Reset {0}", avg1);
            output.WriteLine(" Node Count {0}", e.NodeCount);
            output.WriteLine("Average Cost with Reset {0}", avg2);
            output.WriteLine(" Node Count {0}", e2.NodeCount);
            output.Close();


            output = new StreamWriter("DecisionTree-Rtrack.txt");
            raceTrack = Reinforcer.DataLoader.LoadTrack(Reinforcer.DataLoader.RTrack);
            raceTrack.ResetOnCrash = false;
            examples = Classifier.DataLoader.Load("vi-NoReset-rtrack ValueIteration.txt");
            e = new Classifier.EntropyDecisionTree(
                examples, pruning);
            avg1 = DriveCar(raceTrack, e, 100, output);

            output.WriteLine();
            output.WriteLine("Turning Track Reset On");
            examples = Classifier.DataLoader.Load("vi-WithResetrtrack ValueIteration.txt");
            e2 = new Classifier.EntropyDecisionTree(
                examples, pruning);
            raceTrack.ResetOnCrash = true;
            avg2 = DriveCar(raceTrack, e2, 100, output);

            output.WriteLine("Average Cost no Reset {0}", avg1);
            output.WriteLine(" Node Count {0}", e.NodeCount);
            output.WriteLine("Average Cost with Reset {0}", avg2);
            output.WriteLine(" Node Count {0}", e2.NodeCount);
            output.Close();


        }

        static void Main(string[] args)
        {
            TestQLearning2(); 
            //TestValueIteration();
            //TestQLearning(); 
            //TestEntropyTree();
        }
    }
}

