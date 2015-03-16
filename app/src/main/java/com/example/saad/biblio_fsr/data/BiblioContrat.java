package com.example.saad.biblio_fsr.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by saad on 12/03/2015.
 */
public class BiblioContrat {

    public static final String CONTENT_AUTHORITY = "com.example.saad.biblio_fsr";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_CATEGORY = "category";
    public static final String PATH_BOOK = "book";
 //   public static final String PATH_ID_BOOK="book/id";

    public static final class CategoryEntry implements BaseColumns{
       public static final Uri CONTENT_URI=
               BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_CATEGORY).build();

        public static final String TABLE_NAME="category";
        public static final String COLUMN_CATEGORY_ID="category_id";
        public static final String COLUMN_LABEL="label";

        public static Uri buildCategoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class BookEntry implements BaseColumns{
        public static final Uri CONTENT_URI=
                BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_BOOK).build();


        public static final String TABLE_NAME="book";

        public static final String COLUMN_CATEGORY_ID="category_id";

        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_AUTHOR_NAME="author";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_IS_SELECTED="selected";

        public static Uri buildBookUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildBookWithCategorie(int id_categorie){
            return CONTENT_URI.buildUpon().appendPath(id_categorie+"").build();
        }

        public static  Uri buildBookWith_Id(int id_book){
            return CONTENT_URI.buildUpon()
                    .appendPath("id")
                    .appendPath(id_book+"")
                    .build();
        }

    }
}
