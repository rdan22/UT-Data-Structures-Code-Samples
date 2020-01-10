/*
Creating a linked list from scratch using Nodes.
*/

public class LinkedList314<E> {

    // first == last == null iff size == 0
    private Node<E> first;
    private Node<E> last;
    private int size;
    
    // default constructor is fine
    
    public int size() {
        return size;
    }
    
    // add val to the end of this list
    public void add(E val) {
        Node<E> newNode = new Node<>(val);
        if (size == 0) {
            first = newNode; // is no old last node
        } else {
            // list isn't empty
            // make the old last node's next reference
            // refer to the new node
            last.setNext(newNode);
        }
        last = newNode;
        size++;
    }
    
    // really should toString, print out the elements of this
    // list in order from first to last
    public void printList() {
        Node<E> temp = first;
        while (temp != null) {
            System.out.print(temp.getData() + " ");
            // make temp refer to the next node in the structure
            temp = temp.getNext();
        }
        // what does temp equal here?? temp == null
        System.out.println();
        
        // Professor Norman memorial Loop
//        for (Node<E> temp = first; temp != null; temp = temp.getNext()) {
//            System.out.print(temp.getData() + " ");
//        }
    }
    
    // add val to the front of this list
    public void addFront(E val) {
        if (size == 0) {
            add(val); 
        } else {
            Node<E> newNode = new Node<>(val, first);
            first = newNode;
            size++;
        }
    }
    
    // pre: 0 <= pos <= size()
    public void insert(int pos, E val) {
        if (pos == size) {
            add(val);
        } else if (pos == 0) {
            addFront(val);
        } else {
            // general case, won't affect first or last
            // get reference to node at pos - 1
            Node<E> temp = getNodeAtPos(pos - 1);
            Node<E> newNode = new Node<>(val, temp.getNext());
            temp.setNext(newNode);
            size++;
        }
    }

    // return a reference to the node at the given position
    // pre: 0 <= pos < size
    private Node<E> getNodeAtPos(int pos) {
        if (pos == size - 1) {
            return last;
        }
        Node<E> temp = first;
        for (int i = 0; i < pos; i++) {
            temp = temp.getNext();
        }
        return temp;
    }
    
    // pre: 0 <= pos < size()
    // post: return the element at the given position in this list
    public E get(int pos) {
        return getNodeAtPos(pos).getData();
    }
    
    // pre: size() > 0
    // post: remove and return the first element in this list
    public E removeFirst() {
        E temp = first.getData(); // data being removed and returned
        first = first.getNext();
        size--;
        if (size == 0) {
            assert first == null : "I was wrong about first: " + first; 
            last = null;
        }
        return temp;
    }
    
    public E remove(int pos) {
        if (pos == 0) {
            return removeFirst();
        }
        // general case
        Node<E> temp = getNodeAtPos(pos - 1);
        E result = temp.getNext().getData();
        // make the node that temp is referring to's next reference
        // refer to the node after the node we are about to remove
        temp.setNext(temp.getNext().getNext());
        size--;
        if (pos == size) {
            // OH YEA, better last
            last = temp; // temp is referring to the new last node
        }
        return result;
  
    }
    
    
    
}
