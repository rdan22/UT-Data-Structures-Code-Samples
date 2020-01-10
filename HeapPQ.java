/*
Creating a heap using a priority queue with array as an internal container.
*/
import java.util.Arrays;

public class HeapPQ<E extends Comparable<? super E>> implements PQ<E>{

    private int size;
    private E[] con;

    public HeapPQ() {
        con = getArray(2);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E front() {
        return con[1];
    }

    public int size() {
        return size;
    }

    public void enqueue(E newValue) {
        // make sure we have a spot
        if (size == con.length - 1) {
            enlargeArray(con.length * 2);
        }
        // determine index of new value
        size++;
        int indexNew = size;
        int parentIndex = indexNew / 2;
        // while I am not the root node and I am less than my parent
        // bubble up the tre.
        while (indexNew != 1 
                && newValue.compareTo(con[parentIndex]) < 0) {
            con[indexNew] = con[parentIndex];
            indexNew = parentIndex;
            parentIndex = indexNew / 2;
        }
        con[indexNew] = newValue;
    }


    private void enlargeArray(int newCapacity) {
        E[] temp = getArray(newCapacity);
        System.arraycopy(con, 1, temp, 1, size);
        con = temp;
    }

    private E[] getArray(int newCapacity) {
        return (E[]) (new Comparable[newCapacity]);
    }

    public void printTree() {
        printTree(1, "");
    }

    public E dequeue( ) {
        E result = con[1];
        int indexOfHole = 1;
        boolean done = false;
        // keep moving value down until at lowest left OR 
        // both children greater than or equal to value
        while(indexOfHole * 2 < size && !done) {
            // assume left child is smaller initially
            int smallerChild = indexOfHole * 2;

            if(con[smallerChild].compareTo(con[smallerChild + 1]) > 0) {
                // assumption wrong, right child is smaller of the two
                smallerChild++;
            }

            // is replacement value larger than smaller child???
            if(con[size].compareTo(con[smallerChild]) > 0) {
                // NOT DONE YET, move smaller child up
                con[indexOfHole] = con[smallerChild];
                indexOfHole = smallerChild;
            } else {
                done = true;
            }
        }

        con[indexOfHole] = con[size];
        con[size] = null;
        size--;
        return result;
    }


    private void printTree(int index, String spaces) {
        if(index <= size) {
            printTree(index * 2 + 1, spaces + "    ");
            System.out.println(spaces + con[index]);
            printTree(index * 2, spaces + "    ");
        }
    }

    public void printArray() {
        System.out.println(Arrays.toString(con));
    }
}
