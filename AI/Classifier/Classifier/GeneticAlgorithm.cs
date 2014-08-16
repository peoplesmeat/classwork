using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classifier
{
    /// <summary>
    /// Run a genetic algorithm
    /// </summary>
    class GeneticAlgorithm
    {

        public static int populationSize = 75; 
        public static double chanceOfMutation = 0.05;
        public static int iterations = 20; 

        List<ExampleInstance> examples;
        List<ClassifierAttribute> attributes;
        List<string> possibleClassifications;
        bool useTournament = true; 

        public GeneticAlgorithm(List<ExampleInstance> examples,
            List<ClassifierAttribute> attributes, List<string> possibleClassifications, bool useTourney)
        {
            this.examples = examples;
            this.attributes = attributes;
            this.possibleClassifications = possibleClassifications;
            this.useTournament = useTourney; 
        }

        public GeneticDecisionTree Run()
        {
            DecisionTree.Log("Beginning Genetic Algorithm");
            DecisionTree.Log("Population: {0}, Chance Of Mutation: {1}, Evolutions: {2}",
                populationSize, chanceOfMutation, iterations); 
            List<GeneticDecisionTree> population = new List<GeneticDecisionTree>();
            GeneticDecisionTree bestTree = null;
            for (int i = 0; i < populationSize; i++)
            {
                GeneticDecisionTree gdt1 = new GeneticDecisionTree(attributes, possibleClassifications);
                population.Add(gdt1);
            }

            int epoch = 0;
            while (epoch++<iterations)
            {
                population = Epoch(population, examples, useTournament);

                double avgFitness = 0.0;
                double bestFitness = 0.0;
               
                foreach (GeneticDecisionTree gdt in population)
                {
                    double avgComparedNodes;
                    double fitness = Experiments.CheckAccuracy(gdt, examples, out avgComparedNodes);
                    avgFitness += fitness;
                    if (fitness > bestFitness)
                    {
                        bestFitness = fitness;
                        bestTree = gdt; 
                    }
                    
                }
                avgFitness = avgFitness / population.Count;
              //  DecisionTree.Log(" Epoch {0} With Average Fitness {1:0.000}, Best Fitness {2:0.000}", 
              //      epoch, avgFitness, bestFitness);
            }

            return bestTree; 
        }
        class Test : IComparable
        {
            public GeneticDecisionTree GDT;
            public double Score;
            public double Offset; 

            public Test(GeneticDecisionTree g, double s)
            {
                this.GDT = g;
                this.Score = s; 
            }

            #region IComparable Members

            public int CompareTo(object obj)
            {
                Test t = (Test)obj;
                return this.Score.CompareTo(t.Score); 
            }

            #endregion
        }
        public static GeneticDecisionTree FitnessSelect(List<GeneticDecisionTree> population,
            List<ExampleInstance> examples)
        {
            List<Test> lists = new List<Test>();
            double totalScore = 0.0; 
            for (int i = 0; i < population.Count; i++)
            {
                double avgComparedNodes;
                double acc = Experiments.CheckAccuracy(population[i], examples, out avgComparedNodes);
                lists.Add(new Test(population[i], acc));
                totalScore += acc; 
            }

            double fitness = 0.0; 
            lists.Sort();
            for (int i = 0; i < lists.Count; i++)
            {
                fitness += lists[i].Score / totalScore;
                lists[i].Offset = fitness;

            }

            double selection = new Random().NextDouble();
            int offset = 0;
            while (selection > lists[offset].Offset)
                offset++; 
            
            return lists[offset].GDT; 
        }

        public static GeneticDecisionTree TournamentSelect(List<GeneticDecisionTree> population,
            List<ExampleInstance> examples)
        {
            Random random = new Random();

            double maxAcc = double.MinValue;
            int index = 0;

            for (int i = 0; i < 10; i++)
            {
                int k = random.Next(population.Count);
                GeneticDecisionTree gdt = population[k];
                double avgComparedNodes;
                double acc = Experiments.CheckAccuracy(gdt, examples, out avgComparedNodes);
                
                if (acc > maxAcc)
                {
                    maxAcc = acc;
                    index = k;
                }

            }

            return population[index];
        }

        public static List<GeneticDecisionTree> Epoch(List<GeneticDecisionTree> population,
            List<ExampleInstance> examples, bool useTournament)
        {
            List<GeneticDecisionTree> newPopulation = new List<GeneticDecisionTree>();
            Random random = new Random();
            for (int i = 0; i < population.Count / 2; i++)
            {
                GeneticDecisionTree gdt1; 
                GeneticDecisionTree gdt2;
                if (useTournament)
                {
                    gdt1 = (GeneticDecisionTree)TournamentSelect(population, examples).Clone();
                    gdt2 = (GeneticDecisionTree)TournamentSelect(population, examples).Clone();
                }
                else
                {
                    gdt1 = (GeneticDecisionTree)FitnessSelect(population, examples).Clone();
                    gdt2 = (GeneticDecisionTree)FitnessSelect(population, examples).Clone();
                }

                GeneticDecisionTree.CrossOver(gdt1, gdt2);

                TournamentSelect(population, examples);

                if (random.NextDouble() < chanceOfMutation)
                    GeneticDecisionTree.Mutate(gdt1);
                if (random.NextDouble() < chanceOfMutation)
                    GeneticDecisionTree.Mutate(gdt2);
                newPopulation.Add(gdt1);
                newPopulation.Add(gdt2);
            }

            return newPopulation;
        }
    }
}
