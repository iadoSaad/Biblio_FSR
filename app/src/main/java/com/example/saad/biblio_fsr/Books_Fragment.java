package com.example.saad.biblio_fsr;



import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;


import com.example.saad.biblio_fsr.Adapter.BooksAdapter;
import com.example.saad.biblio_fsr.data.BiblioContrat;


/**
 * A simple {@link Fragment} subclass.
 */
public class Books_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    CursorAdapter adapter;
    ListView listView;
    private Uri mUri;

    public Books_Fragment() {
        // Required empty public constructor
    }
    public interface CallBack_selected{
        public  void onItemSelected_book(Uri book_uri);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle arguments=getArguments();
        if(arguments!=null){
            mUri=arguments.getParcelable("URI");
           if(mUri==null){
               // recover the books when we return to this activity by the up button
               mUri= BiblioContrat.BookEntry.buildBookWithCategorie(
                       getActivity().getSharedPreferences(MainActivity.SELECTED_CATEGORY,getActivity().MODE_PRIVATE).getInt("uri",0));
           }
        }
        View view=inflater.inflate(R.layout.fragment_books_, container, false);
        listView=(ListView)view.findViewById(R.id.listView2);
        adapter=new BooksAdapter(getActivity(),null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor) parent.getItemAtPosition(position);
                Intent intent=new Intent(getActivity(),Selcted_Book_Activity.class);
                Uri uri=BiblioContrat.BookEntry.buildBookWith_Id(
                        cursor.getInt(cursor.getColumnIndex(BiblioContrat.BookEntry._ID))
                );
                ((CallBack_selected)getActivity()).onItemSelected_book(uri);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

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
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
}
