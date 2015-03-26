package com.example.saad.biblio_fsr;





import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.saad.biblio_fsr.Adapter.CategoryAdapter;
import com.example.saad.biblio_fsr.data.BiblioContrat.*;



/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CATEGORY_LOADER = 0;
    CategoryAdapter adapt;

    ListView myList;


    public interface Callback{
      public void onItemSelected(Uri BooksUri);
    }
    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        myList=(ListView)view.findViewById(R.id.listView);
        adapt=new CategoryAdapter(getActivity(),null);
        myList.setAdapter(adapt);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Cursor cursor= (Cursor) parent.getItemAtPosition(position);
                Uri booksUri=BookEntry.buildBookWithCategorie(
                        cursor.getInt(cursor.getColumnIndex(CategoryEntry._ID))) ;
                // save the latest selected Category
                SharedPreferences.Editor editor= getActivity().getSharedPreferences(MainActivity.SELECTED_CATEGORY,getActivity().MODE_PRIVATE).edit();
                editor.putInt("uri",cursor.getInt(cursor.getColumnIndex(CategoryEntry._ID))) ;
                editor.commit();
                ((Callback)getActivity()).onItemSelected(booksUri) ;

            }
        });
        return view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        getLoaderManager().initLoader(CATEGORY_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {


        return new CursorLoader(
                getActivity(),
                CategoryEntry.CONTENT_URI,
                new String[]{CategoryEntry._ID,CategoryEntry.COLUMN_LABEL},
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        adapt.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }
    
    

}
