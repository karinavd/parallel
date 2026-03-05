using System.Threading;
using System;
namespace threaddemo
{
   public class StopThread
    {
        private readonly List<(ThreadClass thread, int delay)> threadsList = [];
        public void AddThread(ThreadClass thread)=> threadsList.Add((thread, new Random().Next(3000,30000)));
        public void Stopper()
        {
            new Thread(() =>
        {
            var sortedList = threadsList.OrderBy(x => x.delay).ToList();
            int time =0;
            foreach(var item in sortedList)
            {
                Thread.Sleep(item.delay-time);
                time =item.delay;
                item.thread.Stop();
            }
       }).Start();
    } 
}}