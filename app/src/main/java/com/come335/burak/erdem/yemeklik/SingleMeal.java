package com.come335.burak.erdem.yemeklik;

/**
 * Created by Erdem on 22-Apr-18.
 */

public class SingleMeal
{
    String name;
    String content;
    String photoURL;
    float points;
    float timesRated;
    float rating;

    public SingleMeal()
    {

    }

    public SingleMeal(String _name, String _content, String _photoURL)
    {
        name = _name;
        content  =_content;
        photoURL = _photoURL;
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
        points += _points;
    }

    public void calculateRating()
    {
        rating = points/timesRated;
    }

    public float getRating()
    {
        return rating;
    }
}
