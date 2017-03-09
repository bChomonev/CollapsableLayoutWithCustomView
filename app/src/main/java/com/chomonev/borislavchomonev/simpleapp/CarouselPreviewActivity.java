package com.chomonev.borislavchomonev.simpleapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;

import java.util.Locale;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class CarouselPreviewActivity extends AppCompatActivity {
    private TestAdapter mAdapter;
    Observable<Integer> onItemSelectedObserver;
    private int currentCenteredItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carousel_preview);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_vertical);

        //initRecyclerView(binding.listHorizontal, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false), mAdapter);
        CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        initRecyclerView(recyclerView, layoutManager);

        // fab button will add element to the end of the list
        findViewById(R.id.fab_scroll).setOnClickListener(v -> {

            final int itemToRemove = mAdapter.mItemsCount;
//            if (10 != itemToRemove) {
//                mAdapter.mItemsCount++;
//                mAdapter.notifyItemInserted(itemToRemove);
//            }

            if (currentCenteredItem > 0) {
                recyclerView.smoothScrollToPosition(currentCenteredItem - 1);
            }
        });

        // fab button will remove element from the end of the list
        findViewById(R.id.fab_change_data).setOnClickListener(v -> {

            final int itemToRemove = mAdapter.mItemsCount - 1;
//            if (0 <= itemToRemove) {
//                mAdapter.mItemsCount--;
//                mAdapter.notifyItemRemoved(itemToRemove);
//            }
            if (mAdapter.getItemCount() > currentCenteredItem + 1) {
                recyclerView.smoothScrollToPosition(currentCenteredItem + 1);
            }
        });
    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager) {
        // enable zoom effect. this line can be customized
//        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        onItemSelectedObserver = PositionObservable.create(layoutManager);
        Observable<Integer> integerObservable = onItemSelectedObserver.observeOn(AndroidSchedulers.mainThread());

        onItemSelectedObserver.subscribe(integer -> {

//            Log.d("test", "emit " + integer);
            currentCenteredItem = integer;
        });

        mAdapter = new TestAdapter(this, integerObservable);

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample mAdapter with random data
        recyclerView.setAdapter(mAdapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());
        // enable center post touching on item and item click listener
        DefaultChildSelectionListener.initCenterItemListener((recyclerView1, carouselLayoutManager, v) -> {

            final int position = recyclerView1.getChildLayoutPosition(v);
            final String msg = String.format(Locale.US, "Item %1$d was clicked", position);
            Toast.makeText(CarouselPreviewActivity.this, msg, Toast.LENGTH_SHORT).show();
        }, recyclerView, layoutManager);

    }

    private static final class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

        private int mItemsCount = 10;
        private Context mContext;
        Observable<Integer> mOnItemSelectedObserver;

        TestAdapter(Context context, Observable<Integer> onItemSelectedObserver) {

            mOnItemSelectedObserver = onItemSelectedObserver;
            mContext = context;
        }

        @Override
        public TestViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View inflate = inflater.inflate(R.layout.item_view,
                    parent, false);

            return new TestViewHolder(inflate, mContext);
        }

        @Override
        public void onBindViewHolder(final TestViewHolder holder, final int position) {

            holder.update(position);
        }

        @Override
        public int getItemCount() {

            return mItemsCount;
        }

        @Override
        public void onViewAttachedToWindow(TestViewHolder holder) {

            super.onViewAttachedToWindow(holder);
            if (holder.test == null) {
                subscribeToObserver(holder);
            }
            if (holder.test != null && holder.test.isUnsubscribed()) {
                subscribeToObserver(holder);
            }
        }

        private void subscribeToObserver(TestViewHolder holder) {

            holder.test = mOnItemSelectedObserver.subscribe(integer -> {

                holder.updateBackground(integer);
            });
        }

        @Override
        public void onViewDetachedFromWindow(TestViewHolder holder) {

            super.onViewDetachedFromWindow(holder);

            int adapterPosition = holder.getAdapterPosition();
            if (holder.test != null) {
                holder.test.unsubscribe();
            }
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        private final View mItemViewBinding;
        public final RelativeLayout holder;
        private final Context mContext;
        public Subscription test;
        //        public ImageView overlay;
//        public ImageView overlayTitle;
        public TextView title;

        TestViewHolder(View itemViewBinding, Context context) {

            super(itemViewBinding);
            this.mContext = context;
            mItemViewBinding = itemViewBinding;
            holder = (RelativeLayout) mItemViewBinding.findViewById(R.id.item_holder);
//            overlay = (ImageView) mItemViewBinding.findViewById(R.id.info_epg_overlay);
            title = (TextView) mItemViewBinding.findViewById(R.id.epg_info_title);
//            overlayTitle = (ImageView) mItemViewBinding.findViewById(R.id.info_epg_overlay_title);

        }

        public void updateBackground(int currentCenterPosition) {

//            int height = overlay.getHeight();

        }

        public void update(int position) {

            title.setText("Position  + " + position);
        }
    }
}