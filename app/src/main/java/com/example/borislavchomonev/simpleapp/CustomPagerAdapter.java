package com.example.borislavchomonev.simpleapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * Created by borislav.chomonev on 22/03/2016.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private int icons[];
    private String tabText[];

    public CustomPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.tabText = context.getResources().getStringArray(R.array.tabs);
        this.icons = new int[]{R.drawable.emoticons03, R.drawable.emoticons08, R.drawable.emoticons13};
    }

    @Override
    public Fragment getItem(int position) {
        MainActivity.MyFragment myFragment = MainActivity.MyFragment.getInstance(position);
        return myFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = context.getResources().getDrawable(icons[position]);
        assert drawable != null;
        int dp36 = context.getResources().getDimensionPixelSize(R.dimen.dp36);
        drawable.setBounds(0, 0, dp36, dp36);
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

}
