/*
Creating a queue from scratch using the IQueue interface.
*/
public class Queue314<E> implements IQueue<E> {

    private E[] con;
    private int size;
    private int back; // index of the last element in the queue, most recently added
    private int front; // index of the first element in the queue, present the longest
    
    public Queue314() {
        con = getArray(5);
        back = -1;
    }
    
    private E[] getArray(int cap) {
        return (E[]) new Object[cap];
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E val) {
        // have to resize if no extra capacity
        if (con.length == size) {
            // put it off as long as possible, but now
            resize();
        }
        back = (back + 1) % con.length;
        con[back] = val;
        size++;
    }

    // pre: size == con.length
    private void resize() {
        // put front element back at 0
        E[] temp = getArray(con.length * 2);
        int indexCon = front;
        for (int i = 0; i < con.length; i++) {
            temp[i] = con[indexCon];
            indexCon = (indexCon + 1) % con.length;
        }
        front = 0;
        back = size - 1;
        con = temp;
    }

    @Override
    public E front() {
        return con[front];
    }

    @Override
    public E dequeue() {
        E oldFront = front();
        con[front] = null; // prevent memory leak
        front = (front + 1) % con.length;
        size--;
        return oldFront;
    }

}
