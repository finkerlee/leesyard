package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.adapter.ImagePagerAdapter;
import com.lijiadayuan.lishijituan.adapter.ViewPageAdapter;
import com.lijiadayuan.lishijituan.bean.AdvView;
import com.lijiadayuan.lishijituan.bean.Benefits;
import com.lijiadayuan.lishijituan.bean.ProductViewBean;
import com.lijiadayuan.lishijituan.bean.Topics;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.LocationService;
import com.lijiadayuan.lishijituan.utils.UsersUtil;
import com.lijiadayuan.lishijituan.view.AddressDialog;
import com.lijiadayuan.lishijituan.view.XCRoundRectImageView;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity implements OnClickListener {

    //轮播图
    private ViewPager mViewFlow;
    //红点
    private CircleFlowIndicator mFlowIndicator;
    //头条
    private ViewFlipper notice_vf;
    //李氏福利社商品
    private GridView mGvGoods;
    //卡票和红包
    private ImageView mIvTicket, mIvRed;

    private AddressDialog dialog;


    ArrayList<String> titleList = new ArrayList<String>();
    private int mCurrPos;
    //首页轮播图的数据
    private ArrayList<AdvView> mAdvViewData;
    //头条数据
    private List<Topics> mTopicsData;
    //首页福利商品的数据
    private List<Benefits> mBenefitsData;

    private ProductViewBean mProductViewBean;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private RelativeLayout mViewPagerLayout;

    private Handler mHandler;

    private ViewPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //当前无用户接触时，自动播放轮播图
                if (mViewFlow != null && !mAdapter.isTouched()) {
                    int targetIndex = mViewFlow.getCurrentItem() + 1;
                    if (targetIndex >= mAdapter.getCount()) {
                        targetIndex = 0;
                    }
                    mViewFlow.setCurrentItem(targetIndex);
                }
                sendMessageDelayed(mHandler.obtainMessage(1), 10000);
                super.handleMessage(msg);
            }
        };
        initView();
        //初始化数据
        initData();




        setListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 初始化数据
     */

    private void initData() {
        RequestQueue mRequestQueue = app.getRequestQueue();
        StringRequest mRequest = new StringRequest(Request.Method.POST, UrlConstants.MAIN_PAGE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJson = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJson)) {
                    if (mJson.has("adv")) {
                        JsonArray mJsonAdvArray = mJson.get("adv").getAsJsonArray();
                        if (mJsonAdvArray.size() > 0) {
                            mAdvViewData = JsonParseUtil.toListByJson(mJsonAdvArray, AdvView.class);
                            //轮播图
                            initBanner(mAdvViewData);
                        }
                    }
                    if (mJson.has("topic")) {
                        JsonArray mJsonTopArray = mJson.get("topic").getAsJsonArray();
                        if (mJsonTopArray.size() > 0) {
                            mTopicsData = JsonParseUtil.toListByJson(mJsonTopArray, Topics.class);
                            for (Topics mTopics : mTopicsData) {
                                titleList.add(mTopics.getTopTitle());
                            }
                            //大院头条
                            initRollNotice();
                        }
                    }
                    if (mJson.has("benefit")) {
                        JsonArray mJsonBenefitArray = mJson.get("benefit").getAsJsonArray();
                        if (mJsonBenefitArray.size() > 0) {
                            mBenefitsData = JsonParseUtil.toListByJson(mJsonBenefitArray, Benefits.class);
                            QuickAdapter<Benefits> mAdpter = new QuickAdapter<Benefits>(MainActivity.this, R.layout.item_giftgoods, mBenefitsData) {
                                @Override
                                protected void convert(BaseAdapterHelper helper, Benefits item) {
                                    SimpleDraweeView mSimpleDraweeView = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage);
                                    mSimpleDraweeView.setImageURI(Uri.parse(item.getBenThumb()));
                                   // AutoUtils.autoSize(helper.getView());
                                }
                            };
                            mGvGoods.setAdapter(mAdpter);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("advNum", 5 + "");
                params.put("topNum", 10 + "");
                params.put("benNum", 6 + "");
                params.put("userId", UsersUtil.getUserId(MainActivity.this));
                return params;
            }
        };
        mRequestQueue.add(mRequest);
    }

    private void setListener() {
        mGvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //跳商品详情
                mProductViewBean = new ProductViewBean();
                Benefits mBenefits = mBenefitsData.get(i);
                mProductViewBean.setGoodsId(mBenefits.getBenId());
                mProductViewBean.setGoodsPrice(mBenefits.getBenPrice() + "");
                mProductViewBean.setGoodsName(mBenefits.getBenName());
                mProductViewBean.setGoodsInfoUrl(UrlConstants.SHOPPING_INFO + mBenefits.getBenId());
                mProductViewBean.setGoodsSpec(mBenefits.getBenSpec());
                String[] mBenData = mBenefits.getBenImg().split(",");

                ArrayList<String> mlist = new ArrayList<String>();
                for (String s : mBenData) {
                    mlist.add(s);
                }
                mProductViewBean.setPicList(mlist);

                mlist = new ArrayList<String>();
                String[] mBenRemark = mBenefits.getBenRemark().split(";");
                for (String s : mBenRemark) {
                    mlist.add(s);
                }
                mProductViewBean.setGoodsIntro(mlist);
                mProductViewBean.setGoodsVerify(mBenefits.getBenVerify());
                mProductViewBean.setGoodsNum(1 + "");
                mProductViewBean.setGoodsType(ProductBaseActivity.GIFT_GOODS);
                mProductViewBean.setGoodStatus(mBenefits.getBaStatus());
                mProductViewBean.setGoodsOtherName(mBenefits.getBenSubtitle());
                Intent mIntent = new Intent(MainActivity.this, ProductBaseActivity.class);
                mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
                startActivity(mIntent);
            }
        });
    }

    protected void initView() {
        mViewFlow = (ViewPager) findViewById(R.id.viewflow);
        findViewById(R.id.iv_address).setOnClickListener(this);
        findViewById(R.id.iv_message).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.iv_message).setOnClickListener(this);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
        notice_vf = (ViewFlipper) findViewById(R.id.homepage_notice_vf);
        findViewById(R.id.iv_more).setOnClickListener(this);
        mGvGoods = (GridView) findViewById(R.id.mGridView);
        mIvTicket = (ImageView) findViewById(R.id.iv_ticket);
        mIvRed = (ImageView) findViewById(R.id.iv_red);
        mViewPagerLayout = (RelativeLayout) findViewById(R.id.mLayoutViewPage);
        mIvTicket.setOnClickListener(this);
        mIvRed.setOnClickListener(this);
        findViewById(R.id.lee_culture).setOnClickListener(this);
        findViewById(R.id.luKongRescus).setOnClickListener(this);
        findViewById(R.id.shareResources).setOnClickListener(this);
        findViewById(R.id.lee_black_list).setOnClickListener(this);
        findViewById(R.id.head_message).setOnClickListener(this);
        findViewById(R.id.search).setOnClickListener(this);
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
        timer.schedule(task, 1000, 4000);
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
    private void initBanner(ArrayList<AdvView> imageUrlList) {
        ViewGroup.LayoutParams linearParams = mViewFlow.getLayoutParams();
        ArrayList<SimpleDraweeView> mSimpleDrawViewList = new ArrayList<>();
        ArrayList<String> mStringUrlList = new ArrayList<>();
        for (AdvView mAdvView : imageUrlList){
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(MainActivity.this);
            simpleDraweeView.setLayoutParams(linearParams);
            mSimpleDrawViewList.add(simpleDraweeView);
            mStringUrlList.add(mAdvView.getAdvImg());
        }


        mAdapter = new ViewPageAdapter(mSimpleDrawViewList, mStringUrlList, new OnClickListener() {
            @Override
            public void onClick(View view) {
                AdvView mAdvView = mAdvViewData.get(mViewFlow.getCurrentItem());

                ProductViewBean mProductViewBean = new ProductViewBean();
                mProductViewBean.setGoodsPrice(mAdvView.getProPrice()+"");
                mProductViewBean.setGoodsName(mAdvView.getProName());
                mProductViewBean.setGoodsInfoUrl(UrlConstants.FINDSHOPPING_INFO + mAdvView.getProId());
                mProductViewBean.setGoodsSpec(mAdvView.getProSpec());
                mProductViewBean.setGoodsNum(mAdvView.getProStock()+"");
                mProductViewBean.setGoodsThumb(mAdvView.getProThumb());
                mProductViewBean.setGoodsPic(mAdvView.getProImg());
                mProductViewBean.setGoodsId(mAdvView.getProId());
                mProductViewBean.setGoodsOtherName(mAdvView.getProSubtitle());
                String [] pics = mAdvView.getProImg().split(",");
                ArrayList<String> mlist = new ArrayList<>();
                for (String s : pics){
                    mlist.add(s);
                }
                mProductViewBean.setGoodsType(ProductBaseActivity.BUY_GOODS);
                mProductViewBean.setPicList(mlist);

                Intent mIntent = new Intent(MainActivity.this,ProductBaseActivity.class);
                mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType,mProductViewBean);
                startActivity(mIntent);
            }
        });
        mViewFlow.setAdapter(mAdapter);
        initPointIndex(mViewPagerLayout,mStringUrlList,mViewFlow);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lijiadayuan.lishijituan/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.lijiadayuan.lishijituan/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_message:
                startActivity(new Intent(MainActivity.this, HeadlineActivity.class));
                break;

//            case R.id.button:
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                break;
//            case R.id.main_notice:
//                startActivity(new Intent(this,HeadlineActivity.class));
//                break;
            case R.id.iv_ticket:
                startActivity(new Intent(this, TicketActivity.class));
                break;
            case R.id.iv_red:
                startActivity(new Intent(this, RedenvelopeActivity.class));
                break;
            case R.id.lee_culture:
                startActivity(new Intent(this, CultureActivity.class));
                break;
            case R.id.shareResources:
                startActivity(new Intent(this, SharingResourceActivity.class));
                break;
            case R.id.search:
                openActivity(SearchActivity.class);
                break;
            case R.id.iv_more:
                openActivity(MoreActivity.class);
                break;

            case R.id.iv_address:

                dialog = new AddressDialog(MainActivity.this, R.style.protocol_dialog, HomeActivity.mCurrentAddress);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
            case R.id.iv_message:
                Intent intent = new Intent(MainActivity.this, MymessageActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void setHeight(LinearLayout linearLayout) {
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        params.height = (dm.widthPixels - 36) / 4;
    }

    @Override
    protected void onRestart() {
        //initData();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        mHandler.removeMessages(1);
        mHandler.sendEmptyMessageDelayed(1,10000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mHandler.removeMessages(1);
        super.onPause();
    }

    /**
     * 初始化下标，并设置下标变化回调
     *
     * @param view      当前全局view
     * @param imageurls 图片地址连接集合
     * @param viewPager 轮播元素
     */
    private void initPointIndex(View view, List<String> imageurls, ViewPager viewPager) {
        final LinearLayout indexParent = (LinearLayout) view.findViewById(R.id.indexpoint_parent);
        for (int i = 0; i < imageurls.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i != imageurls.size()-1)
                layoutParams.setMargins(0, 0, 18, 0);
            SimpleDraweeView imageView = new SimpleDraweeView(this);
            if (i == 0)
                imageView.setBackgroundResource(R.drawable.carousel_dot_indicator_state_select);
            else
                imageView.setBackgroundResource(R.drawable.carousel_dot_indicator_state_normal);
            indexParent.addView(imageView, layoutParams);
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //上一个选择的元素
            private SimpleDraweeView imageView;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (imageView != null)//重置上一个元素，为灰色
                    imageView.setBackgroundResource(R.drawable.carousel_dot_indicator_state_normal);
                if (imageView == null) {//如果刚展示的时候，将第一个元素换成未选择
                    SimpleDraweeView firtInitImageView = (SimpleDraweeView) indexParent.getChildAt(0);
                    firtInitImageView.setBackgroundResource(R.drawable.carousel_dot_indicator_state_normal);
                }
                //获取当前选择的元素，然后将其换成已选择
                imageView = (SimpleDraweeView) indexParent.getChildAt(position);
                imageView.setBackgroundResource(R.drawable.carousel_dot_indicator_state_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
