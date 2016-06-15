package com.chomonev.borislavchomonev.simpleapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by borislav.chomonev on 22/03/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<String> mDataset;

    public RecyclerAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView mIcon;

        public ViewHolder(RelativeLayout v) {
            super(v);
            TextView tvTitle = (TextView) v
                    .findViewById(R.id.text2);
            ImageView icon = (ImageView) v.findViewById(R.id.icon1);

            mTextView = tvTitle;
            mIcon = icon;
        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RelativeLayout v = (RelativeLayout) inflater.inflate(R.layout.view_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String item = getItems().get(position);
        holder.mTextView.setText(item);
    }

    public List<String> getItems() {
        return mDataset;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}