// package <package name>;
package synthesizer;
// import java.lang.reflect.Array;
import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // this.capacity should be set appropriately. Note that the local variable
        // here shadows the field we inherit from AbstractBoundedQueue, so
        // you'll need to use this.capacity to set the capacity
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Helper method to increase index first or last
     */
    private int onePlus(int index) {
        if (index == capacity - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }

        rb[last] = x;
        last = onePlus(last);
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        first = onePlus(first);
        fillCount -= 1;
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int numRemain;
        private int current;

        ArrayRingBufferIterator() {
            numRemain = fillCount();
            current = first;
        }

        public boolean hasNext() {
            return numRemain > 0;
        }

        public T next() {
            T returnItem = rb[current];
            numRemain -= 1;
            current = onePlus(current);
            return returnItem;
        }
    }
}
