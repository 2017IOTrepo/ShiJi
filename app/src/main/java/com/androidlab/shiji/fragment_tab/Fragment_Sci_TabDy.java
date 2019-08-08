package com.androidlab.shiji.fragment_tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidlab.shiji.R;

public class Fragment_Sci_TabDy  extends Fragment {

    private TextView textView;
    public static final String ARGUMENT = "position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_dy_show, container, false);

        textView = view.findViewById(R.id.text_des);
        textView.setText(R.string.tab0);

        if (bundle != null){
            int pos = bundle.getInt("state");
            if (pos == 1){
                textView.setText(R.string.tab1);
            }else if(pos == 2){
                textView.setText(R.string.tab2);
            }else if(pos == 3){
                textView.setText(R.string.tab3);
            }else if(pos == 4){
                textView.setText(R.string.tab4);
            }else if(pos == 5){
                textView.setText(R.string.tab5);
            }else if(pos == 6){
                textView.setText(R.string.tab6);
            }else if(pos == 7){
                textView.setText(R.string.tab7);
            }else if(pos == 8){
                textView.setText(R.string.tab8);
            }else if(pos == 9){
                textView.setText(R.string.tab9);
            }else if(pos == 10){
                textView.setText(R.string.tab10);
            }else if(pos == 11){
                textView.setText(R.string.tab11);
            }else if(pos == 12){
                textView.setText(R.string.tab12);
            }else if(pos == 13){
                textView.setText(R.string.tab13);
            }else if(pos == 14){
                textView.setText(R.string.tab14);
            }else if(pos == 15){
                textView.setText(R.string.tab15);
            }

            Log.i("pot", "onCreateView: "+pos);
        }else {
            Toast.makeText(getActivity(), "传递错误", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

}
