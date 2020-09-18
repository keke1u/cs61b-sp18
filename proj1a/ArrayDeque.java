public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int capacity;
    private static int initialCapacity = 8;
    private static int expandFactor = 2;
    private static int thresholdCapacity = 16;
    private static int contractFactor = 2;
    private static double thresholdRatio = 0.25;

    /** Creates an empty array deque. */
    public ArrayDeque() {
        items = (T[]) new Object[initialCapacity];
        size = 0;
        capacity = initialCapacity;
        nextFirst = capacity - 1;
        nextLast = 0;
    }

    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque */
    public int size() {
        return size;
    }

    /** Helper Method: Decreases index according to circular structure */
    private int oneMinus(int index) {
        if (index == 0) {
            return capacity - 1;
        } else {
            return index - 1;
        }
    }

    /** Helper Method: Increases index according to circular structure */
    private int onePlus(int index) {
        if (index == capacity - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }

    /** Prints the items in the deque from first to last, separated by a space */
    public void printDeque() {
        int currentIndex = onePlus(nextFirst);
        while (currentIndex != nextLast) {
            System.out.print(items[currentIndex] + " ");
            currentIndex = onePlus(currentIndex);
        }
        System.out.println();
    }


    /** Gets the item at given index, where 0 is the front, 1 is the next item.
     * If no such item exists, returns null */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int currentIndex = onePlus(nextFirst);
        while (index != 0) {
            currentIndex = onePlus(currentIndex);
            index -= 1;
        }
        return items[currentIndex];
    }

    /** Resize the original array to a new array with given capacity */
    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];

        int currentFirst = onePlus(nextFirst);
        int currentLast = oneMinus(nextLast);
        if (currentFirst < currentLast) {
            System.arraycopy(items, currentFirst, newItems, 0, size);
            nextFirst = capacity - 1;
            nextLast = size;
        } else {
            int firstLength = capacity - currentFirst;
            int lastLength = nextLast;
            int newCurrentFirst = newCapacity - firstLength;
            System.arraycopy(items, currentFirst, newItems, newCurrentFirst, firstLength);
            System.arraycopy(items, 0, newItems, 0, lastLength);
            nextFirst = oneMinus(newCurrentFirst);
        }
        capacity = newCapacity;
        items = newItems;
    }

    /** Checks whether the array needs to be expanded, and if so, executes it */
    private void expand() {
        if (size == capacity) {
            int newCapacity = capacity * expandFactor;
            resize(newCapacity);
        }
    }

    /** Checks whether the array needs to be contracted, and if so, executes it */
    private void contract() {
        double ratio = (double) size / capacity;
        if (capacity >= thresholdCapacity && ratio < thresholdRatio) {
            int newCapacity = capacity / contractFactor;
            resize(newCapacity);
        }
    }

    /** Adds an item of type T to the front of the deque */
    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = oneMinus(nextFirst);
        size += 1;

        expand();
    }

    /** Adds an item of type T to the back of the deque */
    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = onePlus(nextLast);
        size += 1;

        expand();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        int currentFirst = onePlus(nextFirst);
        T removed = items[currentFirst];
        nextFirst = currentFirst;
        size -= 1;

        contract();

        return removed;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        int currentLast = oneMinus(nextLast);
        T removed = items[currentLast];
        nextLast = currentLast;
        size -= 1;

        contract();

        return removed;
    }

}
