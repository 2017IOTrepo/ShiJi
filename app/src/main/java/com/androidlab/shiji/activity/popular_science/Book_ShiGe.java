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

public class Book_ShiGe  extends AppCompatActivity {
    private List<String> urlList;
    private RecyclerView recyclerView_shige;
    private Book_RecyclerViewAdapater adapater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Sci_Book_Show> list = new ArrayList<>();
    private ProgressBar progressBar;
    private Context context;
    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shige);


        BannerLayout  recyclerBanner = findViewById(R.id.recycler);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView_shige = findViewById(R.id.recyclerview_shige);

        initBook();

        // 书籍item
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapater  = new Book_RecyclerViewAdapater(list, this);
        recyclerView_shige.setHasFixedSize(true);
        recyclerView_shige.setLayoutManager(layoutManager);
        recyclerView_shige.setAdapter(adapater);


        // 轮播图
        List<String> list_banner = new ArrayList<>();
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71huehn73j30ci05r3yq.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71hunhq7ej30go09j3yr.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71hw9hbcoj30j60di40z.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71hwf9f9xj30cs07z0st.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71hwm60nhj30go08k3z9.jpg");
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(this,list_banner);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(Book_LiShi.this, "点击了第 " + position+" 项", Toast.LENGTH_SHORT).show();
                WebUtils.pickWeb(getApplication(),ShiGe_News[position].getDesc(),ShiGe_News[position].getUrl());
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
                        Toast.makeText(Book_ShiGe.this, BookName, Toast.LENGTH_SHORT).show();
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
        mActionBar.setTitle("诗歌古籍");


    }


    private Sci_Book_Show[] books_shige = {

            new Sci_Book_Show(R.drawable.baipu, "白朴元曲集", "白朴", "是白朴创作的其他类书籍。", "诗藏"),
            new Sci_Book_Show(R.drawable.liuxiang, "楚辞", "刘向", "是中国文学史上第一部浪漫主义诗歌总集。", "诗藏"),
            new Sci_Book_Show(R.drawable.zhangheng, "二京赋", "张衡", "是张衡的代表作之一，包括《西京赋》、《东京赋》两篇。", "诗藏"),
            new Sci_Book_Show(R.drawable.yangsheng, "古今风谣", "杨慎", "是中国古代民谣集。", "诗藏"),
            new Sci_Book_Show(R.drawable.huangtingjian, "黄庭坚词", "黄庭坚", "是2011年上海古籍出版社出版的图书，是黄庭坚的词集。", "诗藏"),
            new Sci_Book_Show(R.drawable.nalan, "纳兰词全集", "纳兰容若", "是纳兰性德的词集。", "诗藏"),
            new Sci_Book_Show(R.drawable.tangshi, "全唐诗", "佚名", "是清康熙四十四年（1705年），彭定求、沈三曾等人编校，共计900卷，目录13卷。", "诗藏")

    };


    private com.androidlab.shiji.bean.LiShi_News[] ShiGe_News = {
            new LiShi_News("https://baike.baidu.com/item/白朴元曲集/17617747?fr=aladdin", "白朴元曲集"),
            new LiShi_News("https://baike.baidu.com/item/楚辞/291160", "楚辞"),
            new LiShi_News("https://baike.baidu.com/item/二京赋", "二京赋"),
            new LiShi_News("https://baike.baidu.com/item/纳兰词全集", "纳兰词全集"),
            new LiShi_News("https://baike.baidu.com/item/全唐诗", "全唐诗"),

    };

    public  void  initBook(){
        list.clear();
        for (int i = 0; i < 5; i ++){
            Random ra = new Random();
            int index = ra.nextInt(books_shige.length);
            list.add(books_shige[index]);
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
