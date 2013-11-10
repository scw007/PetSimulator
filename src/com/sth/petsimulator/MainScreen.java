package com.sth.petsimulator;

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

@SuppressLint("NewApi")
public class MainScreen extends Screen{

    private Pet pet;
    private RectangleShape petImage;

    private ShapeView shapeView;

    private RectangleShape hungerBar;
    private int hungerHeight;

    // TODO: get rid of magic numbers
    public void initialize()
    {
        pet = new Pet();
        pet.addObserver(this); // update graphics based on changes here

        // Initially Draw the Pet
        petImage = new RectangleShape();//shapeView.getWidth() / 2), shapeView.getHeight() / 2, shapeView.getWidth(), shapeView.getHeight());
        petImage.setImage(pet.getAnimationString());
        shapeView.add(petImage);

        // Initially draw the hunger bar
        hungerHeight = shapeView.getHeight() / 24;
        hungerBar = new RectangleShape(0, shapeView.getHeight() - hungerHeight, shapeView.getX() + pet.getHunger(), hungerHeight);
        hungerBar.setFillColor(Color.red);
        shapeView.add(hungerBar);

    }

    public void changeWasObserved(Pet pet)
    {
        int width = shapeView.getWidth();
        int height = shapeView.getHeight();
        int scale = pet.getWeight();


        // Stretch the pet proportional to the screen and place him in the center
        petImage.setLeftTop(width / 4 - scale, height / 4 - scale);
        petImage.setRightBottom(width - (width / 4) + scale, height - (height / 4) + scale);
        petImage.setImage(pet.getAnimationString());

        hungerBar.setLeftTop(0, shapeView.getHeight() - (shapeView.getHeight() / 24));
        hungerBar.setRightBottom(pet.getHunger(), shapeView.getHeight());


    }

    public void feedClicked()
    {
        pet.feed();
    }

    public void exerciseClicked()
    {
        pet.exercise();
    }

    public void onTouchDown(float x, float y)
    {
        processTouch(x, y);
    }

    public void onTouchMove(float x, float y)
    {
        processTouch(x, y);
    }

    public void onTouchUp(float x, float y)
    {
        // If you stop touching the pet, change its animation back
        if (pet.getAnimationString().equals("patting"))
        {
            pet.updateAnimation();
        }
    }

    public void processTouch(float x, float y)
    {
        // If you are touching the pet
        if (petImage.contains(x, y))
        {
            pet.pat();
        }
    }

}
