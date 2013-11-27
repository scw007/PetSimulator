package com.sth.petsimulator;

import java.util.NoSuchElementException;
import java.util.Iterator;

// -------------------------------------------------------------------------
/**
 * Tests for the CircularLinkedList class.
 *
 * @author <Steven Whitehead> (scw007)
 * @version (2013.10.29)
 */
public class CircularLinkedListTests
    extends student.TestCase
{
    // ~ Fields ................................................................

    private CircularLinkedList<String> list;


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Creates a brand new, empty CircularLinkedList for each test method.
     */
    public void setUp()
    {
        list = new CircularLinkedList<String>();
    }


    /**
     * Test that add() puts a new item before the current item.
     */
    public void testAdd()
    {
        assertEquals(list.size(), 0); // Make sure list is empty

        list.add("Yes");

        assertEquals(list.getCurrent(), "Yes"); // Assert item is added
        assertEquals(list.size(), 1); // and that the size changed

        list.add("No"); // add when there is at least one element already
        assertEquals(list.getCurrent(), "No"); // assert current is changed
        assertEquals(list.size(), 2);

        list.next(); // getCurrent is Yes
        list.add("Maybe"); // No Maybe Yes
        assertEquals(list.getCurrent(), "Maybe");
        assertEquals(list.size(), 3);
    }


    /**
     * Test getCurrent() when there are no elements in the list
     */
    public void testGetCurrentNoElements()
    {
        Exception occurred = null;
        try
        {
            list.getCurrent();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "There are no elements in the list.",
            occurred.getMessage());
    }

    /**
     * Test removeCurrent() when there are no elements.
     */
    public void testRemoveCurrentNoElements()
    {
        Exception occurred = null;
        try
        {
            list.removeCurrent();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "There are no elements in the list.",
            occurred.getMessage());
    }
    /**
     * Test that removeCurrent() removes the current item.
     */
    public void testRemoveCurrent()
    {
        // Try removing one item
        list.add("Yes");
        assertEquals(list.removeCurrent(), "Yes");
        assertEquals(list.toString(), "[]");

        // Remove one item from two item list
        list.add("Yes");
        list.add("No");
        assertEquals(list.removeCurrent(), "No");
        assertEquals(list.toString(), "[Yes]");

        // Try to remove the middle element
        list.add("No");
        list.add("Maybe");
        list.next();
        assertEquals(list.toString(), "[No, Yes, Maybe]");
        assertEquals(list.removeCurrent(), "No");
        assertEquals(list.getCurrent(), "Yes");

    }


    /**
     * Tests the next() method so that it iterates through the list.
     */
    public void testNext()
    {
        list.next(); // should do nothing

        list.add("Yes");
        list.next();
        assertEquals(list.getCurrent(), "Yes");
        list.add("No"); // ..No Yes No Yes..

        assertEquals(list.getCurrent(), "No");
        list.next();
        assertEquals(list.getCurrent(), "Yes");
        list.next(); // Should wrap to No
        assertEquals(list.getCurrent(), "No");

        list.add("Maybe"); // ...Maybe No Yes Maybe No...
        list.next();
        assertEquals(list.getCurrent(), "No");
        list.next();
        assertEquals(list.getCurrent(), "Yes");
        list.next();
        assertEquals(list.getCurrent(), "Maybe");
    }


    /**
     * Tests the previous() method so that it iterates through the list
     * backwards.
     */
    public void testPrevious()
    {
        list.previous(); // should do nothing

        list.add("Yes");
        list.previous();
        assertEquals(list.getCurrent(), "Yes");

        list.add("No"); // .. No Yes No Yes...

        assertEquals(list.getCurrent(), "No");
        list.previous(); // Wrap to Yes
        assertEquals(list.getCurrent(), "Yes");

        list.add("Maybe"); // ...Maybe Yes No Maybe Yes No...
        list.previous();
        assertEquals(list.getCurrent(), "No");
        list.previous();
        assertEquals(list.getCurrent(), "Yes");
        list.previous();
        assertEquals(list.getCurrent(), "Maybe");
    }


    /**
     * Tests the clear() method on empty and lists with items.
     */
    public void testClear()
    {
        assertEquals(list.size(), 0);

        list.clear(); // call clear on empty list
        assertEquals(list.size(), 0);

        list.add("Yep");
        assertEquals(list.size(), 1);

        list.clear(); // call clear
        assertEquals(list.size(), 0);
    }


    /**
     * Tests toString() so that it returns a string of the items in the list.
     */
    public void testToString()
    {
        assertEquals(list.toString(), "[]");
        list.add("3");
        assertEquals(list.toString(), "[3]");
        list.add("2");
        assertEquals(list.toString(), "[2, 3]");
        list.add("1");

        assertEquals(list.toString(), "[1, 2, 3]");
    }


    /**
     * Test the iterator's hasNext() method.
     */
    public void testHasNext()
    {
        Iterator<String> it = list.iterator();
        assertFalse(it.hasNext());

        list.add("3"); // iterator 3
        it = list.iterator(); // Check a new iterator
        assertTrue(it.hasNext());
        assertEquals(it.next(), "3"); // 3 iterator
        assertFalse(it.hasNext());

        list.add("2");
        it = list.iterator();
        assertTrue(it.hasNext()); // it 2 3
        assertEquals(it.next(), "2"); // 2 it 3
        assertTrue(it.hasNext());
        assertEquals(it.next(), "3"); // 2 3 it
        assertFalse(it.hasNext());

        list.add("1");
        it = list.iterator();
        assertTrue(it.hasNext()); // it 1 2 3
        assertEquals(it.next(), "1");
        assertTrue(it.hasNext()); // 1 it 2 3
        assertEquals(it.next(), "2"); // 1 2 it 3
        assertTrue(it.hasNext());
        assertEquals(it.next(), "3"); // 1 2 3 it
        assertFalse(it.hasNext());

    }

/**
 * Test the iterator's next() method on an empty list.
 */
    public void testItNextEmpty()
    {
        Iterator<String> it = list.iterator(); // no elements, current is null
        Exception occurred = null;
        try
        {
            it.next();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "There is no element after the next.",
            occurred.getMessage());
    }
    /**
     * Test the iterator's next() method.
     */
    public void testItNext()
    {

        list.add("3");
        Iterator<String> it = list.iterator();
        assertEquals(it.next(), "3");

        Exception occurred = null;
        try
        {
            it.next();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals(
            "There is no element after the next.",
            occurred.getMessage());

        list.add("2");
        it = list.iterator();
        assertEquals(it.next(), "2");
        assertEquals(it.next(), "3");
        Exception occurred2 = null;
        try
        {
            it.next();
        }
        catch (Exception exception)
        {
            occurred2 = exception;
        }
        assertNotNull(occurred2);
        assertTrue(occurred2 instanceof NoSuchElementException);
        assertEquals(
            "There is no element after the next.",
            occurred2.getMessage());

    }


    /**
     * Test the unsupported remove() iterator method.
     */
    public void testItRemove()
    {
        Exception occurred = null;
        try
        {
            list.iterator().remove();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof UnsupportedOperationException);
        assertEquals("remove() is not supported.", occurred.getMessage());
    }

}
