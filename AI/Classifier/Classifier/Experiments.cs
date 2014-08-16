using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO; 
namespace Classifier
{
    /// <summary>
    /// This is a static class which is responsible for loading datasets and 
    /// starting off tests. These tests write there results to text files
    /// </summary>
    static class Experiments
    {
        static int kfold = 5; 
        public static void BreakIntoTestAndTraining(
            List<ExampleInstance> examples,
            out List<List<ExampleInstance>> training,
            out List<List<ExampleInstance>> test)
        {
            List<ExampleInstance> remainingExamples = new List<ExampleInstance>(examples); 
            Random random = new Random();

            training = new List<List<ExampleInstance>>();
            test = new List<List<ExampleInstance>>(); 

            for (int i = 0; i < kfold; i++)
            {
                List<ExampleInstance> currTraining = new List<ExampleInstance>(); 
                for (int j = 0; j< examples.Count / kfold; j ++)
                {
                   
                    int item = random.Next(remainingExamples.Count);
                    currTraining.Add(remainingExamples[item]);
                    remainingExamples.RemoveAt(item);

  

                }

                List<ExampleInstance> currTest = new List<ExampleInstance>(examples);
                for (int k = 0; k < currTraining.Count; k++)
                {
                    currTest.Remove(currTraining[k]);
                }

                training.Add(currTraining);
                test.Add(currTest); 
            }
            

        }

        public static void RunGenClassifiers(
            List<ExampleInstance> examples,
            List<ClassifierAttribute> attributes,
            List<string> possibleClassifications,
            TextWriter output,
            out double touneyAvg ,
            out double fitnessAvg,
            out double tourneyComparedAvg,
            out double fitnessComparedAvg 
            )
        {
            output.WriteLine("Beginning Sample {0} examples, {1} attributes",
                examples.Count, attributes.Count);

            DecisionTree.Output = output; 

            List<List<ExampleInstance>> training;
            List<List<ExampleInstance>> test;
            BreakIntoTestAndTraining(examples, out training, out  test);
            output.WriteLine("Using {0}-fold validation", test.Count);

            touneyAvg = 0.0;
            fitnessAvg = 0.0;
            tourneyComparedAvg = 0.0;
            fitnessComparedAvg = 0.0;
            double tourneyavgNodes = 0.0;
            double fitnessAvgNodes = 0.0;

            for (int i = 0; i < training.Count; i++)
            {
                bool useTourney = false;
                for (int j = 0; j < 2; j++)
                {
                    
                    List<ExampleInstance> currTraining = training[i];
                    List<ExampleInstance> currTest = test[i];

                    output.Write("Using Test/Training Set #{0} ", i);
                    if (useTourney)
                    {
                        output.WriteLine("Tournament Select");
                    }
                    else
                    {
                        output.WriteLine("Fitness Select");
                    }
                    DateTime now = DateTime.Now;
                    GeneticAlgorithm ga = new GeneticAlgorithm(currTraining, attributes,
                        possibleClassifications, useTourney);
                    DateTime then = DateTime.Now;

                    GeneticDecisionTree bestTree = ga.Run();

                    double avgComparedNodes = 0.0;
                    double acc = CheckAccuracy(bestTree, currTest, out avgComparedNodes);
                    output.WriteLine("Testing accuracy using test set: {0}",
                        acc);


                    if (useTourney)
                    {
                        touneyAvg += acc / training.Count;
                        tourneyComparedAvg += avgComparedNodes / training.Count;
                        tourneyavgNodes += bestTree.NodeCount / (double)training.Count; 
                    }
                    else
                    {
                        fitnessAvg += acc / training.Count;
                        fitnessComparedAvg += avgComparedNodes / training.Count;
                        fitnessAvgNodes += bestTree.NodeCount / (double)training.Count; 
                    }

                    output.WriteLine();
                    useTourney = true;
                    output.Flush(); 
                }
                output.WriteLine();
            }
            output.WriteLine("Node Count Tournament {0},  Node Count Fitness {1}", tourneyavgNodes, fitnessAvgNodes);
            output.WriteLine("Avg Accuracy Fitness: {0}, Tournament: {1}", fitnessAvg, touneyAvg);
            output.WriteLine("Avg Nodes Compared Fitness: {0}, Tournament: {1}", fitnessComparedAvg, tourneyComparedAvg); 
           
        }

        public static void RunClassifiers(
            List<ExampleInstance> examples,
            List<ClassifierAttribute> attributes, 
            TextWriter output
            )
        {
            output.WriteLine("Beginning Sample {0} examples, {1} attributes",
                examples.Count, attributes.Count);

            EntropyDecisionTree.Output = output; 

            List<List<ExampleInstance>> training; 
            List<List<ExampleInstance>> test; 
            BreakIntoTestAndTraining(examples, out training,out  test);
            output.WriteLine("Using {0}-fold validation", test.Count);

            double unPrunedAvg = 0.0;
            double prunedAvg = 0.0;
            double prunedComparedAvg = 0.0;
            double unprunedComparedAvg = 0.0;
            double avgNodes = 0.0;
            double avgNodesPruned = 0.0; 

            for (int i = 0; i < training.Count; i++)
            {
                List<ExampleInstance> currTraining = training[i];
                List<ExampleInstance> currTest = test[i];

                output.WriteLine("Using Test/Training Set #{0}", i);
                bool prune = false;
                for (int j = 0; j < 2; j++)
                {
                    DateTime now = DateTime.Now;
                    EntropyDecisionTree entropyTree = new EntropyDecisionTree(currTraining, attributes, prune);
                    DateTime then = DateTime.Now;
                    output.WriteLine("Created Entropy Tree  (pruning={0}) in {1} milliseconds containing {2} nodes",
                        prune, (now - then).TotalMilliseconds, entropyTree.NodeCount);

                    now = DateTime.Now;
                    double avgComparedNodes = 0.0;
                    double acc = CheckAccuracy(entropyTree, currTest, output, out avgComparedNodes);
                    then = DateTime.Now;

                    output.WriteLine("Acheived {0} Accurracy in {1} Milliseconds",
                        acc, (now - then).TotalMilliseconds);
                    if (prune)
                    {
                        prunedAvg += acc / training.Count;
                        prunedComparedAvg += avgComparedNodes / training.Count;
                        avgNodesPruned += entropyTree.NodeCount / (double)(training.Count);
                        
                    }
                    else
                    {
                        unPrunedAvg += acc / training.Count;
                        unprunedComparedAvg += avgComparedNodes / training.Count;
                        avgNodes += entropyTree.NodeCount / (double)(training.Count);
                        
                    }
                    prune = true;

                    output.WriteLine();
                }
                output.WriteLine(); 
            }
            output.WriteLine("Avg Nodes Pruned: {0}, Unpruned: {1}", avgNodesPruned, avgNodes);
            output.WriteLine("Avg Accuracy Pruned: {0}, Unpruned: {1}", prunedAvg, unPrunedAvg);
            output.WriteLine("Avg Nodes Compared Pruned: {0}, Unpruned: {1}", prunedComparedAvg, unprunedComparedAvg); 
        }
        public static void RunMutationTest()
        {
            string HouseDataLocation = @"..\..\..\data\house-votes-84.data";
            List<ExampleInstance> examples = DataLoader.Load(HouseDataLocation);
            List<ClassifierAttribute> attributes = DataLoader.GenerateAttributes(examples);

            double touneyAvg;
            double fitnessAvg;
            double tourneyComparedAvg;
            double fitnessComparedAvg;

            GeneticAlgorithm.populationSize = 75;
            GeneticAlgorithm.iterations = 25;

            StreamWriter sw = new StreamWriter("geneticTests.txt");
            StreamWriter sw2 = new StreamWriter("geneticTests-outter-mutations.txt");
            sw2.WriteLine("Population Size, 5 Iterations, 10, 15, 20, 25");


            
            for (double i = 0.01; i < 0.6; i += 0.01)
            {
                GeneticAlgorithm.chanceOfMutation = i; 
                RunGenClassifiers(examples, attributes,
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
                sw2.WriteLine("{0} , {1}",
                    i, fitnessAvg);
                sw2.Flush();
            }
            sw2.WriteLine();

            sw2.Flush();
            
        }
        public static void RunGeneticAlgorithmTest()
        {
            string HouseDataLocation = @"..\..\..\data\house-votes-84.data";
            List<ExampleInstance> examples = DataLoader.Load(HouseDataLocation);
            List<ClassifierAttribute> attributes = DataLoader.GenerateAttributes(examples);

            double touneyAvg ;
            double fitnessAvg;
            double tourneyComparedAvg;
            double fitnessComparedAvg;
            StreamWriter sw = new StreamWriter("geneticTests.txt");
            StreamWriter sw2 = new StreamWriter("geneticTests-outter-iterations.txt");
            sw2.WriteLine("Population Size, 5 Iterations, 10, 15, 20, 25"); 
            for (int i = 30; i < 31; i+=10)
            {
                GeneticAlgorithm.populationSize = i;
                sw2.Write("{0},", i); 
                for (int j = 3; j < 100; j+=2)
                {
                    GeneticAlgorithm.iterations = j; 
                    RunGenClassifiers(examples, attributes,
                        DataLoader.LoadPossibleClassifications(examples), sw,
                        out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
                    sw2.WriteLine("{0} , {1}",
                        j, fitnessAvg);
                    sw2.Flush(); 
                }
                sw2.WriteLine();

                sw2.Flush(); 
            }
        }
 
        public static void RunAllTests(bool useGenetic, string path)
        {
            StreamWriter sw;
            string HouseDataLocation = @"..\..\..\data\house-votes-84.data";
            List<ExampleInstance> examples = DataLoader.Load(HouseDataLocation);
            List<ClassifierAttribute> attributes = DataLoader.GenerateAttributes(examples);

            if (!Directory.Exists(path))
                Directory.CreateDirectory(path);
            double touneyAvg;
            double fitnessAvg;
            double tourneyComparedAvg;
            double fitnessComparedAvg;

            if (useGenetic)
            {
                sw = new StreamWriter(path+"/house.txt");
                RunGenClassifiers(examples, attributes, 
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
            }
            else
            {
                sw = new StreamWriter(path + "/house-entropy.txt");
                RunClassifiers(examples, attributes, sw);
            }
            sw.Close(); 


            string monk1train = @"..\..\..\data\monks-1.train";
            string monk1test = @"..\..\..\data\monks-1.test";
            string monk2train = @"..\..\..\data\monks-2.train";
            string monk2test = @"..\..\..\data\monks-2.test";
            string monk3train = @"..\..\..\data\monks-3.train";
            string monk3test = @"..\..\..\data\monks-3.test";

            examples = DataLoader.LoadMonk(monk1train);
            attributes = DataLoader.GenerateAttributes(examples);
            if (useGenetic)
            {
                sw = new StreamWriter(path + "/monk1-genetic.txt");
                RunGenClassifiers(examples, attributes,
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
            }
            else
            {
                sw = new StreamWriter(path + "/monk1-entropy.txt");
                RunClassifiers(examples, attributes, sw);
            }

            sw.Close(); 

            examples = DataLoader.LoadMonk(monk2train);
            attributes = DataLoader.GenerateAttributes(examples);
            if (useGenetic)
            {
                sw = new StreamWriter(path + "/monk2-genetic.txt");
                RunGenClassifiers(examples, attributes,
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
            }
            else
            {
                sw = new StreamWriter(path + "/monk2-entropy.txt");
                RunClassifiers(examples, attributes, sw);
            }
            sw.Close(); 

            examples = DataLoader.LoadMonk(monk3train);
            attributes = DataLoader.GenerateAttributes(examples);
            if (useGenetic)
            {
                sw = new StreamWriter(path + "/monk3-genetic.txt");
                RunGenClassifiers(examples, attributes,
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
            }
            else
            {
                sw = new StreamWriter(path + "/monk3-entropy.txt");
                RunClassifiers(examples, attributes, sw);
            }
            sw.Close(); 

            string MushroomLocation = @"..\..\..\data\agaricus-lepiota.data";
            examples = DataLoader.Load(MushroomLocation);
            attributes = DataLoader.GenerateAttributes(examples);
            if (useGenetic)
            {
                sw = new StreamWriter(path + "/mushroom-genetic.txt");
                RunGenClassifiers(examples, attributes,
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
            }
            else
            {
                sw = new StreamWriter(path + "/mushroom-entropy.txt");
                RunClassifiers(examples, attributes, sw);
            }
            sw.Close(); 

            string splice = @"..\..\..\data\splice.data";
             examples = DataLoader.LoadSplice(splice);
            attributes = DataLoader.GenerateSpliceAttributes();
            if (useGenetic)
            {
                sw = new StreamWriter(path + "/gensplice-genetic.txt");
                RunGenClassifiers(examples, attributes,
                    DataLoader.LoadPossibleClassifications(examples), sw,
                    out touneyAvg, out fitnessAvg, out tourneyComparedAvg, out fitnessComparedAvg);
            }
            else
            {
                sw = new StreamWriter(path + "/gensplice-entropy.txt");
                RunClassifiers(examples, attributes, sw);
            }
            sw.Close(); 
        }


        public static double CheckAccuracy(DecisionTree dt,
            List<ExampleInstance> examples,out double avgComparedNodes)
        {
            return CheckAccuracy(dt, examples, null, out avgComparedNodes); 
        }
        public static double CheckAccuracy(DecisionTree dt, 
            List<ExampleInstance> examples, TextWriter output, 
            out double avgComparedNodes)
        {
            double good = 0;
            double totalComparisons=0; 
            foreach (ExampleInstance ei in examples)
            {
                int comparisons = 0; 
                string classification = dt.Classify(ei.Variables, out comparisons);
                totalComparisons += comparisons;
                if (classification == ei.Classification)
                    good += 1;
            }
            avgComparedNodes = totalComparisons / examples.Count;
            if (output != null)
            {
                output.WriteLine("Classified {0} examples, using an average of {1} examined nodes",
                    examples.Count, avgComparedNodes); 
            }
            return good / (double)examples.Count; 
        }




  
    }
}
