package com.twelve.morning.notebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Handler;


public class NotesListAdapter extends BaseAdapter implements ListAdapter {


    private Note[] notes = null;
    private Context context;
    public static CheckBox cbSelected = null;
    public static boolean visible = false;


    public NotesListAdapter(Note[] notes, Context ctx) {
        this.notes = notes;
        this.context = ctx;
    }

    public void removeChecks() {
        ArrayList<Note> notesList = new ArrayList<Note>(Arrays.asList(notes));
        for(Note note : notesList){
            note.setSelected(false);
            note.save();
        }
        cbSelected.setChecked(false);
    }

    public ArrayList<Note> getCheckedNotes(){
        ArrayList<Note> notesList = new ArrayList<Note>(Arrays.asList(notes));
        ArrayList<Note> returnList = new ArrayList<Note>(Arrays.asList(notes));

        for(Note note : notesList){
            if(!note.getSelected()){
                returnList.remove(note);
            }
        }

        return returnList;
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
        content.setText(body);

        CheckBox pinned_box = (CheckBox)convertView.findViewById(R.id.cb_pinned);
        pinned_box.setChecked(((Note)notes[position]).getPinned());

        final CheckBox selectBox = (CheckBox)convertView.findViewById(R.id.cb_selected);
        selectBox.setChecked(((Note)notes[position]).getSelected());
        if(!visible)
            selectBox.setVisibility(View.GONE);
        cbSelected = selectBox;

        /*ListView list = (ListView)convertView.findViewById(R.id.list_notes);
        list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeChecks();
                cbSelected.setVisibility(View.VISIBLE);
                MainActivity.delBtn.setVisibility(View.VISIBLE);
                return false;
            }
        });*/

        pinned_box.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Note note = notes[position];
                note.setPinned(!note.getPinned());
                note.save();
                ((MainActivity)context).reloadNotes(null);
            }
        });

        selectBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Note note = notes[position];
                note.setSelected(!note.getSelected());
                note.save();
                ((MainActivity)context).reloadNotes(null);
                selectBox.setVisibility(View.VISIBLE);
            }
        });

//        //define showSelectBox
//
//        //define onTouchEvents
//        pressedView.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public void onTouch(View v){
//                Note note = notes[position];
//
//            }
//        });
//
//        convertView.setOnTouchListener(new);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.visible = false;
                Intent intent = new Intent(context, EditNoteActivity.class);
                Note note = notes[position];
                intent.putExtra("note", note);
                context.startActivity(intent);
        }

        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeChecks();
                visible = true;
                MainActivity.visible = true;
                cbSelected.setVisibility(View.VISIBLE);
                MainActivity.delBtn.setVisibility(View.VISIBLE);
                ((MainActivity)context).reloadNotes(null);
                return true;
            }
        });

        return convertView;
    }

    public Note[] getNotes() {
        return notes;
    }

    public void setCbSelectedVisibility(int visibility){
        cbSelected.setVisibility(visibility);
    }
}
