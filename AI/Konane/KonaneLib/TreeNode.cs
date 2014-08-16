using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace KonaneLib
{
    public class TreeNode<m>
    {
        m data;
        List<TreeNode<m>> children = new List<TreeNode<m>>();
        public m Data
        {
            get { return data; }
            set { data = value; }
        }
        public TreeNode(m data)
        {
            this.data = data;
        }
        public void AddChild(TreeNode<m> c)
        {
            children.Add(c);
        }
        public List<TreeNode<m>> Children
        {
            get { return children; }
        }

        int score = int.MinValue;
        public int Score
        {
            get { return score; }
            set { score = value; }
        }
    }

}
