package synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    /* All methods that you declare or define are automatically public and abstract,
     * even if you omit the public keyword
     */
    int capacity();    // return size of the buffer
    int fillCount();   // return number of items currently in the buffer
    void enqueue(T x); // add item x to the end
    T dequeue();       // delete and return item from the front
    T peek();          // return (but do not delete) item from the front

    default boolean isEmpty() { // is the buffer empty?
        return fillCount() == 0;
    }

    default boolean isFull() {
        return fillCount() == capacity();
    }

    Iterator<T> iterator();
}
