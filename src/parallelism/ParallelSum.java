package parallelism;

import java.lang.Thread;

class ParallelSum extends Thread {
    private int[] arr;
    private int left, right;
    private int result;

    public ParallelSum(int[] arr, int left, int right) {
        this.arr = arr;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        result = computeSum(arr, left, right);
    }

    private int computeSum(int[] arr, int left, int right) {
        if (left == right) return arr[left];
        int mid = left + (right-left)/2;

        ParallelSum leftThread = new ParallelSum(arr, left, mid);
        leftThread.start();

        int rightResult = computeSum(arr, mid + 1, right);

        try {
            leftThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return leftThread.getResult() + rightResult;
    }


    public int getResult() {
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        ParallelSum sumThread = new ParallelSum(arr, 0, arr.length -1 );
        sumThread.start();
        try {
            sumThread.join();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sumThread.getResult());
    }
}
