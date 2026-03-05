using System.Threading;
using System;
namespace threaddemo
{
     public class ThreadClass
    {
        private readonly Thread thread;
        private int id;
        private volatile bool canStop=false;
        public ThreadClass(int id)
        {
            thread = new Thread(Calculator);
            this.id=id;
        }
        void Calculator()
        {
            long sum = 0;
            long count=0;
            do
            {
                count++;
                sum+=2;
            } while (!canStop);
            Console.WriteLine($"Thread index: {id}; Sum: {sum};  Amount of elements: {count}");
        }
        public void Stop()=>canStop=true;
        public void Start()=> thread.Start();
    } 
}