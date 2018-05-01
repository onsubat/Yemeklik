package com.come335.burak.erdem.yemeklik;

/**
 * Created by Erdem on 23-Apr-18.
 */

public class UserHistory
{
    int mealId;
    String date;
    int givenRate;
    String restaurantName;
    String selectedRestaurant;

    public UserHistory()
    {

    }

    public String getSelectedRestaurant()
    {
        return selectedRestaurant;
    }

    public void setSelectedRestaurant(String selectedRestaurant)
    {
        this.selectedRestaurant = selectedRestaurant;
    }

    public int getGivenRate()
    {
        return givenRate;
    }

    public void setGivenRate(int givenRate)
    {
        this.givenRate = givenRate;
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

    public String getRestaurantName()
    {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName)
    {
        this.restaurantName = restaurantName;
    }
}
