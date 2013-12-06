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
    extends ShapeScreen
{
    // The pet is saved between running the app
    @Persistent(create = true)
    private Pet              pet;

    private RectangleShape   petImage;
    private CircularLinkedList<String> sadAnimation, madAnimation,
    neutralAnimation, jumpingAnimation, pattingAnimation, happyAnimation,
    eatingAnimation, runningAnimation;

    private RectangleShape   hungerBar;
    private int              hungerHeight;

    private SimpleDateFormat dateFormat;

    @Persistent
    private long             currentTime;


    /**
     * Initializes the pet and everything else that must be drawn on the screen.
     */
    public void initialize()
    {
        pet.addObserver(this); // update graphics based on changes here

        createAnimationList();

        int scale = pet.getWeight();

        // Background
        RectangleShape bg = new RectangleShape(0, 0, getWidth(), getHeight());
        bg.setImage("bg");
        add(bg);

        petImage = new RectangleShape();
        add(petImage);

        hungerHeight = (int)getHeight() / 24;
        hungerBar = new RectangleShape();
        hungerBar.setFillColor(Color.red);
        add(hungerBar);

        // Initialize the time if it is not known
        if (currentTime == 0)
        {
            currentTime = System.currentTimeMillis();
        }

        Timer.callRepeatedly(this, "checkDate", 0, 5000);

        // Reset the animation back to it's mood
        Timer.callRepeatedly(pet, "updateAnimation", 50, 5000);

        Timer.callRepeatedly(this, "animateFrames", 50, 50);
    }


    /**
     * Check the current time compared to the one stored in the field. If a
     * certain amount of time has passed, the pet will get hungry. If his hunger
     * is less than 0, he will die. TODO: Do something significant when he dies
     */
    public void checkDate()
    {
        long timeDiff = System.currentTimeMillis() - currentTime;
        if (timeDiff >= 300000)
        {
            pet.changeHunger((int)timeDiff);
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
        float width = getWidth();
        float height = getHeight();
        float scale = pet.getWeight();

        // Stretch the pet proportional to the screen and place in the center
        petImage.setLeftTop(width / 4 - scale, height / 4 - scale);
        petImage.setRightBottom(width - (width / 4) + scale, height
            - (height / 4) + scale);

        hungerBar.setLeftTop(0, height - (height / 24));
        hungerBar.setRightBottom(pet.getHunger(), height);

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
        if (pet.getAnimation().equals(Animation.PATTING))
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
        float width = getWidth();
        float height = getHeight();
        float widthDiv = getWidth() / 4;
        float heightDiv = getHeight() / 8;
        float appleBord = widthDiv;
        float exerBord = 2 * widthDiv;
        float mediBord = 3 * widthDiv;

        // If you are touching the pet
        if (petImage.contains(x, y))
        {
            pet.pat();
        }
        else if (y < heightDiv)
        {
            if (x < appleBord)
            {
                pet.feed();
            }
            else if (x < exerBord)
            {
                pet.exercise();
            }
            else if (x < mediBord)
            {

            }
            else
            {

            }
        }
        else if (y > heightDiv * 7)
        {

        }
    }

    /**
     * Creates a circular list to store each individual frame of an animation.
     */
    private void createAnimationList()
    {
        sadAnimation = new CircularLinkedList<String>();
        sadAnimation.add("sad");

        madAnimation = new CircularLinkedList<String>();
        madAnimation.add("mad");

        neutralAnimation = new CircularLinkedList<String>();
        neutralAnimation.add("neutral");

        happyAnimation = new CircularLinkedList<String>();
        happyAnimation.add("happy");

        jumpingAnimation = new CircularLinkedList<String>();
        jumpingAnimation.add("jumping");

        eatingAnimation = new CircularLinkedList<String>();
        eatingAnimation.add("eating");
        eatingAnimation.add("happy");

        runningAnimation = new CircularLinkedList<String>();
        runningAnimation.add("running");

        pattingAnimation = new CircularLinkedList<String>();
        pattingAnimation.add("patting");

    }

    private String aniToString(Animation ani)
    {
        switch (ani)
        {
            case MAD:
                return madAnimation.getCurrent();
            case SAD:
                return sadAnimation.getCurrent();
            case HAPPY:
                return happyAnimation.getCurrent();
            case RUNNING:
                return runningAnimation.getCurrent();
            case EATING:
                return eatingAnimation.getCurrent();
            case PATTING:
                return pattingAnimation.getCurrent();
            case JUMPING:
                return jumpingAnimation.getCurrent();
            default:
                return neutralAnimation.getCurrent();
        }
    }

    /**
     * Goes through each individual frame of an animation.
     */
    public void animateFrames()
    {
        String str;
        Animation ani = pet.getAnimation();
        System.out.println(ani.toString());
        switch (ani)
        {
            case MAD:
                madAnimation.next();
                str = madAnimation.getCurrent();
                break;
            case SAD:
                sadAnimation.next();
                str = sadAnimation.getCurrent();
                break;
            case HAPPY:
                happyAnimation.next();
                str = happyAnimation.getCurrent();
                break;
            case RUNNING:
                runningAnimation.next();
                str = runningAnimation.getCurrent();
                break;
            case EATING:
                eatingAnimation.next();
                str = eatingAnimation.getCurrent();
                break;
            case PATTING:
                pattingAnimation.next();
                System.out.println("dorito");
                str = pattingAnimation.getCurrent();
                break;
            case JUMPING:
                jumpingAnimation.next();
                str = jumpingAnimation.getCurrent();
                break;
            default:
                neutralAnimation.next();
                str = neutralAnimation.getCurrent();
                break;
        }
        System.out.println(str);
        petImage.setImage(str);
    }

    public Pet getPet()
    {
        return pet;

    }

}
