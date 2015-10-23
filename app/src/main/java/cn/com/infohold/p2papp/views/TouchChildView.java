package cn.com.infohold.p2papp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by eric on 2015/10/23.
 */
public class TouchChildView extends View {
    private static final String TAG = TouchChildView.class.getName();

    public TouchChildView(Context context) {
        super(context);
    }

    public TouchChildView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isOnTouch = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "MotionEvent.ACTION_DOWN");
                isOnTouch = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "MotionEvent.ACTION_MOVE");
                isOnTouch = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "MotionEvent.ACTION_UP");
                isOnTouch = false;
                break;
            default:
                break;
        }
        return isOnTouch;
    }
}
