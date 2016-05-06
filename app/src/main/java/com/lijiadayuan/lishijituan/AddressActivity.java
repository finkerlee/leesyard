package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.bean.Addresses;
import com.lijiadayuan.lishijituan.bean.Celebrities;
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.model.CityModel;
import com.lijiadayuan.model.DistrictModel;
import com.lijiadayuan.model.ProvinceModel;
import com.lijiadayuan.service.XmlParserHandler;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class AddressActivity extends BaseActivity implements OnClickListener {
    private ListView catergory_listview;
    private Button button;
    private TextView tvTitle;
    private ImageView imageback;
    private SharedPreferences mSharedPreferences;
    ArrayList<Addresses> AList;

    public static final String TAG = "AddressActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        findViewById();
        initView();
        initdata();
    }

    private void findViewById() {
        button = (Button) findViewById(R.id.id_address);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        catergory_listview = (ListView) this.findViewById(R.id.address_listview);
    }
    private void initView(){
        button.setOnClickListener(this);
        tvTitle.setText("地址管理");
        imageback.setOnClickListener(this);
    }
    private void initdata(){
        //请求队列
        RequestQueue mQueue = app.getRequestQueue();
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.USER_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)){
                    AList =  JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(),Addresses.class);
                    QuickAdapter<Addresses> adapter=new QuickAdapter<Addresses>(AddressActivity.this,R.layout.activity_address_item,AList) {
                        @Override
                        protected void convert(BaseAdapterHelper helper, final Addresses item) {
                            helper.setText(R.id.iv_name, item.getAddName());
                            helper.setText(R.id.iv_iphone,item.getAddPhone());
                            final String pca = getPCA(item.getAddArea());
                            helper.setText(R.id.iv_address, pca + item.getAddDetail());
                            helper.getView().findViewById(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent mIntent = new Intent(AddressActivity.this,ShippingAddressActivity.class);
                                    Addresses address = item;
                                    address.setAddArea(pca);
                                    mIntent.putExtra("address", address);
                                    mIntent.putExtra("addressname",address);
                                    mIntent.putExtra("addressphone",address);
                                    mIntent.putExtra("addressdetail",address);
                                    startActivity(mIntent);
                                }
                            });
                        }
                    };
                    catergory_listview.setAdapter(adapter);
                }
            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  addressParams= new HashMap<String,String>();
                String userId = mSharedPreferences.getString(KeyConstants.UserInfoKey.userId, "");
                addressParams.put("userId",userId);
                return addressParams;
            }
        };
        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.id_address:
                startActivity(new Intent(this, ShippingAddressActivity.class));
                break;
            default:break;
        }
    }

    /**
     * 通过区id获取省市区名称并返回
     * @param areaId
     * @return
     */
    public String getPCA(String areaId){
        int province = Integer.parseInt(areaId.substring(0,2)) - 1;
        Log.i(TAG, province+"");
        int city = Integer.parseInt(areaId.substring(2,4)) - 1;
        Log.i(TAG, city+"");
        int area = Integer.parseInt(areaId.substring(4,6)) - 1;
        Log.i(TAG, area + "");
        try {
            InputStream input = getAssets().open("pca.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = XmlParserHandler.getInstance();
            parser.parse(input, handler);
            input.close();
            return handler.getDataList().get(province).getName() +
                    handler.getDataList().get(province).getCityList().get(city).getName()+
                    handler.getDataList().get(province).getCityList().get(city).getDistrictList().get(area).getName();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return "";
    }
}
