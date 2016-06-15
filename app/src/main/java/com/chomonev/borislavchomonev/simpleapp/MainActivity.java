package com.chomonev.borislavchomonev.simpleapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private FloatingActionButton fab;

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

        /**
         * This method will make every tab to have same width
         */
        mTabs.setDistributeEvenly(true);

        mTabs.setSelectedIndicatorColors(R.color.background_body);
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
                /**
                 * changes the big picture
                 */
                if (state == 0 || state == 2) {
                    changeImage(mPager.getCurrentItem());
                }

                /**
                 * show\hide FAB button when pages change
                 */
                boolean visible = fab.getVisibility() == View.VISIBLE;
                if (state == 1 && visible) {
                    fab.hide();
                } else if (state == 0) {
                    fab.show();
                }
            }
        });
    }

    private void initFloatingBtn(final ViewPager mPager, final CustomPagerAdapter adapter) {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean visible = fab.getVisibility() == View.VISIBLE;
                if (visible) {
                    fab.hide();
                }
                changePage(mPager, adapter);
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

    /**
     * Simple fragment to be used for the sample
     */
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
