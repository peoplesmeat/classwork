using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classifier
{
    /// <summary>
    /// Builds a decision tree using the entropy maximization heuristic
    /// </summary>
    class EntropyDecisionTree : DecisionTree
    {



        public EntropyDecisionTree(List<ExampleInstance> examples, 
            List<ClassifierAttribute> attributes, bool prune)
        {
            tree = DTL(examples, attributes, MajorityValue(examples), prune);
        }

        public EntropyDecisionTree(List<ExampleInstance> examples, bool prune)
        {
            tree = DTL(examples, DataLoader.GenerateAttributes(examples), MajorityValue(examples), prune);
        }

        public EntropyDecisionTree(TreeNode<Decision> root)
        {
            tree = root;
        }

        public TreeNode<Decision> Tree
        {
            get { return tree; }
        }

        static ClassifierAttribute SelectBestAttribute(List<ClassifierAttribute> attributes,
            List<ExampleInstance> examples, string def)
        {

            ClassifierAttribute a = null;
            double maxGain = double.MinValue;
            foreach (ClassifierAttribute ca in attributes)
            {
                double g = ComputeGain(examples, ca, def);

                if (useGainRatio)
                {
                    g = g / ca.Values.Count;
                }

                if (g > maxGain)
                {
                    maxGain = g;
                    a = ca;
                }
            }
            Log("Selecting Maximum Attribute {0} with Gain {1}",
                a.Index, maxGain); 
            return a;

        }


        /// <summary>
        /// Decision Tree Learning as described by the textbook
        /// </summary>
        /// <param name="examples"></param>
        /// <param name="attributes"></param>
        /// <param name="def"></param>
        /// <param name="prune"></param>
        /// <returns></returns>
        static TreeNode<Decision> DTL(List<ExampleInstance> examples,
                                      List<ClassifierAttribute> attributes,
                                      string def, bool prune)
        {

            Log("DTL...{0} examples, {1} attributes", examples.Count, attributes.Count); 
            //There are no examples in this set
            if (examples.Count == 0)
            {
                Log("  There are no examples in this set, returning default value"); 
                return new TreeNode<Decision>(new Decision(def));
            }
            else
            {
                //Determine if All Examples have the same classification?
                bool allSameClassification = true;
                string classification = examples[0].Classification;
                foreach (ExampleInstance ei in examples)
                {
                    if (ei.Classification != classification)
                    {
                        allSameClassification = false;
                        break;
                    }

                }

                if (allSameClassification)
                {
                    Log("  All Examples Have {0} classification", examples[0].Classification); 
                    return new TreeNode<Decision>(new Decision(examples[0].Classification));
                }
                else if (attributes.Count == 0)
                {
                    Log("  Out of Attributes to classify using {0} as majority value", MajorityValue(examples)); 
                    return new TreeNode<Decision>(new Decision(MajorityValue(examples)));
                }
            }

            //Select best attribute using Gain calculation
            ClassifierAttribute bestAttribute = SelectBestAttribute(attributes, examples, def);

            //Calculate Chi-Squared 
            if (prune)
            {
               
                int positives = CountPositives(examples, def);
                int negatives = examples.Count - positives;
                double chiValue = 0.0;
                foreach (string attributeValue in bestAttribute.Values)
                {
                    int pi = 0;
                    int ni = 0;

                    CountClassifcations(examples, bestAttribute, attributeValue, def,
                        out pi, out ni);
                    double pHat = positives * ((double)(pi + ni)) / (double)(positives + negatives);
                    double nHat = negatives * ((double)(pi + ni)) / (double)(positives + negatives);
                    if (pHat != 0 && nHat != 0)
                    {
                        chiValue += Math.Pow((pi - pHat), 2) / (pHat) +
                                    Math.Pow((ni - nHat), 2) / (nHat);
                    }
                }
                Log("Node has Chi Squared {0}", chiValue); 
                if (chiValue < ChiSquared5Percent[bestAttribute.Values.Count - 1])
                {
                    //Failed the Test, return majority value
                    Log("Pruning Attribute, returning {0}", MajorityValue(examples)); 
                    return new TreeNode<Decision>(new Decision(MajorityValue(examples)));
                }
            }
            //End Calculate Chi-Squared

            TreeNode<Decision> newNode = new TreeNode<Decision>(new Decision(bestAttribute));

            string majorityValue = MajorityValue(examples);

            foreach (string attributeValue in bestAttribute.Values)
            {
                List<ExampleInstance> examplesSubset = SubSet(examples, bestAttribute, attributeValue);

                //Construct a constrained copy of the examples
                List<ClassifierAttribute> tempAttributes = new List<ClassifierAttribute>();
                foreach (ClassifierAttribute tempCa in attributes)
                    if (tempCa != bestAttribute)
                        tempAttributes.Add(tempCa);

                //Recurse
                Log("Exploring attribute value {0}", attributeValue); 
                TreeNode<Decision> tn = DTL(examplesSubset, tempAttributes, majorityValue,prune);
                tn.Data.Selection = attributeValue;
                newNode.AddChild(tn);
            }

            return newNode;

        }
        static bool useGainRatio = true;

        public static bool UseGainRatio
        {
            get { return EntropyDecisionTree.useGainRatio; }
            set { EntropyDecisionTree.useGainRatio = value; }
        }

        /// <summary>
        /// Chi squared values indexed on degrees of freedom
        /// </summary>
        static double[] ChiSquared5Percent = {
	3.84, 	5.99 , 7.82  , 	 9.49 , 
	11.07, 12.59 , 14.07 , 	 15.51 , 
	16.92 , 18.31 };

        private static int CountPositives(List<ExampleInstance> examples,
            string whatIsPositive)
        {
            int totalPositives = 0;
            for (int i = 0; i < examples.Count; i++)
                if (examples[i].Classification.Equals(whatIsPositive))
                    totalPositives++;
            return totalPositives;
        }

        private static double ComputeGain(List<ExampleInstance> examples,
            ClassifierAttribute attribute, string whatIsPositive)
        {
            List<string> values = attribute.Values;
            double sum = 0.0;

            int totalPositives = CountPositives(examples, whatIsPositive);
            double totalEntropy = CalculateEntropy(totalPositives, examples.Count - totalPositives);

            for (int i = 0; i < values.Count; i++)
            {
                int positives, negatives;

                positives = negatives = 0;

                CountClassifcations(examples, attribute, values[i], whatIsPositive, out positives, out negatives);

                double entropy = CalculateEntropy(positives, negatives);
                sum -= (double)(positives + negatives) / examples.Count * entropy;
            }

            return totalEntropy + sum;
        }

        private static double CalculateEntropy(int positives, int negatives)
        {
            int total = positives + negatives;
            if (total == 0)
                return 0;
            double ratioPositive = (double)positives / total;
            double ratioNegative = (double)negatives / total;

            if (ratioPositive != 0)
                ratioPositive = -(ratioPositive) * System.Math.Log(ratioPositive, 2);
            if (ratioNegative != 0)
                ratioNegative = -(ratioNegative) * System.Math.Log(ratioNegative, 2);

            double result = ratioPositive + ratioNegative;

            return result;
        }

        static string MajorityValue(List<ExampleInstance> examples)
        {
            List<string> values = new List<string>();
            List<int> count = new List<int>();

            foreach (ExampleInstance ei in examples)
            {
                bool found = false;
                for (int i = 0; i < values.Count; i++)
                {
                    if (values[i] == ei.Classification)
                    {
                        count[i] = count[i] + 1;
                        found = true;
                    }
                }

                if (!found)
                {
                    values.Add(ei.Classification);
                    count.Add(1);
                }
            }

            int maxVal = 0;
            for (int i = 0; i < values.Count; i++)
            {
                if (count[maxVal] < count[i])
                    maxVal = i;
            }

            return values[maxVal];
        }

        static void CountClassifcations(List<ExampleInstance> examples, ClassifierAttribute a, string value, string whatIsPositive,
            out int positive, out int negative)
        {
            positive = negative = 0;
            foreach (ExampleInstance example in examples)
            {
                if (example.Variables[a.Index] == value)
                {
                    if (example.Classification == whatIsPositive)
                        positive++;
                    else negative++;
                }
            }
        }

        static List<ExampleInstance> SubSet(List<ExampleInstance> examples, ClassifierAttribute ca, string v)
        {
            List<ExampleInstance> subset = new List<ExampleInstance>();

            foreach (ExampleInstance ei in examples)
                if (ei.Variables[ca.Index] == v)
                    subset.Add(ei);

            return subset;


        }

        public override object Clone()
        {
            TreeNode<Decision> tn = new TreeNode<Decision>((Decision)this.tree.Data.Clone());
            Clone(this.tree, tn);
            EntropyDecisionTree etp = new EntropyDecisionTree(tn);
            return etp;
        }
    }
}
