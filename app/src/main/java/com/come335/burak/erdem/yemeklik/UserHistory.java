package com.come335.burak.erdem.yemeklik;

/**
 * Created by Erdem on 23-Apr-18.
 */

public class UserHistory
{
    int mealId;
    String date;
    int givenRate;

    public int getGivenRate()
    {
        return givenRate;
    }

    public void setGivenRate(int givenRate)
    {
        this.givenRate = givenRate;
    }

    public UserHistory()
    {

    }

    public int getMealId()
    {
        return mealId;
    }

    public void setMealId(int mealId)
    {
        this.mealId = mealId;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
