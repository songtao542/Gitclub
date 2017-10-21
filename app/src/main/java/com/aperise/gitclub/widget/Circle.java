package com.aperise.gitclub.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangsongtao on 2017/5/28.
 */

public class Circle extends View {

    private float mRadius = 0;
    private int mColor = 0;
    private Paint mPaint;

    public Circle(Context context) {
        super(context);
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setRadius(float r) {
        mRadius = r;
    }

    public void setColor(int c) {
        mColor = c;
    }

    private Paint getPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }
        mPaint.setColor(mColor);
        return mPaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRadius == 0) {
            mRadius = getWidth() < getHeight() ? getWidth() / 2 : getHeight() / 2;
            //mRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9, getContext().getResources().getDisplayMetrics());
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, getPaint());
    }
}
