package com.twelve.morning.notebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesListAdapter extends BaseAdapter implements ListAdapter {


    private Note[] notes = null;
    private Context context;


    public NotesListAdapter(Note[] notes, Context ctx) {
        this.notes = notes;
        this.context = ctx;
    }

    @Override
    public int getCount() {
        return notes.length;
    }

    @Override
    public Object getItem(int position) {
        return notes[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_item, null);
        }

        TextView title = (TextView)convertView.findViewById(R.id.note_title);
        title.setText(((Note)notes[position]).getTitle());

        TextView content = (TextView)convertView.findViewById(R.id.note_content);
        content.setText(((Note)notes[position]).getBody().substring(0, 25));

        /*
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                notifyDataSetChanged();
            }
        });
        */

        return convertView;
    }
}
