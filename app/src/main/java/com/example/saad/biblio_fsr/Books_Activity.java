package com.example.saad.biblio_fsr;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;



public class Books_Activity extends ActionBarActivity implements Books_Fragment.CallBack_selected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        if(savedInstanceState==null){
            Bundle arguments =new Bundle();
            arguments.putParcelable("URI",getIntent().getData());
            Fragment fragment=new Books_Fragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.books_container,fragment).commit();
        }
    }




    @Override
    public void onItemSelected_book(Uri book_uri) {
        Intent intent=new Intent(this,Selcted_Book_Activity.class);
        intent.setData(book_uri);


        startActivity(intent);
    }
}
