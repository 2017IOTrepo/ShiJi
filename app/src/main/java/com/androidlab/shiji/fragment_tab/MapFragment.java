package com.androidlab.shiji.fragment_tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.androidlab.shiji.R;
import com.github.abel533.echarts.VisualMap;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.data.MapData;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Map;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 放置地图
 */
public class MapFragment extends Fragment {


    private int dynasty;
    private WebView chartshow_web;
    private View view;
    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(int dyns) {
        MapFragment fragment = new MapFragment();
        fragment.dynasty = dyns;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false);
        chartshow_web = (WebView)view.findViewById(R.id.chartshow_web);
        chartshow_web.getSettings().setAllowFileAccess(true);
        chartshow_web.getSettings().setJavaScriptEnabled(true);
        switch(dynasty){
            case 0:
                chartshow_web.loadUrl("file:///android_asset/chuanshuo.html");
                break;
            case 1:
                chartshow_web.loadUrl("file:///android_asset/xia.html");
                break;
            case 2://商
                chartshow_web.loadUrl("file:///android_asset/shang.html");
                break;
            case 3://西周
                chartshow_web.loadUrl("file:///android_asset/xizhou.html");
                break;
            case 4://东周
                //chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
                break;
            case 5://东周战国
                //chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
                break;
            case 6://qin
                chartshow_web.loadUrl("file:///android_asset/qin.html");
                break;
            case 7://西汉
                chartshow_web.loadUrl("file:///android_asset/xihan.html");
                break;
            case 8://东汉
                chartshow_web.loadUrl("file:///android_asset/donghan.html");
                break;
            case 9://三国
                //chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
                break;
            case 10://西晋
                chartshow_web.loadUrl("file:///android_asset/xijin.html");
                break;
            case 11://东晋
                chartshow_web.loadUrl("file:///android_asset/dongjin.html");
                break;
            case 12://南北朝
                chartshow_web.loadUrl("file:///android_asset/nanbeichao.html");
                break;
            case 13:
                chartshow_web.loadUrl("file:///android_asset/sui.html");
                break;
            case 14://唐
                chartshow_web.loadUrl("file:///android_asset/tang.html");
                break;
            case 15: //五代十国
                //chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
                break;
            case 16:
                chartshow_web.loadUrl("file:///android_asset/beisong.html");
                break;
            case 17:
                chartshow_web.loadUrl("file:///android_asset/nansong.html");
                break;
            case 18:
                chartshow_web.loadUrl("file:///android_asset/yuan.html");
                break;
            case 19:
                chartshow_web.loadUrl("file:///android_asset/Ming.html");
                break;
            case 20:
                chartshow_web.loadUrl("file:///android_asset/Qing.html");
                break;
            case 21:
                chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
                break;
            case 22:
                chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
                break;
            default:
                chartshow_web.loadUrl("file:///android_asset/gongheguo.html");
        }

        /**
         * js方法的调用必须在html页面加载完成之后才能调用。
         * 用webview加载html还是需要耗时间的，必须等待加载完，在执行代用js方法的代码。
         */
        chartshow_web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //showMap();
            }
        });



        initData();

        initView();

        return view;
    }

    private void showMap() {
        chartshow_web.loadUrl("javascript:clear()");

        GsonOption option = new GsonOption();
        option.backgroundColor("#FFFFFF");
        option.title("全国地图大数据");
        option.tooltip().trigger(Trigger.item);
        String[] colors = new String[6];
        colors[0] = "#5475f5";
        colors[1] = "#9feaa5";
        colors[2] = "#85daef";
        colors[3] = "#74e2ca";
        colors[4] = "#e6ac53";
        colors[5] = "#9fb5ea";
        List<VisualMap> visualMapList = new ArrayList<VisualMap>();
        VisualMap visualMap = new VisualMap();
        visualMap.show(true)
                .min(0)
                .max(600)
                .x(X.left)
                .y(Y.center)
                .color(colors)
                .splitNumber(6);
/***
 * splitList: [
 {start: 500, end:600},{start: 400, end: 500},
 {start: 300, end: 400},{start: 200, end: 300},
 {start: 100, end: 200},{start: 0, end: 100},
 ],
 */
        visualMapList.add(visualMap);
        option.visualMap(visualMapList);

        Map map = new Map();
        ItemStyle label = new ItemStyle();
        label.normal().show(true);
        label.emphasis().show(true);
        map.name("数据")
                .mapType("world")
                .roam(true)
                .label(label);
        List<MapData> data = new ArrayList<MapData>();
        data.add(new MapData("北京",242));
        data.add(new MapData("上海",345));
        data.add(new MapData("河北",453));
        data.add(new MapData("云南",263));
        data.add(new MapData("黑龙江",344));
        data.add(new MapData("安徽",123));
        data.add(new MapData("新疆",25));
        data.add(new MapData("浙江",38));
        data.add(new MapData("湖北",70));
        data.add(new MapData("甘肃",245));
        data.add(new MapData("内蒙古",135));
        data.add(new MapData("吉林",536));
        data.add(new MapData("贵州",521));
        data.add(new MapData("青海",142));
        data.add(new MapData("四川",435));
        data.add(new MapData("海南",123));
        data.add(new MapData("香港",453));
        data.add(new MapData("天津",12));
        data.add(new MapData("重庆",432));
        data.add(new MapData("河南",123));
        data.add(new MapData("辽宁",62));
        data.add(new MapData("湖南",153));
        data.add(new MapData("山东",432));
        data.add(new MapData("江苏",120));
        data.add(new MapData("江西",105));
        data.add(new MapData("广西",452));
        data.add(new MapData("山西",153));
        data.add(new MapData("陕西",263));
        data.add(new MapData("陕西",153));
        data.add(new MapData("广东",136));
        data.add(new MapData("西藏",165));
        data.add(new MapData("宁夏",451));
        data.add(new MapData("台湾",462));
        data.add(new MapData("澳门",153));

        map.setData(data);
        option.series(map);
        chartshow_web.loadUrl("javascript:setOption(" + option + ")");
    }


    private void showPieChart() {
        chartshow_web.loadUrl("javascript:clear()");

        ItemStyle dataStyle = new ItemStyle();
        dataStyle.normal().label().show(true).formatter("{b}\n({d}%)");

        GsonOption option = new GsonOption();

        Pie pie = new Pie("访问来源");
        pie.clockWise(false).center("48%", "45%").radius("55", "80")
                .itemStyle(dataStyle)
                .data(
                        new Data("直接访问",335),
                        new Data("邮件营销",310),
                        new Data("联盟广告",274),
                        new Data("视频广告",235),
                        new Data("搜索引擎",400)
                );
        option.series(pie);

        chartshow_web.loadUrl("javascript:setOption(" + option + ")");
    }

    private void initData() {


    }

    private void initView() {


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
