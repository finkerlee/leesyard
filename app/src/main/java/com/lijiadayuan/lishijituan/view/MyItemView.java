package com.lijiadayuan.lishijituan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.lishijituan.utils.DPIUtil;

/**
 * 我的条目
 */


public class MyItemView extends LinearLayout {

    //上下文
    private Context mContext;
    //item的标题
    private String title;
    //item的标题的textsize
    private float titleTextSize;
    //item的标题的字的颜色
    private int title_color;
    //item新的消息或者已认证 等 的 图标
    private int icon_Image;
    //item 更多
    private boolean isShowIconMore;

    private LayoutInflater mInflater;
    //标题的textview
    private TextView mTvTitle;
    //新的消息或认证的icon
    private SimpleDraweeView mIconNewMsg;
    //更多的icon
    private SimpleDraweeView mIconMore;

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }
        this.mContext = context;

        TypedArray attrsArray = context.obtainStyledAttributes(attrs, R.styleable.MyItemView, 0, 0);
        title = attrsArray.getString(R.styleable.MyItemView_item_title_text);
        icon_Image = attrsArray.getResourceId(R.styleable.MyItemView_item_right_icon,1);
        isShowIconMore = attrsArray.getBoolean(R.styleable.MyItemView_item_is_show_more_icon,true);
        titleTextSize = attrsArray.getDimension(R.styleable.MyItemView_item_title_size,9);
        title_color = attrsArray.getColor(R.styleable.MyItemView_item_title_color, Color.BLACK);

        attrsArray.recycle();
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, DPIUtil.dip2px(44));
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        addView(initView(),layoutParams);
    }

    /**
     * 设置标签
     * */
    private View initView(){
        if(mInflater==null){
            mInflater = LayoutInflater.from(mContext);
        }
        View parentView = mInflater.inflate(R.layout.layout_item_next, null);
        mTvTitle = (TextView) parentView.findViewById(R.id.item_title);
        mIconNewMsg = (SimpleDraweeView) parentView.findViewById(R.id.new_message_icon);
        mIconMore = (SimpleDraweeView) parentView.findViewById(R.id.item_more_icon);
        setViewValues();
        return parentView;
    }

    /**
     * 设置条目数据
     */
    private void setViewValues(){
        mTvTitle.setText(title);
        mTvTitle.setTextSize(titleTextSize);
        mTvTitle.setTextColor(title_color);

        if(icon_Image == 1){
            mIconNewMsg.setVisibility(View.GONE);
        }else{
            mIconNewMsg.setVisibility(View.VISIBLE);
            mIconNewMsg.setImageURI(Uri.parse("res://com.lijiadayuan.lishijituan/" + icon_Image));
        }

        //设置更多按钮是否显示
        if(isShowIconMore){
            mIconMore.setVisibility(View.VISIBLE);
        }else {
            mIconMore.setVisibility(View.GONE);
        }


    }

}

