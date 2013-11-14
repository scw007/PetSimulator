package com.sth.petsimulator;

import student.TestCase;


// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author pitibow
 *  @version Nov 8, 2013
 */
public class PetTest extends TestCase
{
    private Pet pet;

    public void setUp()
    {
        pet = new Pet();
    }


    public void testPat()
    {
        assertEquals(50, pet.getHappiness());
        pet.pat();
        assertEquals(51, pet.getHappiness());
    }

}
