package com.come335.burak.erdem.yemeklik;

import java.util.ArrayList;

/**
 * Created by Erdem on 22-Apr-18.
 */

public class SingleMeal
{
    int id;
    String name;
    String content;
    String photoURL;
    float totalPoints;
    int timesRated;
    float rating;
    private ArrayList<String> restaurants = new ArrayList<>();

    public SingleMeal()
    {

    }

    public ArrayList<String> getRestaurants()
    {
        return restaurants;
    }

    public void setRestaurants(ArrayList<String> restaurants)
    {
        this.restaurants = restaurants;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setPhotoURL(String photoURL)
    {
        this.photoURL = photoURL;
    }

    public String getContent()
    {
        return content;
    }

    public String getPhotoURL()
    {
        return photoURL;
    }

    public void addPoints(int _points)
    {
        totalPoints += _points;
    }

    public float getTotalPoints()
    {
        return totalPoints;
    }

    public void setTotalPoints(float totalPoints)
    {
        this.totalPoints = totalPoints;
    }

    public float calculateRating(float _totalPoints, int _timesRated)
    {
        if(_timesRated == 0)
            return 0;
        else
        {
            rating = _totalPoints / _timesRated;
            return rating;

        }
    }

    public float getRating()
    {
        return rating;
    }

    public void setRating(float rating)
    {
        this.rating = rating;
    }

    public int getTimesRated()
    {
        return timesRated;
    }

    public void setTimesRated(int timesRated)
    {
        this.timesRated = timesRated;
    }
}
