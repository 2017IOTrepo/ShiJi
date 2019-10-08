package com.androidlab.shiji.fragment_search_result;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.MapContent;
import com.androidlab.shiji.helper.MapContentAdapter;
import com.github.abel533.echarts.Geo;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.code.CoordinateSystem;
import com.github.abel533.echarts.code.Roam;
import com.github.abel533.echarts.data.MapData;
import com.github.abel533.echarts.data.ScatterData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Map;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;

import java.util.ArrayList;
import java.util.List;

//地图
public class FragmentMap extends Fragment {
    private View view;

    private WebView searchMap;
    private RecyclerView recyclerView;

    public static FragmentMap newInstance() {
        Bundle args = new Bundle();
        FragmentMap fragment = new FragmentMap();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_2, container, false);
        searchMap = (WebView) view.findViewById(R.id.search_map);
        searchMap.getSettings().setAllowFileAccess(true);
        searchMap.getSettings().setJavaScriptEnabled(true);
        searchMap.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        searchMap.getSettings().setSupportZoom(true);
        searchMap.getSettings().setDisplayZoomControls(true);
        searchMap.loadUrl("file:///android_asset/test.html");

        /**
         * js方法的调用必须在html页面加载完成之后才能调用。
         * 用webview加载html还是需要耗时间的，必须等待加载完，在执行代用js方法的代码。
         */
        searchMap.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showMap();
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_map);
        initRec();
        return view;
    }

    private void initRec() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//设置Adapter
        List<MapContent> datas = new ArrayList<>();
        datas.add(new MapContent(R.drawable.num1, R.drawable.bshiji, "作者司马迁", "司马迁（前145年或前135年-不可考），字子长，夏阳(今陕西韩城南)人。西汉史学家、散文家。司马谈之子，任太史令，因替李陵败降之事辩解而受宫刑，后任中书令。发奋继续完成所著史籍，被后世尊称为史迁、太史公、历史之父。"));
        datas.add(new MapContent(R.drawable.num2, R.drawable.bshiji, "作者司马迁", "司马迁（前145年或前135年-不可考），字子长，夏阳(今陕西韩城南)人。西汉史学家、散文家。司马谈之子，任太史令，因替李陵败降之事辩解而受宫刑，后任中书令。发奋继续完成所著史籍，被后世尊称为史迁、太史公、历史之父。"));
        datas.add(new MapContent(R.drawable.num3, R.drawable.bshiji, "作者司马迁", "司马迁（前145年或前135年-不可考），字子长，夏阳(今陕西韩城南)人。西汉史学家、散文家。司马谈之子，任太史令，因替李陵败降之事辩解而受宫刑，后任中书令。发奋继续完成所著史籍，被后世尊称为史迁、太史公、历史之父。"));
        datas.add(new MapContent(R.drawable.num4, R.drawable.bshiji, "作者司马迁", "司马迁（前145年或前135年-不可考），字子长，夏阳(今陕西韩城南)人。西汉史学家、散文家。司马谈之子，任太史令，因替李陵败降之事辩解而受宫刑，后任中书令。发奋继续完成所著史籍，被后世尊称为史迁、太史公、历史之父。"));
        MapContentAdapter recycleAdapter = new MapContentAdapter(datas);
        recyclerView.setAdapter(recycleAdapter);

    }

    private void showMap() {
        //searchMap.loadUrl("javascript:clear()");

        GsonOption option = new GsonOption();
        option.backgroundColor("#FFFFFF");
        Title title = new Title();
        title.setText("史记" + "On The Map");
        title.setLeft("center");
        option.title(title);
        //option.tooltip().trigger(Trigger.item);
//        String[] colors = new String[6];
//        colors[0] = "#5475f5";
//        colors[1] = "#9feaa5";
//        colors[2] = "#85daef";
//        colors[3] = "#74e2ca";
//        colors[4] = "#e6ac53";
//        colors[5] = "#9fb5ea";
//        List<VisualMap> visualMapList = new ArrayList<VisualMap>();
//        VisualMap visualMap = new VisualMap();
//        visualMap.show(true)
//                .min(0)
//                .max(600)
//                .x(X.left)
//                .y(Y.center)
//                .color(colors)
//                .splitNumber(6);
///***
// * splitList: [
// {start: 500, end:600},{start: 400, end: 500},
// {start: 300, end: 400},{start: 200, end: 300},
// {start: 100, end: 200},{start: 0, end: 100},
// ],
// */
//        visualMapList.add(visualMap);
        //option.visualMap(visualMapList);

        Geo geo = new Geo();
        geo.map("china");
        geo.roam(Roam.move);
        option.geo(geo);

        Scatter scatter = new Scatter();
        scatter.name("测试点");
        scatter.coordinateSystem(CoordinateSystem.geo);
        List<ScatterData> mapdata = new ArrayList<>();
        List<Object> vals = new ArrayList<>();
        vals.add("12");
        vals.add("45");
        ScatterData data1 = new ScatterData(110.45, 35.47, 1);
        //data1.setValue(vals);
        ScatterData data2 = new ScatterData(120.2, 30.3, 2);
        //data2.setValue(vals);
        ScatterData data3 = new ScatterData(120.8, 39.5, 3);
        //data3.setValue(vals);
        ScatterData data4 = new ScatterData(110.2, 28.8, 4);
        //data4.setValue(vals);
        mapdata.add(data1);
        mapdata.add(data2);
        mapdata.add(data3);
        mapdata.add(data4);
        scatter.setData(mapdata);
        scatter.symbolSize(18);
        ItemStyle itemStyle = new ItemStyle();
        Normal normal = new Normal();
        normal.formatter("{@[2]}");
        normal.show(true);
        itemStyle.normal(normal);
        scatter.setLabel(itemStyle);
        option.series(scatter);

        Map map = new Map();
        ItemStyle label = new ItemStyle();
        label.normal().show(true);
        label.emphasis().show(true);
        map.name("数据")
                .mapType("china")
                .roam(true)
                .label(label);
        List<MapData> data = new ArrayList<MapData>();
        data.add(new MapData("北京", 242));
        data.add(new MapData("上海", 345));
        data.add(new MapData("河北", 453));
        data.add(new MapData("云南", 263));
        data.add(new MapData("黑龙江", 344));
        data.add(new MapData("安徽", 123));
        data.add(new MapData("新疆", 25));
        data.add(new MapData("浙江", 38));
        data.add(new MapData("湖北", 70));
        data.add(new MapData("甘肃", 245));
        data.add(new MapData("内蒙古", 135));
        data.add(new MapData("吉林", 536));
        data.add(new MapData("贵州", 521));
        data.add(new MapData("青海", 142));
        data.add(new MapData("四川", 435));
        data.add(new MapData("海南", 123));
        data.add(new MapData("香港", 453));
        data.add(new MapData("天津", 12));
        data.add(new MapData("重庆", 432));
        data.add(new MapData("河南", 123));
        data.add(new MapData("辽宁", 62));
        data.add(new MapData("湖南", 153));
        data.add(new MapData("山东", 432));
        data.add(new MapData("江苏", 120));
        data.add(new MapData("江西", 105));
        data.add(new MapData("广西", 452));
        data.add(new MapData("山西", 153));
        data.add(new MapData("陕西", 263));
        data.add(new MapData("陕西", 153));
        data.add(new MapData("广东", 136));
        data.add(new MapData("西藏", 165));
        data.add(new MapData("宁夏", 451));
        data.add(new MapData("台湾", 462));
        data.add(new MapData("澳门", 153));

        map.setData(data);
        // option.series(map);

        searchMap.loadUrl("javascript:loadEcharts('" + option.toString() + "')");
    }
}
