using System;
using System.Diagnostics;
using System.Threading;

namespace ThreadMinSharp
{
    class Program
    {
        private static readonly int dim = 100000000;
        private static readonly int threadNum = 3;
        static void Main(string[] args)
        {
            Console.WriteLine();
            Console.WriteLine($"The number of elements: {dim}");
            Console.WriteLine($"The number of threads: {threadNum}");
            
            ArrClass arrClass = new ArrClass(dim, threadNum);
            arrClass.InitArr();

            Stopwatch time = Stopwatch.StartNew();
            var value= arrClass.PartMin(0, dim);
            time.Stop();
            ShowResult("PartMin",value.min,value.index,time.ElapsedMilliseconds);

            Stopwatch timePar = Stopwatch.StartNew();
            arrClass.ParallelMin();
            timePar.Stop();
            ShowResult("ParallelMin",arrClass.Min,arrClass.MinIndex,timePar.ElapsedMilliseconds);
            Console.ReadKey();
        }
        private static void ShowResult(string nameMethod,long minElement,int indexValue,long time){
            Console.WriteLine($"{nameMethod}");
            Console.WriteLine($"Min element:{minElement}; index:{indexValue}");
            Console.WriteLine($"Time: {time}ms");
        }
    }
}