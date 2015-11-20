package cn.com.infohold.p2papp.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by eric on 2015/11/17.
 */
public class WrapScrollView extends ScrollView {
    public WrapScrollView(Context context) {
        super(context);
    }

    public WrapScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec;

        if (getLayoutParams().height == ViewPager.LayoutParams.WRAP_CONTENT) {
            // The great Android "hackatlon", the love, the magic.
            // The two leftmost bits in the height measure spec have
            // a special meaning, hence we can't use them to describe height.
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        } else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }

        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
