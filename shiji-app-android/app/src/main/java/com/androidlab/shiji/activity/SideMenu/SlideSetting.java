package com.androidlab.shiji.activity.SideMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.androidlab.shiji.R;
import com.androidlab.shiji.ui.view.ButtonView;
import com.androidlab.shiji.ui.view.SlideSwitch;

public class SlideSetting  extends AppCompatActivity{
    private ButtonView btView;

    SlideSwitch sSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btView = findViewById(R.id.btView1);
        btView.setChangeListener(new ButtonView.onChangeListener() {
            @Override
            public void onChange(boolean state) {
                Toast.makeText(SlideSetting.this, "状态:"+state, Toast.LENGTH_SHORT).show();
            }
        });

//        sSwitch = (SlideSwitch) this.findViewById(R.id.slide_switch);
//        sSwitch.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener(){
//
//            @Override
//            public void onStateChanged(boolean state) {
//                // TODO Auto-generated method stub
//                if(true == state)
//                {
//                    Toast.makeText(SlideSetting.this, "开启", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(SlideSetting.this, "关闭", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        });
        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("设置");

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
