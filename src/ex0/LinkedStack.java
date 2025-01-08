package ex0;

public class LinkedStack<T> implements MyStack<T> {

    private ListNode<T> top;
    private int size;

    //Constructors, initialization parameters and data
    public LinkedStack(){
        this.size = 0;
        this.top = null;
    }

    /**
     * Encapsulate item into ListNode and let top point to the new node, and node point to the node originally pointed to by top.
     * @param item
     */
    @Override
    public void push(T item) {
        ListNode<T> node = new ListNode<>(item);
        node.next = top;
        top = node;
        this.size++; //update size
    }

    /**
     * If stack is empty, an exception is thrown, otherwise the data of the node pointed to by top is returned and top is updated
     * @return
     */
    @Override
    public T pop() {
        if(top == null){
            throw new IllegalStateException("Stack is empty.");
        }else {
            T data = top.data;
            top = top.next;
            this.size--;//update size
            return data;
        }
    }

    /**
     * If stack is empty, an exception is thrown, otherwise the data of the node pointed to by top is returned and top is not updated
     * @return
     */
    @Override
    public T peek() {
        if(top == null){
            throw new IllegalStateException("Stack is empty.");
        }
        return top.data;
    }

    /**
     * return size
     * @return
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * return if the stack is empty
     * @return
     */
    @Override
    public boolean isEmpty() {
        return (top == null);
    }

    private static class ListNode<T>{
        private final T data;
        private ListNode<T> next;

        private ListNode(T data, ListNode<T> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(T data){
            this.data = data;
        }
    }
}
