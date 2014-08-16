Bill Davis
Project 3

This project contains implementations of Value-Iteration and Q-Learning. It also contains a version of the Entropy decision tree which was developed in the last project to use for function approximation. 

The Files contained in this project are

Action.cs – Represents an acceleration

DataLoader.cs – Utility class to generate a RaceTrack from a data file

Program – Contains a main function 

QLearner.cs – Implementation of the QLearning Algorithm

RaceTrack.cs – Represents a race track. Contains a movement algorithm based upon Bresenham's algorithm

ReinforcementLearner.cs – Contains an abstract base class used for Value-Iteration and Q-Learning. Contains functions for generating the state space

ValueIteration.cs – Implementation of Value Iteration

ValueNode.cs – Utility class used to represent a single node in state space with multiple actions and real values associated with each action

Vector2.cs – Utility class for 2-vector of integer values


In the data folder are runs for all three algorithms on all three tracks. There is also a learning folder which contains the data used to train the decision tree

To Build use
msbuild Reinforcer.csproj
