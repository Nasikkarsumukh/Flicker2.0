package com.example.sumukh.flicker20;

import android.widget.BaseAdapter;
import com.android.volley.toolbox.NetworkImageView;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
public class PhotosAdapter extends BaseAdapter {
    private Context mContext;
    private List<BaseImage> mItems;

    public PhotosAdapter(Context mContext, List<BaseImage> items) {
        this.mContext = mContext;
        this.mItems = items;
    }


    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.recent_photo_item, parent, false);
        }

        NetworkImageView flickrPhoto = ViewHolder.get(convertView, R.id.imgPhoto);
        BaseImage flickrImage = (BaseImage) getItem(position);
        flickrPhoto.setImageUrl(flickrImage.getUrl(), ImageManager.loader());
        return convertView;
    }

    public static class ViewHolder {

        // Returning generic return type to reduce casting noice
        public static <T extends View> T get(View view, int id) {
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return (T) childView;
        }
    }


}
