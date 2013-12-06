package com.sth.petsimulator;

import student.TestCase;
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
     * Tests the Pat method so that it raises happiness.
     */
    public void testPat()
    {
        assertEquals(50, pet.getHappiness());
        pet.pat();
        assertEquals(51, pet.getHappiness());
    }

    /**
     * Tests the Feed method so that it increases the stats appropriately.
     */
    public void testFeed()
    {
        assertEquals(50, pet.getHappiness());
        assertEquals(50, pet.getWeight());
        assertEquals(50, pet.getHunger());
        pet.feed();
        assertEquals(51, pet.getHappiness());
        assertEquals(51, pet.getWeight());
        assertEquals(51, pet.getHunger());
    }

    /**
     * Tests the Exercise method so that it changes weight appropriately.
     */
    public void testExercise()
    {
        assertEquals(50, pet.getWeight());
        pet.exercise();
        assertEquals(49, pet.getWeight());
    }

    /**
     * Test that update animation sets the correct animation.
     */
    public void testUpdateAnimation()
    {
        pet.changeHappiness(-45);
        pet.updateAnimation();
        assertEquals(pet.getAnimation(), Animation.MAD);

        pet.changeHappiness(13);
        pet.updateAnimation();
        assertEquals(pet.getAnimation(), Animation.SAD);

        pet.changeHappiness(20);
        pet.updateAnimation();
        assertEquals(pet.getAnimation(), Animation.NEUTRAL);

        pet.changeHappiness(30);
        pet.updateAnimation();
        assertEquals(pet.getAnimation(), Animation.HAPPY);

    }

}
