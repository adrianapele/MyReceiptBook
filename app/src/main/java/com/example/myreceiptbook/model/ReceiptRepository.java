package com.example.myreceiptbook.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReceiptRepository
{
    private ReceiptDao receiptDao;

    private LiveData<List<Receipt>> allReceipts;

    public ReceiptRepository(Application application)
    {
        ReceiptDatabase database = ReceiptDatabase.getInstance(application);
        receiptDao = database.receiptDao();
        allReceipts = receiptDao.getAllReceipts();
    }

    public void insert(Receipt receipt)
    {
        new InsertReceiptAsyncTask(receiptDao).execute(receipt);
    }

    public void update(Receipt receipt)
    {
        new UpdateReceiptAsyncTask(receiptDao).execute(receipt);
    }

    public void delete(Receipt receipt)
    {
        new DeleteReceiptAsyncTask(receiptDao).execute(receipt);
    }

    public void deleteAllNotes()
    {
        new DeleteAllReceiptsAsyncTask(receiptDao).execute();
    }

    public LiveData<List<Receipt>> getAllReceipts()
    {
        return allReceipts;
    }


    private static class InsertReceiptAsyncTask extends AsyncTask<Receipt, Void, Void>
    {
        private ReceiptDao receiptDao;

        private InsertReceiptAsyncTask(ReceiptDao receiptDao)
        {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(Receipt... receipts)
        {
            receiptDao.insert(receipts[0]);
            return null;
        }
    }

    private static class UpdateReceiptAsyncTask extends AsyncTask<Receipt, Void, Void>
    {
        private ReceiptDao receiptDao;

        private UpdateReceiptAsyncTask(ReceiptDao receiptDao)
        {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(Receipt... receipts)
        {
            receiptDao.update(receipts[0]);
            return null;
        }
    }

    private static class DeleteReceiptAsyncTask extends AsyncTask<Receipt, Void, Void>
    {
        private ReceiptDao receiptDao;

        private DeleteReceiptAsyncTask(ReceiptDao receiptDao)
        {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(Receipt... receipts)
        {
            receiptDao.delete(receipts[0]);
            return null;
        }
    }

    private static class DeleteAllReceiptsAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private ReceiptDao receiptDao;

        private DeleteAllReceiptsAsyncTask(ReceiptDao receiptDao)
        {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            receiptDao.deleteAllReceipts();
            return null;
        }
    }
}
