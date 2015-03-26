package com.example.saad.biblio_fsr;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.example.saad.biblio_fsr.service.BookService;


public class MainActivity extends ActionBarActivity implements Home_Fragment.Callback,Books_Fragment.CallBack_selected {


    boolean twoPane;
    String FRAGMENT_BOOKS_TAG= "book_tag" ;

    public static final String SELECTED_CATEGORY="selected_category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSharedPreferences("first",MODE_PRIVATE).getBoolean("first",true)){

            Intent intent=new Intent(this,BookService.class);
            startService(intent);
        }

        twoPane=false;

    if(findViewById(R.id.container_home_two)!=null) {

        twoPane=true;
         Fragment fragment = new Books_Fragment();

    if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.books_category,fragment,FRAGMENT_BOOKS_TAG)
                .commit();
    }

}else{
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_home, new Home_Fragment())
                    .commit();
        }

    }

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
