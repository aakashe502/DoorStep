package com.hadIt.doorstep.roomDatabase.cart;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hadIt.doorstep.roomDatabase.cart.model.Data;


@Database(entities = {Data.class},version = 5)
public abstract class DataDatabase extends RoomDatabase {

    private static final String DATABASE_NAME="Data";
    public abstract DataDao dataDao();
    private static volatile DataDatabase INSTANCE=null;

    public static DataDatabase getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (DataDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE=Room.databaseBuilder(context,DataDatabase.class,
                            DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build() ;
                }
            }
        }
        return  INSTANCE;
    }

    static Callback callback=new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    static class PopulateAsynTask extends AsyncTask<Void,Void,Void>{
        private DataDao dataDao;

        PopulateAsynTask(DataDatabase dataDatabase)
        {
            dataDao=dataDatabase.dataDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAll();
            return null;
        }
    }
}