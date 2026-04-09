public class Main {
    public static void main(String[] args) {
        int dim = 1000000000;
        int threadNum = 3;

        System.out.println("The number of elements: " + dim);
        System.out.println("The number of threads: " + threadNum);

        ArrClass finder = new ArrClass(dim, threadNum);
        finder.initArr();
        
         long startTimePar = System.currentTimeMillis();
        MinResult parResult = finder.parallelMin();
        long endTimePar = System.currentTimeMillis();
        showResult("ParallelMin", parResult.min, parResult.index, endTimePar - startTimePar);

        long startTimeSeq = System.currentTimeMillis();
        MinResult seqResult = finder.partMin(0, dim);
        long endTimeSeq = System.currentTimeMillis();
        showResult("PartMin", seqResult.min, seqResult.index, endTimeSeq - startTimeSeq);

       
    }

    private static void showResult(String nameMethod, long minElement, int indexValue, long time) {
        System.out.println("\n" + nameMethod);
        System.out.println("Min element: " + minElement + "; index: " + indexValue);
        System.out.println("Time: " + time + "ms");
    }
}