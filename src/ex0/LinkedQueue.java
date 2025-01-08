package ex0;

public class LinkedQueue<E> implements MyQueue<E> {

    private ListNode<E> front;
    private ListNode<E> back;
    private int size;

    //Constructor initializes the parameters of Queue
    public LinkedQueue(){
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    /**
     * Encapsulate the item into a ListNode, and then if the Queue is empty, let both the front and back pointers point to the first element. If it is not empty, only increase the back pointer
     * @param item
     */
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
        size++; //update size
    }

    /**
     * If the Queue is empty, an exception is thrown; otherwise, the data of the node pointed to by the front pointer is returned and the position of the front pointer is updated.
     * @return
     */
    @Override
    public E dequeue() {
        if(isEmpty()){
            throw new IllegalStateException("Stack is empty.");
        }
        E data = front.data;
        front = front.next;
        if(front == null){
        back = null;
        }
        size--; // update size
        return data;
    }

    /**
     * If the queue is empty, an exception is thrown. If it is not empty, the data of the node pointed to by front is returned according to the FIFO principle.
     * @return
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
        return front.data;
    }

    /**
     * return size
     * @return
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * return if the queue is empty
     * @return
     */
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
