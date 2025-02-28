package ex9;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DotProduct {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.dotProduct
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[] a, int[] b, int cutoff) {
        DotProduct.CUTOFF = cutoff;
        // pass in the matrix a and b, and the initial lo and hi
        return POOL.invoke(new DotProductTask(a, b, 0, a.length)); // TODO: add parameters to match your constructor
    }

    /**
     * sequentially calculate the dot product, linear time here
     *
     * @param a
     * @param b
     * @param lo
     * @param hi
     * @return
     */
    public static int sequentialDotProduct(int[] a, int[] b, int lo, int hi) {
        int answer = 0;
        for (int i = lo; i < hi; i++) {
            answer += a[i] * b[i];
        }
        return answer;
    }


    private static class DotProductTask extends RecursiveTask<Integer> {
        // TODO: select fields
        private final int[] a, b;
        private final int lo, hi;

        public DotProductTask(int[] a, int[] b, int lo, int hi) {
            // TODO: implement constructor
            this.a = a;
            this.b = b;
            this.lo = lo;
            this.hi = hi;
        }

        public Integer compute() {
            //TODO: Implement.
            if (hi - lo <= DotProduct.CUTOFF) {
                // base case, calculate this process sequentially
                return sequentialDotProduct(a, b, lo, hi);
            } else {
                int mid = lo + (hi - lo) / 2;  // split the task into two
                // left subtask that starts from lo to mid
                DotProductTask left = new DotProductTask(a, b, lo, mid);
                // right subtask that starts from mid to hi;
                DotProductTask right = new DotProductTask(a, b, mid, hi);

                left.fork();  // fork this left to create a new thread
                // the current thread computes the right subtask directly
                Integer rigthResult = right.compute();
                Integer leftResult = left.join();  // wait for the left subtask to finish

                return rigthResult + leftResult;  // combining the result from two subtasks
            }
        }
    }

}
