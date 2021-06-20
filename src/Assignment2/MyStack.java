package Assignment2;

public class MyStack<T> implements DelayedStack<T> {
    private T[] stack;
    private int delay;
    private int maxDelay;
    private int currentDelay;
    private int size;
    private boolean reset;//reset will be true after a sequence of pop operations

    public MyStack(int maxDelay) {
        this.stack = (T[]) new Object[5];
        this.delay = maxDelay;
        this.maxDelay = maxDelay;
        this.currentDelay = maxDelay;
        this.size = 0;
        this.reset = false;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void push(T element) {
        if (size() >= stack.length) {
            T[] temp = (T[]) new Object[size() * 2];
            for (int i = 0; i < size(); i++) {
                temp[i] = stack[i];
            }
            stack = temp;
        }
        stack[size()] = element;
        //if reset is true, the maxDelay will work
        if (reset) {
            currentDelay = maxDelay;
            delay = currentDelay - 1;
            size++;
            reset = false;
        } else {
            size++;
            delay--;
        }
    }

    @Override
    public T pop() throws IllegalStateException {
        if (size() <= 0) {
            throw new IllegalStateException();
        } else if (getDelay() <= 0) {
            T current = stack[size() - 1];
            stack[size() - 1] = null;
            size--;
            reset = true;
            return current;
        }
        return null;
    }

    @Override
    public T peek() throws IllegalStateException {
        if (size <= 0) {
            throw new IllegalStateException();
        }
        return stack[size() - 1];
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
            int length = size();
            for (int i = 0; i < length; i++) {
                stack[i] = null;
            }
            size = 0;
            reset = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(T elem) {
        int length = size();
        if (elem == null) {
            for (int i = 0; i < length; i++) {
                if (stack[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (elem.equals(stack[i])) {
                    return true;
                }
            }
        }
        return false;
    }


}