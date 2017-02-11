package mytapp.xmz.com.mysteriousapp.modules.qiwen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/11/16.
 */
public class MyScrollview extends ScrollView {
    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(listener!=null){
            listener.onscrollChanged(l,t,oldl,oldt);
        }
    }

    public interface OnMyScrollChangedListener{
        void onscrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    public void setOnMyScrollChangedListener(OnMyScrollChangedListener listener) {
        this.listener = listener;
    }

    private OnMyScrollChangedListener listener;
}
