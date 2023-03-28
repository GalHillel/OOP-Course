import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UndoableStringBuilderTest {
    private char rand_char() { // ALL symbols
        return (char) (int) Math.floor(Math.random() * (-128) + 128);
    }

    private int rand_num(int size) {
        return (int) Math.floor(Math.random() * (-size + 1) + size);
    }

    private String rand_st(int size) {
        StringBuilder st = new StringBuilder();
        for (int i = 0; i < size; i++) {
            st.append(rand_char());
        }
        return st.toString();
    }

    @Test
    void append() {
        UndoableStringBuilder SB = new UndoableStringBuilder();
        String st1 = rand_st(100);
        SB.append(st1);
        assertEquals(SB.toString(), st1);
        String st2 = rand_st(40);
        SB.append(st2);
        assertEquals(SB.toString(), st1 + st2);
    }

    @Test
    void delete() {
        UndoableStringBuilder SB = new UndoableStringBuilder();
        String st1 = rand_st(100);
        SB.append(st1);
        int rand1 = rand_num(st1.length());
        int rand2 = rand_num(st1.length());
        SB.delete(Math.min(rand1, rand2), Math.max(rand1, rand2));
        assertEquals(SB.toString(), st1.substring(0, Math.min(rand1, rand2)) + st1.substring(Math.max(rand1, rand2)));
        st1 = SB.toString();
        int rand3 = rand_num(st1.length());
        int rand4 = rand_num(st1.length());
        SB.delete(Math.min(rand3, rand4), Math.max(rand3, rand4));
        assertEquals(SB.toString(), st1.substring(0, Math.min(rand3, rand4)) + st1.substring(Math.max(rand3, rand4)));
    }

    @Test
    void insert() {
        UndoableStringBuilder SB = new UndoableStringBuilder();
        String st1 = rand_st(100);
        SB.append(st1);
        String st2 = rand_st(50);
        int rand = (int) Math.floor(Math.random() * (-100 + 1) + 100);
        SB.insert(rand, st2);
        assertEquals(SB.toString(), st1.substring(0, rand) + st2 + st1.substring(rand));
    }

    @Test
    void replace() {
        UndoableStringBuilder SB = new UndoableStringBuilder();
        String st1 = rand_st(100);
        SB.append(st1);
        String st2 = rand_st(20);
        int rand1 = (int) Math.floor(Math.random() * (-50 + 1) + 50);
        int rand2 = (int) Math.floor(Math.random() * (50 - 100 + 1) + 100);
        SB.replace(rand1, rand2, st2);
        assertEquals(SB.toString(), st1.substring(0, rand1) + st2 + st1.substring(rand2));
    }

    @Test
    void reverse() {
        UndoableStringBuilder SB = new UndoableStringBuilder();
        String st1 = rand_st(100);
        SB.append(st1);
        SB.reverse();
        String SBst = SB.toString();
        for (int i = 0; i < st1.length(); i++) {
            assertEquals(SBst.charAt(i), st1.charAt(st1.length() - 1 - i));
        }
    }

    @Test
    void undo() {
        UndoableStringBuilder SB = new UndoableStringBuilder();
        Stack<String> us = new Stack<>();
        String st1 = rand_st(100);
        int rand1 = rand_num(st1.length());
        int rand2 = rand_num(st1.length());
        String st2 = rand_st(50);
        SB.append(st1);
        us.push(SB.toString());
        SB.delete(Math.min(rand1, rand2), Math.max(rand1, rand2));
        us.push(SB.toString());
        SB.insert(SB.toString().length(), st2);
        us.push(SB.toString());
        SB.replace(Math.min(rand1, rand2), Math.max(rand1, rand2), st1);
        us.push(SB.toString());
        // starting undo sequence:
        assertEquals(SB.toString(), us.pop());
        SB.undo();
        assertEquals(SB.toString(), us.pop());
        SB.undo();
        assertEquals(SB.toString(), us.pop());
        SB.undo();
        assertEquals(SB.toString(), us.pop());

    }

    @Test
    void testToString() {
    }
}