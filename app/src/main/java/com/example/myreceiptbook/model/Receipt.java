package com.example.myreceiptbook.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "receipt_table")
public class Receipt
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String shortDescription;

    private String largeDescription;

    private String imageUri;

    public Receipt(String title, String shortDescription, String largeDescription, String imageUri)
    {
        this.title = title;
        this.shortDescription = shortDescription;
        this.largeDescription = largeDescription;
        this.imageUri = imageUri;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public String getLargeDescription()
    {
        return largeDescription;
    }

    public String getImageUri()
    {
        return imageUri;
    }
}
