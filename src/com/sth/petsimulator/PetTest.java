package com.sth.petsimulator;

import student.TestCase;
import sofia.util.Timer;
import sofia.app.Persistent;


// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 * @author Steven Whitehead (scw007)
 * @author Tianchen Peng (ptian94)
 * @author Laxmi Harshitha Patha (laxmip5)
 * @version (11.14.2013)
 */
public class PetTest extends TestCase
{
    private Pet pet;

    public void setUp()
    {
        pet = new Pet();
    }

    /**
     * Tests the Pat method.
     */
    public void testPat()
    {
        assertEquals(50, pet.getHappiness());
        pet.pat();
        assertEquals(51, pet.getHappiness());
    }

    /**
     * Tests the Feed method.
     */
    public void testFeed()
    {
        assertEquals(50, pet.getHappiness());
        assertEquals(50, pet.getWeight());
        assertEquals(50, pet.getHunger());
        pet.feed();
        assertEquals(51, pet.getHappiness());
        assertEquals(51, pet.getWeight());
        assertEquals(49, pet.getHunger());
    }



}
