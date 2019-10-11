package com.androidlab.shiji.activity.book_spread;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import com.androidlab.shiji.R;
import com.androidlab.shiji.bean.KeyWords;
import com.androidlab.shiji.ui.view.TextViewVertical;
import com.androidlab.shiji.utils.StaticVariable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book_Spread_Detail extends AppCompatActivity {

    private String BookText;
    private String keyword = "是";


//    private TextViewVertical tv_BookText;

    private TextView tv_BookText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_spread_detail);

        tv_BookText = findViewById(R.id.tv_BookText);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/STXINGKA.TTF");
        keyword = getIntent().getStringExtra("keyword");
//        BookText = getIntent().getStringExtra("book_spread_content");
        BookText = StaticVariable.content;
        System.out.println(BookText);
        setColor();
        tv_BookText.setTypeface(typeface);
    }

    /**
     * 文字变色工具方法
     */
    public void setColor(){
        SpannableString s = new SpannableString(BookText);
        keyword=escapeExprSpecialWord(keyword);
        BookText=escapeExprSpecialWord(BookText);
        if (BookText.contains(keyword)&&!TextUtils.isEmpty(keyword)){
            try {
                Pattern p = Pattern.compile(keyword);
                Matcher m = p.matcher(s);
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    // 绿色的字
                    s.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    // 红色背景
//                    s.setSpan(new BackgroundColorSpan(Color.DKGRAY), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    // 字体加粗
                    s.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }catch (Exception e){
                Log.e("Result_Fragment_3", "matcherSearchTitle: "+e.toString());
            }

            tv_BookText.setText(s);
        }
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return keyword
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }
}
