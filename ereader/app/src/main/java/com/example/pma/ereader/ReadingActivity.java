package com.example.pma.ereader;

import android.os.Bundle;
import android.view.View;

import com.example.pma.ereader.ui.Fullscreen;

public class ReadingActivity extends Fullscreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        hide(); // start in fullscreen
        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
    }
}
