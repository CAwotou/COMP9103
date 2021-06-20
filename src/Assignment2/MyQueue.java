package Assignment2;

public class MyQueue<T> implements DelayedQueue<T> {
    private T[] queue;
    private int delay;
    private int maxDelay;
    private int currentDelay;
    private int size;
    private int index;//the start position of elements in the array
    private int length;//the end position of elements in the array
    private boolean reset;//reset will be true after a sequence of dequeue operations

    public MyQueue(int maxDelay) {
        this.queue = (T[]) new Object[5];
        this.delay = maxDelay;
        this.maxDelay = maxDelay;
        this.currentDelay = maxDelay;
        this.size = 0;
        this.index = 0;
        this.length = 0;
        this.reset = false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void enqueue(T element) {
        if (length >= queue.length) {
            T[] temp = (T[]) new Object[size() * 2];
            for (int i = index, j = 0; i < length; i++, j++) {
                temp[j] = queue[i];
            }
            queue = temp;
            index = 0;
            length = size();
        }
        //if reset is true, the maxDelay will work
        if (reset) {
            currentDelay = maxDelay;
            delay = currentDelay - 1;
            reset = false;
        } else {
            delay--;
        }
        queue[length] = element;
        length++;
        size++;
    }

    @Override
    public T dequeue() throws IllegalStateException {
        if (size <= 0) {
            throw new IllegalStateException();
        } else if (getDelay() <= 0) {
            T first = queue[index];
            index++;
            size--;
            reset = true;
            return first;
        }
        return null;
    }

    @Override
    public T peek() throws IllegalStateException {
        if (size() <= 0) {
            throw new IllegalStateException();
        }
        return queue[index];
    }

    @Override
    public int getDelay() {
        if (delay < 0) {
            return 0;
        }
        return delay;
    }

    @Override
    public void setMaximumDelay(int d) {
        //maxDelay will not work immediately
        maxDelay = d;
    }

    @Override
    public int getMaximumDelay() {
        return maxDelay;
    }

    @Override
    public boolean clear() {
        if (getDelay() <= 0) {
            T[] temp = (T[]) new Object[100];
            queue = temp;
            size = 0;
            index = 0;
            length = 0;
            reset = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(T elem) {
        if (elem == null) {
            for (int i = index; i < length; i++) {
                if (queue[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = index; i < length; i++) {
                if (elem.equals(queue[i])) {
                    return true;
                }
            }
        }
        return false;
    }
}