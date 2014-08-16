using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO; 
namespace Classifier
{
    /// <summary>
    /// A utility class to load data out of the various input files
    /// Since no preprocessing is done on the data sets multiple input 
    /// functions are used. 
    /// </summary>
    public class DataLoader
    {
        public static List<ExampleInstance> Load(string filename)
        {
            StreamReader sr = new StreamReader(new FileStream(filename, FileMode.Open));
            List<ExampleInstance> instances = new List<ExampleInstance>();
            string line = sr.ReadLine().Trim();
            while (true)
            {
                string[] tokens = line.Split(new char[] { ',' });
                ExampleInstance e = new ExampleInstance(tokens, 1, tokens.Length - 1, tokens[0]);
                instances.Add(e);
                line = sr.ReadLine();
                if (line == null)
                    break;
                else
                    line = line.Trim(); 
            }
            sr.Close(); 
            return instances; 

        }

        public static List<string> LoadPossibleClassifications(List<ExampleInstance> examples)
        {
            List<string> classifications = new List<string>();
            foreach (ExampleInstance ei in examples)
            {
                if (!classifications.Contains(ei.Classification))
                    classifications.Add(ei.Classification); 
            }

            return classifications; 
        }


        public static List<ExampleInstance> LoadMonk(string filename)
        {
            StreamReader sr = new StreamReader(new FileStream(filename, FileMode.Open));
            List<ExampleInstance> instances = new List<ExampleInstance>();
            string line = sr.ReadLine().Trim();
            while (true)
            {
                string[] tokens = line.Split(new char[] { ' ' });              
                ExampleInstance e = new ExampleInstance(tokens, 1, tokens.Length - 2, tokens[0]);                
                instances.Add(e);
                line = sr.ReadLine();
                if (line == null)
                    break;
                else
                    line = line.Trim(); 
            }

            return instances; 

        }

       
        public static List<ExampleInstance> LoadSplice(string filename)
        {
            StreamReader sr = new StreamReader(new FileStream(filename, FileMode.Open));
            List<ExampleInstance> instances = new List<ExampleInstance>();
            string line = sr.ReadLine().Trim();
            while (true)
            {
                string[] tokens = line.Split(new char[] { ',' });                
                List<string> variables = new List<string>();
                for (int i = 0; i < tokens[tokens.Length - 1].Trim().Length; i++)
                {
                    variables.Add(tokens[tokens.Length - 1].Trim()[i].ToString()); 
                }
                ExampleInstance e = new ExampleInstance(variables.ToArray(), 0, variables.Count, tokens[0]);
                if (e.Classification != "N")
                {
                    instances.Add(e);
                }
                line = sr.ReadLine();
                if (line == null)
                    break;
                else
                    line = line.Trim();
            }
            sr.Close();
            return instances; 

        }

        public static List<ClassifierAttribute> GenerateSpliceAttributes()
        {
            List<ClassifierAttribute> attributes = new List<ClassifierAttribute>();
            for (int i = 0; i < 60; i++)
            {
                attributes.Add(new ClassifierAttribute(i,
                    new string[] { "A", "G", "T", "C", "D", "N", "S", "R" })); 
            }
            return attributes; 
        }

        public static List<ClassifierAttribute> GenerateAttributes(List<ExampleInstance> examples)
        {
            List<ClassifierAttribute> attributes = new List<ClassifierAttribute>();
            for (int i = 0; i < examples[0].Variables.Length; i++)
            {
                attributes.Add(new ClassifierAttribute(i, new string[] { examples[0].Variables[i] }));

            }
            for (int i = 0; i < examples.Count; i++)
            {
                for (int j = 0; j < examples[i].Variables.Length; j++)
                {
                    attributes[j].AddValue(examples[i].Variables[j]);
                }
            }
            return attributes;
        }
    }
}
