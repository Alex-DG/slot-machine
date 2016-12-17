package com.android.alex.slotmachine.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.alex.slotmachine.R;

/**
 * **** Alternative solution with a endless ****
 *
 * Created by Alex on 25-Nov-15.
 */
public class ListSlotAdapter extends BaseAdapter {
    private static LayoutInflater mInflater;
    private static ImageView viewHolder;
    private Integer[] mCouponImages;

    public ListSlotAdapter(Context c, Integer[] coupomImages) {
        ListSlotAdapter.mInflater = LayoutInflater.from(c);
        this.mCouponImages = coupomImages;
    }

    @Override
    public int getCount() {
        return 300;
    }

    @Override
    public Object getItem(int position) {
        // display the right item in your array.
        return position % mCouponImages.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
        // return position % mCouponImages.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_slot_machine, null);
            viewHolder = (ImageView) convertView.findViewById(R.id.img_slot);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImageView) convertView.getTag();
        }

        viewHolder.setImageResource(this.mCouponImages[position % mCouponImages.length]);


        ViewTreeObserver vto = viewHolder.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                // Remove after the first run so it doesn't fire forever
                viewHolder.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = viewHolder.getMeasuredHeight();
                int finalWidth = viewHolder.getMeasuredWidth();

                return true;
            }
        });

        return convertView;
    }

}
