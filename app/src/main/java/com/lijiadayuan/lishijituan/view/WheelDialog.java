package com.lijiadayuan.lishijituan.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.lijiadayuan.lishijituan.R;
import com.lijiadayuan.model.CityModel;
import com.lijiadayuan.model.DistrictModel;
import com.lijiadayuan.model.ProvinceModel;
import com.lijiadayuan.service.XmlParserHandler;
import com.lijiadayuan.widget.OnWheelChangedListener;
import com.lijiadayuan.widget.WheelView;
import com.lijiadayuan.widget.adapters.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by lifanqiao on 16/3/25.
 */
public class WheelDialog extends Dialog implements OnWheelChangedListener {

    private AssetManager asset;

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView medtConfirm, tvClose;
    private Context context;

    public WheelDialog(Context mContext,int resId, AssetManager assetManager, IRefreshUI iRefreshUI) {
        super(mContext, resId);
//        setCancelable(false);
        this.context = mContext;
        this.iRefreshUI = iRefreshUI;
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        this.asset = assetManager;
//        window.setWindowAnimations(R.style.selected);
//        this.clickListener = clickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        setUpViews();
        setUpListener();
        setUpData();
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        medtConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                iRefreshUI.refresh(mCurrentProviceName + mCurrentCityName + mCurrentDistrictName, areaId);
            }
        });
    }

    /**
     * 定义接口,用于activity实现回调方法更新UI
     */
    public interface IRefreshUI{

        public void refresh(String info, String areaId);
    }

    private IRefreshUI iRefreshUI;

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    protected Map<String, String> mCityIdMap = new HashMap<>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省份id
     */
    protected String proId;

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;

    /**
     * 当前城市id
     */
    protected String cityId;

    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;

    /**
     * 当前区id
     */
    protected String areaId;

    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName ="";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode ="";


    private List<ProvinceModel> provinceList = null;
    private List<CityModel> cityList = null;
    private List<DistrictModel> districtList = null;

    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas()
    {

        try {
            InputStream input = asset.open("pca.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList!= null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                proId = provinceList.get(0).getId();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList!= null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    cityId = cityList.get(0).getId();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    areaId = districtList.get(0).getId();
                    mCurrentDistrictName = districtList.get(0).getName();
                }
            }
            //*/
//            mProvinceDatas = new String[provinceList.size()];
//            for (int i=0; i< provinceList.size(); i++) {
//                // 遍历所有省的数据
//                mProvinceDatas[i] = provinceList.get(i).getName();
//                List<CityModel> cityList = provinceList.get(i).getCityList();
//                String[] cityNames = new String[cityList.size()];
//                for (int j=0; j< cityList.size(); j++) {
//                    // 遍历省下面的所有市的数据
//                    cityNames[j] = cityList.get(j).getName();
//                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
//                    String[] distrinctNameArray = new String[districtList.size()];
//                    mCityIdMap.put(cityList.get(j).getName(),cityList.get(j).getId());
//                    for (int k=0; k<districtList.size(); k++) {
//                        // 遍历市下面所有区/县的数据
//                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getId(), districtList.get(k).getName());
//                        // 区/县对于的邮编，保存到mZipcodeDatasMap
//                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getId());
//                        distrinctNameArray[k] = districtModel.getName();
//                    }
//                    // 市-区/县的数据，保存到mDistrictDatasMap
//                    mDistrictDatasMap.put(cityList.get(j).getId(), distrinctNameArray);
//                }
//                // 省-市的数据，保存到mCitisDatasMap
//                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
//            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = districtList.get(newValue).getName();
            areaId = districtList.get(newValue).getId();
        }
    }
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
//        String test = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
//        mCurrentCityName = mCityIdMap.get(test);
//        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
//        if (areas == null) {
//            areas = new String[] { "" };
//        }
//        mViewDistrict.setViewAdapter(new ArrayWheelAdapter(context, areas));
//        mViewDistrict.setCurrentItem(0);
//        mCurrentDistrictName = areas[0];
        mCurrentCityName = cityList.get(pCurrent).getName();
        districtList = cityList.get(pCurrent).getDistrictList();
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter(context, districtList));
        mViewDistrict.setCurrentItem(0);

    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
//        mCurrentProviceName = mProvinceDatas[pCurrent];
//        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
//        if (cities == null) {
//            cities = new String[] { "" };
//        }
//        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
//        mViewCity.setCurrentItem(0);
//        updateAreas();
        mCurrentProviceName = provinceList.get(pCurrent).getName();
        cityList = provinceList.get(pCurrent).getCityList();
        mViewCity.setViewAdapter(new ArrayWheelAdapter(context, cityList));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        medtConfirm = (TextView) findViewById(R.id.iv_finisih);
        tvClose = (TextView) findViewById(R.id.tv_close);


    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter(context, provinceList));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }
}
