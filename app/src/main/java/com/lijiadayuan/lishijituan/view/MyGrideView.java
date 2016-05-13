package com.lijiadayuan.lishijituan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by zhaoyi on 16/4/30.
 */
public class MyGrideView extends GridView{
    public MyGrideView(Context context) {
        super(context);
    }

    public MyGrideView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGrideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE)
        {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

}
