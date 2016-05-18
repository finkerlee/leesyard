package com.lijiadayuan.lishijituan.adapter;

import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;

/**
 * 图片滚动加载
 * Created by GYH on 14-1-8.
 */
public class ViewPageAdapter extends PagerAdapter {

    private List<SimpleDraweeView> imageViews;
    private List<String> imageurls;
    //设置条目点击事件
    private View.OnClickListener clickListener;
    //是否接触
    private boolean isTouched;

    /**
     * 判断是否手指在屏幕上
     * @return
     */
    public boolean isTouched() {
        return isTouched;
    }

    public ViewPageAdapter(List<SimpleDraweeView> imageViews,List<String> imageurls,View.OnClickListener clickListener){
        this(imageViews,imageurls);
        this.clickListener = clickListener;
    }

    public ViewPageAdapter(List<SimpleDraweeView> imageViews,List<String> imageurls){
        this.imageurls=imageurls;
        this.imageViews=imageViews;
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        imageViews.get(position).setOnClickListener(clickListener);
        imageViews.get(position).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                    isTouched = true;
                else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                    isTouched = false;
                return false;
            }
        });
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(imageurls.get(position)))
                .setAutoPlayAnimations(true)
                .build();
        imageViews.get(position).setController(controller);
        container.addView(imageViews.get(position), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return imageViews.get(position);
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int arg1, Object arg2) {
        container.removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
