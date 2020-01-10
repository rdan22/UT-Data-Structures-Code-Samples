/*
Creating a generic list with array as internal container.
*/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GenericList<E> implements Iterable<E> {

    private static final int DEFAULT_INITIAL_CAP = 10;

    // instance variables
    private E[] con; // short for container
    private int size;

    // create an empty list
    public GenericList() {
        con = getArray(DEFAULT_INITIAL_CAP);
    }
    
    
    private E[] getArray(int cap) {
        return (E[]) new Object[cap];
    }


    // create an empty list with the given capacity
    // pre: cap > 0 
    public GenericList(int cap) {
        if (cap <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0: " + cap);
        }
        con = getArray(cap);
    }

    // pre: data != null, 
    // post: create an GenericList that is a shallow copy of the elements in data
    public GenericList(E[] data) {
        if (data == null) {
            // whoops!
            throw new IllegalArgumentException("parameter data may not be null");
        }
        con = getArray(data.length + DEFAULT_INITIAL_CAP);
        System.arraycopy(data, 0, con, 0, data.length);
        size = data.length;
    }

    public int size() {
        return size;
    }

    // pre: 0 <= pos < size()
    // return the element at the given position
    public E get(int pos) {
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException("position "
                    + " is out of bounds: " + pos
                    + " size of list is " + size());
        }
        return con[pos];
    }

    // pre: none
    // post: get(size() - 1) returns value added, in other words add value to end of list
    public void add(E val) {
        insert(size, val);
    }


    // newCap > con.length
    private void ensureCapacity(int newCap) {
        E[] temp = getArray(newCap);
        System.arraycopy(con, 0, temp, 0, con.length);
        con = temp;
    }


    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[" + con[0]);
        for (int i = 1; i < size; i++) {
            // what if added list to itself
            if (this == con[i]) {
                sb.append("[this list]");
            } else {
                sb.append(", ");
                sb.append(con[i]);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // pre: 0 <= pos <= size()
    // post: get(pos) returns val
    // size() = old size() + 1
    // old elements at position pos and greater 
    // are shifted one element to the right
    public void insert(int pos, E val) {
        // pre con
        if (size == con.length) {
            ensureCapacity(con.length * 2);
        }

        for (int i = size; i > pos; i--) {
            con[i] = con[i - 1];
        }
        con[pos] = val;
        size++;        
    }

    // pre: 0 <= pos < size()
    // post: element at given position removed from list
    // size() = old size() - 1
    // all elements at positions > pos shifted
    // one index to the left
    public E remove(int pos) {
        // check precon
        E removed = con[pos];
        size--;
        for (int i = pos; i < size; i++) {
            con[i] = con[i + 1];
        }
        // let the GC do its job
        con[size] = null;
        return removed;
    }

//    // pre: 0 <= pos <= size(), other != null
//    // post: size() = old size() + other.size()
//    // all elements of other inserted into this
//    // list starting at position pos
//    private void insertAllSLOW(int pos, IntList other) {
//        for (int i = 0; i < other.size; i++) {
//            insert(pos + i, other.con[i]);
//        }
//    }

    // pre: 0 <= pos <= size(), other != null, other is not this list
    // post: size() = old size() + other.size()
    // all elements of other inserted into this
    // list starting at position pos
    public void insertAll(int pos, GenericList<E> other) {
        // check precons
        if (other == this) {
            throw new IllegalArgumentException("cannot insert listinto itself");
        }

        if (size + other.size > con.length) {
            ensureCapacity(size + other.size + DEFAULT_INITIAL_CAP);
        }

        // shift down elements after insert pos
        for (int i = size - 1; i >= pos; i--) {
            con[i + other.size] = con[i];
        }

        // add elements from other
        for (int i = 0; i < other.size; i++) {
            con[i + pos] = other.con[i];
        }
    }
    
    public boolean equals(Object o) {
        boolean same = o != null && getClass() == o.getClass();
        if (same) {
            GenericList<?> oList = (GenericList<?>) o;
            same = size == oList.size;
            int i = 0;
            while (same && i < size) {
                same = con[i].equals(oList.con[i]);
                i++;
            }
        }
        return same;
    }

    public Iterator<E> iterator() {
        return new GLIterator();
    }
    
    // inner class
    private class GLIterator implements Iterator<E> {
        
        private int indexOfNext;
        private boolean removeOk;
        
        public boolean hasNext() {
            return indexOfNext < size();
        }       
        
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("hasNext is false.");
            }
            removeOk = true;
            E result = con[indexOfNext];
            indexOfNext++;
            return result;
        }
        
        // remove the last thing returned by next
        public void remove() {
            if (!removeOk) {
                throw new IllegalStateException("Must call next before calling next.");
            }
            removeOk = false;
            indexOfNext--;
            GenericList.this.remove(indexOfNext);
        }
    }
    
}
