package com.androidlab.shiji.fragment_tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.activity.popular_science.Sci_Hostiory;

public class Fragment_Popular_Science extends Fragment {

    private View view;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;

    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;

    public static Fragment_Popular_Science newInstance() {
        Bundle args = new Bundle();
        Fragment_Popular_Science fragment = new Fragment_Popular_Science();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        view = inflater.inflate(R.layout.fragment3, container,false);

        cardView1 = view.findViewById(R.id.carview1);
        iv1 = view.findViewById(R.id.histroy);
        iv1.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       Intent i = new Intent(getActivity(), Sci_Hostiory.class);
                                       startActivity(i);
                                   }
                               });
        return view;
    }
}
