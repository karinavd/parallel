using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace ProducerConsumer
{
    class Program
    {
        private readonly static int storageSize = 5;
        private readonly static int consumers = 4;
        private readonly static int producers = 2;
        private readonly static int totalItems = 4;

         private Semaphore access = new Semaphore(1, 1);
        private Semaphore full = new Semaphore(storageSize, storageSize);
        private Semaphore empty = new Semaphore(0, storageSize);
        private readonly List<string> storage = new List<string>();
        private int idProduct = 0;

        static void Main(string[] args)
        {
            Program program = new Program();
            program.Starter();

            Console.ReadKey();
        }
        private void Starter()
        {
            CreateAndStartThreads(producers, totalItems, Producer);
            CreateAndStartThreads(consumers, totalItems, Consumer);
        }
        private void CreateAndStartThreads(int countThreads,int totalItems, Action<int,int> action)
        {
            int tempChunk = totalItems / countThreads;
            int rest = totalItems % countThreads;
            for(int i = 0; i < countThreads; i++)
            {
                int itemsForThisThread = tempChunk + (i < rest ? 1 : 0);
                int threadIndex = i;
                Thread thread = new Thread(() => action(threadIndex, itemsForThisThread));
                thread.Start();
            }
        }


        private void Producer(int index, int itemNumbers)
        {
            for (int i = 0; i < itemNumbers; i++)
            {
                full.WaitOne();
                access.WaitOne();

                storage.Add("item " + idProduct);
                Console.WriteLine($"Producer {index} added item {idProduct}");
                idProduct++;

                access.Release();
                empty.Release();
                
            }
        }

        private void Consumer(int index, int itemNumbers)
        {
            for (int i = 0; i < itemNumbers; i++)
            {
                empty.WaitOne();
                Thread.Sleep(1000);
                access.WaitOne();
                
                string item = storage[0];
                storage.RemoveAt(0);
                
                full.Release();
                access.Release();
                
                Console.WriteLine($"Consumer {index} took item {item}");
            }
        }
    }
}