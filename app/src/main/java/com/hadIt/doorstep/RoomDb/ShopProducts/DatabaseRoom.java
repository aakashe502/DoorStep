package com.hadIt.doorstep.RoomDb.ShopProducts;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hadIt.doorstep.Database.DataDatabase;

@Database(entities = {RoomData.class},version = 5)
public abstract class DatabaseRoom extends androidx.room.RoomDatabase {
    private static final String DATABASE_NAME="Shops";
    public abstract DaoQuery Daodata();
    private static volatile DatabaseRoom INSTANCE=null;

    public static DatabaseRoom getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (DataDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE= Room.databaseBuilder(context,DatabaseRoom.class,
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

    static class PopulateAsynTask extends AsyncTask<Void,Void,Void> {
        private DaoQuery dataDaoQuery;

        PopulateAsynTask(DatabaseRoom dataDatabase)
        {
            dataDaoQuery =dataDatabase.Daodata();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDaoQuery.deleteAll();
            return null;
        }
    }
}
