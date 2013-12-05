package com.sth.petsimulator;

import android.widget.Button;
import android.widget.TextView;
import sofia.graphics.ShapeView;

// -------------------------------------------------------------------------
/**
 * Tests the GUI. Check that all the buttons, touch functions, and the infoLabel
 * work correctly.
 *
 * @author (Steven Whitehead)
 * @version (2013.10.15)
 */
public class MainScreenTest
extends student.AndroidTestCase<MainScreen>
{
    // ~ Fields ................................................................


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

    }
    
    public void testFeedButton()
    {
        click(0,0); // clicks the feed button
        assertEquals(this.getPet().getAnimation(), EATING);
    }

    public void testExerciseButton()
    {
        click(0,0); // clicks the feed button
        assertEquals(this.getPet().getAnimation(), JUMPING);
    }

    public void testTouchPet()
    {
        click() // click the center of the screen
        assertEquals(this.getPet().getAnimation(), PATTING);
    }


}
