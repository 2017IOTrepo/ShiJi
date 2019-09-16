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

public class Book_YiShu extends AppCompatActivity {


    private List<String> urlList;
    private RecyclerView recyclerView_yishu;
    private Book_RecyclerViewAdapater adapater;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Sci_Book_Show> list = new ArrayList<>();
    private ProgressBar progressBar;
    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_yishu);


        BannerLayout recyclerBanner =  findViewById(R.id.recycler);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView_yishu = findViewById(R.id.recyclerview_yishu);

        initBook();
        // 书籍item
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapater  = new Book_RecyclerViewAdapater(list, this);
        recyclerView_yishu.setHasFixedSize(true);
        recyclerView_yishu.setLayoutManager(layoutManager);
        recyclerView_yishu.setAdapter(adapater);

        // 轮播图
        List<String> list_banner = new ArrayList<>();
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71iil9yrlj31av0nj7b7.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71iirj1v4j30ia0aewfp.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71iix451aj30fh0870tb.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71ij3qllbj31jk0pzafj.jpg");
        list_banner.add("http://ww1.sinaimg.cn/large/006nBCHPly1g71ijdwar9j30f0078aac.jpg");
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(this,list_banner);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(Book_LiShi.this, "点击了第 " + position+" 项", Toast.LENGTH_SHORT).show();
                WebUtils.pickWeb(getApplication(),YiShu_News[position].getDesc(),YiShu_News[position].getUrl());
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
                        Toast.makeText(Book_YiShu.this, BookName, Toast.LENGTH_SHORT).show();
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
        mActionBar.setTitle("艺术古籍");



    }



    private Sci_Book_Show[] books_yishu = {

            new Sci_Book_Show(R.drawable.yiming, "八美图", "佚名", "描写宋代杭州人柳树春经历的悲欢离合故事。", "艺藏"),
            new Sci_Book_Show(R.drawable.luopodaoren, "常言道", "落魄道人", "是以漫画式的辛辣笔法，叙述财主钱士命贪财悭吝，寡廉鲜耻，最终财尽人亡的故事。", "艺藏"),
            new Sci_Book_Show(R.drawable.huangxiaopei, "大马扁", "黄小配", "是讽刺后期清朝社会腐败的著作。", "艺藏"),
            new Sci_Book_Show(R.drawable.penghaozi, "定鼎奇闻", "蓬蒿子", "通过奇闻异事反映清政府的现实。", "艺藏"),
            new Sci_Book_Show(R.drawable.wangji, "海国春秋", "汪寄", "叙述韩速、闾丘仲卿二人在海国建功立业五十年，而两宋兴衰已三百年的故事。", "艺藏"),
            new Sci_Book_Show(R.drawable.caoxueqin, "红楼梦", "曹雪芹", "是一部具有世界影响力的人情小说，举世公认的中国古典小说巅峰之作。", "艺藏"),
            new Sci_Book_Show(R.drawable.xudachun, "乐府传声", "李大椿", "本书对于唱法的分析和研究非常详密，颇受戏曲界重视。", "艺藏"),
            new Sci_Book_Show(R.drawable.wengui, "明月台", "翁桂", "以裴既寿、甘百善一反一正角色的故事，诉求本书主旨。", "艺藏"),
            new Sci_Book_Show(R.drawable.yangsheng2, "墨池琐录", "杨慎", "是中国明代书法理论著作。", "艺藏"),
            new Sci_Book_Show(R.drawable.yiming2, "木兰奇女转", "佚名", "近现代的言情小说代表作。", "艺藏"),
            new Sci_Book_Show(R.drawable.zhangyingwen, "清密藏", "张应文", "是中国古代工艺美术鉴赏著作。", "艺藏"),
            new Sci_Book_Show(R.drawable.wujingzi, "儒林外史", "吴敬梓", "是中国古代工艺美术鉴赏著作。", "艺藏")
    };


    private com.androidlab.shiji.bean.LiShi_News[] YiShu_News = {
            new LiShi_News("https://baike.baidu.com/item/八美图/13214956", "八美图"),
            new LiShi_News("hhttps://baike.baidu.com/item/红楼梦/15311", "红楼梦"),
            new LiShi_News("https://baike.baidu.com/item/明月台", "明月台"),
            new LiShi_News("https://baike.baidu.com/item/墨池琐录", "墨池琐录"),
            new LiShi_News("https://baike.baidu.com/item/儒林外史/27018", "儒林外史")

    };


    public  void  initBook(){
        list.clear();
        for (int i = 0; i < 5; i ++){
            Random ra = new Random();
            int index = ra.nextInt(books_yishu.length);
            list.add(books_yishu[index]);
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
