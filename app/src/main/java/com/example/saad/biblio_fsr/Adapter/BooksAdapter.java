package com.example.saad.biblio_fsr.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saad.biblio_fsr.R;
import com.example.saad.biblio_fsr.data.BiblioContrat;

/**
 * Created by saad on 13/03/2015.
 */
public class BooksAdapter extends CursorAdapter {


    public BooksAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public static class ViewHolder{
        public final TextView title;
        public final TextView author;
        public final ImageView imageView;

        ViewHolder(View view){
            title=(TextView) view.findViewById(R.id.text_book);
            author=(TextView) view.findViewById(R.id.text_author);
            imageView=(ImageView)view.findViewById(R.id.imageView2);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_books_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        int titlePos=cursor.getColumnIndex(BiblioContrat.BookEntry.COLUMN_TITLE);
        int authorPos=cursor.getColumnIndex(BiblioContrat.BookEntry.COLUMN_AUTHOR_NAME);
        String title=cursor.getString(titlePos);
        String author=cursor.getString(authorPos);
        viewHolder.title.setText(title);
        viewHolder.author.setText(author);
    }
}
