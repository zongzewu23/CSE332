package ex0;

public class ArrayQueue<T> implements MyQueue<T> {
    private int capacity;
    private int front;
    private int back;
    private T[] array;
    private int size;

    public ArrayQueue(){
        this.capacity = 10;
        this.front = this.capacity/2;
        this.back = this.capacity/2;
        this.array = (T[]) new Object[10];
        this.size = 0;

    }
    @Override
    public void enqueue(T item) {

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
            array[back] = item;
            back = (back + 1) % capacity;
            size++;
    }

    @Override
    public T dequeue() {
        if (!isEmpty()) {
            T data = array[front];
            array[front] = null;
            front = (front + 1) % capacity;
            size--;
            return data;
        }
            return null;

    }

    @Override
    public T peek() {
        if (isEmpty()) return null;
        return array[front];
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == back;
    }
}
