package com.lijiadayuan.lishijituan;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.lijiadayuan.lishijituan.adapter.ImagePagerAdapter;
import com.lijiadayuan.lishijituan.view.XCRoundRectImageView;

public class MainActivity extends BaseActivity implements OnClickListener{
    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray= new ArrayList<String>();
    ArrayList<String> titleList= new ArrayList<String>();
    private LinearLayout notice_parent_ll;
    private LinearLayout notice_ll;
    private ViewFlipper notice_vf;
    private int mCurrPos;
    private EditText mSearchBox = null;
    private Drawable drawable = null;
    private Drawable drawMain, drawFind, drawNews, drawMine;
    private LinearLayout radioMain, radioFind, radioNews, radioMine;
    private XCRoundRectImageView ivTicket, ivRed;
    private ImageButton imagesharing,imageculture,imageproduct;
    private LinearLayout framenotice;
    private Button button;
    private ImageView ivmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        button=(Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        findViewById();
        initView();

        imageUrlList
                .add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
        imageUrlList
                .add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
        imageUrlList
                .add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");
        imageUrlList
                .add("http://g.hiphotos.baidu.com/image/pic/item/b3119313b07eca80131de3e6932397dda1448393.jpg");

        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44301359");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/43486527");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44648121");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44619589");
        titleList.add("常见Android进阶笔试题");
        titleList.add("GridView之仿支付宝钱包首页");
        titleList.add("仿手机QQ网络状态条的显示与消失 ");
        titleList.add("Android循环滚动广告条的完美实现 ");
        initBanner(imageUrlList);
        initRollNotice();
    }
    protected void initView() {
        mSearchBox.setOnClickListener(this);
//        button.setOnClickListener(this);

        drawable = getResources().getDrawable(R.drawable.search_icon);
        drawable.setBounds(5, 0, 30, 30);
        mSearchBox.setCompoundDrawables(drawable, null, null, null);
        imagesharing.setOnClickListener(this);
        imageculture.setOnClickListener(this);
        imageproduct.setOnClickListener(this);
        ivTicket.setOnClickListener(this);
        ivRed.setOnClickListener(this);
        ivmore.setOnClickListener(this);
    }

    protected void findViewById() {
        // TODO Auto-generated method stub
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        mSearchBox = (EditText) findViewById(R.id.index_search_edit);
//        button=(Button)findViewById(R.id.button);
        radioFind = (LinearLayout) findViewById(R.id.iv_culture);
        radioMain = (LinearLayout) findViewById(R.id.iv_rescue);
        radioNews = (LinearLayout) findViewById(R.id.iv_resource);
        radioMine = (LinearLayout) findViewById(R.id.iv_balacklist);
        setHeight(radioFind);
        setHeight(radioMain);
        setHeight(radioNews);
        setHeight(radioMine);
        ivTicket = (XCRoundRectImageView) findViewById(R.id.iv_ticket);
        ivRed = (XCRoundRectImageView) findViewById(R.id.iv_red);
        ivTicket.setImageResource(R.drawable.ticket);
        ivRed.setImageResource(R.drawable.red_packet);
        imagesharing= (ImageButton) findViewById(R.id.iv_sharing_resource);
        imageculture= (ImageButton) findViewById(R.id.iv_lishi_culture);
        imageproduct= (ImageButton) findViewById(R.id.imageButton);
        framenotice= (LinearLayout) findViewById(R.id.homepage_notice_ll);
        ivmore= (ImageView) findViewById(R.id.iv_more);
//        drawMain.setBounds(0, 0, 60, 60);
//        drawNews.setBounds(0, 0, 60, 60);
//        drawMine.setBounds(0, 0, 60, 60);
    }
    private void initRollNotice() {
        FrameLayout main_notice = (FrameLayout) findViewById(R.id.main_notice);
        main_notice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HeadlineActivity.class));
            }
        });
        notice_parent_ll = (LinearLayout) getLayoutInflater().inflate(
                R.layout.layout_notice, null);
        notice_ll = ((LinearLayout) this.notice_parent_ll
                .findViewById(R.id.homepage_notice_ll));
        notice_vf = ((ViewFlipper) this.notice_parent_ll
                .findViewById(R.id.homepage_notice_vf));
        main_notice.addView(notice_parent_ll);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moveNext();
                        Log.d("Task", "下一个");
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 4000);
    }

    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(this, R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(this, R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    private void setView(int curr, int next) {

        View noticeView = getLayoutInflater().inflate(R.layout.notice_item,
                null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
//        notice_tv.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                Bundle bundle = new Bundle();
//                bundle.putString("url", linkUrlArray.get(mCurrPos));
//                bundle.putString("title", titleList.get(mCurrPos));
//                Intent intent = new Intent(MainActivity.this,
//                        BaseWebActivity.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;

    }
    private void initBanner(ArrayList<String> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(this, imageUrlList,
                linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button:
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                break;
//            case R.id.main_notice:
//                startActivity(new Intent(this,HeadlineActivity.class));
//                break;
            case R.id.iv_ticket:
                startActivity(new Intent(this,TicketActivity.class));
                break;
            case R.id.iv_red:
                startActivity(new Intent(this,RedenvelopeActivity.class));
                break;
            case R.id.iv_lishi_culture:
                startActivity(new Intent(this,CultureActivity.class));
                break;
            case R.id.iv_sharing_resource:
                startActivity(new Intent(this,SharingResourceActivity.class));
                break;
            case R.id.imageButton:
                startActivity(new Intent(this,ProductBaseActivity.class));
                break;
            case R.id.index_search_edit:
                openActivity(SearchActivity.class);
                break;
            case R.id.iv_more:
                openActivity(FindActivity.class);
                break;
            default:
                break;
        }
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//        button=(Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    case R.id.button:
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                        break;
//                    default:break;
//                }
//            }
//        });
//    }


    private void setHeight(LinearLayout linearLayout){
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.height = (dm.widthPixels - 36) / 4;
    }

}
