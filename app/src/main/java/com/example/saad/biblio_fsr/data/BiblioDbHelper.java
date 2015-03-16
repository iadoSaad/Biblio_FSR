package com.example.saad.biblio_fsr.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.saad.biblio_fsr.data.BiblioContrat.*;

/**
 * Created by saad on 12/03/2015.
 */
public class BiblioDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    static final String DATABASE_NAME="books.db";
    
    public BiblioDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CATEGORY_TABLE="CREATE TABLE "+ CategoryEntry.TABLE_NAME+"("+
                CategoryEntry._ID +" INTEGER PRIMARY KEY,"+
                CategoryEntry.COLUMN_LABEL+" TEXT NOT NULL"+
                ");";
        final String SQL_CREATE_BOOK_TABLE="CREATE TABLE "+ BookEntry.TABLE_NAME+"("+
                BookEntry._ID + " INTEGER PRIMARY KEY,"+
                BookEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL,"+
                BookEntry.COLUMN_AUTHOR_NAME + " TEXT NOT NULL,"+
                BookEntry.COLUMN_TITLE+" TEXT NOT NULL,"+
                BookEntry.COLUMN_DESCRIPTION+" TEXT NOT NULL,"+
                BookEntry.COLUMN_IS_SELECTED+"INTEGER DEFAULT 0, "+
                "FOREIGN KEY ("+BookEntry.COLUMN_CATEGORY_ID+") REFERENCES "+
                CategoryEntry.TABLE_NAME+" ("+ CategoryEntry._ID+")"+
                " );";
    db.execSQL(SQL_CREATE_CATEGORY_TABLE);
    db.execSQL(SQL_CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+BookEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+CategoryEntry.TABLE_NAME);
    }
}
