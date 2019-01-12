package com.anushka.androidtutz.contactmanager.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.anushka.androidtutz.contactmanager.db.entity.Contact;

@Database(entities = Contact.class, version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();

    private static ContactsAppDatabase sInstance;

    public static ContactsAppDatabase getDatabase(final Context context){
        if(sInstance == null){
            synchronized (ContactsAppDatabase.class){
                if(sInstance == null){
                    sInstance = Room
                            .databaseBuilder(context.getApplicationContext(), ContactsAppDatabase.class, "contactDB")
                            .build();
                }
            }
        }
        return sInstance;
    }
}
