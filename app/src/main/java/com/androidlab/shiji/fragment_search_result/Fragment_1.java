package com.androidlab.shiji.fragment_search_result;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidlab.shiji.R;
import com.androidlab.shiji.fragment_tab.Fragment2;
import com.androidlab.shiji.fragment_tab.Fragment_Popular_Science;

public class Fragment_1  extends Fragment{

     private View view;

    public static Fragment_1 newInstance() {
        Bundle args = new Bundle();
        Fragment_1 fragment = new Fragment_1();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_1, container,false);


        return view;
    }
}
