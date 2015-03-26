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
import com.example.saad.biblio_fsr.data.BiblioContrat.*;

/**
 * Created by saad on 12/03/2015.
 */
public class CategoryAdapter extends CursorAdapter {

    public CategoryAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public static class ViewHolder{
        public final TextView textView;
        public final ImageView imageView;

        ViewHolder(View view){

            textView=(TextView) view.findViewById(R.id.book_category_text);
            imageView=(ImageView)view.findViewById(R.id.imageView);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_home_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder= (ViewHolder) view.getTag();
        int labelPos=cursor.getColumnIndex(CategoryEntry.COLUMN_LABEL);
        String value=cursor.getString(labelPos);
        viewHolder.textView.setText(value);
    }
}
