package com.sth.petsimulator;

public class Pet
    extends sofia.util.Observable
{
    private int hunger;
    private int weight;
    private int happiness;


    public Pet()
    {
        // TODO: setup initial values how we want them
        hunger = 50;
        weight = 50;
        happiness = 50;

        notifyObservers();
    }


    /**
     * Called when the user touches or "pats" the pet. Slightly raises happiness
     */
    public void pat()
    {
        happiness++;

        notifyObservers(); // an animation will go along with this
    }


    /**
     * Called when giving food to the pet. Lowers hunger but raises weight and
     * happiness.
     */
    public void feed()
    {
        hunger--;
        weight++;
        happiness++;

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

        notifyObservers();
    }

    public int getHunger()
    {
        return hunger;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getHappiness()
    {
        return happiness;
    }
}
