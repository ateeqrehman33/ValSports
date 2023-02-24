package com.ventura.bracketslib.customviews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Emil on 21/10/17.
 */

public class WrapContentHeightViewPager extends ViewPager {

    private Context context;

    public WrapContentHeightViewPager(Context context) {
        super(context);
        this.context = context;
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            int h = child.getMeasuredHeight();

            if (h > height) height = h;

            int[] screenSIze = getScreenSIze();
            if (screenSIze[1] > height)
                height = screenSIze[1];
            //overriding wrap content feature
            // int[] screenSize = getScreenSIze();
            // height = 1800;
        }


        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int[] getScreenSIze() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;

        int[] size = {w, h};
        return size;

    }
}