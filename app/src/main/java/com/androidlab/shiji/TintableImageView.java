package com.androidlab.shiji;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


/**
 * https://gist.github.com/tylerchesley/5d15d859be4f3ce31213
 */
public class TintableImageView extends AppCompatImageView {

  private ColorStateList tint;

  public TintableImageView(Context context) {
    super(context);
  }

  public TintableImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context, attrs, 0);
  }

  public TintableImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init(context, attrs, defStyle);
  }

  private void init(Context context, AttributeSet attrs, int defStyle) {
    TypedArray a = context.obtainStyledAttributes(
        attrs, R.styleable.TintableImageView, defStyle, 0);
    tint = a.getColorStateList(
        R.styleable.TintableImageView_tint_f);
    a.recycle();
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    if (tint != null && tint.isStateful()) {
      updateTintColor();
    }
  }

  public void setColorFilter(ColorStateList tint) {
    this.tint = tint;
    super.setColorFilter(tint.getColorForState(getDrawableState(), 0));
  }

  private void updateTintColor() {
    int color = tint.getColorForState(getDrawableState(), 0);
    setColorFilter(color);
  }

}
