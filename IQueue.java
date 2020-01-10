public interface IQueue<E> {

    // return true if empty, false otherwise
    public boolean isEmpty();
    
    // add val to end of this queue
    public void enqueue(E val); // aka add
    
    // return the element in this queue
    // that has been present the longest
    public E front(); // aka peek
    
    // remove and return the element in this queue
    // that has been present the longest
    public E dequeue(); // remove
}
