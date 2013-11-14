package com.sth.petsimulator;

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

    /**
     * Initializes all the characteristics of the pet.
     *
     */
    public void initialize()
    {
        //pet = new Pet();
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

        pet.updateAnimation();
    }

    /**
     * Sets the Pet to the center of the screen.
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

        hungerBar.setLeftTop(0, height
            - (height / 24));
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
     */
    public void onTouchDown(float x, float y)
    {
        processTouch(x, y);
    }

    /**
     * Touch on the screen changes the locaiton of the object
     */
    public void onTouchMove(float x, float y)
    {
        processTouch(x, y);
    }

    /**
     * Ends the onTouchDown.
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
