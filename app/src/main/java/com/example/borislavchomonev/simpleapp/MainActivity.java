package com.example.borislavchomonev.simpleapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {
    boolean goRight = true;
    private ImageView bigImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bigImage = (ImageView) findViewById(R.id.image_top);

        final ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        final CustomPagerAdapter adapter = new CustomPagerAdapter(getSupportFragmentManager(), this);
        initPagerAndTabs(mPager, adapter);
        initFloatingBtn(mPager, adapter);
    }

    private void initPagerAndTabs(final ViewPager mPager, final CustomPagerAdapter adapter) {
        mPager.setAdapter(adapter);
        SlidingTabLayout mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setCustomTabView(R.layout.custom_tabs_view, R.id.tabText);
        mTabs.setDistributeEvenly(true);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.background_body);
            }
        });
        mTabs.setViewPager(mPager);
        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0 || state == 2) {
                    changeImage(mPager.getCurrentItem());
                }
            }
        });
    }

    private void initFloatingBtn(final ViewPager mPager, final CustomPagerAdapter adapter) {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable = getResources().getDrawable(R.drawable.emoticons02);
                assert drawable != null;
                int dp36 = getResources().getDimensionPixelSize(R.dimen.dp36);
                drawable.setBounds(0, 0, dp36, dp36);
                ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                SpannableString spannableString = new SpannableString("You don't like this page?  ");
                spannableString.setSpan(imageSpan, spannableString.length() - 1, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                Snackbar.make(view, spannableString, Snackbar.LENGTH_LONG)
                        .setAction("Change", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changePage(mPager, adapter);
                            }
                        }).show();
            }
        });
    }

    private void changeImage(int position) {
        switch (position) {
            case 0:
                bigImage.setImageResource(R.drawable.image_one);
                break;
            case 1:
                bigImage.setImageResource(R.drawable.image_two);
                break;
            case 2:
                bigImage.setImageResource(R.drawable.image_three);
                break;
            default:
                break;
        }
    }

    private void changePage(ViewPager mPager, CustomPagerAdapter adapter) {
        int currentItem = mPager.getCurrentItem();
        if (goRight) {
            if (currentItem >= adapter.getCount() - 1) {
                goRight = false;
                currentItem--;
            } else {
                currentItem++;
            }
        } else {
            if (currentItem == 0) {
                goRight = true;
                currentItem++;
            } else {
                currentItem--;
            }
        }
        mPager.setCurrentItem(currentItem, true);
    }

    public static class MyFragment extends Fragment {
        public static MyFragment getInstance(int position) {
            return new MyFragment();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.view_fragment, container, false);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);

            List<String> newData = new ArrayList<>();
            for (int i = 1; i <= 200; i++) {
                newData.add("Hello World " + i);
            }
            RecyclerAdapter adapter = new RecyclerAdapter(newData);
            recyclerView.setAdapter(adapter);
            return recyclerView;
        }
    }

}
