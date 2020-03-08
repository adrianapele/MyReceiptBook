package com.example.myreceiptbook.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ReceiptViewModel extends AndroidViewModel
{
    private ReceiptRepository repository;
    private LiveData<List<Receipt>> allReceipts;
    private MutableLiveData<Receipt> currentSelectedReceipt = new MutableLiveData<Receipt>();

    public ReceiptViewModel(@NonNull Application application)
    {
        super(application);

        repository = new ReceiptRepository(application);
        allReceipts = repository.getAllReceipts();
    }

    public void setCurrentSelectedReceipt(Receipt receipt)
    {
        currentSelectedReceipt.setValue(receipt);
    }

    public LiveData<Receipt> getCurrentSelectedReceipt()
    {
        return currentSelectedReceipt;
    }

    public void insert(Receipt receipt)
    {
        repository.insert(receipt);
    }

    public void update(Receipt receipt)
    {
        repository.update(receipt);
    }

    public void delete(Receipt receipt)
    {
        repository.delete(receipt);
    }

    public void deleteAllNotes()
    {
        repository.deleteAllNotes();
    }

    public LiveData<List<Receipt>> getAllNotes()
    {
        return allReceipts;
    }
}
