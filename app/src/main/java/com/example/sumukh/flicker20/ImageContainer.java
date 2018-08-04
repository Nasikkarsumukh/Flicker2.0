package com.example.sumukh.flicker20;

import com.android.volley.toolbox.NetworkImageView;


import android.content.Context;
import android.util.AttributeSet;
public class ImageContainer extends NetworkImageView {

    public ImageContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    public ImageContainer(Context context) {
        super(context);
    }

    public ImageContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override //Setting up the dimensions
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
