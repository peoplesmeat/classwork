using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;



namespace Classifier
{
    /// <summary>
    /// abstract class which represents a decision tree. THe main function here is
    /// classify which takes an example instance and returns a classification
    /// </summary>
    abstract class DecisionTree: ICloneable
    {
        protected TreeNode<Decision> tree;


        public string Classify(string[] attributes, out int comparisons)
        {
            TreeNode<Decision> curr = tree;
            comparisons = 0;
            while (curr.Children.Count != 0)
            {
                bool found = false;
                foreach (TreeNode<Decision> t in curr.Children)
                {
                    comparisons++;
                    if (attributes[curr.Data.Attribute.Index] ==
                        t.Data.Selection)
                    {
                        curr = t;
                        found = true;
                        break;
                    }
                }
                if (!found)
                    throw new Exception("Attribute Had Unseen Value");
            }
            return curr.Data.Classification;
        }

      

        protected void Clone(TreeNode<Decision> src, TreeNode<Decision> dst)
        {
            foreach (TreeNode<Decision> child in src.Children)
            {
                TreeNode<Decision> newChild = new TreeNode<Decision>((Decision)child.Data.Clone());
                dst.AddChild(newChild);                
                Clone(child, newChild);
            }

        }

        public int NodeCount
        {
            get
            {
                int count = 0;
                InOrder(tree, ref count);
                return count;
            }
        }

        void InOrder(TreeNode<Decision> node, ref int count)
        {
            count++;
            foreach (TreeNode<Decision> child in node.Children)
                InOrder(child, ref count);

        }

        static System.IO.TextWriter output = null;

        public static System.IO.TextWriter Output
        {
            get { return EntropyDecisionTree.output; }
            set { EntropyDecisionTree.output = value; }
        }

        public  static void Log(string value, params object[] args)
        {
            if (output != null)
            {
                output.WriteLine(String.Format(value, args));
            }
        }

        public abstract object Clone();
    }

   
}
