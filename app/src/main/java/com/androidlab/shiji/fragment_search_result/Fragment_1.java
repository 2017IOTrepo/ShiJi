package com.androidlab.shiji.fragment_search_result;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidlab.shiji.R;
import com.androidlab.shiji.fragment_tab.Fragment2;
import com.androidlab.shiji.fragment_tab.Fragment_Popular_Science;
import com.github.abel533.echarts.Grid;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.axis.Axis;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.AxisTick;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.AxisType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.AxisData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.style.TextStyle;

import java.util.ArrayList;
import java.util.List;

//词向量
public class Fragment_1  extends Fragment{

     private View view;

     private WebView search_wordVec;

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
        search_wordVec = view.findViewById(R.id.search_word_vec);
        search_wordVec.getSettings().setAllowFileAccess(true);
        search_wordVec.getSettings().setJavaScriptEnabled(true);
        search_wordVec.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        search_wordVec.getSettings().setSupportZoom(true);
        search_wordVec.getSettings().setDisplayZoomControls(true);
        search_wordVec.loadUrl("file:///android_asset/aTabletest.html");

        /**
         * js方法的调用必须在html页面加载完成之后才能调用。
         * 用webview加载html还是需要耗时间的，必须等待加载完，在执行代用js方法的代码。
         */
        search_wordVec.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showTable();
            }
        });

        return view;
    }

    private void showTable() {

        /*
        GsonOption option = new GsonOption();
        Title title = new Title();
        title.setText("史记词向量结果");
        title.left("center");
        option.title(title);
        option.color("#3398DB");

        ValueAxis axisx = new ValueAxis();
        axisx.type(AxisType.value);
        option.xAxis(axisx);

        ValueAxis axisy = new ValueAxis();
        axisy.type(AxisType.category);

        List<AxisData> axiss = new ArrayList<>();
        axiss.add(new AxisData("vec1"));
        axiss.add(new AxisData("vec2"));
        axiss.add(new AxisData("vec3"));
        axiss.add(new AxisData("vec4"));
        axiss.add(new AxisData("vec5"));
        axiss.add(new AxisData("vec6"));
        //axisy.setData(axiss);
        axisy.data("vec1","vec2","vec3","vec4","vec5","vec6");
        option.yAxis();

        Bar bar = new Bar();
        bar.name("访问");
        bar.barWidth(new Integer(6));


        bar.data(10, 52, 200, 334, 390, 330);
        option.series(bar);

        search_wordVec.loadUrl("javascript:loadEcharts('" + option.toString() + "')");

        */

        Object[] xAxis = new Object[]{
                "太史公记","司马迁",  "纪传体", "二十四史", "西汉", "史家绝唱", "汉武帝"
        };
        Object[] yAxis = new Object[]{
                36,40, 48, 62, 75, 81,90
        };
        GsonOption option = new GsonOption();
        Title title = new Title();
        title.setText("史记词向量结果");
        title.left("center");
        option.title(title);
        option.color("#3398DB");
       // option.tooltip().trigger(Trigger.axis);

        Legend legend = new Legend();
        legend.data("关联度(%)");
        legend.left("right");
        option.legend(legend);
        Grid grid = new Grid();
        grid.containLabel(true);
        option.grid(grid);

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.position("top");
        option.xAxis(valueAxis);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        AxisLabel axisLabel = new AxisLabel();
        TextStyle textStyle = new TextStyle();
        textStyle.fontWeight("bold");
        textStyle.fontSize(new Integer(15));
        axisLabel.textStyle(textStyle);
        categorxAxis.axisLabel(axisLabel);
        option.yAxis(categorxAxis);

        Bar bar = new Bar();
        bar.name("关联度(%)").data(yAxis).barWidth(new Integer(30));
        option.series(bar);

        search_wordVec.loadUrl("javascript:loadEcharts('" + option.toString() + "')");
    }
}
