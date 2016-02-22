package com.zhh.sweepview.view;



import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by zhanghe on 16/2/22.
 */

@SuppressLint("NewApi")
public class SweepView extends View {

    public SweepView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public SweepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SweepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private static final int COLOR_GREEN = 0xFF00FF00;
    private static final int COLOR_YELLOW = 0xFFFFFF00;
    private static final int COLOR_ORANGE = 0xFFF3660A;
    private static final int COLOR_RED = 0xFFFF0000;
    private static final int COLOR_Purple = 0xFFB200B5;
    private static final int COLOR_DEEP_PURPLE = 0xFF320032;
    private static final int COLOR_LIGHT_WHITE = 0xFFDCC9F0;

    private int mWidth = 300;
    private int mHeight = 300;
    private Paint mPaint;
    private RectF mRcf;
    private float mCenterX = 0;
    private float mCenterY = 0;
    private float mStrokeWidth = 40;

    private int mCurrentNum = 0;

    private float mRollCenterX = 0;
    private float mRollCenterY = 0;
    private float mRoollRadius = 8;

    float scale = 270f / 500f;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);

        mRcf = new RectF(0 + mStrokeWidth / 2, 0 + mStrokeWidth / 2, mWidth - mStrokeWidth / 2,
                mHeight - mStrokeWidth / 2);
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mRollCenterY = mStrokeWidth + mRoollRadius;
        mRollCenterX = mCenterX;
    }

    private static float mCurDegree = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        drawARcs(canvas);
        drawCenterText(canvas);
        drawTexts(canvas);
        drawRoll(canvas, mCurDegree);
    }

    private void drawARcs(Canvas canvas) {

        float startDegree = -90f;
        float addDegree = 1f;
        mPaint.setColor(COLOR_GREEN);
        canvas.drawArc(mRcf, startDegree, 35f * scale + addDegree, false, mPaint);
        mPaint.setColor(COLOR_YELLOW);
        canvas.drawArc(mRcf, startDegree + 35f * scale, 40f * scale + addDegree, false, mPaint);
        mPaint.setColor(COLOR_ORANGE);
        canvas.drawArc(mRcf, startDegree + 75f * scale, 40f * scale + addDegree, false, mPaint);
        mPaint.setColor(COLOR_RED);
        canvas.drawArc(mRcf, startDegree + 115f * scale, 35f * scale + addDegree, false, mPaint);
        mPaint.setColor(COLOR_Purple);
        canvas.drawArc(mRcf, startDegree + 150f * scale, 100f * scale + addDegree, false, mPaint);
        mPaint.setColor(COLOR_DEEP_PURPLE);
        canvas.drawArc(mRcf, startDegree + 250f * scale, 250f * scale + addDegree, false, mPaint);
    }

    private void drawTexts(Canvas canvas) {
        canvas.save();
        Paint mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(dpToPx(1));
        mLinePaint.setColor(COLOR_LIGHT_WHITE);

        for (int i = 0; i < 60; i++) {
            if (i >= 0 && i <= 45) {
                canvas.drawLine(mCenterX, mStrokeWidth, mCenterX, mStrokeWidth / 2, mLinePaint);
            }
            canvas.rotate(6, mCenterX, mCenterY);
        }

        canvas.drawArc(mRcf, -90, 270, false, mLinePaint);
    }

    private void drawCenterText(Canvas canvas) {
        Paint mTextPaint = new Paint();
        mTextPaint.setColor(COLOR_DEEP_PURPLE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(dpToPx(30));
        mTextPaint.setStyle(Paint.Style.FILL);
        int textWidth = (int) mTextPaint.measureText(mCurrentNum + "");
        canvas.drawText(mCurrentNum + "", mCenterX - textWidth / 2, mCenterY + dpToPx(30) / 2, mTextPaint);
    }


    private void drawRoll(Canvas canvas, float degree) {
        // canvas.save();
        Paint mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLUE);
        float x = (float) (Math.cos(Math.toRadians(degree - 90)) * (mCenterX - mStrokeWidth - mRoollRadius))
                + mCenterX;
        float y = (float) (Math.sin(Math.toRadians(degree - 90)) * (mCenterX - mStrokeWidth - mRoollRadius))
                + mCenterY;
        canvas.drawCircle(x, y, mRoollRadius, mCirclePaint);
    }

    private float mSetDegree = 0;

   /* public void setDegree(float degree) {
        mSetDegree = degree;
        if (isStart) {
            isStop = false;
        }

        startAnimation();
    }*/

    public void setValue(int value) {

        if (value < 0) {
            value = 0;
            mSetDegree = 0;
        } else if (value > 500) {
            mSetDegree = 270;
        } else if (value >= 0 && value <= 500) {
            mSetDegree = scale * value;
        }

        ValueAnimator textAnimator = ValueAnimator.ofInt(0, value);
        textAnimator.setDuration(1000);
        textAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentNum = Integer.parseInt(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        textAnimator.start();

        ValueAnimator degreeAnimator = ValueAnimator.ofFloat(0, mSetDegree);
        degreeAnimator.setDuration(1000);
        degreeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurDegree = Float.parseFloat(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        degreeAnimator.start();

    }

   /* private void startAnimation() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                float add = (mSetDegree - mCurDegree) / 10;
                isStart = true;
                while (mCurDegree <= mSetDegree) {
                    if (isStop) {
                        isStop = false;
                        isStart = false;
                        break;
                    }
                    mCurDegree += add;
                    postInvalidate();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        ).start();
    }*/

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
