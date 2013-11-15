package com.sth.petsimulator;

import java.util.Date;
import java.text.SimpleDateFormat;
import sofia.util.Timer;
import sofia.app.Persistent;
import android.graphics.RectF;
import sofia.graphics.Color;
import sofia.graphics.ShapeView;
import android.widget.ProgressBar;
import sofia.app.ShapeScreen;
import sofia.graphics.RectangleShape;
import sofia.app.Screen;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;

/**
 * The main class of the screen. Creates (or loads) the pet and everything
 * necessary to draw on the screen.
 *
 * @author Steven Whitehead (scw007)
 * @author Tianchen Peng (ptian94)
 * @author Laxmi Harshitha Patha (laxmip5)
 * @version (11.14.2013)
 */
public class MainScreen
    extends Screen
{

    // The pet is saved between running the app
    @Persistent(create = true)
    private Pet            pet;

    private RectangleShape petImage;

    private ShapeView      shapeView;

    private RectangleShape hungerBar;
    private int            hungerHeight;

    private SimpleDateFormat dateFormat;

    @Persistent
    private long currentTime;


    /**
     * Initializes the pet and everything else that must be drawn on the screen.
     */
    public void initialize()
    {
        pet.addObserver(this); // update graphics based on changes here

        int width = shapeView.getWidth();
        int height = shapeView.getHeight();
        int scale = pet.getWeight();
        // Initially Draw the Pet
        petImage =
            new RectangleShape(width / 4 - scale, height / 4 - scale, width
                - (width / 4) + scale, height - (height / 4) + scale);
        petImage.setImage(pet.getAnimationString());
        shapeView.add(petImage);

        // Initially draw the hunger bar
        hungerHeight = height / 24;
        hungerBar =
            new RectangleShape(
                0,
                height - hungerHeight,
                pet.getHunger(),
                height);
        hungerBar.setFillColor(Color.red);
        shapeView.add(hungerBar);

        // Initialize the time if it is not known
        if (currentTime == 0)
        {
            currentTime = System.currentTimeMillis();
        }

        Timer.callRepeatedly(this, "checkDate", 0, 5000);

        Timer.callRepeatedly(pet, "updateAnimation", 0, 5000);
    }

    /**
     * Check the current time compared to the one stored in the field.
     * If a certain amount of time has passed, the pet will get hungry.
     * If his hunger is less than 0, he will die.
     * TODO: Do something significant when he dies
     */
    public void checkDate()
    {
        if (System.currentTimeMillis() - currentTime >= 300000)
        {
            pet.changeHunger(-2);
            if (pet.getHunger() <= 0)
            {
                pet = new Pet();
            }

            currentTime = System.currentTimeMillis();
        }

    }

    /**
     * Called when one of the pet's fields are changed. Update the view
     * appropriately
     *
     * @param pet
     *            the pet object
     */
    public void changeWasObserved(Pet pet)
    {
        int width = shapeView.getWidth();
        int height = shapeView.getHeight();
        int scale = pet.getWeight();

        // Stretch the pet proportional to the screen and place him in the
// center
        petImage.setLeftTop(width / 4 - scale, height / 4 - scale);
        petImage.setRightBottom(width - (width / 4) + scale, height
            - (height / 4) + scale);
        petImage.setImage(pet.getAnimationString());

        hungerBar.setLeftTop(0, height - (height / 24));
        hungerBar.setRightBottom(pet.getHunger(), height);

    }


    /**
     * Notes if the feed button was clicked.
     */
    public void feedClicked()
    {
        pet.feed();
    }


    /**
     * Notes if the exercise button was clicked.
     */
    public void exerciseClicked()
    {
        pet.exercise();
    }


    /**
     * Touch on the screen is processed.
     *
     * @param x
     *            the x value
     * @param y
     *            the y value
     */
    public void onTouchDown(float x, float y)
    {
        processTouch(x, y);
    }


    /**
     * Touch on the screen changes the location of the object
     *
     * @param x
     *            the x value
     * @param y
     *            the y value
     */
    public void onTouchMove(float x, float y)
    {
        processTouch(x, y);
    }


    /**
     * Ends the onTouchDown.
     *
     * @param x
     *            the x value
     * @param y
     *            the y value
     */
    public void onTouchUp(float x, float y)
    {
        // If you stop touching the pet, change its animation back
        if (pet.getAnimationString().equals("patting"))
        {
            pet.updateAnimation();
        }
    }


    /**
     * Records when you touch the pet.
     *
     * @param x
     *            the x value
     * @param y
     *            the y value
     */
    public void processTouch(float x, float y)
    {
        // If you are touching the pet
        if (petImage.contains(x, y))
        {
            pet.pat();
        }
    }

}
