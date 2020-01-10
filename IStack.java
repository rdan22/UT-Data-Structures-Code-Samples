public interface IStack<E> {
    
    // pre: none
    // post: val added to the top of this stack
    //  isEmpty() == false, top() return val
    void push(E val); // aka add
    
    // pre: none
    // post: return false if there is one or more elements in this
    // stack, true if no elements
    boolean isEmpty();
    
    // pre: none (often !isEmpty())
    // post: return the top element of the stack if it exists
    E top(); // aka peek
    
    // pre: none (often !isEmpty())
    // post: remove and return the top element if it exists
    E pop(); // aka remove 
    
}
