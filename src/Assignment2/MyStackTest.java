package Assignment2;

import static org.junit.Assert.*;

import org.junit.Test;

public class MyStackTest {

    @Test
    public void testStackConstructor() {
        MyStack<String> s = new MyStack<>(123);
        assertEquals(123, s.getDelay());
        s.push("abc");
        assertEquals(122, s.getDelay());
        assertEquals(1, s.size());
        assertEquals(null, s.pop());
        assertEquals(123, s.getMaximumDelay());
        assertEquals(122, s.getDelay());
        assertFalse(s.contains("123"));
        assertTrue(s.contains("abc"));
    }

    @Test
    public void testSize() {
        MyStack<String> s = new MyStack<>(10);
        assertEquals(0, s.size());
        for (int i = 0; i < 99; i++) {
            s.push(i + "");
        }
        assertEquals(99, s.size());
        s.pop();
        assertEquals(98, s.size());

        MyStack<Integer> s1 = new MyStack<>(10);
        s1.push(1);
        assertEquals(1, s1.size());
        s1.pop();
        assertEquals(1, s1.size());
        for (int i = 0; i < 9; i++) {
            s1.push(i);
        }
        assertEquals(10, s1.size());
        s1.pop();
        assertEquals(9, s1.size());
        s1.push(10);
        assertEquals(10, s1.size());
        s1.pop();
        assertEquals(10, s1.size());
    }

    @Test
    public void testPop() {
        MyStack<String> s = new MyStack<>(100);
        assertEquals(0, s.size());
        s.push("abc");
        assertEquals(null, s.pop());
        for (int i = 0; i < 20; i++) {
            s.push(i + "");
        }
        assertEquals(21, s.size());
        assertEquals(null, s.pop());
        for (int i = 0; i < 100; i++) {
            s.push(i + "");
        }
        assertEquals(121, s.size());
        assertEquals("99", s.pop());
        MyStack<Integer> s1 = new MyStack<>(100);
        assertEquals(0, s1.size());
        assertEquals(100, s1.getDelay());
        for (int i = 0; i < 100; i++) {
            s1.push(i);
        }
        assertEquals(0, s1.getDelay());
        assertEquals(100, s1.size());
        assertEquals((Integer) 99, s1.pop());
        assertEquals((Integer) 98, s1.pop());
        assertEquals(0, s1.getDelay());
        assertEquals(98, s1.size());
        s1.push(100);
        assertEquals(99, s1.getDelay());
        assertEquals(99, s1.size());
    }

    @Test
    public void testPop2() {
        MyStack<String> s = new MyStack<>(4);
        s.push("1");
        s.push("2");
        s.push("3");
        s.setMaximumDelay(10);
        s.push("4");
        assertEquals(0, s.getDelay());
        assertEquals(10, s.getMaximumDelay());
        assertEquals("4", s.pop());
        assertEquals(0, s.getDelay());
        s.push("5");
        assertEquals(9, s.getDelay());
    }

    @Test
    public void testPop3() {
        MyStack<String> s = new MyStack<>(10);
        for (int i = 0; i < 10; i++) {
            s.push(i + "");
        }
        assertEquals(0, s.getDelay());
        s.pop();
        s.push("a");
        assertEquals(9, s.getDelay());
        s.push("b");
        assertEquals(8, s.getDelay());
        assertEquals(null, s.pop());
        for (int i = 0; i < 10; i++) {
            s.push(i + "");
        }
        assertEquals("9", s.pop());
        assertEquals("8", s.pop());
        assertEquals("7", s.pop());
        assertEquals("6", s.pop());
    }

    @Test
    public void testPeek() {
        MyStack<String> s = new MyStack<>(2);
        for (int i = 0; i < 20; i++) {
            s.push(i + "");
        }
        assertEquals("19", s.peek());
        assertEquals("19", s.peek());
        assertEquals("19", s.peek());
        s.push(null);
        assertEquals(null, s.peek());
    }

    @Test
    public void testPeek2() {
        MyStack<Integer> s = new MyStack<>(1000);
        for (int i = 0; i < 200; i++) {
            s.push(i);
        }
        assertEquals((Integer) 199, s.peek());
        s.push(null);
        assertEquals(null, s.peek());
        s.push(123345678);
        assertEquals((Integer) 123345678, s.peek());
    }


    @Test
    public void testGetDelay() {
        MyStack<String> s = new MyStack<>(5);
        assertEquals(5, s.getDelay());
        s.push("a");
        assertEquals(4, s.getDelay());
        s.push("b");
        s.push("c");
        s.push("d");
        s.push("e");
        assertEquals(0, s.getDelay());
        s.pop();
        assertEquals(0, s.getDelay());
        s.push("f");
        assertEquals(4, s.getDelay());
    }

    @Test
    public void testGetMaxDelay() {
        MyStack<String> s = new MyStack<>(10);
        assertEquals(10, s.getDelay());
        s.push("a");
        s.push("b");
        assertEquals(8, s.getDelay());
        s.setMaximumDelay(4);
        assertEquals(4, s.getMaximumDelay());
        s.setMaximumDelay(-1);
        assertEquals(-1, s.getMaximumDelay());
        for (int i = 0; i < 10; i++) {
            s.push(i + "");
        }
        assertEquals(-1, s.getMaximumDelay());
        s.pop();
        s.push("c");
        assertEquals(0, s.getDelay());
        assertEquals(-1, s.getMaximumDelay());
    }

    @Test
    public void testClear() {
        MyStack<String> s = new MyStack<>(2);
        s.push("a");
        assertEquals(1, s.getDelay());
        assertFalse(s.clear());
        s.push("b");
        assertTrue(s.clear());
        assertTrue(s.clear());
        s.push("a");
        assertEquals(1, s.getDelay());
        assertFalse(s.clear());
        s.push("b");
        assertEquals(0, s.getDelay());
        assertTrue(s.clear());
        assertFalse(s.contains("a"));
        assertFalse(s.contains("b"));
    }

    @Test
    public void testContains() {
        MyStack<String> s = new MyStack<>(2);
        s.push("a");
        s.push("b");
        s.push(null);
        assertTrue(s.contains("a"));
        assertTrue(s.contains("b"));
        assertTrue(s.contains(null));
        assertFalse(s.contains("1"));
        assertTrue(s.clear());
        assertFalse(s.contains("a"));
        assertFalse(s.contains("b"));
        s.setMaximumDelay(4);
        s.push("1");
        assertEquals(3, s.getDelay());
        assertTrue(s.contains("1"));
    }

    @Test(expected = IllegalStateException.class)
    public void testExceptionOfPop1() {
        MyStack<String> s = new MyStack<>(2);
        s.pop();
    }

    @Test
    public void testExceptionOfPop2() {
        MyStack<String> s = new MyStack<>(2);
        try {
            s.push("a");
            s.push("b");
            assertEquals("b", s.pop());
            assertEquals("a", s.pop());
            s.pop();
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            return;
        }


    }

    @Test
    public void testExceptionOfPop3() {
        MyStack<String> s = new MyStack<>(2);
        try {
            s.push("a");
            s.push("b");
            s.clear();
            s.pop();
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            return;
        }


    }

    @Test(expected = IllegalStateException.class)
    public void testExceptionOfPeek1() {
        MyStack<String> s = new MyStack<>(2);
        s.peek();
    }

    @Test
    public void testExceptionOfPeek2() {
        MyStack<String> s = new MyStack<>(2);
        try {
            s.push("a");
            s.push("b");
            s.clear();
            s.peek();
            fail("Exception was not thrown");
        } catch (IllegalStateException e) {
            return;
        }
    }
}
