package com.sth.petsimulator;

import android.content.Intent;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the GUI. Check that touching in the various locations works
 * correctly.
 *
 * @author (Steven Whitehead)
 * @version (2013.10.15)
 */
public class MainScreenTest
extends student.AndroidTestCase<MainScreen>
{
    // ~ Fields ................................................................
    private MainScreen screen;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Test cases that extend AndroidTestCase must have a parameterless
     * constructor that calls super() and passes it the screen/activity class
     * being tested.
     */
    public MainScreenTest()
    {
        super(MainScreen.class);
    }


    // ~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Initializes the text fixtures.
     */
    public void setUp()
    {
        screen = this.getScreen();
    }

    public void testFeedButton()
    {
        click(screen.getShapeView(), 0,0); // clicks the feed button
        assertEquals(screen.getPet().getAnimation(), Animation.EATING);
    }

    public void testExerciseButton()
    {
        click(screen.getShapeView(), 0,0); // clicks the feed button
        assertEquals(screen.getPet().getAnimation(), Animation.JUMPING);
    }

    public void testTouchPet()
    {
        click(screen.getShapeView(), 0,0); // click the center of the screen
        assertEquals(screen.getPet().getAnimation(), Animation.PATTING);
    }


}
