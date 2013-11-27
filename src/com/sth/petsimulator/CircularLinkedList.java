package com.sth.petsimulator;

import java.util.NoSuchElementException;
import java.util.Iterator;

// -------------------------------------------------------------------------
/**
 * A circular, doubly-linked list.
 *
 * @param <E>
 *            the type of element stored in the list
 * @author <Steven Whitehead> (scw007)
 * @version (2013.10.29)
 */
public class CircularLinkedList<E>
    extends sofia.util.Observable
    implements Iterable<E>

{
    // ~ Fields ................................................................

    private Node<E> currentNode;
    private int     size;


    // ~ Constructors ..........................................................

    /**
     * Create a new CircularLinkedList object.
     */
    public CircularLinkedList()
    {
        size = 0;
        currentNode = new Node<E>(null);
    }


    /**
     * Move to the next item on the list. Nodes have a previous and a next node
     * stored so that it can wrap around in a circle.
     */
    public void next()
    {
        if (currentNode.next() != null)
        {
            currentNode = currentNode.next();
        }
        notifyObservers();
    }


    /**
     * Move to the previous item on the list. Wraps around in a circle.
     */
    public void previous()
    {
        if (currentNode.previous() != null)
        {
            currentNode = currentNode.previous();
        }
        notifyObservers();
    }


    /**
     * Get the data at the current item.
     *
     * @return the data at the item.
     */
    public E getCurrent()
    {
        if (currentNode.data() != null)
        {
            return currentNode.data();
        }
        else
        {
            throw new NoSuchElementException(
                "There are no elements in the list.");
        }
    }


    /**
     * Get the size of the list.
     *
     * @return the size of the list
     */
    public int size()
    {
        return size;
    }


    /**
     * Add an item to the list before the current item.
     *
     * @param item
     *            the data to add to the node being inserted
     */
    public void add(E item)
    {
        if (size() == 0)
        {
            currentNode.setData(item);
        }
        else
        {
            Node<E> newCurrNode = new Node<E>(item);
            Node<E> oldCurrPrev = currentNode.previous();

            if (size() > 1)
            {
                oldCurrPrev.split();
                oldCurrPrev.join(newCurrNode); // Add after old previous
            }
            else
            // size() == 1
            {
                currentNode.join(newCurrNode); // prev and next are the same
            }

            newCurrNode.join(currentNode); // Add before the currentNode
            currentNode = newCurrNode;
        }
        notifyObservers();
        size++;

    }


    /**
     * Removes the current item and returns it.
     *
     * @return the item being removed.
     */
    public E removeCurrent()
    {
        if (size == 0) // No items
        {
            throw new NoSuchElementException(
                "There are no elements in the list.");
        }
        else
        // More than two
        {
            E e = currentNode.data();
            if (size() == 1)
            {
                currentNode.setData(null);
            }
            else
            // size() > 1
            {
                Node<E> oldPrev = currentNode.previous();
                Node<E> oldNext = currentNode.next();

                oldPrev.split();
                currentNode.split();

                if (size() > 2)
                {
                    oldPrev.join(oldNext);
                    currentNode = oldNext;
                }
                else
                {
                    currentNode = oldPrev;
                }
            }
            size--;
            notifyObservers();
            return e;

        }
    }


    /**
     * Clear the list.
     */
    public void clear()
    {
        size = 0;
        currentNode = new Node<E>(null);
        notifyObservers();
    }


    /**
     * Calls toString on every item in the list.
     *
     * @return a string of all the items
     */
    public String toString()
    {
        if (currentNode.data() != null)
        {
            StringBuilder sb = new StringBuilder("[");
            Iterator<E> it = iterator();
            while (it.hasNext())
            {
                sb.append(it.next().toString() + ", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // delete last ", "
            sb.append("]"); // the last
            return sb.toString();
        }
        else
        // Nothing in current item
        {
            return "[]";
        }

    }


    /**
     * Gets the iterator.
     *
     * @return the iterator
     */
    public Iterator<E> iterator()
    {
        return new CircularLinkedListIterator();
    }


    // ~ Inner classes .........................................................

    /**
     * The iterator for CircularLinkedList. Allows one to iterate through the
     * list.
     *
     * @author Steven Whitehead (scw007)
     * @version (2013.11.01)
     */
    private class CircularLinkedListIterator
        implements Iterator<E>
    {
        // ~ Fields ............................................................

        private Node<E> current;
        private int     counter;


        // ~ Constructors ......................................................

        /**
         * Creates an iterator before the current node. Assigns fields so that
         * it does not change the list itself.
         */
        public CircularLinkedListIterator()
        {
            current = currentNode; // changes when next() is called by iterator
            counter = 0; // keeps track of how many times next() is called
        }


        // ~ Public methods ....................................................

        /**
         * Gets whether or not there is a node after the current one
         *
         * @return if there is a node after the current
         */
        public boolean hasNext()
        {
            // counter goes up each time next() is called
            return (counter < size());
        }


        /**
         * Move the iterator along the list.
         *
         * @return the data at the next node.
         */
        public E next()
        {
            if (hasNext())
            {
                Node<E> tempCurr = current;
                current = current.next(); // node.next()
                counter++;
                return tempCurr.data();
            }
            else
            {
                throw new NoSuchElementException(
                    "There is no element after the next.");
            }
        }


        /**
         * Unsupported remove method.
         */
        public void remove()
        {
            throw new UnsupportedOperationException(
                "remove() is not supported.");
        }
    }
}
