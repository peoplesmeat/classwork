using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classifier
{
 
    /// <summary>
    /// Randomly generate a decision tree given a set of attributes
    /// and possible classifications
    /// </summary>
    class GeneticDecisionTree : DecisionTree
    {

        private static double ProbabilityOfClassifier = 0.2;
        private static double ProbabilityOfClassifierWhenMutating = 0.5; 


        List<ClassifierAttribute> attributes;
        List<string> possibleClassifications;

        public GeneticDecisionTree(List<ClassifierAttribute> attributes,
            List<string> possibleClassifications)
        {
            
            this.attributes = attributes;
            this.possibleClassifications = possibleClassifications; 
            tree = AddRandomNode(attributes, 
                possibleClassifications, ProbabilityOfClassifier);
            //Log("Created Random Decision Tree with {0} Nodes",
            //    this.NodeCount); 

        }
  

        private static Random random = new Random(); 
        static TreeNode<Decision> AddRandomNode(List<ClassifierAttribute> attributes, 
            List<string> possibleClassifications, double probabilityOfClassifier)
        {
            double r = random.NextDouble();
            if (r < probabilityOfClassifier)
            {
                return new TreeNode<Decision>(new Decision(
                    possibleClassifications[random.Next(possibleClassifications.Count)]
                    ));
            }
            else
            {
                ClassifierAttribute ca = attributes[random.Next(attributes.Count)];
                TreeNode<Decision> d = new TreeNode<Decision>(new Decision(ca));
                foreach (string k in ca.Values)
                {
                    TreeNode<Decision> tn = AddRandomNode(attributes,
                        possibleClassifications, probabilityOfClassifier + 0.10);
                    tn.Data.Selection = k;
                    d.AddChild(tn); 
                    
                }

                return d; 
            }
           
            

        }


        static TreeNode<Decision> SelectThroughInOrder(TreeNode<Decision> node,
            int selection)
        {
            int count = 0;
            return SelectThroughInOrderWorker(node, ref count, selection); 
        }

        static TreeNode<Decision> SelectThroughInOrderWorker(TreeNode<Decision> node, ref int count, 
            int selection)
        {
            if (count == selection)
            {
                return node;
            }

            count++;

            foreach (TreeNode<Decision> child in node.Children)
            {
                TreeNode<Decision> n = SelectThroughInOrderWorker(child, ref count, 
                    selection);
                if (n != null)
                    return n;
            }

            return null; 
        }

        public static void Mutate(GeneticDecisionTree gdt)
        {
            Random random = new Random();

            TreeNode<Decision> node = SelectThroughInOrder(gdt.tree,
                    random.Next(gdt.NodeCount));
            if (node.Children.Count == 0)
                node = node.Parent;
            if (node == null)
                return;

            int childToMutate = random.Next(node.Children.Count);
            TreeNode<Decision> toMutate = node.Children[childToMutate];
            TreeNode<Decision> newNode =
                AddRandomNode(gdt.attributes, gdt.possibleClassifications,
                ProbabilityOfClassifierWhenMutating);
            newNode.Data.Selection = toMutate.Data.Selection;

            node.Children[childToMutate] = newNode; 




        }

        public static void CrossOver(GeneticDecisionTree gdt1,
    GeneticDecisionTree gdt2)
        {
            Random random = new Random();

            TreeNode<Decision> t1 = SelectThroughInOrder(gdt1.tree,
                    random.Next(gdt1.NodeCount));
            TreeNode<Decision> t2 = SelectThroughInOrder(gdt2.tree,
                random.Next(gdt2.NodeCount));

            if (t1.Children.Count == 0)
                t1 = t1.Parent;
            if (t2.Children.Count == 0)
                t2 = t2.Parent;
            if (t1 == null || t2 == null)
                return;

            Decision t1D = (Decision)t1.Data.Clone() ;
            Decision t2D = (Decision)t2.Data.Clone(); 

            List<TreeNode<Decision>> children1 = t1.Children;            

            List<TreeNode<Decision>> children2 = t2.Children;

            t1.Children = children2;
            t1.Data.Attribute = t2D.Attribute;
            t1.Data.Classification = t2D.Classification; 
            foreach (TreeNode<Decision> child in children2)
            {
                child.Parent = t1;
            }

            t2.Children = children1;
            t2.Data.Attribute = t1D.Attribute;
            t2.Data.Classification = t1D.Classification;
            foreach (TreeNode<Decision> child in children1)
            {
                child.Parent = t2;
            }


        }


        public override object Clone()
        {
            GeneticDecisionTree gdt = new GeneticDecisionTree(this.attributes, this.possibleClassifications);
            gdt.tree = new TreeNode<Decision>((Decision)this.tree.Data.Clone());
            Clone(this.tree, gdt.tree);
            return gdt;
        }
       
    }
}
