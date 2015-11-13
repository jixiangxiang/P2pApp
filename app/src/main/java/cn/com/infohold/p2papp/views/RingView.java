package cn.com.infohold.p2papp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.com.infohold.p2papp.R;


/**
 * Created by eric on 2015/6/10.
 */
public class RingView extends View implements View.OnClickListener {
    private float radius;
    private Integer centerColor;
    private Integer backgroudColor;
    private int angle;
    private Float strokeWidth;
    private Boolean isDynamic = true;
    int i = 0;

    public RingView(Context context) {
        super(context);
    }

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RingView, 0, 0);

        try {
            radius = a.getDimension(R.styleable.RingView_ringViewRadius, 50.0f);
            centerColor = a.getColor(R.styleable.RingView_centerColor, getResources().getColor(android.R.color.white));
            backgroudColor = a.getColor(R.styleable.RingView_bgColor, getResources().getColor(android.R.color.transparent));
            angle = a.getInteger(R.styleable.RingView_angle, 360);
            strokeWidth = a.getDimension(R.styleable.RingView_ringViewStrokeWidth, 0.5f);
            isDynamic = a.getBoolean(R.styleable.RingView_isDynamic, false);
        } catch (Exception e) {
            Log.e("RingView", " attr excepiton is " + e.getLocalizedMessage());
        } finally {

            a.recycle();

        }
    }

    public RingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setCenterColor(Integer centerColor) {
        this.centerColor = centerColor;
    }

    public void setBackgroudColor(Integer backgroudColor) {
        this.backgroudColor = backgroudColor;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 首先定义一个paint
        Paint paint = new Paint();
        paint.setColor(getBackgroudColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(getStrokeWidth());
        canvas.drawCircle(getRadius(), getRadius(), getRadius() - paint.getStrokeWidth(), paint);
        Paint paintArc = new Paint();
        paintArc.setColor(getCenterColor());
        paintArc.setStyle(Paint.Style.STROKE);
        paintArc.setStrokeWidth(getStrokeWidth());
        paintArc.setAntiAlias(true);
        RectF rectF = new RectF(paint.getStrokeWidth(), paint.getStrokeWidth(), getRadius() * 2 - paint.getStrokeWidth(), getRadius() * 2 - paint.getStrokeWidth());
        if (getIsDynamic()) {
            canvas.drawArc(rectF, -90, i, false, paintArc);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    if (i <= getAngle()) {
                        i += 3;
                        invalidate();
                    }
                }
            });
        } else {
            canvas.drawArc(rectF, -90, getAngle(), false, paintArc);
        }
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setIsDynamic(Boolean isDynamic) {
        this.isDynamic = isDynamic;
    }

    public void setStrokeWidth(Float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Float getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    public void onClick(View view) {

    }

    public float getRadius() {
        return radius;
    }

    public Integer getCenterColor() {
        return centerColor;
    }

    public Integer getBackgroudColor() {
        return backgroudColor;
    }

    public int getAngle() {
        return angle;
    }

    public Boolean getIsDynamic() {
        return isDynamic;
    }
}
