/*
 * StackNode.java
 */

package rit.stu.act1;

import rit.cs.Stack;
import rit.cs.Node;

/**
 * A stack implementation that uses a Node to represent the structure.
 * @param <T> The type of data the stack will hold
 * @author Sean Strout @ RIT CS
 * @author William J. Reid (wjr3714)
 */
public class StackNode<T> implements Stack<T> {

    // Stack = Last In, First Out (LIFO)
    // Use the methods implemented in the Node class to implement the methods below.

    /** The top of the stack ("the only visible element"). */
    private Node<T> top;
    /** The size of the stack. */
    public int size;

    /**
     * Create an empty stack.
     */
    public StackNode() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Verify whether the stack is empty or not.
     * @return True if the stack is empty, false otherwise.
     */
    @Override
    public boolean empty() {
        return this.size == 0;
    }

    /**
     * Remove the top element in the stack and return it.
     * @return The top element in the stack.
     */
    @Override
    public T pop() {
        assert !empty();
        Node<T> node = top;
        top = top.getNext();
        size--;
        return node.getData();
    }

    /**
     * Add a new element to the top of the stack.
     * @param element The new element to be added to the stack.
     */
    @Override
    public void push(T element) {
        Node<T> node = new Node(element, null);
        if (this.size==0) {
            this.top = node;
        }
        else{
            node.setNext(top);
            this.top = node;
        }
        size++;
    }

    /**
     * Retrieve the top element of the stack.
     * @return The top element of the stack.
     */
    @Override
    public T top() {
        assert !empty();
        return this.top.getData();
    }
}
