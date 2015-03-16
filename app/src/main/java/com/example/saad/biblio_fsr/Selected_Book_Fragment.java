package com.example.saad.biblio_fsr;



import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.saad.biblio_fsr.data.BiblioContrat;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class Selected_Book_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mUri;

    TextView title,author,descritpion;

    public Selected_Book_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments=getArguments();
        if(arguments!=null){
            mUri=arguments.getParcelable("URI");
        }
        View view=inflater.inflate(R.layout.fragment_selected__book_, container, false);
        title=(TextView)view.findViewById(R.id.text_title);
        author=(TextView)view.findViewById(R.id.text_author);
        descritpion=(TextView)view.findViewById(R.id.text_description);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        getLoaderManager().initLoader(0,null,this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        if(mUri!=null){


            return new CursorLoader(
                    getActivity(),
                    mUri,
                    null,null,null,null);

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        cursor.moveToFirst();
        title.setText("\t"+cursor.getString(cursor.getColumnIndex(BiblioContrat.BookEntry.COLUMN_TITLE)));
        author.setText("\t"+cursor.getString(cursor.getColumnIndex(BiblioContrat.BookEntry.COLUMN_AUTHOR_NAME)));
        descritpion.setText("\t"+cursor.getString(cursor.getColumnIndex(BiblioContrat.BookEntry.COLUMN_DESCRIPTION)));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
