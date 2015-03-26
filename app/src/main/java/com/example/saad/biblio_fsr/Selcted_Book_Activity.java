package com.example.saad.biblio_fsr;


import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;



public class Selcted_Book_Activity extends ActionBarActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selcted__book_);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments =new Bundle();
            arguments.putParcelable("URI",getIntent().getData());
            Fragment fragment=new Selected_Book_Fragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_selected_book, fragment)
                    .commit();
        }
    }





}
