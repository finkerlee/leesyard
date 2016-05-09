package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
import com.lijiadayuan.lishijituan.http.UrlConstants;
import com.lijiadayuan.lishijituan.utils.JsonParseUtil;
import com.lijiadayuan.lishijituan.utils.KeyConstants;
import com.lijiadayuan.lishijituan.utils.UsersUtil;

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
    private ArrayList<Addresses> AList;
    private QuickAdapter<Addresses> adapter;
    private RequestQueue mQueue;
    private String currentMode = "";

    public static final String TAG = "AddressActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        mSharedPreferences = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        currentMode = getIntent().getStringExtra(KeyConstants.IntentPageKey.AddressMode);
        initView();
        initdata();
    }

    private void initView() {
        button = (Button) findViewById(R.id.id_address);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        catergory_listview = (ListView) this.findViewById(R.id.address_listview);
        button.setOnClickListener(this);
        tvTitle.setText("地址管理");
        imageback.setOnClickListener(this);
        AList = new ArrayList<>();
        adapter = new QuickAdapter<Addresses>(AddressActivity.this, R.layout.activity_address_item, AList) {
            @Override
            protected void convert(final BaseAdapterHelper helper, final Addresses item) {
                helper.setText(R.id.iv_name, item.getAddName());
                helper.setText(R.id.iv_iphone, item.getAddPhone());
                final String pca = UsersUtil.getPCA(item.getAddArea());
                helper.setText(R.id.iv_address, pca + item.getAddDetail());
                helper.getView().findViewById(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mIntent = new Intent(AddressActivity.this, ShippingAddressActivity.class);
                        mIntent.putExtra("addressType", ShippingAddressActivity.UPDATA_ADDRESS);
                        mIntent.putExtra("address", item);
                        startActivityForResult(mIntent, ShippingAddressActivity.UPDATA_ADDRESS);
                    }
                });

                helper.getView().findViewById(R.id.delete).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("main", helper.getPosition() + "");
                        new AlertDialog.Builder(AddressActivity.this)
                                .setTitle("删除地址").setMessage("确定删除地址").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteAddress(item.getAddId(), helper.getPosition());
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();

                    }
                });
                helper.getView().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentMode.equals(KeyConstants.IntentPageValues.forResult)) {
                            Intent intent = new Intent(AddressActivity.this, ProductBaseActivity.class);
                            intent.putExtra(KeyConstants.IntentPageValues.Address, item);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });
            }
        };
        catergory_listview.setAdapter(adapter);
//        catergory_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (currentMode.equals(KeyConstants.IntentPageValues.forResult)){
//                    Intent intent = new Intent(AddressActivity.this,ProductBaseActivity.class);
//                    intent.putExtra(KeyConstants.IntentPageValues.Address,AList.get(i));
//                    setResult(RESULT_OK,intent);
//                    finish();
//                }
//
//            }
//        });

    }

    private void initdata() {
        //请求队列
        mQueue = app.getRequestQueue();
        selectAddress();
    }

    private void selectAddress() {
        // 创建一个字符串请求
        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants.USER_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                JsonObject mJsonObject = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObject)) {
                    adapter.clear();
                    adapter.addAll(JsonParseUtil.toListByJson(mJsonObject.get("response_data")
                            .getAsJsonArray(), Addresses.class));
                    adapter.notifyDataSetChanged();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> addressParams = new HashMap<String, String>();
                addressParams.put("userId", UsersUtil.getUserId(AddressActivity.this));
                return addressParams;
            }
        };
        // 将请求添加到请求队列中(即发送请求)
        mQueue.add(request);
    }

    private void deleteAddress(final String addId, final int position) {
        StringRequest mDeleteQequest = new StringRequest(Request.Method.POST, UrlConstants.DELETE_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonObject mJsonObj = JsonParseUtil.getJsonByString(response).getAsJsonObject();
                if (JsonParseUtil.isSuccess(mJsonObj)) {
                    String result = mJsonObj.get("response_data").getAsString();
                    if ("1".equals(result)) {
                        Toast.makeText(AddressActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                        adapter.remove(position);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(AddressActivity.this, "删除失败", Toast.LENGTH_LONG).show();
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
                params.put("addId", addId);
                return params;
            }
        };
        mQueue.add(mDeleteQequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.id_address:
                Intent intent = new Intent(this, ShippingAddressActivity.class);
                intent.putExtra("addressType", ShippingAddressActivity.ADD_ADDRESS);
                startActivityForResult(intent, ShippingAddressActivity.ADD_ADDRESS);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            selectAddress();

        }
    }


}
