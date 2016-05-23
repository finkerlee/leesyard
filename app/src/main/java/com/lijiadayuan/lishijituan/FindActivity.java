package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.google.gson.JsonObject;
import com.lijiadayuan.lishijituan.adapter.FindAdpter;
import com.lijiadayuan.lishijituan.adapter.FindSpacesItemDecoration;
import com.lijiadayuan.lishijituan.adapter.ViewPageHolder;
import com.lijiadayuan.lishijituan.bean.Product;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.view.AddressDialog;

import java.util.Arrays;
import java.util.List;
public class FindActivity extends BaseActivity implements View.OnClickListener{
    //瀑布流
    private RecyclerView mRecyclerView;//image
    private TextView tvTitle;
    //商品数据集合
    private List<Product> mList;
    private AddressDialog dialog;
    //轮播图控件
    private ConvenientBanner mConvenientBanner;
    //轮播图的集合
    private List<String> networkImages;

    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        //初始化view
        initView();
        //初始化数据
        initData();
        //设置监听事件
        setListener();
    }
    //设置监听事件
    private void setListener() {
        mConvenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                 //TODO 设置bananer的点击事件
            }
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.iv_address).setOnClickListener(this);
        findViewById(R.id.iv_messsage).setOnClickListener(this);
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        //设置RecyclerView 为瀑布流
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }

    //加载数据
    private void initData() {
        // 创建请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    mList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Product.class);
                    //根据数据去加载banaer
                    initBanaer();

                    FindAdpter mAdpter = new FindAdpter(FindActivity.this,mList);
                    mRecyclerView.setAdapter(mAdpter);

                    //设置间距
                    FindSpacesItemDecoration decoration=new FindSpacesItemDecoration(16);
                    mRecyclerView.addItemDecoration(decoration);
//                    QuickAdapter<Product> mAdpter = new QuickAdapter<Product>(FindActivity.this,R.layout.image,mList) {
//                        @Override
//                        protected void convert(BaseAdapterHelper helper, Product item) {
//                           SimpleDraweeView msp = (SimpleDraweeView) helper.getView().findViewById(R.id.itemImage);
//                            msp.setImageURI(Uri.parse(item.getProThumb()));
////                            AutoUtils.autoSize(helper.getView());
//                        }
//                    };
//
//                    gridView.setAdapter(mAdpter);
//                    gridView.setOnItemClickListener(new OnItemClickListener() {
//                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                            Intent mIntent = new Intent(FindActivity.this,ProductBaseActivity.class);
//                            ProductViewBean mProductViewBean = ProductViewBean.getProductViewBean(mList.get(position), ProductBaseActivity.BUY_GOODS);
//                            Log.i("main",mProductViewBean.getGoodsType()+"");
//                            mIntent.putExtra(KeyConstants.IntentPageValues.productViewBeanType, mProductViewBean);
//                            startActivity(mIntent);
//                        }
//                    });
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);

    }
    //初始化banaer
    private void initBanaer(){
        networkImages = Arrays.asList(images);
        mConvenientBanner.setPages(new CBViewHolderCreator<ViewPageHolder>() {
            @Override
            public ViewPageHolder createHolder() {
                return new ViewPageHolder();
            }
        },networkImages).setPageIndicator(new int[]{R.drawable.carousel_dot_indicator_state_select,R.drawable.carousel_dot_indicator_state_normal});
    }


    @Override
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.iv_address:
               dialog = new AddressDialog(FindActivity.this, R.style.protocol_dialog,HomeActivity.mCurrentAddress);
               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dialog.show();
               break;
           case R.id.iv_messsage:
               Intent mIntent = new Intent(FindActivity.this,MymessageActivity.class);
               startActivity(mIntent);
               break;
       }
    }

    @Override
    protected void onResume() {
//        mHandler.removeMessages(1);
//        mHandler.sendEmptyMessageDelayed(1,10000);
        //开始自动翻页
        mConvenientBanner.startTurning(5000);

        super.onResume();
    }

    @Override
    protected void onPause() {
        //停止翻页
        mConvenientBanner.stopTurning();
        super.onPause();
    }

}

