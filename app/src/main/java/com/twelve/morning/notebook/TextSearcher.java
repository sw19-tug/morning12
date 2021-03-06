package com.twelve.morning.notebook;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Color;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.List;

public class TextSearcher {
    int lastPos = 0, currentPos = -1;
    private static TextSearcher instance = null;

    static SpannableString ss = null;
    static ForegroundColorSpan clr = null;

    private TextSearcher(){};

    static TextSearcher GetInstance()
    {
        if(instance == null)
            instance = new TextSearcher();

        return instance;
    }

    int SearchNextInstance(Note note, String text)
    {
        currentPos = note.getBody().indexOf(text, lastPos);
        if(currentPos == -1)
        {
            if(lastPos == 0)
                return -1;
            currentPos = note.getBody().indexOf(text, 0);
        }

        lastPos = currentPos+text.length();
        return currentPos;
    }


    static public void highlightText(Activity activity, Note note, int position, int size)
    {
        TextSearcher.removePreviousHighlight(activity);

        moveToCharacter(activity, position);
        ss = new SpannableString(note.getBody());
        clr = new ForegroundColorSpan(Color.GRAY);
        ss.setSpan(clr, position, position+size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        EditText edit_text_body = activity.findViewById(R.id.et_edit_note_body);
        edit_text_body.setText(ss);
    }

    static private void removePreviousHighlight(Activity activity)
    {
        if(ss != null && clr != null) {
            ss.removeSpan(clr);
            EditText edit_text_body = activity.findViewById(R.id.et_edit_note_body);
            edit_text_body.setText(ss);
            ss = null;
            clr = null;
        }
    }

    static void moveToCharacter(Activity activity, int position)
    {
        ScrollView sv = activity.findViewById(R.id.scrollview);
        EditText textView = activity.findViewById(R.id.et_edit_note_body);
        Layout layout = textView.getLayout();
        sv.smoothScrollTo(0, layout.getLineTop(layout.getLineForOffset(position)));
    }
}
