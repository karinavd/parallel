using System.Threading;
using System;
namespace threaddemo
{
     public class ThreadClass
    {
        private readonly Thread thread;
        private int id;
        private int step;
        private int delay;
        private volatile bool canStop=false;
        public ThreadClass(int id,int step)
        {
            thread = new Thread(Calculator);
            this.id=id;
            this.step = step;
        }
        public void setDelay(int delay){
    this.delay = delay;
}
        void Calculator()
        {
            long sum = 0;
            long count=0;
            do
            {
                count++;
                sum+=step;
            } while (!canStop);
            Console.WriteLine($"Thread index: {id}; Sum: {sum};  Amount of elements: {count}; Delay: {delay}");
        }
        public void Stop()=>canStop=true;
        public void Start()=> thread.Start();
    } 
}