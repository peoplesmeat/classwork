using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classifier
{
    /// <summary>
    /// A class which represents a classifier attribute. Each attribute has a 
    /// number of possible values and an index. This index references the index
    ///  in the attribute array of an example
    /// </summary>
    public class ClassifierAttribute
    {
        List<string> values = new List<string>();
        int index;

        public ClassifierAttribute(int index)
        {
            this.index = index;
        }

        public ClassifierAttribute(int index, string[] tValues)
            : this(index)
        {
            values.AddRange(tValues);
        }

        public void AddValue(string k)
        {
            if (!values.Contains(k))
                values.Add(k);
        }
        public List<string> Values
        {
            get { return values; }
        }

        public int Index
        {
            get { return index; }
        }
    }
}
