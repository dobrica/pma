package com.example.pma.ereader.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Fullscreen extends AppCompatActivity {

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 450;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    protected boolean mVisible;
    protected FloatingActionButton fab;
    protected View mContentView;
    protected ActionBar actionBar;

    protected void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    protected void hide() {
        // Hide UI first
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (fab != null) {
            fab.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    protected void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        mVisible = true;

        if (fab != null) {
            fab.show();
        }
    }

    public void slideTopViewOffScreen(View view) {
        animateViewSlide(view, 0, -view.getHeight());
    }

    public void slideTopViewBackToScreen(View view) {
        animateViewSlide(view, -view.getHeight(), 0);
    }

    public void slideBottomViewOffScreen(View view) {
        animateViewSlide(view, 0, view.getHeight());
    }

    public void slideBottomViewBackToScreen(View view) {
        animateViewSlide(view, view.getHeight(), 0);
    }

    private void animateViewSlide(View view, int fromYDelta, int toYDelta) {
        TranslateAnimation animate = new TranslateAnimation(
                0, 0,
                fromYDelta, toYDelta);
        animate.setDuration(400);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

}
