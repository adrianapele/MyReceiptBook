package com.example.myreceiptbook.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Receipt.class}, version = 1, exportSchema = false)
public abstract class ReceiptDatabase extends RoomDatabase
{
    private static ReceiptDatabase instance;

    public abstract ReceiptDao receiptDao();

    public static synchronized ReceiptDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ReceiptDatabase.class, "receipt_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private ReceiptDao receiptDao;

        private PopulateDbAsyncTask(ReceiptDatabase db)
        {
            receiptDao = db.receiptDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            receiptDao.insert(new Receipt("Title 1", "Short description 1", "Very long long long long description 1", ""));
            receiptDao.insert(new Receipt("Title 2", "Short description 2", "Very long long long long description 2", ""));
            receiptDao.insert(new Receipt("Title 3", "Short description 3", "Very long long long long description 3", ""));
            return null;
        }
    }
}
