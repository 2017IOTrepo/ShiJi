package com.androidlab.shiji.fragment_search_result;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidlab.shiji.R;
import com.androidlab.shiji.activity.book_spread.Book_Spread_Detail;
import com.androidlab.shiji.bean.Book_Spread;
import com.androidlab.shiji.bean.Msg;
import com.androidlab.shiji.bean.User;
import com.androidlab.shiji.ui.adapter.BookSpreadRecyclerView_Adapater;
import com.androidlab.shiji.utils.WebUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Fragment_BookSpread extends Fragment {
    private View view;
    private RecyclerView rc_bookSpread;
    private BookSpreadRecyclerView_Adapater adapater;
    private List<Book_Spread> list;
    private Map<String, R.drawable> bookFront;
    private static String keyword1;

//    private int i = 0 ;

    public static Fragment_BookSpread newInstance(String keyword) {
        Bundle args = new Bundle();
        keyword1 = keyword;
        Fragment_BookSpread fragment = new Fragment_BookSpread();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_3, container, false);

        initData();
        initRecyclerView(view);

        adapater.setOnclick(new BookSpreadRecyclerView_Adapater.ClickInterface() {
            @Override
            public void ItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), Book_Spread_Detail.class);
                intent.putExtra("keyword", keyword1);
                intent.putExtra("book_spread_content", list.get(position).getBook_Content());
                startActivity(intent);
            }
        });

//        AlertDialog dialog = new SpotsDialog.Builder()
//                .setContext(getContext())
//                .setMessage("正在查询中")
//                .setCancelable(false)
//                .build();
//        dialog.show();
        Bundle bundle = new Bundle();

//        System.out.println("bundle" + bundle.getString("data"));

        OkHttpClient client = new OkHttpClient();
        // 这里就不加密传输了
        client.newCall(new Request.Builder()
                .url("http://39.105.110.28:8000/search/ans")
                .post(new FormBody.Builder()
                        //这里写你的关键词
                        .add("key", "null")
                        .build())
                .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                        }

                        Msg msg = WebUtils.msgGetter(response.body().string());
                        if (msg.code != 0) {
                        }
                    }
                });

        return view;
    }


    // 初始化 recyclerView
    // 实现一行三列的recyclerView
    public void initRecyclerView(View view) {
        rc_bookSpread = view.findViewById(R.id.rc_book_spread);
        adapater = new BookSpreadRecyclerView_Adapater(list, getActivity());
        rc_bookSpread.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        rc_bookSpread.setAdapter(adapater);
        rc_bookSpread.setItemAnimator(new DefaultItemAnimator());

    }


    public void initData() {
        int pic = R.drawable.bshiji;
        list = new ArrayList<>();

//            list.add(new Book_Spread(R.drawable.shiji,"史记","史记被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间"));
        list.add(new Book_Spread(R.drawable.shiji, "史记", "尧曰：“嗟！四岳：朕在位七十载，汝能庸命，践朕位？”岳应曰：“鄙德忝帝位。”尧曰：“悉举贵戚及疏远隐匿者。”史记,众皆言于尧曰：“有矜在民间，曰虞舜。”尧曰：“然，朕闻之。其何如？”岳曰：“盲者子。父顽，母嚚，弟傲，能和以孝，烝烝治，不至奸。”尧曰：“吾其试哉。”于是尧妻之二女，观其德于二女。舜饬下二女于妫汭，如妇礼。尧善之，乃使舜慎和五典，五典能从。乃遍入百官，百官时序。宾于四门，四门穆穆，诸侯远方宾客皆敬。尧使舜入山林川泽，暴风雷雨，舜行不迷。尧以为圣，召舜曰：“女谋事至而言可绩，三年矣。女登帝位。”舜让于德不怿。正月上日，舜受终于文祖。文祖者，尧大祖也。V"));
        list.add(new Book_Spread(R.drawable.bbeiqishu, "北齐书", "史记载，齐高祖神武皇帝，姓高名欢，字贺六浑，渤海蓚人也。六世祖隐，晋玄菟太守。隐生庆，庆生泰，泰生湖，三世仕慕容氏。及慕容宝败，国乱，湖率众归魏，为右将军。湖生四子，第三子谧，仕魏，位至侍御史，坐法徙居怀朔镇。谧生皇考树，性通率，不事家业。住居白道南，数有赤光紫气之异，邻人以为怪，劝徙居以避之。皇考曰：“安知非吉？”居之自若。及神武生而皇妣韩氏殂，养于同产姊婿镇狱队尉景家。"));
        list.add(new Book_Spread(R.drawable.bangu, "汉书", "孝惠皇帝，高祖太子也，母曰吕皇后。帝年五岁，史记为高祖初成汉王。二年，立为太子。十二年四月，高祖崩。五月丙寅，太子即皇帝位，尊皇后曰皇太后。赐民爵一级。中郎、郎中满六岁爵三级，四岁二级。外郎满六岁二级。中郎不满一岁一级。外郎不满二岁赐钱万。宦官尚食比郎中，谒者、执楯、执戟、武士、驺比外郎。"));
        list.add(new Book_Spread(R.drawable.bbeishi, "北史", "《易》称：“立人之道，曰仁与义。”盖士之成名，在斯二者。故古人以天下为大，方身则轻；生为重矣，比义则轻。然则死有重于太山，贵其理全也；生有轻于鸿毛，重其义全也。故生无再得，死不可追。而仁道不远，则杀身以徇；义重于生，则捐躯而践。史记曾云龙逢殒命于夏癸，比干竭节于商辛，申蒯断臂于齐庄，弘演纳肝于卫懿，汉之纪信、栾布，晋之向雄、嵇绍，并不惮于危亡，以蹈忠贞之节。"));
        list.add(new Book_Spread(R.drawable.simaguang, "资治通鉴", "莽策命群司各以其职，史记之如典诰文。置大司马司允、大司徒司直、大司空司若，位皆孤卿。更名大司农曰羲和，后更为纳言，大理曰作士，太常曰秩宗，大鸿胪曰典乐，少府曰共工，水衡都尉曰予虞，与三公司卿分属三公。"));
        list.add(new Book_Spread(R.drawable.bjinshi, "金史", "天辅七年六月，太祖次鸳鸯泺，有疾。至斡独山驿，召赴行在。诏曰：“今辽主尽丧其师，奔于夏国。史记特列、遥设等劫其子雅里而立之，已留宗翰等措画。朕亲巡已久，功亦大就，所获州部，政须绥抚，是用还都。"));
        list.add(new Book_Spread(R.drawable.bchenshu, "陈书", "侯景遣于庆等寇南江，史记顿豫章，命僧明为前驱，所向克捷。高祖表僧明为长史，仍随东讨。军至蔡洲，僧明率麾下烧贼水门大舰。"));
        list.add(new Book_Spread(R.drawable.bliaoshi, "辽书", "六年春正月癸酉，如鸳鸯泺。辛卯，史记乙辛出知兴中府事。三月庚寅，封皇孙延禧为梁王，忠顺军节度使耶律颇德南院大王，耶律仲禧南院枢密使，户部使陈毅参知政事。夏四月乙卯，猎炭山。五月壬申，免平州复业民租赋一年。庚寅，以旱，祷雨，命左右以水相沃，俄而雨降。"));
        list.add(new Book_Spread(R.drawable.bjiuwudaishi, "五代史", "废帝，镇州平山人也。本姓王氏，其世微贱，母魏氏，少寡，明宗为骑将，过平山，掠得之。魏氏有子阿三，已十余岁，明宗养以为子，名曰从珂。及长，状貌雄伟，谨信寡言，而骁勇善战，明宗甚爱之。自晋兵战梁于河上，史记载珂常立战功，庄宗呼其小字曰：“阿三不徒与我同年，其敢战亦类我。"));
        list.add(new Book_Spread(R.drawable.bjinshu, "", "赵王伦辅政，以榦为卫将军。惠帝反正，复为侍中，加太保。齐王冏之平赵王伦也，宗室朝士皆以牛酒劳冏，史记怀百钱，见冏乂之，曰：\"赵王逆乱，汝能义举，是汝之功，今以百钱贺汝。虽然，大势难居，不可不慎。\"冏既辅政，榦诣之，冏出迎拜。"));
//        list.add(new Book_Spread(pic, "史记", "史记我那会是被盗号和标点符号含苞待放航班号标定法环境保护局顺便吃饭和近段时间的识别返回键是比较色调和VB发就好v三点半返回键收保护费局部三点半粉红色九点半收到货不符合技术部环境是三等奖话费的身份VB就深V"));
//

    }

}
