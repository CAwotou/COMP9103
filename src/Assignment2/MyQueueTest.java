package Assignment2;

import org.junit.Test;

import static org.junit.Assert.*;


public class MyQueueTest {

    @Test
    public void testQueueConstructor() {
        MyQueue<String> q = new MyQueue<>(4);
        assertEquals(4, q.getDelay());
        assertEquals(0, q.size());
        assertEquals(4, q.getMaximumDelay());
        assertFalse(q.contains("abc"));
    }

    @Test
    public void testQueueSize() {
        MyQueue<String> q = new MyQueue<>(3);
        assertEquals(0, q.size());
        q.enqueue("1");
        assertEquals(1, q.size());
        q.enqueue("2");
        assertEquals(2, q.size());
        q.enqueue("3");
        q.clear();
        assertEquals(0, q.size());
    }

    @Test
    public void testEnQueue() {
        MyQueue<String> q = new MyQueue<>(3);
        assertEquals(0, q.size());
        for (int i = 0; i < 10; i++) {
            q.enqueue(i + "");
        }
        assertEquals(10, q.size());
        q.setMaximumDelay(10);
        for (int i = 0; i < 10; i++) {
            q.enqueue(i + "");
        }
        assertEquals(0, q.getDelay());
        assertEquals("0", q.dequeue());
        assertEquals(19, q.size());
    }

    @Test
    public void testDeque() {
        MyQueue<String> q = new MyQueue<>(2);
        q.enqueue("1");
        assertEquals(null, q.dequeue());
        q.enqueue("2");
        assertEquals("1", q.dequeue());
        assertEquals(0, q.getDelay());
        assertEquals(2, q.getMaximumDelay());
        assertEquals("2", q.dequeue());
    }

    @Test
    public void testPeek() {
        MyQueue<String> q = new MyQueue<>(2);
        q.enqueue("1");
        assertEquals("1", q.peek());
        for (int i = 0; i < 100; i++) {
            q.enqueue("" + i);
        }
        assertEquals("1", q.peek());
        assertEquals(101, q.size());
    }

    @Test
    public void testGetDelay() {
        MyQueue<String> q = new MyQueue<>(2);
        assertEquals(2, q.getDelay());
        q.setMaximumDelay(4);
        assertEquals(2, q.getDelay());
        q.enqueue("1");
        assertEquals(1, q.getDelay());
        q.enqueue("2");
        assertEquals(0, q.getDelay());
        assertEquals("1", q.dequeue());
        q.enqueue("3");
        assertEquals(3, q.getDelay());
    }

    @Test
    public void testSetMaxDelay() {
        MyQueue<String> q = new MyQueue<>(2);
        assertEquals(2, q.getMaximumDelay());
        q.setMaximumDelay(10);
        assertEquals(10, q.getMaximumDelay());
        assertEquals(2, q.getDelay());
    }

    @Test
    public void testClear() {
        MyQueue<String> q = new MyQueue<>(2);
        assertFalse(q.clear());
        q.enqueue("1");
        assertFalse(q.clear());
        q.enqueue("2");
        assertTrue(q.clear());
    }

    @Test
    public void testContains() {
        MyQueue<String> q = new MyQueue<>(2);
        assertFalse(q.contains("123"));
        assertFalse(q.contains(null));
        q.enqueue(null);
        assertFalse(q.contains("123"));
        assertTrue(q.contains(null));
        q.enqueue("123");
        assertTrue(q.contains("123"));
        q.dequeue();
        assertFalse(q.contains(null));
        assertTrue(q.contains("123"));
        q.dequeue();
        assertFalse(q.contains("123"));
    }

    @Test(expected = IllegalStateException.class)
    public void testExceptionOfDeQueue1() {
        MyQueue<String> q = new MyQueue<>(2);
        q.dequeue();
    }

    @Test
    public void testExceptionOfDeQueue2() {
        MyQueue<String> q = new MyQueue<>(2);
        try{
            q.enqueue("1");
            q.enqueue("2");
            q.clear();
            q.dequeue();
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            return;
        }
    }

    @Test
    public void testExceptionOfDeQueue3() {
        MyQueue<String> q = new MyQueue<>(2);
        try{
            q.enqueue("1");
            q.enqueue("2");
            assertEquals("1",q.dequeue());
            assertEquals("2",q.dequeue());
            q.dequeue();
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            return;
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testExceptionOfPeek1() {
        MyQueue<String> q = new MyQueue<>(2);
        q.peek();
    }

    @Test
    public void testExceptionOfPeek2(){
        MyQueue<String> q = new MyQueue<>(2);
        try{
            q.enqueue("1");
            q.enqueue("2");
            q.clear();
            q.peek();
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            return;
        }
    }
}
