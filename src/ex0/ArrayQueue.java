package ex0;

public class ArrayQueue<T> implements MyQueue<T> {
    private int capacity;
    private int front;
    private int back;
    private T[] array;
    private int size;

    //Constructor initializes the parameters of Queue
    public ArrayQueue(){
        this.capacity = 10;
        this.front = this.capacity/2;
        this.back = this.capacity/2;
        this.array = (T[]) new Object[10];
        this.size = 0;
    }

    /**
     * Add the item to the back index position in the array, and then point back to the next position
     * @param item
     */
    @Override
    public void enqueue(T item) {
        //When the elements fill the entire array, copy the entire array to a new array that is twice the size of the original, and then assign the new array to the array.
        if (size() == capacity) {
            T[] copyArray = (T[]) new Object[capacity * 2];
            for (int i = 0; i < size(); i++) {
                copyArray[i] = array[(front + i) % capacity];
            }
            front = 0;
            back = size();
            capacity *= 2;
            array = copyArray;
        }
        //Regardless of whether the array needs to be expanded or not, the item is added to the back index of the array normally, and then back is increased by one. If it exceeds the left boundary of the array, it returns to the right boundary of the array.
            array[back] = item;
            back = (back + 1) % capacity;//Implementation of circular array
            size++;//update size
    }

    /**
     * If there is an element in the Queue, the value at the front index is returned, and like the back index,
     * after reaching the right boundary, it returns to the left boundary and starts again.
     * @return
     */
    @Override
    public T dequeue() {
        if (!isEmpty()) {
            T data = array[front];
            front = (front + 1) % capacity;
            size--;
            return data;
        }
        //If the array is empty, an exception is returned.
        throw new IllegalStateException("Stack is empty.");

    }

    /**
     If the array is empty, an exception is thrown, otherwise the first value is returned, which complies with FIFO
     *
     * @return
     */
    @Override
    public T peek() {
        if (isEmpty())   throw new IllegalStateException("Stack is empty.");;
        return array[front];
    }

    /**
     * Return size
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     *  return if the queue is empty
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
