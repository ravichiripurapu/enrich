package data.pipeline.api.collections;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code LinkedList} class represents a linkedList (or multiset) of
 *  generic items. It supports insertion and iterating over the
 *  items in arbitrary order.
 *  <p>
 *  This implementation uses a singly-linked
 *
 * @author Ravi Chiripurapu on 1/1/19
 *
 *  @param <Item> the generic type of an item in this linkedList
 */
public class LinkedList<Item> implements Iterable<Item> {
    private Node<Item> first;    // beginning of linkedList
    private int n;               // number of elements in linkedList

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty linkedList.
     */
    public LinkedList() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this linkedList is empty.
     *
     * @return {@code true} if this linkedList is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this linkedList.
     *
     * @return the number of items in this linkedList
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this linkedList.
     *
     * @param  item the item to add to this linkedList
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }


    /**
     * Returns an iterator that iterates over the items in this linkedList in arbitrary order.
     *
     * @return an iterator that iterates over the items in this linkedList in arbitrary order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


}
