package com.twelve.morning.notebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_item, null);
        }

        TextView title = (TextView)convertView.findViewById(R.id.note_title);
        title.setText(((Note)notes[position]).getTitle());

        TextView content = (TextView)convertView.findViewById(R.id.note_content);
        String body = ((Note)notes[position]).getBody();
        content.setText(body.substring(0, Math.min(body.length(), 25)));

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
        Button pinBtn = convertView.findViewById(R.id.btn_pin);

        pinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Note note = notes[position];
                note.setPinned(true);
                note.save();

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                Note note = notes[position];
                intent.putExtra("title", note.getTitle());
                context.startActivity(intent);
        }

        });

        return convertView;
    }

    public Note[] getNotes() {
        return notes;
    }
}
