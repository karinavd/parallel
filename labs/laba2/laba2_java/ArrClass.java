class ArrClass {
    private int dim;
    private int threadNum;
    private int threadCount = 0;
    private Thread[] threads;
    private int[] arr;
    private Object lockerForMin = new Object();
    private Object lockerForCount = new Object();
    private long min = Long.MAX_VALUE;
    private int minIndex = -1;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        this.threadNum = threadNum;
        this.threads = new Thread[threadNum];
        this.arr = new int[dim];
    }

    public void initArr() {
        for (int i = 0; i < dim; i++) {
            arr[i] = i;
        }
        arr[90000000] = -500;
    }

    public MinResult partMin(int startIndex, int finishIndex) {
        long localMin = Long.MAX_VALUE;
        int localIndex = -1;
        
        for (int i = startIndex; i < finishIndex; i++) {
            if (arr[i] < localMin) {
                localMin = arr[i];
                localIndex = i;
            }
        }
        return new MinResult(localMin, localIndex);
    }

    public MinResult parallelMin() {
        int bound = dim/threadNum;
        int startBound = 0;

        for (int i = 0; i < threadNum - 1; i++) {
            final int start = startBound;
            final int finish = startBound + bound;
            threads[i] = new Thread(() -> starterThread(new Bound(start, finish)));
            threads[i].start();
            startBound += bound;
        }
        
        final int lastStart = startBound;
        threads[threadNum - 1] = new Thread(() -> starterThread(new Bound(lastStart, dim)));
        threads[threadNum - 1].start();

        synchronized (lockerForCount) {
            while (threadCount < threadNum) {
                try {
                    lockerForCount.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        
        return new MinResult(this.min, this.minIndex);
    }

    private void starterThread(Bound bound) {
        MinResult res = partMin(bound.startIndex, bound.finishIndex);

        synchronized (lockerForMin) {
            collectMin(res.min, res.index);
        }
        incThreadCount();
    }

    private void incThreadCount() {
        synchronized (lockerForCount) {
            threadCount++;
            lockerForCount.notify();
        }
    }

    private void collectMin(long localMin, int localIndex) {
        if (localMin < this.min) {
            this.min = localMin;
            this.minIndex = localIndex;
        }
    }
}