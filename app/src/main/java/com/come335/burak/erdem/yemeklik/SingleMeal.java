package com.come335.burak.erdem.yemeklik;

/**
 * Created by Erdem on 22-Apr-18.
 */

public class SingleMeal
{
    String name;
    String content;
    String photoURL;

    public SingleMeal()
    {

    }

    public SingleMeal(String _mealName, String _mealContent, String _mealImage)
    {
        name = _mealName;
        content  =_mealContent;
        photoURL = _mealImage;
    }

    public String getName()
    {
        return name;
    }

    public String getContent()
    {
        return content;
    }

    public String getImage()
    {
        return photoURL;
    }

    public void setName(String _name)
    {
        name=_name;
    }

    public void setContent(String _content)
    {
        content=_content;
    }

    public void setImage(String _image)
    {
        photoURL=_image;
    }
}
