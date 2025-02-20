package parallelism;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Integer> {
    private int[] arr;
    private int lo, hi;
    private static final int SEQUENTIAL_CUTOFF = 100_000;

    public SumTask(int[] arr, int lo, int hi) {
        this.arr = arr;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected Integer compute() {
        if (hi - lo <= SEQUENTIAL_CUTOFF) {
            int sum = 0;
            for (int i = lo; i < hi; i++) {
                sum += arr[i];
            }
            return sum;
        } else {
            int mid = lo + (hi - lo) / 2;
            SumTask left = new SumTask(arr, lo, mid);
            SumTask right = new SumTask(arr, mid, hi);

            left.fork();  // 并行执行左子任务
            Integer rightRes = right.compute(); // 当前线程执行右子任务
            Integer leftRes = left.join();  // 等待左任务完成

//            right.fork();
//            Integer leftRes = left.compute();
//            Integer rightRes = right.join();


            return leftRes + rightRes;
        }
    }



    private static int computeSequentially(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    public static void main(String[] args) {
        int SIZE = 100_000_000;
        int[] arr = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = i;
        }

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(arr, 0, SIZE);

        long startTime = System.nanoTime();
        int parallelSum = pool.invoke(task);
        long parallelTime = System.nanoTime() - startTime;

        System.out.println("ForkJoinPool =" + parallelSum);
        System.out.println("ForkJoinPool time used： " + parallelTime/1_000_000.0 + " ms");

        startTime = System.nanoTime();
        int singleThreadSum = computeSequentially(arr);
        long singleThreadTime = System.nanoTime() - startTime;

        System.out.println("SingleThreadSum ="+ singleThreadSum);
        System.out.println("SingleThread Time used: " + singleThreadTime/1_000_000.0 + " ms");

        double speedup = (double) singleThreadTime / parallelTime;
        System.out.println("SpeedUp ratio =" + speedup + " times");


    }
}
