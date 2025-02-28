package ex9;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.filterEmpty
    // Ignoring the initialization of arrays, your implementation must have linear work and log(n) span
    public static String[] filterEmpty(String[] arr, int cutoff) {
        FilterEmpty.CUTOFF = cutoff;
        // TODO: Implement to match the filter/pack procedure discussed in class.
        // Reminder: the main steps are:
        // 1) do a map on the arr of strings
        int[] bits = MapStringBits(arr, cutoff);
        // 2) do prefix sum on the map result (implementation provided for you in ParallelPrefix.java)
        int[] bitsum = ParallelPrefix.prefixSum(bits, cutoff);
        // 3) initialize and array whose length matches the last value in the prefix sum result
        String[] output = new String[bitsum[bitsum.length - 1]];
        // 4) do a map to populate that new array.
        MapBitsumString(output, arr, bits, bitsum, cutoff);
        return output;
    }

    /**
     * A none return value Map function, which enters the MBSAction
     *
     * @param output
     * @param input
     * @param bits
     * @param bitsum
     * @param cutoff
     */
    public static void MapBitsumString(String[] output, String[] input, int[] bits, int[] bitsum, int cutoff) {
        POOL.invoke(new MapBitsumStringAction(output, input, bits, bitsum, 0, input.length));
    }

    // map class that extends from RecursiveAction
    private static class MapBitsumStringAction extends RecursiveAction {
        private final String[] output, input;
        private final int[] bits, bitsum;
        private final int lo, hi;

        public MapBitsumStringAction(String[] output, String[] input, int[] bits, int[] bitsum, int lo, int hi) {
            this.output = output;
            this.input = input;
            this.bits = bits;
            this.bitsum = bitsum;
            this.lo = lo;
            this.hi = hi;
        }

        public void compute() {
            // small enough Action, let the sequential function do it for you
            if (hi - lo <= FilterEmpty.CUTOFF) {  // note that the lo and hi is on the scope of input/bits/bitsum.length
                sequentialMapBitsumString(output, input, bits, bitsum, lo, hi);
            } else {
                int mid = lo + (hi - lo) / 2;  // mid-point

                // left sub Action
                MapBitsumStringAction left = new MapBitsumStringAction(output, input, bits, bitsum, lo, mid);
                MapBitsumStringAction right = new MapBitsumStringAction(output, input, bits, bitsum, mid, hi);

                // invoke them together, acting more like a Map model
                invokeAll(left, right);
            }
        }

        /**
         * Map the qualified String to the output array
         *
         * @param output
         * @param input
         * @param bits
         * @param bitsum
         * @param lo
         * @param hi
         */
        private static void sequentialMapBitsumString(String[] output, String[] input, int[] bits, int[] bitsum, int lo, int hi) {
            for (int i = lo; i < hi; i++) {
                // bits[i] == 1 indicates that this string is qualified
                if (bits[i] == 1) {
                    // bitsum[i] - 1 represents the index where the qualified string should be
                    output[bitsum[i] - 1] = input[i];
                }
            }
        }
    }

    /**
     * An entry function for the MSBAction, but do return an int[] because I want to implement
     * both return void(takes in parameter to modify the target arr) and return int[] function for practicing
     *
     * @param arr
     * @param cutoff
     * @return
     */
    public static int[] MapStringBits(String[] arr, int cutoff) {
        FilterEmpty.CUTOFF = cutoff;
        // initialize a arr.length size bits[] array
        int[] resultBits = new int[arr.length];
        // enter the action pool
        POOL.invoke(new MapStringBitsAction(arr, resultBits, 0, arr.length));
        return resultBits;  // return bits[] to pass the ownership
    }

    // extends from RecursiveAction; its purpose is to mark the qualified String
    // by marking the corresponding bits[index] to 1
    private static class MapStringBitsAction extends RecursiveAction {
        private final String[] arr;
        private final int[] bits;
        private final int lo, hi;

        public MapStringBitsAction(String[] arr, int[] bits, int lo, int hi) {
            this.arr = arr;
            this.bits = bits;
            this.lo = lo;
            this.hi = hi;
        }

        public void compute() {
            // small enough Action to call the sequential function
            if (hi - lo <= FilterEmpty.CUTOFF) {
                sequentialMapStringBits(arr, bits, lo, hi);
            } else {
                // mid-point
                int mid = lo + (hi - lo) / 2;

                // split to subtasks
                MapStringBitsAction left = new MapStringBitsAction(arr, bits, lo, mid);
                MapStringBitsAction right = new MapStringBitsAction(arr, bits, mid, hi);

                // invokeAll to make it more like a map model
                invokeAll(left, right);
            }
        }

        /**
         * If String is not empty, then it's qualified, so set the bits[i] as 1 to represent
         * it's qualified, otherwise set the corresponding bits[i] to 0.
         *
         * @param arr
         * @param bits
         * @param lo
         * @param hi
         */
        private static void sequentialMapStringBits(String[] arr, int[] bits, int lo, int hi) {
            for (int i = lo; i < hi; i++) {
                if (!arr[i].isEmpty()) {
                    bits[i] = 1;
                } else {
                    bits[i] = 0;
                }
            }
        }
    }

}
