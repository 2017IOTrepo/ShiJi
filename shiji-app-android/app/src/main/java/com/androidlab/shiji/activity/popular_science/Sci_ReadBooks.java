package com.androidlab.shiji.activity.popular_science;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidlab.shiji.R;
import com.androidlab.shiji.utils.FileUtils;
import com.bifan.txtreaderlib.bean.TxtMsg;
import com.bifan.txtreaderlib.interfaces.ILoadListener;
import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.main.TxtReaderView;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Sci_ReadBooks extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0x01;
    private String FilePath = Environment.getExternalStorageDirectory() + "/a/";
    private Boolean Permit = false;

    private TxtReaderView mTxtReaderView;

    private String BookName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);
//        if (CheckPermission()) {
//            Permit = true;
//        }
        mTxtReaderView = findViewById(R.id.txtReaderView);
        createFolder();
        CopyAssets();
        Intent i = getIntent();
        BookName = i.getStringExtra("BookName");
        mTxtReaderView.loadTxtFile(FilePath + BookName, new ILoadListener() {
            @Override
            public void onSuccess() {
                Log.e("read succeed", "onSuccess: "+BookName );
            }

            @Override
            public void onFail(TxtMsg txtMsg) {
                Log.e("read failed ", "onFail: "+BookName );

            }

            @Override
            public void onMessage(String message) {

            }
        });


    }



    //创建文件夹
    public   void createFolder() {
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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
//            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
//            String[] pros = {MediaStore.Files.FileColumns.DATA};
//            try {
//                Cursor cursor = managedQuery(uri, pros, null, null, null);
//                int actual_txt_column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                String path = cursor.getString(actual_txt_column_index);
//                Log.e("4555", "onActivityResult: " + path );
//
//            } catch (Exception e) {
//                Toast.makeText(this, "选择出错了", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }


//
//    private Boolean CheckPermission() {
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//            return false;
//        }
//        return true;
//    }


//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Permit = true;
//            } else {
//                // Permission Denied
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }


//    private Toast t;
//
//    private void toast(String msg) {
//        if (t != null) {
//            t.cancel();
//        }
//        t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//        t.show();
//    }

//
//    public void loadFile(String BookName) {
//
//        FileUtils fu = new FileUtils();
//
//        fu.createFolder();
//
//        fu.CopyAssets();
//
//        if (Permit) {
//            TxtConfig.saveIsOnVerticalPageMode(this,false);
//
//            if (TextUtils.isEmpty(FilePath) || !(new File(FilePath)).exists()) {
//                toast("文件不存在");
//            } else {
//                HwTxtPlayActivity.loadTxtFile(this, FilePath+BookName);
//            }
//        }
//    }

}
