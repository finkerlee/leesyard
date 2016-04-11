package com.lijiadayuan.lishijituan;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by lifanqiao on 16/3/17.
 */
public class SearchActivity extends Activity{
    private Drawable drawable = null;
    private TextView cancel;
    private AutoCompleteTextView acTextView;
    private String[] res={"李氏红酒","李氏文化","李氏黑名单","李氏集团","李氏陆空救援","李氏酒厂"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        cancel= (TextView)findViewById(R.id.search_btn_back);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        acTextView= (AutoCompleteTextView) findViewById(R.id.search_et_input);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,res);
        acTextView.setAdapter(adapter);
        drawable = getResources().getDrawable(R.drawable.search_icon);
        drawable.setBounds(5, 0, 40, 40);
        acTextView.setCompoundDrawables(drawable, null, null, null);


    }
}
