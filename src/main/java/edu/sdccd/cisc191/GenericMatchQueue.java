package edu.sdccd.cisc191;

import java.util.LinkedList;

public class GenericMatchQueue<T> {

    private final LinkedList<T> items = new LinkedList<>();

    public void enqueue(T item) {
        // TODO: add the item to the back of the queue
        items.addLast(item);
    }

    public T dequeue() {
        // TODO: remove and return the front item
        // throw IllegalStateException if the queue is empty
<<<<<<< HEAD
        if (items.isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }
=======
        if (items.isEmpty()) throw new IllegalStateException("Queue is empty");
>>>>>>> 2f5c252347041e3eb908ff71da5da7a2136e6d86
        return items.removeFirst();
    }

    public T peek() {
        // TODO: return the front item without removing it
        // throw IllegalStateException if the queue is empty
<<<<<<< HEAD
        if (items.isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }
=======
        if (items.isEmpty()) throw new IllegalStateException("Queue is empty");
>>>>>>> 2f5c252347041e3eb908ff71da5da7a2136e6d86
        return items.getFirst();
    }

    public boolean isEmpty() {
        // TODO: return true when the queue has no items
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}