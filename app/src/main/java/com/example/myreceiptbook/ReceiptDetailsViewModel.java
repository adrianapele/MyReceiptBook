package com.example.myreceiptbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myreceiptbook.model.Receipt;

public class ReceiptDetailsViewModel extends ViewModel
{
   private final MutableLiveData<Receipt> selectedReceipt = new MutableLiveData<>();

   public void selectReceipt(Receipt receipt)
   {
       selectedReceipt.setValue(receipt);
   }

   public LiveData<Receipt> getSelectedReceipt()
   {
       return selectedReceipt;
   }
}
