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
import com.lzj.gallery.library.views.BannerViewPager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Book_LiShi  extends AppCompatActivity {


    private  List<String> urlList;
    private RecyclerView recyclerView_lishi;
    private Book_RecyclerViewAdapater adapater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Sci_Book_Show> list = new ArrayList<>();
    private ProgressBar progressBar;
    private Context context;
    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_lishi);
        BannerLayout  recyclerBanner =  findViewById(R.id.recycler);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView_lishi = findViewById(R.id.recyclerview_lishi);

        initBook();
        // 书籍item
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapater  = new Book_RecyclerViewAdapater(list, this);
        recyclerView_lishi.setHasFixedSize(true);
        recyclerView_lishi.setLayoutManager(layoutManager);
        recyclerView_lishi.setAdapter(adapater);


        // 轮播图
        List<String> list_banner = new ArrayList<>();
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g6vvo3uj67j30dw09mt9h.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g6vvof0h3fj331d1k5kb6.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g6vvomn6aaj30hs0bvt9e.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g6vvot9sndj30ci058glv.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g6vvp0hfr6j30hs0bvt9p.jpg");
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(this,list_banner);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(Book_LiShi.this, "点击了第 " + position+" 项", Toast.LENGTH_SHORT).show();
                WebUtils.pickWeb(getApplication(),LiShi_News[position].getDesc(),LiShi_News[position].getUrl());
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
                        Toast.makeText(Book_LiShi.this, BookName, Toast.LENGTH_SHORT).show();
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
        mActionBar.setTitle("历史古籍");

    }



    private Sci_Book_Show[] books_lishi = {

            new Sci_Book_Show(R.drawable.bangu, "汉书", "班固", "是中国第一部纪传体断代史，“二十四史”之一。", "史藏"),
            new Sci_Book_Show(R.drawable.libaiyao, "北齐书", "李百药", "属纪传体断代史，共50卷，纪8卷，列传42卷。", "史藏"),
            new Sci_Book_Show(R.drawable.xuanzang, "大唐西域记", "玄奘", "是由唐代玄奘口述、辩机编的地理史籍，成书于唐贞观二十年。", "史藏"),
            new Sci_Book_Show(R.drawable.simaguang, "资治通鉴", "司马光", "是一部多卷本编年体史书，共294卷。", "史藏"),
            new Sci_Book_Show(R.drawable.yinmo, "五代春秋", "尹沫", "是考察五代春秋时期的重要史料。", "史藏"),
            new Sci_Book_Show(R.drawable.zhongtai, "中国哲学史", "钟泰", "是梳理学术大旨及源流的著作。", "史藏")

    };


    private LiShi_News[] LiShi_News = {
            new LiShi_News("https://baike.baidu.com/item/北齐书", "北齐书"),
            new LiShi_News("https://baike.baidu.com/item/大唐西域记", "大唐西域记"),
            new LiShi_News("https://baike.baidu.com/item/汉书", "汉书"),
            new LiShi_News("https://baike.baidu.com/item/史记/254522?fr=aladdin", "史记"),
            new LiShi_News("https://baike.baidu.com/item/资治通鉴", "资治通鉴")

    };

    public  void  initBook(){
        list.clear();
        for (int i = 0; i < 5; i ++){
            Random ra = new Random();
            int index = ra.nextInt(books_lishi.length);
            list.add(books_lishi[index]);
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
