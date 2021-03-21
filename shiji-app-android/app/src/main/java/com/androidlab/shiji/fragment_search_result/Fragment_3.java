package com.androidlab.shiji.fragment_search_result;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.androidlab.shiji.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//古籍分布
public class  Fragment_3 extends Fragment {
    private View view;

    public static Fragment_3 newInstance() {
        Bundle args = new Bundle();
        Fragment_3 fragment = new Fragment_3();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        view = inflater.inflate(R.layout.fragment_3, container, false);
        return view;
    }

    /**
     * 文字变色工具类
     */
    public static class KeywordUtil {

        /**
         * 关键字高亮变色
         *
         * @param color   变化的色值
         * @param text    文字
         * @param keyword 文字中的关键字
         * @return 结果SpannableString
         */
        public static SpannableString matcherSearchTitle(int color, String text, String keyword) {
            SpannableString s = new SpannableString(text);
            keyword = escapeExprSpecialWord(keyword);
            text = escapeExprSpecialWord(text);
            if (text.contains(keyword) && !TextUtils.isEmpty(keyword)) {
                try {
                    Pattern p = Pattern.compile(keyword);
                    Matcher m = p.matcher(s);
                    while (m.find()) {
                        int start = m.start();
                        int end = m.end();
                        s.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } catch (Exception e) {
                    Log.e("Result_Fragment_3", "matcherSearchTitle: " + e.toString());
                }
            }
            return s;
        }

        /**
         * 转义正则特殊字符 （$()*+.[]?\^{},|）
         *
         * @param keyword
         * @return keyword
         */
        public static String escapeExprSpecialWord(String keyword) {
            if (!TextUtils.isEmpty(keyword)) {
                String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
                for (String key : fbsArr) {
                    if (keyword.contains(key)) {
                        keyword = keyword.replace(key, "\\" + key);
                    }
                }
            }
            return keyword;
        }
    }

}
