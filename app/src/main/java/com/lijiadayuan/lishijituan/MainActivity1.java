package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lijiadayuan.lishijituan.adapter.ImagePagerAdapter;
import com.lijiadayuan.lishijituan.adapter.PictureAdpter2;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.LocationService;
import com.lijiadayuan.lishijituan.view.AddressDialog;
import com.lijiadayuan.lishijituan.view.XCRoundRectImageView;
import com.lijiadayuan.lishijituan.view.photoscorrect;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity1 extends BaseActivity implements OnClickListener{
    //轮播图
    private ViewFlow mViewFlow;
    //红点
    private CircleFlowIndicator mFlowIndicator;
    //头条
    private ViewFlipper notice_vf;
    //李氏福利社商品
    private GridView mGvGoods;
    //李氏福利商品数据
    private int[] mGiftGoodslist = {R.drawable.byh_1,R.drawable.rick_cooker,
            R.drawable.bodybuilding,R.drawable.sj_4,R.drawable.lsj_5,R.drawable.nscs_6};
    private ArrayList<Integer> mGiftGoodsList;
    //卡票和红包
    private ImageView mIvTicket,mIvRed;
    //李氏文化，陆空救援，共享资源，李氏黑名单
    private ImageView mIvLeeCulture,mIvLuKongRescus,mIvShareRescous,mIvLeeBlackList;
    //保存当前地址
    private String mCurrentAddress;

    private AddressDialog dialog;




    private ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray= new ArrayList<String>();
    ArrayList<String> titleList= new ArrayList<String>();
    private int mCurrPos;
    private LinearLayout radioMain, radioFind, radioNews, radioMine;
    private XCRoundRectImageView ivTicket, ivRed;
    private ImageButton imagesharing,imageculture,imageproduct;
    private ImageView ivmore;
    private LocationService locationService;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        initView();
        //初始化轮播图数据
        imageUrlList.add("http://b.hiphotos.baidu.com/image/pic/item/d01373f082025aaf95bdf7e4f8edab64034f1a15.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/image/pic/item/6159252dd42a2834da6660c459b5c9ea14cebf39.jpg");
        imageUrlList.add("http://d.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd976427f6c3901213fb80e911c.jpg");
        imageUrlList.add("http://g.hiphotos.baidu.com/image/pic/item/b3119313b07eca80131de3e6932397dda1448393.jpg");
        //轮播图
        initBanner(imageUrlList);

        //初始化福利商品数据
        PictureAdpter2 mAdpter = new PictureAdpter2(MainActivity1.this,mGiftGoodslist,R.layout.item_giftgoods);
        mGvGoods.setAdapter(mAdpter);



        linkUrlArray.add("");
        linkUrlArray.add("");
        linkUrlArray.add("");
        linkUrlArray.add("");

        titleList.add("常见Android进阶笔试题");
        titleList.add("GridView之仿支付宝钱包首页");
        titleList.add("仿手机QQ网络状态条的显示与消失 ");
        titleList.add("Android循环滚动广告条的完美实现 ");
        //大院头条
        initRollNotice();
        
        setListener();
    }

    private void setListener() {
        mGvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //跳商品详情
                startProductBaseActivity();
            }
        });
    }

    protected void initView() {
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        findViewById(R.id.iv_address).setOnClickListener(this);
        findViewById(R.id.iv_message).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        notice_vf = (ViewFlipper) findViewById(R.id.homepage_notice_vf);
        findViewById(R.id.iv_more).setOnClickListener(this);
        mGvGoods = (GridView) findViewById(R.id.mGridView);
        mIvTicket = (ImageView) findViewById(R.id.iv_ticket);
        mIvRed = (ImageView) findViewById(R.id.iv_red);
        mIvTicket.setOnClickListener(this);
        mIvRed.setOnClickListener(this);
        findViewById(R.id.lee_culture).setOnClickListener(this);
        findViewById(R.id.luKongRescus).setOnClickListener(this);
        findViewById(R.id.shareResources).setOnClickListener(this);
        findViewById(R.id.lee_black_list).setOnClickListener(this);
        findViewById(R.id.head_message).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);

      //  imagesharing.setOnClickListener(this);
      //  imageculture.setOnClickListener(this);
      //  imageproduct.setOnClickListener(this);
      //  ivTicket.setOnClickListener(this);
      //  ivRed.setOnClickListener(this);
        //ivmore.setOnClickListener(this);
    }

    private void initRollNotice() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moveNext();
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

        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;

    }
    //初始化轮播图
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


    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.hasAddr()){
                mCurrentAddress = bdLocation.getAddrStr();
                Log.i("main",bdLocation.getAddrStr());
                locationService.unregisterListener(mListener); //注销掉监听
                locationService.stop(); //停止定位服务

                dialog = new AddressDialog(MainActivity1.this, R.style.protocol_dialog,mCurrentAddress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_message:
                startActivity(new Intent(MainActivity1.this,HeadlineActivity.class));
                break;

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
            case R.id.lee_culture:
                startActivity(new Intent(this,CultureActivity.class));
                break;
            case R.id.shareResources:
                startActivity(new Intent(this,SharingResourceActivity.class));
                break;
            case R.id.search:
                openActivity(SearchActivity.class);
                break;
            case R.id.iv_more:
                openActivity(MoreActivity.class);
                break;

            case R.id.iv_address:
                locationService = ((LeeApplication) getApplication()).locationService;
                locationService.registerListener(mListener);
                //注册监听
                int type = getIntent().getIntExtra("from", 0);
                if (type == 0) {
                    locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                } else if (type == 1) {
                    locationService.setLocationOption(locationService.getOption());
                }
                locationService.start();
                break;

            default:
                break;
        }
    }

    private void setHeight(LinearLayout linearLayout){
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.height = (dm.widthPixels - 36) / 4;
    }
    private void startProductBaseActivity(){
        ProductViewBean mProductViewBean = new ProductViewBean();
        mProductViewBean.setGoodsPrice("222");
        mProductViewBean.setGoodsNum("10");
        mProductViewBean.setGoodsName("浪漫八音盒");
        mProductViewBean.setGoodsInfoUrl("www.baidu.com");
        Intent mIntent = new Intent(this,ProductBaseActivity.class);
        mIntent.putExtra(KeyConstants.IntentPageKey.GoodsPageType,ProductBaseActivity.GIFT_GOODS);
        mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType,mProductViewBean);
        startActivity(mIntent);

    }
}