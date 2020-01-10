/*
Creating a stack from scratch using the IStack interface.
*/
import java.util.LinkedList;

public class Stack314<E> implements IStack<E> {

    // front of list is top of stack
    private LinkedList<E> con;
    
    public Stack314() {
        con = new LinkedList<>();  
    }
    
    // pre: val != null
    // post: !isEmpty(), top() returns val
    public void push(E val) {
        con.addFirst(val);
    }
    
    public boolean isEmpty() {
        return con.isEmpty();
    }
    
    // pre: !isEmpty()
    // post: remove and return the element most recently added to this stack
    public E pop() {
        return con.removeFirst();
    }
    
    // pre: !isEmpty()
    // post: return the element most recently added to this stack
    public E top() {
        return con.getFirst();
    }
    
    // print st in reverse order
    public static <T> void printRev(Stack314<T> st) {
        Stack314<T> temp = new Stack314<>();
        while (!st.isEmpty()) {
            temp.push(st.pop());
        }
        // now temp stores st in reverse order
        // now reverse temp back into st while printing
        while (!temp.isEmpty()) {
            System.out.println(temp.top());
            st.push(temp.pop());
        }
    }

    public static <T> void printRevRec(Stack314<T> st) {
        // recursive case
        if (!st.isEmpty()) {
            T top = st.pop();
            // print out the rest of st in reverse order
            printRevRec(st);
            System.out.println(top);
            st.push(top);
        }
        // else stack empty, do NOTHING, we're done
    }
}
