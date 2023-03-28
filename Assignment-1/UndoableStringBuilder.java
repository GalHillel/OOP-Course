import java.util.Stack;

public class UndoableStringBuilder {
    private String str;
    private Stack<String> Str = new Stack<String>();

    /**
     * +
     * A function that adds a received string to the existing string
     *
     * @param str - The received string
     */
    public UndoableStringBuilder append(String str) {

        if (Str.isEmpty()) {
            Str.push(str);
        } else {
            this.str = Str.pop() + str;
            Str.push(this.str);
        }
        return this;
    }

    /**
     * A function that erase the sequence between the received indexes
     *
     * @param start
     * @param end
     */
    public UndoableStringBuilder delete(int start, int end) {
        this.str = Str.peek();
        String s1 = "", s2 = "";
        if (start < end && end < this.str.length()) {
            for (int i = 0; i < start; i++)
                s1 = s1 + this.str.charAt(i);
            for (int i = end; i < this.str.length(); i++)
                s2 = s2 + this.str.charAt(i);
            this.str = s1 + s2;
            Str.push(this.str);
        }
        return this;
    }

    /**
     * A function that switches between the received string and the string house
     * between the indexes
     *
     * @param start - The index of starting to replace the string
     * @param end   - The index of ending to replace the string
     * @param str   - The received string
     */
    public UndoableStringBuilder replace(int start, int end, String str) {
        this.str = Str.peek();
        String s1 = "", s2 = "";
        if (start <= end && str != null) {
            for (int i = 0; i < start; i++) {
                s1 = s1 + this.str.charAt(i);
            }
            for (int i = end; i < this.str.length(); i++) {
                s2 = s2 + this.str.charAt(i);
            }
            this.str = s1 + str + s2;
            Str.push(this.str);
        }
        return this;
    }

    /**
     * A function that inserts the string into this character sequence
     *
     * @param offset - The index of adding the string
     * @param str    - The received string
     */
    public UndoableStringBuilder insert(int offset, String str) {
        replace(offset, offset, str);
        return this;
    }

    /**
     * A function that causes the current sequence to be replaced by the reverse of
     * the
     * sequence
     */
    public UndoableStringBuilder reverse() {
        this.str = Str.peek();
        String s = "";
        for (int i = this.str.length() - 1; i >= 0; i--)
            s = s + this.str.charAt(i);
        Str.push(s);
        return this;
    }

    /**
     * A function that undoes the last action taken
     */
    public void undo() {
        if (!this.Str.isEmpty())
            this.Str.pop();
    }

    /**
     * A toString function
     *
     * @return
     */
    public String toString() {
        return Str.peek().toString();
    }
}
