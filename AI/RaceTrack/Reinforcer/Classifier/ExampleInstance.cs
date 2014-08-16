using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Classifier
{
    /// <summary>
    /// Represents an example which is loaded from a datafile
    /// </summary>
    public class ExampleInstance
    {
        string[] variables;
        string classDescription;
        public string[] Variables
        {
            get { return variables; }
            set { variables = value; }
        }

        public ExampleInstance(string[] array, int offset, int count,
            string classDescription)
        {
            variables = new string[count];
            Array.Copy(array, offset, variables, 0, count);

            this.classDescription = classDescription;
        }

        public string Classification
        {
            get { return classDescription; }
        }


    }
}
