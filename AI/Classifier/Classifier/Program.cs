using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Text.RegularExpressions; 
namespace Classifier
{
 



    /// <summary>
    /// The Main Class, responsabile for running experiments
    /// </summary>
    class Program
    {

        static void Main(string[] args)
        {
            EntropyDecisionTree.UseGainRatio = false;
            Experiments.RunAllTests(false,"results");
            EntropyDecisionTree.UseGainRatio = true;
            Experiments.RunAllTests(false,"resultsGain");
            Experiments.RunAllTests(true, "genetic");

            return; 
           
        }





    }
}
