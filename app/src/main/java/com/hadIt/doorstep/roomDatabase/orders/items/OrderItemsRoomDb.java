package com.hadIt.doorstep.roomDatabase.orders.items;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hadIt.doorstep.roomDatabase.orders.items.model.OrderItemsRoomModel;

@Database(entities = {OrderItemsRoomModel.class}, version = 5)
public abstract class OrderItemsRoomDb extends RoomDatabase {
    private static final String DATABASE_NAME="OrderItems";
    public abstract ItemsQuery Query();
    private static volatile OrderItemsRoomDb INSTANCE=null;

    public static OrderItemsRoomDb getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (OrderItemsRoomDb.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE= Room.databaseBuilder(context, OrderItemsRoomDb.class,
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
        private ItemsQuery dataDaoQuery;

        PopulateAsynTask(OrderItemsRoomDb orderItemsRoomDb)
        {
            dataDaoQuery = orderItemsRoomDb.Query();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            dataDaoQuery.refreshDb();
            return null;
        }
    }
}
