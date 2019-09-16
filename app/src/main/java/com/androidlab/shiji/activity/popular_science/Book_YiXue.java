package com.androidlab.shiji.activity.popular_science;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.LiShi_News;
import com.androidlab.shiji.bean.Sci_Book_Show;
import com.androidlab.shiji.ui.adapter.Book_RecyclerViewAdapater;
import com.androidlab.shiji.ui.view.BannerLayout;
import com.androidlab.shiji.utils.WebUtils;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Book_YiXue extends AppCompatActivity {

    private List<String> urlList;
    private RecyclerView recyclerView_yixue;
    private Book_RecyclerViewAdapater adapater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Sci_Book_Show> list = new ArrayList<>();
    private ProgressBar progressBar;
    private Context context;
    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_yixue);

        BannerLayout recyclerBanner =  findViewById(R.id.recycler);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView_yixue = findViewById(R.id.recyclerview_yixue);

        initBook();
        // 书籍item
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapater  = new Book_RecyclerViewAdapater(list, this);
        recyclerView_yixue.setHasFixedSize(true);
        recyclerView_yixue.setLayoutManager(layoutManager);
        recyclerView_yixue.setAdapter(adapater);

        // 轮播图
        List<String> list_banner = new ArrayList<>();
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71jgpymufj30xc0p0442.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71jgw893sj30j60a8aae.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71jh3o6lmj30m80gomxw.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g6vvot9sndj30ci058glv.jpg");
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(this,list_banner);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(Book_LiShi.this, "点击了第 " + position+" 项", Toast.LENGTH_SHORT).show();
                WebUtils.pickWeb(getApplication(),YiXue_News[position].getDesc(),YiXue_News[position].getUrl());
            }
        });
        recyclerBanner.setAdapter(webBannerAdapter);



        // item点击的事件
        adapater.setOnclick(new Book_RecyclerViewAdapater.ClickInterface() {
            @Override
            public void onItemClick(final View view, final int position) {
                Log.e("DoitemClick", "onItemClick: " );
                final String BookName = list.get(position).getBook_Title();
//                createFolder();
//                CopyAssets();
                // 使用Handler进行延时  并返回主线程
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        HwTxtPlayActivity.loadTxtFile(view.getContext(),FilePath+BookName+".txt");//传递一个文件路径
                        Toast.makeText(Book_YiXue.this, BookName, Toast.LENGTH_SHORT).show();
                    }
                },2000);

            }
        });


        mSwipeRefreshLayout = findViewById( R.id.swipe_refresh );
        mSwipeRefreshLayout.setColorSchemeResources( R.color.egi );
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshBooks();
            }
        } );

        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("医学古籍");

    }


    private Sci_Book_Show[] books_yixue = {

            new Sci_Book_Show(R.drawable.bianque, "八十一难经", "扁鹊", "是中医现存较早的经典著作。", "医藏"),
            new Sci_Book_Show(R.drawable.lishizhen, "本草纲目", "李时珍", "是述本草要籍与药性理论的著作。", "医藏"),
            new Sci_Book_Show(R.drawable.susun, "本草图经", "苏颂", "是古代中药学著作。简称《咽经》。", "医藏"),
            new Sci_Book_Show(R.drawable.lishizhen2, "濒湖脉学", "李时珍", "是研究脉学的心得。", "医藏"),
            new Sci_Book_Show(R.drawable.zhuzhenheng, "丹溪心法", "朱震亨", "是一部综合性医书，共五卷（一作三卷）。", "医藏"),
            new Sci_Book_Show(R.drawable.qikong, "外科大成", "祁坤", "是中医外科重要参考书。。", "医藏")

    };


    private com.androidlab.shiji.bean.LiShi_News[] YiXue_News = {
            new LiShi_News("https://baike.baidu.com/item/黄帝八十一难经/8443705", "八十一难经"),
            new LiShi_News("https://baike.baidu.com/item/本草纲目/20585830", "本草纲目"),
            new LiShi_News("https://baike.baidu.com/item/本草图经", "本草图经"),
            new LiShi_News("https://baike.baidu.com/item/丹溪心法", "丹溪心法")


    };

    public  void  initBook(){
        list.clear();
        for (int i = 0; i < 5; i ++){
            Random ra = new Random();
            int index = ra.nextInt(books_yixue.length);
            list.add(books_yixue[index]);
        }
    }


    private void refreshBooks(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep( 2000 );
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        initBook();
                        adapater.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing( false );
                    }
                } );
            }
        } ).start();
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    /**
     * 文件操作
     */
    /*_____________________________________________________________________*/

    //创建文件夹
    public  void createFolder() {
        //新建一个File，传入文件夹目录
        File file = new File(Environment.getExternalStorageDirectory() + "/a");
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file.mkdirs();
        }else{

        }
    }

    public  void CopyAssets(){
        String newPath = Environment.getExternalStorageDirectory() + "/a";
        AssetManager assetManager = getAssets();
        String [] files = null;
        try{
            files = assetManager.list("");

        } catch (IOException e) {
            Log.e("Failed", "CopyAssets: Failed to get file", e);
        }

        if (files != null) for (String filename : files){
            InputStream in  = null;
            OutputStream out = null;
            try{
                in  = assetManager.open(filename);
                if((filename.substring(filename.length()-3)).equals("txt")){
                    File outFile = new File(Environment.getExternalStorageDirectory() + "/a", filename);
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                    Log.e("fgggggg", "CopyAssets: "+ filename);
                }
            } catch (IOException e) {
                Log.e("Failed", "CopyAssets: Failed to copy file",e );
            }
            finally {
                if (in != null){
                    try{
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null){
                    try{
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    private void copyFile(InputStream in, OutputStream out) throws  IOException{
        byte [] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }


    /*_____________________________________________________________________*/


}
