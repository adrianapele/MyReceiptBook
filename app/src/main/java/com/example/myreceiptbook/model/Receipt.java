package com.example.myreceiptbook.model;

public class Receipt
{
    private String title;
    private String shortDescription;
    private String largeDescription;
    private String imageUri;

    public Receipt(String title, String shortDescription, String largeDescription, String imageUri) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.largeDescription = largeDescription;
        this.imageUri = imageUri;
    }

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
