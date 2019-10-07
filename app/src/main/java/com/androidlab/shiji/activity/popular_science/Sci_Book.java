package com.androidlab.shiji.activity.popular_science;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.Sci_Book_Show;
import com.androidlab.shiji.ui.adapter.Book_RecyclerViewAdapater;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.victor.loading.book.BookLoading;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sci_Book extends AppCompatActivity {

    private RecyclerView recyclerView;
    int y;
    private List<Sci_Book_Show> list = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Book_RecyclerViewAdapater adapater;
    private ProgressBar progressBar;

    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private CardView cardView4;


    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";

    private LinearLayout itemdetail;


    private boolean flag = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        recyclerView = findViewById(R.id.rc_book);
        progressBar = findViewById(R.id.progress_bar);

        cardView1 = findViewById(R.id.cardview1);
        cardView2 = findViewById(R.id.cardview2);
        cardView3 = findViewById(R.id.cardview3);
        cardView4 = findViewById(R.id.cardview4);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        initBook();
        adapater = new Book_RecyclerViewAdapater(list, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapater);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sci_Book.this, Book_LiShi.class);
                startActivity(i);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sci_Book.this, Book_ShiGe.class);
                startActivity(i);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sci_Book.this, Book_YiShu.class);
                startActivity(i);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sci_Book.this, Book_YiXue.class);
                startActivity(i);
            }
        });
        adapater.setOnclick(new Book_RecyclerViewAdapater.ClickInterface() {
            @Override
            public void onItemClick(final View view, final int position) {
                Log.e("DoitemClick", "onItemClick: " );
                final String BookName = list.get(position).getBook_Title();
                createFolder();
                CopyAssets();
                // 使用Handler进行延时  并返回主线程
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        HwTxtPlayActivity.loadTxtFile(view.getContext(),FilePath+BookName+".txt");//传递一个文件路径
                        Toast.makeText(Sci_Book.this, BookName, Toast.LENGTH_SHORT).show();
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
        mActionBar.setTitle("中国古籍");

        flag = false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            TranslateAnimation animation = new TranslateAnimation(0, 0, -y, 0);
            animation.setDuration(520);
            animation.setFillAfter(true);
            recyclerView.startAnimation(animation);
        }
    }


    private Sci_Book_Show[] book_shows = {
            new Sci_Book_Show(R.drawable.yiming2, "八美图", "佚名", "描写宋代杭州人柳树春经历的悲欢离合故事。", "艺藏"),
            new Sci_Book_Show(R.drawable.penghaozi, "定鼎奇闻", "蓬蒿子", "通过奇闻异事反映清政府的现实。", "艺藏"),
            new Sci_Book_Show(R.drawable.caoxueqin, "红楼梦", "曹雪芹", "是一部具有世界影响力的人情小说，举世公认的中国古典小说巅峰之作。", "艺藏"),
            new Sci_Book_Show(R.drawable.wujingzi, "儒林外史", "吴敬梓", "代表着中国古代讽刺小说的高峰，它开创了以小说直接评价现实生活的范例。", "艺藏"),
            new Sci_Book_Show(R.drawable.lishizhen, "本草纲目", "李时珍", "是述本草要籍与药性理论的著作。", "医藏"),
            new Sci_Book_Show(R.drawable.bianque, "八十一难经", "扁鹊", "是中医现存较早的经典著作。", "医藏"),
            new Sci_Book_Show(R.drawable.zhuzhenheng, "丹溪心法", "朱震亨", "是一部综合性医书，共五卷（一作三卷）。", "医藏"),
            new Sci_Book_Show(R.drawable.qikong, "外科大成", "祁坤", "是中医外科重要参考书。", "医藏"),
            new Sci_Book_Show(R.drawable.shiji, "史记", "司马迁", "中国历史上第一部纪传体通史", "史藏"),
            new Sci_Book_Show(R.drawable.xuanzang, "大唐西域记", "玄奘", "是由唐代玄奘口述、辩机编的地理史籍，成书于唐贞观二十年。", "史藏"),
            new Sci_Book_Show(R.drawable.bangu, "汉书", "班固", "是中国第一部纪传体断代史，“二十四史”之一。", "史藏"),
            new Sci_Book_Show(R.drawable.simaguang, "资治通鉴", "司马光", "是一部多卷本编年体史书，共294卷。", "史藏"),
            new Sci_Book_Show(R.drawable.zhangheng, "二京赋", "张衡", "是张衡的代表作之一，包括《西京赋》、《东京赋》两篇。", "诗藏"),
            new Sci_Book_Show(R.drawable.baipu, "白朴元曲集", "白朴", "是白朴创作的其他类书籍。", "诗藏")

    };

    public  void  initBook(){
         list.clear();
         for (int i = 0; i < 5; i ++){
             Random ra = new Random();
             int index = ra.nextInt(book_shows.length);
             list.add(book_shows[index]);
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
            Log.i("4444444", "createFolder: ");
        }else{
            Log.i("445555554", "createFolder: ");
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
