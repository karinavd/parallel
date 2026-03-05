using System.Threading;
using System;

namespace threaddemo
{
    public class Program
    {
        static void Main()
        {
            Console.WriteLine("Enter the number of threads:");
            int count =  Convert.ToInt32(Console.ReadLine());
            StopThread stopper = new StopThread();

            for (int i = 1; i <= count; i++)
            {
                ThreadClass thread = new ThreadClass(i);
                stopper.AddThread(thread);
                thread.Start();
            }
            stopper.Stopper();
        }
    }
}
