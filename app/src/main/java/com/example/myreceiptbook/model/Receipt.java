package com.example.myreceiptbook.model;

import android.media.Image;

public class Receipt
{
    private String title;
    private String shortDescription;
    private String largeDescription;
    private String imageUri;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public String getLargeDescription()
    {
        return largeDescription;
    }

    public void setLargeDescription(String largeDescription)
    {
        this.largeDescription = largeDescription;
    }

    public String getImageUri()
    {
        return imageUri;
    }

    public void setImageUri(String imageUri)
    {
        this.imageUri = imageUri;
    }
}
