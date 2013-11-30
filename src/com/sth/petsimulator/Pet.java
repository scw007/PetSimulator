package com.sth.petsimulator;

import sofia.util.Timer;
import sofia.app.Persistent;

/**
 * The model for the pet. Controls his various attributes (hunger, weight,
 * happiness) as well as which animation should be drawn at the time.
 *
 * @author Steven Whitehead (scw007)
 * @author Tianchen Peng (ptian94)
 * @author Laxmi Harshitha Patha (laxmip5)
 * @version (11.14.2013)
 */
public class Pet
    extends sofia.util.Observable
{
    // All of these fields must be primitives so that the object is Persistent
    private int       hunger;
    private int       weight;
    private int       happiness;
    private Animation animation;

    /**
     * Constructor that initializes the pet's values.
     */
    public Pet()
    {
        // TODO: setup initial values how we want them
        hunger = 50;
        weight = 50;
        happiness = 50;
        animation = Animation.NEUTRAL;

    }


    /**
     * Called when the user touches or "pats" the pet. Slightly raises happiness
     */
    public void pat()
    {
        animation = Animation.PATTING;
        happiness++;

        notifyObservers(); // an animation will go along with this
    }


    /**
     * Called when giving food to the pet. Lowers hunger but raises weight and
     * happiness.
     */
    public void feed()
    {
        if (hunger < 100)
        {
            hunger++;
        }

        weight++;
        happiness++;
        animation = Animation.EATING;

        notifyObservers();
    }


    /**
     * Called when the user does a strenuous activity with the pet. Lowers
     * weight and increases happiness.
     */
    public void exercise()
    {
        weight--;
        happiness++;
        animation = Animation.RUNNING;

        notifyObservers();
    }


    /**
     * Depending on the happiness level, the pet changes animation.
     */
    public void updateAnimation()
    {
        if (getHappiness() < 10)
        {
            animation = Animation.MAD;
        }
        else if (getHappiness() < 35)
        {
            animation = Animation.SAD;
        }
        else if (getHappiness() < 65)
        {
            animation = Animation.NEUTRAL;
        }
        else
        {
            animation = Animation.HAPPY;
        }

        notifyObservers();
    }


    /**
     * Increase or decrease the hunger by a specified amount.
     *
     * @param hung
     *            the amount to increase or decrease the hunger
     */
    public void changeHunger(int hung)
    {
        hunger += hung;
    }


    /**
     * Returns the hunger value
     *
     * @return his hunger level
     */
    public int getHunger()
    {
        return hunger;
    }


    /**
     * Returns the pets weight.
     *
     * @return his weight
     */
    public int getWeight()
    {
        return weight;
    }


    /**
     * Returns the happiness level.
     *
     * @return his happiness
     */
    public int getHappiness()
    {
        return happiness;
    }


    public Animation getAnimation()
    {
        return animation;
    }



}
