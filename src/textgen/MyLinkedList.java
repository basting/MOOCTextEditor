package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
    LLNode<E> head;
    LLNode<E> tail;
    int size;

    /** Create a new empty LinkedList */
    public MyLinkedList() {
        size = 0;
    }

    /**
     * Appends an element to the end of the list
     * 
     * @param element
     *            The element to add
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException(
                    "Null elements not permitted in List.");
        }

        if (tail == null) {
            tail = head = new LLNode<>(element);
            size++;
            return true;
        }
        LLNode<E> temp = new LLNode<>(element);
        tail.next = temp;
        temp.prev = tail;
        tail = temp;
        size++;

        return true;
    }

    /**
     * Get the element at position index
     * 
     * @throws IndexOutOfBoundsException
     *             if the index is out of bounds.
     */
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int idx = 0;
        LLNode<E> temp = head;
        while (idx < index) {
            temp = temp.next;
            idx++;
        }
        return temp.data;
    }

    /**
     * Add an element to the list at the specified index
     * 
     * @param The
     *            index where the element should be added
     * @param element
     *            The element to add
     */
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException(
                    "Null elements not permitted in List.");
        }

        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if(head == null) {
            add(element);
            return;
        }
        
        int idx = 0;
        LLNode<E> temp = head;
        while (idx < index - 1) {
            temp = temp.next;
            idx++;
        }
        LLNode<E> tempNew = new LLNode<>(element);
        tempNew.next = temp.next;
        if (tempNew.next != null) {
            tempNew.next.prev = tempNew;            
        } else {
            tail = tempNew;
        }
        tempNew.prev = temp;
        temp.next = tempNew;
        size++;
    }

    /** Return the size of the list */
    public int size() {
        return size;
    }

    /**
     * Remove a node at the specified index and return its data element.
     * 
     * @param index
     *            The index of the element to remove
     * @return The data element removed
     * @throws IndexOutOfBoundsException
     *             If index is outside the bounds of the list
     * 
     */
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            LLNode<E> temp = head;
            
            if(temp.next == null) {
                head = null;
                tail = null;
                size--;
                return temp.data;
            }
            
            head = head.next;
            head.prev = null;
            temp.next = null;
            size--;
            return temp.data;
        }

        int idx = 0;
        LLNode<E> temp = head;
        while (idx < index - 1) {
            temp = temp.next;
            idx++;
        }
        LLNode<E> removing = temp.next;
        if (removing.next == null) {
            tail = temp;
            temp.next = null;
            removing.prev = null;
            return removing.data;
        }
        temp.next = removing.next;
        removing.next.prev = temp;
        removing.next = null;
        removing.prev = null;
        size--;
        return removing.data;
    }

    /**
     * Set an index position in the list to a new element
     * 
     * @param index
     *            The index of the element to change
     * @param element
     *            The new element
     * @return The element that was replaced
     * @throws IndexOutOfBoundsException
     *             if the index is out of bounds.
     */
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException(
                    "Null elements not permitted in List.");
        }

        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        int idx = 0;
        LLNode<E> temp = head;
        while (idx < index) {
            temp = temp.next;
            idx++;
        }
        E old = temp.data;
        temp.data = element;
        return old;
    }
}

class LLNode<E> {
    LLNode<E> prev;
    LLNode<E> next;
    E data;

    public LLNode(E e) {
        this.data = e;
        this.prev = null;
        this.next = null;
    }

}
