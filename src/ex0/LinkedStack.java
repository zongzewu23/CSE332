package ex0;

public class LinkedStack<T> implements MyStack<T> {

    private ListNode<T> top;
    private int size;

    public LinkedStack(){
        this.size = 0;
        this.top = null;
    }

    @Override
    public void push(T item) {
        ListNode<T> node = new ListNode<>(item);
        node.next = top;
        top = node;
        this.size++;
    }

    @Override
    public T pop() {
        if(top == null){
            return null;
        }else {
            T data = top.data;
            top = top.next;
            this.size--;
            return data;
        }
    }

    @Override
    public T peek() {
        return top.data;
    }

    @Override
    public int size() {
        return this.size;
    }

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
