package com.example.myreceiptbook.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReceiptDao
{
    @Insert
    void insert(Receipt receipt);

    @Update
    void update(Receipt receipt);

    @Delete
    void delete(Receipt receipt);

    @Query("DELETE FROM receipt_table")
    void deleteAllReceipts();

    @Query("SELECT * FROM receipt_table")
    LiveData<List<Receipt>> getAllReceipts();
}
