package com.twelve.morning.notebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_notes);

        ArrayAdapter<String> list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[] {
                "Two plus two is four",
                "Minus one that's three, quick maths",
                "Everyday man's on the block",
                "Smoke trees (ah)",
                "See your girl in the park",
                "That girl is a uckers",
                "When the ting went quack-quack-quack",
                "You man were ducking (you man ducked)"
        });
        list_view.setAdapter(list_adapter);
    }
}
