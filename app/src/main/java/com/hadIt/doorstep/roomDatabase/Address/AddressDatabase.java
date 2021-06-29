package com.hadIt.doorstep.roomDatabase.Address;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hadIt.doorstep.roomDatabase.cart.DataDao;
import com.hadIt.doorstep.roomDatabase.cart.DataDatabase;

import static androidx.room.Room.databaseBuilder;


@Database(entities = {AddressModel.class},version = 5)
public abstract class AddressDatabase extends RoomDatabase {
    private static final String DATABASE_NAME1="Address";
    public abstract DataDao dataDao();
    private static volatile AddressDatabase INSTANCE=null;

    public static AddressDatabase getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (AddressDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE=Room.databaseBuilder(context,AddressDatabase.class,
                            DATABASE_NAME1)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build() ;
                }
            }
        }
        return  INSTANCE;
    }

    static RoomDatabase.Callback callback=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    static class PopulateAsynTask extends AsyncTask<Void,Void,Void> {
        private AddressDao dataDao;

        PopulateAsynTask(AddressDatabase dataDatabase)
        {
            dataDao= (AddressDao) dataDatabase.dataDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAll();
            return null;
        }
    }
}
