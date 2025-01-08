package ex0;

public class LinkedQueue<E> implements MyQueue<E> {

    private ListNode<E> front;
    private ListNode<E> back;
    private int size;

    public LinkedQueue(){
        this.front = null;
        this.back = null;
        this.size = 0;
    }
    @Override
    public void enqueue(E item) {
        ListNode<E> node = new ListNode<>(item);
        if(isEmpty()){
            back = node;
            front = node;
        }else{
            back.next = node;
            back = node;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if(isEmpty()){
            return null;
        }
        E data = front.data;
        front = front.next;
        if(front == null){
        back = null;
        }
        size--;
        return data;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == null && back == null;
    }

    private static class ListNode<E>{
        private final E data;
        private ListNode<E> next;

        private ListNode(E data, ListNode<E> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(E data){
            this.data = data;
        }
    }
}
