package com.example.saad.biblio_fsr.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by saad on 12/03/2015.
 */
public class BibilioProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher =buildUriMatcher();
    BiblioDbHelper dbHelper;

    static final int CATEGORY=100;
    static final int BOOKS_WITH_CATEGORY=200;
    static final int BOOK_BY_ID=201;
    static final int BOOK=203;

    private static final String selectionBooksByCategorie=
            BiblioContrat.BookEntry.TABLE_NAME+
                    "."+ BiblioContrat.BookEntry.COLUMN_CATEGORY_ID+" = ?";

    private static final String selectionBookById=
            BiblioContrat.BookEntry.TABLE_NAME+
                    "."+ BiblioContrat.BookEntry._ID+" = ?";


    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
        final String authority=BiblioContrat.CONTENT_AUTHORITY;

        matcher.addURI(authority,BiblioContrat.PATH_CATEGORY,CATEGORY);
        matcher.addURI(authority,BiblioContrat.PATH_BOOK+"/#",BOOKS_WITH_CATEGORY);
        matcher.addURI(authority,BiblioContrat.PATH_BOOK,BOOK);
        matcher.addURI(authority,BiblioContrat.PATH_BOOK+"/*/#",BOOK_BY_ID);
        return matcher ;
    }

    @Override
    public boolean onCreate() {
        dbHelper=new BiblioDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
           Cursor ret=null;

        switch (sUriMatcher.match(uri)){
            case CATEGORY : {
                ret = dbHelper.getReadableDatabase().query(
                        BiblioContrat.CategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
            break;
            }
            case BOOKS_WITH_CATEGORY :
            {
             ret= getBooksWithCategorie(uri,projection,sortOrder);
                break;
            }
            case BOOK_BY_ID:
            {
                ret=getBookById(uri,projection,sortOrder);
                break;
            }
           default:
               throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        ret.setNotificationUri(getContext().getContentResolver(), uri);
        return ret;
    }

    private Cursor getBookById(Uri uri, String[] projection, String sortOrder) {
        Cursor ret=null;
        String selection=selectionBookById;
        String[] selectionArgs=new String[]{uri.getPathSegments().get(2)};
        ret=dbHelper.getReadableDatabase().query(
                BiblioContrat.BookEntry.TABLE_NAME
                ,projection
                ,selection
                ,selectionArgs
                ,null
                ,null
                ,sortOrder);


        return ret;

    }

    private Cursor getBooksWithCategorie(Uri uri, String[] projection, String sortOrder) {
        Cursor ret=null;
        String selection=selectionBooksByCategorie;
        String[] selectionArgs=new String[]{uri.getPathSegments().get(1)};
        ret=dbHelper.getReadableDatabase().query(
                BiblioContrat.BookEntry.TABLE_NAME
                ,projection
                ,selection
                ,selectionArgs
                ,null
                ,null
                ,sortOrder);


        return ret;
    }




    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db=dbHelper.getWritableDatabase();
        final int math=sUriMatcher.match(uri);
        Uri ret;
        long _id;
    Uri retUri=null;
        switch (math){
            case CATEGORY:
          _id= db.insert(BiblioContrat.CategoryEntry.TABLE_NAME,null,values);
                if ( _id >= 0 )
                    ret = BiblioContrat.CategoryEntry.buildCategoryUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

             case BOOK:
           _id=db.insert(BiblioContrat.BookEntry.TABLE_NAME,null,values);
                 if ( _id >= 0 )
                     ret = BiblioContrat.BookEntry.buildBookUri(_id);
                 else
                     throw new android.database.SQLException("Failed to insert row into " + uri);
                 break;
             default:
                 throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ret;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
