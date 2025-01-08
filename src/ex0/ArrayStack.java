package ex0;

public class ArrayStack<T> implements MyStack<T> {
    private T[] array;
    private int topIndex;
    private int capacity;

    //Constructor initialization parameters and configuration
    public ArrayStack(){
        this.capacity = 10;
        this.topIndex = -1;
        this.array = (T[]) new Object[10];
    }

    /**
     *Add items to the array and expand the array to twice its original size when appropriate
     * @param item
     */
    @Override
    public void push(T item) {
        //When the topmost element does not reach the array boundary, update topIndex normally and assign item to the index
        if(topIndex < capacity - 1){
            topIndex++;
            array[topIndex] = item;
        //When the topmost element reaches the array boundary, expand the array to twice its original size
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

    /**
     * If it is not empty, return the data at the topIndex position and update the topIndex, otherwise throw an exception
     * @return
     */
    @Override
    public T pop() {
        if(topIndex >= 0){
            T data = array[topIndex];
            topIndex--;
            return data;
        }else{
            throw new IllegalStateException("Stack is empty.");
        }

    }

    /**
     * Returns the data at the topindex position but does not update topIndex. If stack is empty, an exception is thrown.
     * @return
     */
    @Override
    public T peek() {
        if(isEmpty()){
            throw new IllegalStateException("Stack is empty.");
        }
        return array[topIndex];
    }

    /**
     * return size
     * @return
     */
    @Override
    public int size() {
        return topIndex + 1;
    }

    /**
     * return if the stack is empty
     * @return
     */
    @Override
    public boolean isEmpty() {
        return topIndex == -1;
    }
}
