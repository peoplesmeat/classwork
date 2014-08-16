using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classifier
{
    /// <summary>
    /// A classification, a selection (this refers to the parents 
    /// attributes) and a attribute. Some of these values
    /// may be null depending on the place in the tree. For example
    /// classification is only set when a node has no children
    /// </summary>
    public class Decision : ICloneable
    {
        public ClassifierAttribute Attribute;
        public string Selection;
        public string Classification; 

        public Decision(ClassifierAttribute k)
        {
            Attribute = k; 
        }

        public Decision(string c)
        {
            Classification = c;             
        }

        public override string ToString()
        {
            string s = ""; 
            if (Attribute != null) 
              s += Attribute.Index.ToString() + " " ;
            if (Classification != null)
            {
                if (Classification.Length > 3)
                {
                    s += Classification.Substring(0, 3).ToUpper().ToString() + " ";
                }
                else
                {
                    s += Classification + " " ; 
                }
            }
            if (Selection != null) 
                s += Selection.ToString();
            return s;
        }

        public object Clone()
        {
            Decision d = new Decision(this.Attribute);
            if (this.Selection != null) 
                d.Selection = (string)this.Selection.Clone(); 
            if (this.Classification != null) 
                d.Classification = (string)this.Classification.Clone();
            return d; 
        }
    }
    
    /// <summary>
    /// Class used to build a decision tree
    /// </summary>
    /// <typeparam name="m"></typeparam>
    public class TreeNode<m>
    {
        m data;
        static int uniqueId = 0;
        int myId; 
         TreeNode<m> parent; 
        List<TreeNode<m>> children = new List<TreeNode<m>>();
        public m Data
        {
            get { return data; }
            set { data = value; }
        }
        public TreeNode(m data)
        {
            this.data = data;
            myId = uniqueId++; 
         
        }

        public int UniqueId
        {
            get { return myId; } 
        }
        public void AddChild(TreeNode<m> c)
        {
            children.Add(c);
            c.parent = this; 
        }
        public List<TreeNode<m>> Children
        {
            get { return children; }
            set { children = value; } 
        }
        
        public  TreeNode<m> Parent
        {
            get { return parent; }
            set { parent = value; }
        }

        public string Name
        {
            get
            {
                string s =  "(" + UniqueId + ") " + data.ToString();

                return s;
            }
        }
    }

}
