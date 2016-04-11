package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.lijiadayuan.lishijituan.bean.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends Activity implements OnClickListener {
    private ListView catergory_listview;
    private List<Address> addressList;
    private Button button;
    private TextView tvTitle;
    private ImageView imageback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        findViewById();
        initView();
    }

    protected void findViewById() {
        button = (Button) findViewById(R.id.id_address);
        tvTitle = (TextView) findViewById(R.id.text_title);
        imageback = (ImageView) findViewById(R.id.iv_back);
        catergory_listview = (ListView) this.findViewById(R.id.address_listview);
        addressList = new ArrayList<Address>();

        addressList.add(new Address(
                "001", "张志强强", "13112345678", 1, 1, 1, "月坛南街", 1, 0, "000001"
        ));
        addressList.add(new Address(
                "002", "张志强强", "13112345678", 1, 1, 1, "月坛南街", 1, 0, "000001"
        ));
        addressList.add(new Address(
                "003", "张志强强", "13112345678", 1, 1, 1, "月坛南街", 1, 0, "000001"
        ));

        catergory_listview.setAdapter(new AddressAdapter(addressList));
    }
    protected void initView(){
        button.setOnClickListener(this);

        tvTitle.setText("地址管理");

        imageback.setOnClickListener(this);
    }

    private class AddressAdapter extends BaseAdapter {

        private List<Address> addresses;
        private LayoutInflater layoutInflater;

        public AddressAdapter(List<Address> addresses) {

            this.addresses = addresses;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return addresses.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return addresses.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @SuppressWarnings("null")
        @Override
        public View getView(int i, View convertView, ViewGroup parent) {

            ViewHolder holder = new ViewHolder();

            layoutInflater = LayoutInflater.from(AddressActivity.this);

            //组装数据
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.activity_address_item, null);
                holder.name = (TextView) convertView.findViewById(R.id.iv_name);
                holder.iphone = (TextView) convertView.findViewById(R.id.iv_iphone);
                holder.address = (TextView) convertView.findViewById(R.id.iv_address);
                //使用tag存储数据
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Address address = addresses.get(i);
            holder.name.setText(address.getAddName());
            holder.iphone.setText(address.getAddPhone());
            holder.address.setText(address.getAddProvince() + address.getAddCity() + address.getAddArea() + address.getAddDetail());
            //	holder.title.setText(array[position]);

            return convertView;

        }


    }


//    // 适配显示的图片数组
//    private String[] mTitlename = {};
//    //给照片添加文字显示(Title)
//    private Integer[] mTitleiphone = { 111111111, 222222222, 333333333, 444444444, 5555555,
//            666666666, 77777777, 88888888 };
//
//    private String[] mTitleaddress={"北京市西城区月坛南路69号", "北京市西城区月坛南路69号","北京市西城区月坛南路69号", "北京市西城区月坛南路69号", "摄影摄像/数码配件",
//            "北京市西城区月坛南路69号", "北京市西城区月坛南路69号", "北京市西城区月坛南路69号"};


    public static class ViewHolder {
        TextView name;
        TextView iphone;
        TextView address;
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
}
