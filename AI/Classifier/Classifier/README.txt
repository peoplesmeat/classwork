Decision Trees
Bill Davis

These files implement two kinds of decision trees. 
Entropy Trees (using information gain) in gain ratio and 
pruning mode

Genetic Algorithms using tournament and fitness proportionate selection

The interesting files are 

EntropyDecisionTree.cs - Implements the decision tree based on information 
gain heuristic

GeneticDecisionTree.cs - Builds a random decision tree. Implements the 
mutation and crossover functions

GeneticAlgorithm.cs - Runs the genetic algorithm, using mutation and crossover
Implements the tournament and fitness proportionate selection criteria. 

DecisionTree.cs Abstract class from which the GeneticDecisionTree and 
the EntropyTree derive. 

ClassifierAttribute.cs - Class for describing attributes

Program.cs - The main function

There are a number of utility files TreeNode.cs, DataLoader and Experiments


The datasets were not preprocessed before being used
They are included in the data directory.

data/genetic Results from genetic algorithm
data/results Results from entropy tree
data/resultsGain Results from entropy with gain ratio enabled
data/inputs Input data sets used