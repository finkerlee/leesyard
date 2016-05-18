package com.tootoo.app.core.utils.ui;

/**
 * Created by GYH on 14-2-19.
 */
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackyViewPager extends ViewPager {

    private boolean isTouched;
    public HackyViewPager(Context context) {
        this(context,null);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_DOWN)
            isTouched = true;
        else if(e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    isTouched = false;
                }
            },50);

        }
        return super.dispatchTouchEvent(e);
    }


    @Override
    public boolean canScrollVertically(int direction) {
        if(direction == -1)
            return isTouched();
        return super.canScrollVertically(direction);
    }

    public boolean isTouched() {
        return isTouched;
    }

    @Override
    public PagerAdapter getAdapter() {
        return super.getAdapter();
    }
}
