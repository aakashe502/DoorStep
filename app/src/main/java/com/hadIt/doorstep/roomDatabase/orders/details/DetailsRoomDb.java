package com.hadIt.doorstep.roomDatabase.orders.details;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hadIt.doorstep.roomDatabase.orders.details.model.OrderDetailsRoomModel;

@Database(entities = {OrderDetailsRoomModel.class}, version = 5)
public abstract class DetailsRoomDb extends RoomDatabase {
    private static final String DATABASE_NAME="Orders";
    public abstract OrdersQuery OrderQuery();
    private static volatile DetailsRoomDb INSTANCE=null;

    public static DetailsRoomDb getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (DetailsRoomDb.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE= Room.databaseBuilder(context, DetailsRoomDb.class,
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
        private OrdersQuery dataDaoQuery;

        PopulateAsynTask(DetailsRoomDb dataDatabase)
        {
            dataDaoQuery = dataDatabase.OrderQuery();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDaoQuery.refreshDb();
            return null;
        }
    }
}
