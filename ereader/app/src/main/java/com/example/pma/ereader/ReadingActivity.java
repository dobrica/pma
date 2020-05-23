package com.example.pma.ereader;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.pma.ereader.ui.Fullscreen;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReadingActivity extends Fullscreen {

    private View options;
    private FrameLayout bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        mContentView = findViewById(R.id.fullscreen_content);
        bookmark = findViewById(R.id.bookmark);
        options = findViewById(R.id.options_container);

        hide();
        mVisible = false;

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();

                if (mVisible) {
                    slideBottomViewBackToScreen(options);
                    slideTopViewBackToScreen(bookmark);
                } else {
                    slideBottomViewOffScreen(options);
                    slideTopViewOffScreen(bookmark);
                }
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_reading_fragment);
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.bottom_navigation), navController);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        slideBottomViewOffScreen(options);
        slideTopViewOffScreen(bookmark);
    }

}
