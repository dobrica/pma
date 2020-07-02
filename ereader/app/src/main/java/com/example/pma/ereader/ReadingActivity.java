package com.example.pma.ereader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.pma.ereader.ui.Fullscreen;
import com.example.pma.ereader.ui.PageFragment;
import com.github.mertakdut.BookSection;
import com.github.mertakdut.CssStatus;
import com.github.mertakdut.Reader;
import com.github.mertakdut.exception.OutOfPagesException;
import com.github.mertakdut.exception.ReadingException;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReadingActivity extends Fullscreen implements PageFragment.OnFragmentReadyListener {

    private View options;
    private FrameLayout bookmark;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Reader reader;
    private int pageCount = Integer.MAX_VALUE;
    private int pxScreenWidth;
    private int pxScreenHeight;
    private boolean isSkippedToPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        mContentView = findViewById(R.id.reading_layout);
        bookmark = findViewById(R.id.bookmark);
        options = findViewById(R.id.options_container);

        hide();
        mVisible = false;

        NavController navController = Navigation.findNavController(this, R.id.nav_reading_fragment);
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.bottom_navigation), navController);

        pxScreenWidth = getResources().getDisplayMetrics().widthPixels;
        pxScreenHeight = getResources().getDisplayMetrics().heightPixels;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.fullscreen_content);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (getIntent() != null && getIntent().getExtras() != null) {
            String filePath = getIntent().getExtras().getString("filePath");

            try {
                reader = new Reader();

                // Setting optionals once per file is enough.
                int contentPerSectionSize = pxScreenHeight / 2 - 50;
                reader.setMaxContentPerSection(contentPerSectionSize);
                reader.setCssStatus(CssStatus.OMIT); // for web view CssStatus.INCLUDE
                reader.setIsIncludingTextContent(true);
                reader.setIsOmittingTitleTag(true);

                // This method must be called before readSection.
                if (filePath != null) {
                    reader.setFullContent(filePath);
                }

                if (reader.isSavedProgressFound()) {
                    int lastSavedPage = reader.loadProgress();
                    mViewPager.setCurrentItem(lastSavedPage);
                }

            } catch (ReadingException e) {
                Toast.makeText(ReadingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public View onFragmentReady(int position) {

        BookSection bookSection = null;

        try {
            bookSection = reader.readSection(position);
        } catch (ReadingException e) {
            e.printStackTrace();
            Toast.makeText(ReadingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (OutOfPagesException e) {
            e.printStackTrace();
            this.pageCount = e.getPageCount();

            if (isSkippedToPage) {
                Toast.makeText(ReadingActivity.this, "Max page number is: " + this.pageCount, Toast.LENGTH_LONG).show();
            }

            mSectionsPagerAdapter.notifyDataSetChanged();
        }

        isSkippedToPage = false;

        if (bookSection != null) {
            return setFragmentView(false, bookSection.getSectionContent(), "text/html", "UTF-8"); // isContentStyled = true for web view
        }

        return null;
    }

    private View setFragmentView(boolean isContentStyled, String data, String mimeType, String encoding) {

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout linearLayout = new LinearLayout(ReadingActivity.this);
        linearLayout.setLayoutParams(layoutParams);

        TextView textView = new TextView(ReadingActivity.this);
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(ContextCompat.getColor(this, R.color.lightText)); // set page text color

        textView.setText(Html.fromHtml(data, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                String imageAsStr = source.substring(source.indexOf(";base64,") + 8);
                byte[] imageAsBytes = Base64.decode(imageAsStr, Base64.DEFAULT);
                Bitmap imageAsBitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                int imageWidthStartPx = (pxScreenWidth - imageAsBitmap.getWidth()) / 2;
                int imageWidthEndPx = pxScreenWidth - imageWidthStartPx;
                int imageHeight = imageAsBitmap.getHeight() > pxScreenHeight ? (pxScreenHeight - 50) : imageAsBitmap.getHeight();

                Drawable imageAsDrawable = new BitmapDrawable(getResources(), imageAsBitmap);
                imageAsDrawable.setBounds(imageWidthStartPx, 0, imageWidthEndPx, imageHeight);
                return imageAsDrawable;
            }
        }, null));

    //    textView.setTextSize();

        int pxPadding = dpToPx(15);

        textView.setPadding(pxPadding, pxPadding, pxPadding, pxPadding);

        textView.setTextSize(20);

        linearLayout.addView(textView);

        linearLayout.setOnClickListener(new View.OnClickListener() {
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

        return linearLayout;
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        slideBottomViewOffScreen(options);
        slideTopViewOffScreen(bookmark);
    }

}
