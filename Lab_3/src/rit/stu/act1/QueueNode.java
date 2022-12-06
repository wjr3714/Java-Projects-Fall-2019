/*
 * QueueNode.java
 */

package rit.stu.act1;

import rit.cs.Node;
import rit.cs.Queue;

/**
 * A queue implementation that uses a Node to represent the structure.
 * @param <T> The type of data the queue will hold
 * @author Sean Strout @ RIT CS
 * @author William J. Reid (wjr3714)
 */
public class QueueNode<T> implements Queue<T> {

    // Queue = First In, First Out (FIFO)
    // Use the methods implemented in the Node class to implement the methods below.

    /** The current front of the queue. The first element in the queue that has not left the queue. */
    private Node<T> front;
    /** The current back of the queue. The last element in the queue that has not left the queue. */
    private Node<T> back;
    /** The size of the queue. */
    private int size;

    /**
     * Create an empty queue.
     */
    public QueueNode() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    /**
     * Retrieve the last element in the queue.
     * @return The last element in the queue.
     */
    @Override
    public T back() {
        assert !empty();
        return this.back.getData();
    }

    /**
     * Remove front element in the queue and return it.
     * @pre Assert the queue is not empty.
     * @return The front element in the queue.
     */
    @Override
    public T dequeue() {
        assert !empty();
        Node <T> node = this.front;
        this.front = this.front.getNext();
        this.size--;
        return node.getData();
    }

    /**
     * Verify whether the queue is empty or not.
     * @return True if the queue is empty, false otherwise.
     */
    @Override
    public boolean empty() {
        return this.size == 0;
    }

    /**
     * Add a new element to the end (back) of the queue.
     * @param element The new element to be added to the queue.
     */
    @Override
    public void enqueue(T element) {
        Node<T> node = new Node(element, null);
        if (this.size == 0){
            this.front = node;
        }
        else{
            this.back.setNext(node);
        }
        this.back = node;
        this.size++;
    }

    /**
     * Retrieve the front (first) element in the queue.
     * @return The front element in the queue.
     */
    @Override
    public T front() {
        assert !empty();
        return this.front.getData();
    }
}
