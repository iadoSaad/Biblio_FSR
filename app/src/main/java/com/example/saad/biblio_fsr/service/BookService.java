package com.example.saad.biblio_fsr.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.example.saad.biblio_fsr.data.BiblioContrat;
import com.example.saad.biblio_fsr.data.BooksData;

/**
 * Created by saad on 15/03/2015.
 */
public class BookService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BookService() {
        super("books");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    createDataIfNotExist();
    }
    void createDataIfNotExist(){

        int id_book=0;
        Cursor c=getContentResolver().query(
                BiblioContrat.CategoryEntry.CONTENT_URI,
                null, null, null, null);
        if(c!=null&&c.moveToFirst()){
            c.close();
            return ;
        }

        String ctr[]= BooksData.Categories;
        for(int i=0;i<ctr.length;i++){
            ContentValues cc=new ContentValues();
            cc.put(BiblioContrat.CategoryEntry._ID,i);
            cc.put(BiblioContrat.CategoryEntry.COLUMN_LABEL,ctr[i]);
            getContentResolver().insert(BiblioContrat.CategoryEntry.CONTENT_URI, cc);
            for(int j=0;j<20;j++){
                cc=new ContentValues();
                cc.put(BiblioContrat.BookEntry._ID,id_book++);
                cc.put(BiblioContrat.BookEntry.COLUMN_CATEGORY_ID,i);
                cc.put(BiblioContrat.BookEntry.COLUMN_TITLE," Title of book number "+id_book);
                cc.put(BiblioContrat.BookEntry.COLUMN_AUTHOR_NAME,"author of book number "+id_book);
                cc.put(BiblioContrat.BookEntry.COLUMN_DESCRIPTION,"this is a description of book number "+id_book+" that belong to the "+ctr[i]+" category");
                getContentResolver().insert(BiblioContrat.BookEntry.CONTENT_URI, cc);
            }


        }
        if(c!=null)  c.close();

        SharedPreferences.Editor editor= getSharedPreferences("first",MODE_PRIVATE).edit();
        editor.putBoolean("first",false);
        editor.commit();

    }
}
