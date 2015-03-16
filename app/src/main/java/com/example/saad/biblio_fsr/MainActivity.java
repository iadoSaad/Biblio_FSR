package com.example.saad.biblio_fsr;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.saad.biblio_fsr.data.BiblioContrat.*;
import com.example.saad.biblio_fsr.data.BiblioDbHelper;
import com.example.saad.biblio_fsr.data.BooksData;
import com.example.saad.biblio_fsr.service.BookService;


public class MainActivity extends ActionBarActivity implements Home_Fragment.Callback,Books_Fragment.CallBack_selected {


    boolean twoPane;
    String FRAGMENT_BOOKS_TAG="book_tag" ;

    public static final String SELECTED_CATEGORY="selected_category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSharedPreferences("first",MODE_PRIVATE).getBoolean("first",true)){
          //  createDataIfNotExist();
            Intent intent=new Intent(this,BookService.class);
            startService(intent);
        }

        twoPane=false;

    if(findViewById(R.id.container_home_two)!=null) {
        View view=findViewById(R.id.container_home_two);

        twoPane=true;
         Fragment fragment = new Books_Fragment();

    if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.books_category,fragment,FRAGMENT_BOOKS_TAG)
                .commit();
    }

}else{
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_home, new Home_Fragment())
                .commit();
    }

    }


   public void createDataIfNotExist(){

        int id_book=0;
        Cursor c=getContentResolver().query(
               CategoryEntry.CONTENT_URI,
                null, null, null, null);
        if(c!=null&&c.moveToFirst()){
            c.close();
            return ;
        }

            String ctr[]= BooksData.Categories;
        for(int i=0;i<ctr.length;i++){
        ContentValues cc=new ContentValues();
        cc.put(CategoryEntry._ID,i);
        cc.put(CategoryEntry.COLUMN_LABEL,ctr[i]);
        getContentResolver().insert(CategoryEntry.CONTENT_URI, cc);
            for(int j=0;j<20;j++){
                cc=new ContentValues();
                cc.put(BookEntry._ID,id_book++);
                cc.put(BookEntry.COLUMN_CATEGORY_ID,i);
                cc.put(BookEntry.COLUMN_TITLE," Title of book number "+id_book);
                cc.put(BookEntry.COLUMN_AUTHOR_NAME,"author of book number "+id_book);
                cc.put(BookEntry.COLUMN_DESCRIPTION,"this is a description of book number "+id_book+" that belong to the "+ctr[i]+" category");
                getContentResolver().insert(BookEntry.CONTENT_URI, cc);
            }


        }
      if(c!=null)  c.close();

      SharedPreferences.Editor editor= getSharedPreferences("first",MODE_PRIVATE).edit();
        editor.putBoolean("first",false);
        editor.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home_Fragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Uri booksUri) {
        if( ! twoPane){
            Intent intent=new Intent(this,Books_Activity.class);
            intent.setData(booksUri);


            startActivity(intent);
        }
        else{
            Bundle arguments =new Bundle();
            arguments.putParcelable("URI",booksUri);
            Fragment fragment=new Books_Fragment();
            fragment.setArguments(arguments);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.books_category,fragment,FRAGMENT_BOOKS_TAG)
                    .commit();
        }

    }

    @Override
    public void onItemSelected_book(Uri book_uri) {
        if( ! twoPane){
            Intent intent=new Intent(this,Selcted_Book_Activity.class);
            intent.setData(book_uri);


            startActivity(intent);
        }
        else{
            Bundle arguments =new Bundle();
            arguments.putParcelable("URI",book_uri);
            Fragment fragment=new Selected_Book_Fragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.book_detail,fragment,FRAGMENT_BOOKS_TAG)
                    .commit();
        }

    }
}
