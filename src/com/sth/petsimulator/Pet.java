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

    private enum Animation
    {
        SAD,
        MAD,
        NEUTRAL,
        HAPPY,
        JUMPING,
        EATING,
        RUNNING,
        PATTING
    }

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
        Timer.callRepeatedly(this, "updateAnimation", 0, 5000);

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
        if (hunger > 0)
        {
            hunger--;
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
     * Depending on the happiness level, the pet changes
     * animation.
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
     * Returns the hunger value
     * @return his hunger level
     */
    public int getHunger()
    {
        return hunger;
    }

    /**
     * Returns the pets weight.
     * @return his weight
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Returns the happiness level.
     * @return his happiness
     */
    public int getHappiness()
    {
        return happiness;
    }

    /**
     * Returns the animation as a string. This is necessary to load image files.
     * @return the animation as a string
     */
    public String getAnimationString()
    {
        switch (animation)
        {
            case MAD:
                return "mad";
            case SAD:
                return "sad";
            case HAPPY:
                return "happy";
            case RUNNING:
                return "running";
            case EATING:
                return "eating";
            case PATTING:
                return "patting";
            case JUMPING:
                return "jumping";
            default:
                return "neutral";

        }
    }
}
