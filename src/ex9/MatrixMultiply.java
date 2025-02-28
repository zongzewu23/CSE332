package ex9;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiply {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.multiply.
    // Ignoring the initialization of arrays, your implementation should have n^3 work and log(n) span
    public static int[][] multiply(int[][] a, int[][] b, int cutoff) {
        MatrixMultiply.CUTOFF = cutoff;
        int[][] product = new int[a.length][b[0].length];
        // create the entry task with its initial left, right, top, bottom initialized
        POOL.invoke(new MatrixMultiplyAction(a, b, 0, b[0].length, 0, a.length, product)); // TODO: add parameters to match your constructor
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff) {
        MatrixMultiply.CUTOFF = cutoff;
        // create the entry task for compute the dot product for this row and col
        return POOL.invoke(new DotProductTask(a, b, 0, a.length, row, col)); // TODO: add parameters to match your constructor
    }

    /**
     * same logic except specifics which row and col to calculate
     *
     * @param a
     * @param b
     * @param lo
     * @param hi
     * @param row
     * @param col
     * @return
     */
    private static int sequentialDotProduct(int[][] a, int[][] b, int lo, int hi, int row, int col) {
        int answer = 0;
        for (int i = lo; i < hi; i++) {
            answer += a[row][i] * b[i][col];
        }
        return answer;
    }

    private static class MatrixMultiplyAction extends RecursiveAction {
        // TODO: select fields
        private final int[][] a, b;
        private final int left, right, top, bottom;
        // use this field to modify the product[][], will be passed in when creating tasks
        private final int[][] product;

        public MatrixMultiplyAction(int[][] a, int[][] b, int left, int right, int top, int bottom, int[][] product) {
            // TODO: implement constructor
            this.a = a;
            this.b = b;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
            this.product = product;
        }

        public void compute() {
            // TODO: implement
            // this is a CUTOFF * CUTOFF matrix, time to calculate it's dot product and insert the value to the product[][]
            if ((right - left <= MatrixMultiply.CUTOFF) && (bottom - top <= MatrixMultiply.CUTOFF)) {
                sequentialMultiply(a, b, left, right, top, bottom);
            } else {
                // split the matrix into four submatrix(Four Quadrants)
                int colMid = left + (right - left) / 2;  // Vertical Splitting Matrix
                int rowMid = top + (bottom - top) / 2;   // Horizontal Split Matrix
                // 2nd quadrant
                MatrixMultiplyAction left_top = new MatrixMultiplyAction(a, b, left, colMid, top, rowMid, product);
                // 1st quadrant
                MatrixMultiplyAction right_top = new MatrixMultiplyAction(a, b, colMid, right, top, rowMid, product);
                // 3rd quadrant
                MatrixMultiplyAction left_bottom = new MatrixMultiplyAction(a, b, left, colMid, rowMid, bottom, product);
                // 4th quadrant
                MatrixMultiplyAction right_bottom = new MatrixMultiplyAction(a, b, colMid, right, rowMid, bottom, product);

                left_top.fork();  // create  new thread for 2nd quadrant
                left_bottom.fork();  // create  new thread for 3rd quadrant
                right_bottom.fork();  // create  new thread for 4th quadrant

                right_top.compute();  // compute the 1st quadrant in this current thread

                left_top.join(); // wait for the 2nd quadrant to finish
                left_bottom.join();  // wait for the 3rd quadrant to finish
                right_bottom.join();  // wait for the 4th quadrant to finish
                // method found in ed discussion board #625, might be a little bit slower, but much less code
//                invokeAll(left_top, right_top, left_bottom, right_bottom);
            }
        }

        /**
         * moved it to here so can access the private product[][] and modify it without pass it as a parameter
         *
         * @param a
         * @param b
         * @param left
         * @param right
         * @param top
         * @param bottom
         */
        private void sequentialMultiply(int[][] a, int[][] b, int left, int right, int top, int bottom) {
            // for each element in the submatrix, call dotProduct(RecursiveTask) to calculate the dot product
            for (int row = top; row < bottom; row++) {
                for (int col = left; col < right; col++) {
                    // notice it's calculating the dot product from the entire row of a and col of b
                    // not the row of some sub-matrix or subtask
                    this.product[row][col] = dotProduct(a, b, row, col, MatrixMultiply.CUTOFF);
                }
            }
        }

    }

    private static class DotProductTask extends RecursiveTask<Integer> {
        // TODO: select fields
        private final int[][] a, b;
        private final int lo, hi;
        private final int row, col;

        public DotProductTask(int[][] a, int[][] b, int lo, int hi, int row, int col) {
            // TODO: implement constructor
            this.a = a;
            this.b = b;
            this.lo = lo;
            this.hi = hi;
            this.row = row;
            this.col = col;
        }

        public Integer compute() {
            // TODO: implement
            // small enough sub matrix(sub task, sub array) to calculate
            if (hi - lo <= MatrixMultiply.CUTOFF) {
                return sequentialDotProduct(a, b, lo, hi, row, col);
            } else {
                // mid-point
                int mid = lo + (hi - lo) / 2;
                // left subtask
                DotProductTask left = new DotProductTask(a, b, lo, mid, row, col);
                // right subtask
                DotProductTask right = new DotProductTask(a, b, mid, hi, row, col);

                left.fork();  // create new thread for left subtask
                int rightResult = right.compute();  // compute the right subtask on the current thread
                int leftResult = left.join();  // wait for the left subtask to finish

                // combine the result from the left and right subtask for getting this parent task's result
                return rightResult + leftResult;
            }
        }
    }

}
