package ex0;

public class ArrayStack<T> implements MyStack<T> {
    private T[] array;
    private int topIndex;
    private int capacity;

    public ArrayStack(){
        this.capacity = 10;
        this.topIndex = -1;
        this.array = (T[]) new Object[10];
    }
    @Override
    public void push(T item) {
        if(topIndex < capacity - 1){
            topIndex++;
            array[topIndex] = item;
        }else{

            T[] copyArray = (T[]) new Object[capacity * 2];
            for(int i = 0; i <= topIndex; i++){
                copyArray[i] = array[i];
            }
            capacity *= 2;
            topIndex++;
            copyArray[topIndex] = item;
            array = copyArray;
        }
    }

    @Override

    public T pop() {
        if(topIndex >= 0){
            T data = array[topIndex];
            topIndex--;
            return data;
        }else{
            return null;
        }

    }

    @Override
    public T peek() {
        return array[topIndex];
    }

    @Override
    public int size() {
        return topIndex + 1;
    }

    @Override
    public boolean isEmpty() {
        return topIndex == -1;
    }
}
