package cn.com.infohold.p2papp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by eric on 2015/10/23.
 */
public class TouchTestView extends LinearLayout {
    public static String TAG = TouchTestView.class.getName();

    public TouchTestView(Context context) {
        super(context);
    }

    public TouchTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                isIntercept = false;
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
            default:
                break;
        }
        Log.e(TAG, "onInterceptTouchEvent is " + isIntercept);
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isTouche = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "MotionEvent.ACTION_DOWN");
                isTouche = true;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "MotionEvent.ACTION_MOVE");
                isTouche = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "MotionEvent.ACTION_UP");
                isTouche = false;
                break;
            default:
                break;
        }
        return isTouche;
    }
}
