public class LinkedListDeque<T> implements Deque<T> {
    private IntNode sentinel;
    private int size;

    private class IntNode {
        private IntNode prev;
        private T item;
        private IntNode next;

        public IntNode(IntNode p, T i, IntNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    /** Creates an empty linked list deque */
    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** Creates a linked list deque */
    /*

    public LinkedListDeque(T x) {
        sentinel = new IntNode(null, null, null);
        sentinel.next = new IntNode(sentinel, x, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

     */

    @Override
    /** Adds an item of type T to the front of the deque */
    public void addFirst(T item) {
        sentinel.next.prev = new IntNode(sentinel, item, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size += 1;
    }

    @Override
    /** Adds an item of type T to the back of the deque */
    public void addLast(T item) {
        sentinel.prev.next = new IntNode(sentinel.prev, item, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    @Override
    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    /** Returns the number of items in the deque */
    public int size() {
        return size;
    }

    @Override
    /** Prints the items in the deque from first to last, separated by a space */
    public void printDeque() {
        IntNode p = sentinel;

        while (p.next.item != null) {
            System.out.print(p.next.item);
            System.out.print(' ');
            p = p.next;
        }
    }

    @Override
    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        IntNode p = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return p.item;
    }

    @Override
    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        IntNode p = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return p.item;
    }

    @Override
    /** Gets the item at given index, where 0 is the front, 1 is the next item.
     * If no such item exists, returns null */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        IntNode p = sentinel.next;
        while (index != 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /** Same as get, but uses recursion */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel, index);
    }

    private T getRecursiveHelper(IntNode start, int index) {
        if (index == 0) {
            return start.next.item;
        }
        return getRecursiveHelper(start.next, index - 1);
    }

}
