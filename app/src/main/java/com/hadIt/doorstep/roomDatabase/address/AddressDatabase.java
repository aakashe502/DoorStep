package com.hadIt.doorstep.roomDatabase.address;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hadIt.doorstep.roomDatabase.address.model.AddressModel;
import com.hadIt.doorstep.utils.Constants;

@Database(entities = {AddressModel.class},version = Constants.dbVersion)
public abstract class AddressDatabase extends RoomDatabase {

    private static final String DATABASE_NAME1="Address";
    public abstract AddressDao addressDao();
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
        private AddressDao addressDao;

        PopulateAsynTask(AddressDatabase dataDatabase)
        {
            addressDao = dataDatabase.addressDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            addressDao.deleteAll();
            return null;
        }
    }
}
