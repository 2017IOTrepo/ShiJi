package com.androidlab.shiji.fragment_search_result;

import android.app.AlertDialog;
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
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.fragment_tab.Fragment2;
import com.androidlab.shiji.fragment_tab.Fragment_Popular_Science;
import com.androidlab.shiji.utils.StaticVariable;
import com.androidlab.shiji.utils.WebUtils;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//词向量
public class Fragment_1 extends Fragment {

    private View view;

    private WebView search_wordVec;

    private List<Object> xAxis;
    private List<Object> yAxis;
    private static String keyword1;
    private AlertDialog dialog;
    public boolean isGet = false;


    public static Fragment_1 newInstance(String keyword) {
        Bundle args = new Bundle();
        keyword1 = keyword;
        Fragment_1 fragment = new Fragment_1();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_word_vector, container, false);
        search_wordVec = view.findViewById(R.id.search_word_vec);
        search_wordVec.getSettings().setAllowFileAccess(true);
        search_wordVec.getSettings().setJavaScriptEnabled(true);
        search_wordVec.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        search_wordVec.getSettings().setSupportZoom(true);
        search_wordVec.getSettings().setDisplayZoomControls(true);
        search_wordVec.loadUrl("file:///android_asset/aTabletest.html");



        System.out.println("键值" + keyword1);


        search_wordVec.setWebViewClient(new WebViewClient() {
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
//        init();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("正在加载中")
                .setCancelable(false)
                .build();
        dialog.show();
        init();
    }

    private void init() {
        xAxis = new ArrayList<>();
        yAxis = new ArrayList<>();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(300000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(300000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(300000, TimeUnit.SECONDS)//设置连接超时时间
                .build();

        // 这里就不加密传输了
        client.newCall(new Request.Builder()
                .url("http://39.105.110.28:8000/search/vec")
                .post(new FormBody.Builder()
                        // 这里写关键词
                        .add("Key", keyword1)
                        .build())
                .build())

                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            return;
                        }

                        JSONObject jsonObject = JSONObject.fromObject(response.body().string());
                        int code = jsonObject.getInt("code");

                        if (code != 0) {
                            return;
                        }

                        JSONArray jsonArray = null;

                        try {
                            jsonArray = jsonObject.getJSONArray("data");
                            for (Object jobj :
                                    jsonArray) {
                                JSONObject job = JSONObject.fromObject(jobj);
                                xAxis.add(job.getString("BookName"));
                                yAxis.add(job.getString("Sums"));
                            }
                        } catch (Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.cancel();
                                }
                            });
                            return;
                        }

//                        showTable();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                /**
                                 * js方法的调用必须在html页面加载完成之后才能调用。
                                 * 用webview加载html还是需要耗时间的，必须等待加载完，在执行代用js方法的代码。
                                 */
                                dialog.cancel();
                                showTable();
                            }
                        });
                    }
                });
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
        search_wordVec.loadUrl("javascript:loadEcharts("" + option.toString() + "")");
        */

//        Object[] xAxis = new Object[]{
//                "三国志", "元史", "北史", "北齐书", "南史", "南齐书", "史记", "后汉书", "周书", "宋书", "宋史", "新五代史", "旧五代史", "旧唐书", "明史", "晋书", "梁书", "汉书", "清史稿", "辽史", "金史", "陈书", "隋书", "魏书"
//        };
//        Object[] yAxis = new Object[]{
//                10000, 9234, 9023, 8934, 8212, 8023, 7133, 7012, 6976, 6767,
//                6490, 6080, 5012, 5013, 4011, 4000, 3911, 3800, 3200,
//                3200, 3100, 3000, 2999, 2988
//        };


        Object[] xAxisArray = new String[xAxis.size()];

        //使用for循环得到数组
        for (int i = 0; i < xAxis.size(); i++) {
            xAxisArray[i] = xAxis.get(i);
        }

        Object[] yAxisArray = new String[yAxis.size()];

        //使用for循环得到数组
        for (int i = 0; i < yAxis.size(); i++) {
            yAxisArray[i] = yAxis.get(i);
        }

        GsonOption option = new GsonOption();
        Title title = new Title();
        title.setText(keyword1 + "词向量结果");
        title.left("center");
        option.title(title);
        option.color("#3398DB");
        // option.tooltip().trigger(Trigger.axis);

        Legend legend = new Legend();
        legend.data("词数量(个)");
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
        categorxAxis.data(xAxisArray);
        AxisLabel axisLabel = new AxisLabel();
        TextStyle textStyle = new TextStyle();
        textStyle.fontWeight("bold");
        textStyle.fontSize(new Integer(15));
        axisLabel.textStyle(textStyle);
        categorxAxis.axisLabel(axisLabel);
        option.yAxis(categorxAxis);

        Bar bar = new Bar();
        bar.name("词数量(个)").data(yAxisArray).barWidth(new Integer(30));
        option.series(bar);

        search_wordVec.loadUrl("javascript:loadEcharts('" + option.toString() + "')");
    }
}


//package com.androidlab.shiji.fragment_search_result;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.webkit.WebView;
//import android.widget.Toast;
//
//import com.androidlab.shiji.R;
//import com.androidlab.shiji.bean.WordVector;
//import com.androidlab.shiji.ui.adapter.WordVectorAdapter;
//import com.github.abel533.echarts.Grid;
//import com.github.abel533.echarts.Legend;
//import com.github.abel533.echarts.Title;
//import com.github.abel533.echarts.axis.AxisLabel;
//import com.github.abel533.echarts.axis.CategoryAxis;
//import com.github.abel533.echarts.axis.ValueAxis;
//import com.github.abel533.echarts.json.GsonOption;
//import com.github.abel533.echarts.series.Bar;
//import com.github.abel533.echarts.style.TextStyle;
//
//import java.util.ArrayList;
//import java.util.List;
//
////词向量
//public class WordVectorFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private WordVectorAdapter wordVectorAdapter;
//    private View view;
//
//    private WebView search_wordVec;
//    private List<WordVector> wordVectorList;
//
//
//    public static WordVectorFragment newInstance() {
//        Bundle args = new Bundle();
//        WordVectorFragment fragment = new WordVectorFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        view = inflater.inflate(R.layout.fragment_word_vector, container, false);
//        wordVectorList = new ArrayList<>();
//        wordVectorList.add(new WordVector("朝代 1 请问", "23%"));
//
//        initView();
//
//        return view;
//    }
//
//    private void initView() {
//        recyclerView = (RecyclerView) view.findViewById(R.id.search_recyclerView_ans);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setLongClickable(true);
//        wordVectorAdapter = new WordVectorAdapter(wordVectorList);
//        wordVectorAdapter.setOnItemClickLitener(new WordVectorAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(WordVector wordVector) {
//            }
//
//            @Override
//            public void onItemLongClick(WordVector wordVector) {
//                android.content.ClipboardManager cm = (android.content.ClipboardManager) getActivity().getSystemService(getActivity().CLIPBOARD_SERVICE);
//                cm.setText(wordVector.wordContent + "概率:" + wordVector.probability);
//                Toast.makeText(getContext(), "已复制到粘贴板中！", Toast.LENGTH_SHORT).show();
//            }
//        });
//        recyclerView.setAdapter(wordVectorAdapter);
//    }
//}
