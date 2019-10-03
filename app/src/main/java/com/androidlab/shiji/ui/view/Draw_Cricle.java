package com.androidlab.shiji.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Draw_Cricle extends View{

    Paint paint;


    public Draw_Cricle(Context context, AttributeSet attr) {
        super(context, null);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

//        实例化画笔对象
        paint = new Paint();
//        给画笔设置颜色
        paint.setColor(Color.WHITE);
//        设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
//        paint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆
        paint.setStrokeWidth(1);//设置画笔粗细

        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 15, paint);

    }
}
