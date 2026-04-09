namespace ThreadMinSharp;
public class ArrClass
{
    private readonly int dim;
        private readonly int threadNum;
        private static int threadCount = 0;
        private  readonly Thread[] thread;
        private  readonly int[] arr;   
        private  readonly object lockerForMin = new object();
        private  readonly object lockerForCount = new object();
        public long Min { get; private set; } = long.MaxValue;
        public int MinIndex { get; private set; } = -1;

        public ArrClass(int dim,int threadNum)
        {
            this.dim = dim;
            this.threadNum  = threadNum;
            this.thread = new Thread[threadNum];
            this.arr = new int[dim];
        }
        public long ParallelMin()
        {
            long bound = dim/threadNum;
            long startBound = 0;
            for(int i = 0; i < threadNum-1; i++)
            {
                thread[i] = new Thread(StarterThread);
                thread[i].Start(new Bound(startBound, startBound+bound));
                startBound+=bound;
            }
            thread[threadNum-1] = new Thread(StarterThread);
            thread[threadNum-1].Start(new Bound(startBound, dim));

            lock (lockerForCount)
            {
                while (threadCount < threadNum)
                {
                    Monitor.Wait(lockerForCount);
                }
            }
            return Min;
        }

        public void InitArr()
        {
            for (int i = 0; i < dim; i++)
            {
                arr[i] = i;
            }

            arr[100000] = -500;
        }
        
        private void StarterThread(object param)
        {
            if (param is Bound)
            {
                var res = PartMin((param as Bound).StartIndex, (param as Bound).FinishIndex);

                lock (lockerForMin)
                {
                    CollectMin(res.min, res.index);
                }
                IncThreadCount();
            }
        }

        private  void IncThreadCount()
        {
            lock (lockerForCount)
            {
                threadCount++;
                Monitor.Pulse(lockerForCount);
            }
        }
       
        public void CollectMin(long min,int index)
        {
            if (min < this.Min)
            {
                this.Min=min;
                this.MinIndex=index;
            }
        }

        public (long min, int index) PartMin(long startIndex, long finishIndex)
        {
            long min = long.MaxValue;
            int index = -1;
            for (long i = startIndex; i < finishIndex; i++)
            {
                if (arr[i]<min){
                min=arr[i];
                index=(int)i;
                }
            }
            return (min,index);
        }
    }