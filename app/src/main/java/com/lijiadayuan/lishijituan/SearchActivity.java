package com.lijiadayuan.lishijituan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.lijiadayuan.lishijituan.utils.LocalUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lifanqiao on 16/3/17.
 */
public class SearchActivity extends BaseActivity {
    private TextView mTvSearch;
    private EditText acTextView;
    private LocalUtils mLocalUtils;
    private ArrayList<String> mHistoryData;
    private ListView mLvHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mLocalUtils = new LocalUtils(LeeApplication.searchPath);
        mHistoryData = (ArrayList<String>) mLocalUtils.read();
        acTextView= (EditText) findViewById(R.id.search_et_input);
        mLvHistory = (ListView) findViewById(R.id.history_list);

        QuickAdapter<String> mAdpter = new QuickAdapter<String>(SearchActivity.this,R.layout.item_search_history,mHistoryData) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_search,item);
            }
        };

        mLvHistory.setAdapter(mAdpter);
        mTvSearch= (TextView)findViewById(R.id.search_btn_back);
        mTvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acTextView.getText().toString().isEmpty()){
                    Toast.makeText(SearchActivity.this,"请输入关键字",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("keyword",acTextView.getText().toString());
                startActivity(intent);
                mHistoryData.add(acTextView.getText().toString());
                mLocalUtils.put(mHistoryData);

            }
        });

        mLvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("keyword",mHistoryData.get(i));
                startActivity(intent);
            }
        });
    }


}
