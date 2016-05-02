package com.lijiadayuan.lishijituan.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lijiadayuan.lishijituan.R;

/**
 * Created by lifanqiao on 16/4/21.
 */
public class AddressDialog extends Dialog {
    private TextView tvAddress;
    private View.OnClickListener mListener;
    private String address;

    public AddressDialog(Context context, int theme,String address) {
        super(context,theme);
        this.address = address;
        Window window = this.getWindow();
        mListener = (View.OnClickListener) context;
        window.setGravity(Gravity.BOTTOM);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvAddress.setText(address);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
